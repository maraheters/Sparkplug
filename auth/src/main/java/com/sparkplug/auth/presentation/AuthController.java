package com.sparkplug.auth.presentation;

import com.sparkplug.auth.application.dto.AuthResponse;
import com.sparkplug.auth.application.dto.ClientRegisterCommand;
import com.sparkplug.auth.application.dto.LoginCommand;
import com.sparkplug.auth.application.usecase.ClientAuthUseCase;
import com.sparkplug.auth.application.usecase.LoginUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final ClientAuthUseCase clientAuthUseCase;

    @Autowired
    public AuthController(LoginUseCase loginUseCase, ClientAuthUseCase clientAuthUseCase) {
        this.loginUseCase = loginUseCase;
        this.clientAuthUseCase = clientAuthUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginCommand command) {
        return ResponseEntity.ok(
                loginUseCase.execute(command));
    }

    @PostMapping("/clients/register")
    public ResponseEntity<AuthResponse> register(@RequestBody ClientRegisterCommand command) {
        return ResponseEntity.ok(
                clientAuthUseCase.register(command));
    }
}
