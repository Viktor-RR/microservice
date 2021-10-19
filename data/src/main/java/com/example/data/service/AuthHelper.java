package com.example.data.service;

import com.example.data.entity.AuthenticationUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class AuthHelper {

    private final ObjectMapper objectMapper;

    public AuthHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @SneakyThrows
    public String getRole(String token) {
        final byte[] decode = Base64.getDecoder().decode(token.getBytes(StandardCharsets.UTF_8));
        final String data = new String(decode);
        final AuthenticationUser authenticationUser = objectMapper.readValue(data, AuthenticationUser.class);
        return authenticationUser.getRole();

    }
    @SneakyThrows
    public Long getId(String token) {
        final byte[] decode = Base64.getDecoder().decode(token.getBytes(StandardCharsets.UTF_8));
        final String data = new String(decode);
        final AuthenticationUser authenticationUser = objectMapper.readValue(data, AuthenticationUser.class);
        return authenticationUser.getId();
    }
}
