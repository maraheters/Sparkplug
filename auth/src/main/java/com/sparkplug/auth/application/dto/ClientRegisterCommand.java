package com.sparkplug.auth.application.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sparkplug.auth.application.serializer.ClientRegisterCommandDeserializer;
import com.sparkplug.auth.domain.vo.Email;
import com.sparkplug.auth.domain.vo.PhoneNumber;
import com.sparkplug.auth.domain.vo.RawPassword;
import com.sparkplug.auth.domain.vo.Username;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@JsonDeserialize(using = ClientRegisterCommandDeserializer.class)
public record ClientRegisterCommand(
        @Schema(type = "string", example = "username") Username username,
        @Schema(type = "string", example = "email@email.com") Email email,
        @Schema(type = "string", example = "+70000000000") PhoneNumber phoneNumber,
        @Schema(type = "string", example = "pasSw$ord788") RawPassword password
) {

    public ClientRegisterCommand(
            @NotNull Username username, Email email, PhoneNumber phoneNumber, @NotNull RawPassword password) {

        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;

        if (!isValid())
            throw new IllegalArgumentException("Must provide at least email or phone number.");

    }

    public boolean emailAndPhoneNumber() {
        return email != null && phoneNumber != null;
    }

    public boolean emailOnly() {
        return email != null && phoneNumber == null;
    }

    public boolean phoneNumberOnly() {
        return email == null && phoneNumber != null;
    }

    private boolean isValid() {
        return !(email == null && phoneNumber == null);
    }
}