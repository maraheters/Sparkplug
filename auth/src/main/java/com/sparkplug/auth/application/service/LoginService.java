package com.sparkplug.auth.application.service;

import com.sparkplug.auth.application.dto.request.LoginRequest;
import com.sparkplug.auth.application.dto.response.AuthResponse;
import com.sparkplug.auth.application.usecase.LoginUseCase;
import com.sparkplug.auth.application.security.service.JwtService;
import com.sparkplug.auth.application.security.user.SparkplugUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginUseCase {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public LoginService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        var username = request.username();
        var password = request.password();

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        var token = jwtService.generateToken(username);

        var userDetails = (SparkplugUserDetails) authentication.getPrincipal();

        var authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::toString)
                .toList();

        return new AuthResponse(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getPhoneNumber(),
                token,
                authorities
        );
    }
}
