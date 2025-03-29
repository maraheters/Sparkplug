package com.sparkplug.auth.application.usecase;

import com.sparkplug.auth.application.dto.LoginCommand;
import com.sparkplug.auth.application.dto.AuthResponse;

public interface LoginUseCase {
    AuthResponse execute(LoginCommand loginCommand);
}
