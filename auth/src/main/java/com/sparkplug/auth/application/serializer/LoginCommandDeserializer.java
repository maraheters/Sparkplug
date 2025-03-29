package com.sparkplug.auth.application.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.sparkplug.auth.application.dto.LoginCommand;
import com.sparkplug.auth.domain.vo.RawPassword;
import com.sparkplug.auth.domain.vo.Username;

import java.io.IOException;

public class LoginCommandDeserializer extends JsonDeserializer<LoginCommand> {
    @Override
    public LoginCommand deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        // Extract values as plain strings
        var usernameValue = node.has("username") ? node.get("username").asText() : null;
        var passwordValue = node.has("password") ? node.get("password").asText() : null;

        // Convert to Value Objects
        var username = new Username(usernameValue);
        var password = new RawPassword(passwordValue);

        return new LoginCommand(username, password);
    }
}
