package com.sparkplug.auth.application.usecase;

import com.sparkplug.auth.application.dto.LoginCommand;
import com.sparkplug.auth.application.dto.AuthResponse;
import com.sparkplug.auth.security.service.JwtService;
import com.sparkplug.auth.security.user.SparkplugUserDetails;
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
    public AuthResponse execute(LoginCommand loginCommand) {

        var username = loginCommand.username();
        var password = loginCommand.password();

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username.value(), password.value()));

        var token = jwtService.generateToken(username.value());

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
