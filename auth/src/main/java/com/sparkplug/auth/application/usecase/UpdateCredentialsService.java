package com.sparkplug.auth.application.usecase;

import com.sparkplug.auth.application.dto.request.UpdateEmailRequest;
import com.sparkplug.auth.application.dto.request.UpdatePasswordRequest;
import com.sparkplug.auth.application.dto.request.UpdatePhoneNumberRequest;
import com.sparkplug.auth.application.dto.request.UpdateUsernameRequest;
import com.sparkplug.auth.domain.contract.PasswordHasher;
import com.sparkplug.auth.domain.entity.User;
import com.sparkplug.auth.domain.repository.UsersRepository;
import com.sparkplug.auth.domain.vo.Email;
import com.sparkplug.auth.domain.vo.PhoneNumber;
import com.sparkplug.auth.domain.vo.RawPassword;
import com.sparkplug.auth.domain.vo.Username;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateCredentialsService implements UpdateCredentialsUseCase {

    private final UsersRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UpdateCredentialsService(UsersRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void updatePassword(Long userId, UpdatePasswordRequest request) {
        var user = findById(userId);

        user.changePassword(new RawPassword(request.oldPassword()), new RawPassword(request.newPassword()), (PasswordHasher) passwordEncoder);

        repository.save(user);
    }

    @Override
    @Transactional
    public void updateEmail(Long userId, UpdateEmailRequest request) {
        var user = findById(userId);
        user.changeEmail(new Email(request.email()));

        repository.save(user);
    }

    @Override
    @Transactional
    public void updatePhoneNumber(Long userId, UpdatePhoneNumberRequest request) {
        var user = findById(userId);
        user.changePhoneNumber(new PhoneNumber(request.phoneNumber()));

        repository.save(user);
    }

    @Override
    @Transactional
    public void updateUsername(Long userId, UpdateUsernameRequest request) {
        var user = findById(userId);
        user.changeUsername(new Username(request.username()));

        repository.save(user);
    }

    private User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found by id: " + id));
    }
}
