package com.sparkplug.auth.application.usecase;

import com.sparkplug.auth.application.dto.request.ClientRegisterPhoneNumberRequest;
import com.sparkplug.auth.application.dto.response.AuthResponse;
import com.sparkplug.auth.application.dto.request.ClientRegisterEmailRequest;
import com.sparkplug.auth.domain.contract.PasswordHasher;
import com.sparkplug.auth.domain.entity.Client;
import com.sparkplug.auth.domain.entity.ClientAuthority;
import com.sparkplug.auth.domain.enums.ClientRole;
import com.sparkplug.auth.domain.repository.ClientAuthoritiesRepository;
import com.sparkplug.auth.domain.repository.ClientsRepository;
import com.sparkplug.auth.domain.repository.UsersRepository;
import com.sparkplug.auth.domain.vo.Email;
import com.sparkplug.auth.domain.vo.PhoneNumber;
import com.sparkplug.auth.domain.vo.RawPassword;
import com.sparkplug.auth.domain.vo.Username;
import com.sparkplug.auth.infrastructure.security.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Predicate;

@Service
public class ClientRegisterService implements ClientRegisterUseCase {

    private final ClientsRepository clientsRepository;
    private final ClientAuthoritiesRepository authoritiesRepository;
    private final UsersRepository usersRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientRegisterService(
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
    @Transactional
    public AuthResponse registerWithEmail(ClientRegisterEmailRequest request) {
        var username = new Username(request.username());
        var email = new Email(request.email());
        var password = new RawPassword(request.password());

        validateUniqueness("Username", username, usersRepository::existsByUsername);
        validateUniqueness("Email", email, usersRepository::existsByEmail);

        var client = createClient(username, email, null, password);

        var id = clientsRepository.save(client).getId();
        var token = jwtService.generateToken(client.getUsername().value());

        return new AuthResponse(
                id,
                client.getUsername().value(),
                client.getEmail().value(),
                "",
                token,
                client.getAuthorities()
        );
    }

    @Override
    @Transactional
    public AuthResponse registerWithPhoneNumber(ClientRegisterPhoneNumberRequest request) {
        var username = new Username(request.username());
        var phoneNumber = new PhoneNumber(request.phoneNumber());
        var password = new RawPassword(request.password());

        validateUniqueness("Username", username, usersRepository::existsByUsername);
        validateUniqueness("Phone number", phoneNumber, usersRepository::existsByPhoneNumber);

        var client = createClient(username, null, phoneNumber, password);

        var id = clientsRepository.save(client).getId();
        var token = jwtService.generateToken(client.getUsername().value());

        return new AuthResponse(
                id,
                client.getUsername().value(),
                "",
                client.getPhoneNumber().value(),
                token,
                client.getAuthorities()
        );
    }

    private Client createClient(Username username, Email email, PhoneNumber phoneNumber, RawPassword password) {
        var basicRole = authoritiesRepository.findByName(ClientRole.CLIENT_BASIC.toString())
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + ClientRole.CLIENT_BASIC));
        List<ClientAuthority> roles = List.of(basicRole);

        var hasher = (PasswordHasher) passwordEncoder;

        if (email != null) {
            return Client.createWithEmail(username, email, password, roles, hasher);
        }
        return Client.createWithPhoneNumber(username, phoneNumber, password, roles, hasher);
}

    private <T> void validateUniqueness(String fieldName, T value, Predicate<T> existsCheck) {
        if (existsCheck.test(value)) {
            throw new IllegalArgumentException(fieldName + " is already taken: " + value);
        }
    }


}
