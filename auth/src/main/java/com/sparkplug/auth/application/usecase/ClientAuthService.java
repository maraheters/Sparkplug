package com.sparkplug.auth.application.usecase;

import com.sparkplug.auth.application.dto.AuthResponse;
import com.sparkplug.auth.application.dto.ClientRegisterCommand;
import com.sparkplug.auth.domain.entity.Client;
import com.sparkplug.auth.domain.entity.ClientAuthority;
import com.sparkplug.auth.domain.enums.ClientRole;
import com.sparkplug.auth.domain.repository.ClientAuthoritiesRepository;
import com.sparkplug.auth.domain.repository.ClientsRepository;
import com.sparkplug.auth.domain.repository.UsersRepository;
import com.sparkplug.auth.security.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ClientAuthService implements ClientAuthUseCase {

    private final ClientsRepository clientsRepository;
    private final ClientAuthoritiesRepository authoritiesRepository;
    private final UsersRepository usersRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientAuthService(
            ClientsRepository clientsRepository,
            ClientAuthoritiesRepository authoritiesRepository,
            UsersRepository usersRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder) {
        this.clientsRepository = clientsRepository;
        this.authoritiesRepository = authoritiesRepository;
        this.usersRepository = usersRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponse register(ClientRegisterCommand command) {
        validateUniqueness(command);

        var passwordHash = passwordEncoder.encode(command.password().value());
        var client = createClient(command, passwordHash);

        var id = clientsRepository.save(client).getId();
        var token = jwtService.generateToken(command.username().value());

        return new AuthResponse(
                id,
                command.username().value(),
                command.email().value(),
                command.phoneNumber().value(),
                token,
                Stream.of(ClientRole.CLIENT_BASIC)
                        .map(ClientRole::toString)
                        .toList()
        );
    }

    private void validateUniqueness(ClientRegisterCommand command) {
        if (usersRepository.existsByUsername(command.username())) {
            throw new IllegalArgumentException("Username is already taken: " + command.username());
        }
        if (command.email() != null && usersRepository.existsByEmail(command.email())) {
            throw new IllegalArgumentException("Email is already taken: " + command.email());
        }
        if (command.phoneNumber() != null && usersRepository.existsByPhoneNumber(command.phoneNumber())) {
            throw new IllegalArgumentException("Phone number is already taken: " + command.phoneNumber());
        }
    }

    private Client createClient(ClientRegisterCommand command, String passwordHash) {
        var basicRole = authoritiesRepository.findByName(ClientRole.CLIENT_BASIC.toString())
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + ClientRole.CLIENT_BASIC));
        List<ClientAuthority> roles = List.of(basicRole);

        if (command.emailAndPhoneNumber())
            return Client.createWithPhoneNumberAndEmail(command.username(), command.phoneNumber(), command.email(), passwordHash, roles);
        else if (command.emailOnly())
            return Client.createWithEmail(command.username(), command.email(), passwordHash, roles);
        else
            return Client.createWithPhoneNumber(command.username(), command.phoneNumber(), passwordHash, roles);
    }


}
