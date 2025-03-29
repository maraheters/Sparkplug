package com.sparkplug.auth.application.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.sparkplug.auth.application.dto.ClientRegisterCommand;
import com.sparkplug.auth.domain.vo.Email;
import com.sparkplug.auth.domain.vo.PhoneNumber;
import com.sparkplug.auth.domain.vo.RawPassword;
import com.sparkplug.auth.domain.vo.Username;

import java.io.IOException;

public class ClientRegisterCommandDeserializer extends JsonDeserializer<ClientRegisterCommand> {

    @Override
    public ClientRegisterCommand deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        // Extract values as plain strings
        var usernameValue = node.has("username") ? node.get("username").asText() : null;
        var emailValue = node.has("email") ? node.get("email").asText() : null;
        var phoneNumberValue = node.has("phoneNumber") ? node.get("phoneNumber").asText() : null;
        var passwordValue = node.has("password") ? node.get("password").asText() : null;

        // Convert to Value Objects
        var username = new Username(usernameValue);
        var email = emailValue != null ? new Email(emailValue) : null;
        var phoneNumber = phoneNumberValue != null ? new PhoneNumber(phoneNumberValue) : null;
        var password = new RawPassword(passwordValue);

        return new ClientRegisterCommand(username, email, phoneNumber, password);
    }
}
