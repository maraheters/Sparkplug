package com.sparkplug.auth.infrastructure.security.custom;

import org.springframework.security.crypto.password.PasswordEncoder;

public class NoOpPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        var rawPasswordString = rawPassword.toString();

        return rawPasswordString.equals(encodedPassword);
    }
}
