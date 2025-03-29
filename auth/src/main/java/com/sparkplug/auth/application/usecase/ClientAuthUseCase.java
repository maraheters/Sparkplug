package com.sparkplug.auth.application.usecase;

import com.sparkplug.auth.application.dto.AuthResponse;
import com.sparkplug.auth.application.dto.ClientRegisterCommand;

public interface ClientAuthUseCase {

    AuthResponse register(ClientRegisterCommand command);
}
