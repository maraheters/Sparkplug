package com.sparkplug.auth.application.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sparkplug.auth.application.serializer.LoginCommandDeserializer;
import com.sparkplug.auth.domain.vo.RawPassword;
import com.sparkplug.auth.domain.vo.Username;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@JsonDeserialize(using = LoginCommandDeserializer.class)
public record LoginCommand(
        @Schema(type = "string", example = "username") @NotNull Username username,
        @Schema(type = "string", example = "pasSw$ord788") @NotNull RawPassword password) {
}
