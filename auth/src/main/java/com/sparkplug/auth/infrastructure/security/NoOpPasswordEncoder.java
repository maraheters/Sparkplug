package com.sparkplug.auth.infrastructure.security;

import com.sparkplug.auth.domain.contract.PasswordHasher;
import com.sparkplug.auth.domain.vo.RawPassword;
import org.springframework.security.crypto.password.PasswordEncoder;

public class NoOpPasswordEncoder implements PasswordEncoder, PasswordHasher {

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        var rawPasswordString = rawPassword.toString();

        return rawPasswordString.equals(encodedPassword);
    }

    @Override
    public String hashPassword(RawPassword rawPassword) {
        return encode(rawPassword.value());
    }
}
