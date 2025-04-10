package com.sparkplug.auth.application.service;

import com.sparkplug.auth.application.dto.request.AdminRegisterEmailRequest;
import com.sparkplug.auth.application.dto.request.AdminRegisterPhoneNumberRequest;
import com.sparkplug.auth.application.dto.response.AuthResponse;
import com.sparkplug.auth.application.repository.AdminAuthoritiesRepository;
import com.sparkplug.auth.application.repository.AdminsRepository;
import com.sparkplug.auth.application.repository.UsersRepository;
import com.sparkplug.auth.application.usecase.AdminRegisterUseCase;
import com.sparkplug.auth.domain.contract.PasswordHasher;
import com.sparkplug.auth.domain.entity.Admin;
import com.sparkplug.auth.domain.vo.Email;
import com.sparkplug.auth.domain.vo.PhoneNumber;
import com.sparkplug.auth.domain.vo.RawPassword;
import com.sparkplug.auth.domain.vo.Username;
import com.sparkplug.auth.application.security.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Predicate;

@Service
public class AdminRegisterService implements AdminRegisterUseCase {

    private final AdminsRepository adminsRepository;
    private final AdminAuthoritiesRepository authoritiesRepository;
    private final UsersRepository usersRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UniquenessValidator uniquenessValidator = new UniquenessValidator();

    @Autowired
    public AdminRegisterService(
            AdminsRepository adminsRepository,
            AdminAuthoritiesRepository authoritiesRepository,
            UsersRepository usersRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder) {

        this.adminsRepository = adminsRepository;
        this.authoritiesRepository = authoritiesRepository;
        this.usersRepository = usersRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public AuthResponse registerWithEmail(AdminRegisterEmailRequest request) {
        var username = new Username(request.username());
        var email = new Email(request.email());
        var password = new RawPassword(request.password());
        var roles = request.roles();

        uniquenessValidator.validateUniqueness("Username", username, usersRepository::existsByUsername);
        uniquenessValidator.validateUniqueness("Email", email, usersRepository::existsByEmail);

        var admin = createAdmin(username, email, null, password, roles);
        var id = adminsRepository.save(admin).getId();
        var token = jwtService.generateToken(admin.getUsername().value());

        return new AuthResponse(
                id,
                admin.getUsername().value(),
                admin.getEmail().value(),
                "",
                token,
                admin.getAuthorities()
        );
    }

    @Override
    @Transactional
    public AuthResponse registerWithPhoneNumber(AdminRegisterPhoneNumberRequest request) {
        var username = new Username(request.username());
        var phoneNumber = new PhoneNumber(request.phoneNumber());
        var password = new RawPassword(request.password());
        var roles = request.roles();

        uniquenessValidator.validateUniqueness("Username", username, usersRepository::existsByUsername);
        uniquenessValidator.validateUniqueness("Phone number", phoneNumber, usersRepository::existsByPhoneNumber);

        var admin = createAdmin(username, null, phoneNumber, password, roles);
        var id = adminsRepository.save(admin).getId();
        var token = jwtService.generateToken(admin.getUsername().value());

        return new AuthResponse(
                id,
                admin.getUsername().value(),
                "",
                admin.getPhoneNumber().value(),
                token,
                admin.getAuthorities()
        );
    }

    private Admin createAdmin(Username username, Email email, PhoneNumber phoneNumber, RawPassword password, List<String> roles) {
        var authorities = authoritiesRepository.findAllByNameIn(roles);
        var hasher = (PasswordHasher) passwordEncoder;

        if (email != null) {
            return Admin.createWithEmail(username, email, password, authorities, hasher);
        }
        return Admin.createWithPhoneNumber(username, phoneNumber, password, authorities, hasher);
    }
}
