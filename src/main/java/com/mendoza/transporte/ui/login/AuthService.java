package com.mendoza.transporte.ui.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mendoza.transporte.auth.AuthResponse;
import com.mendoza.transporte.auth.LoginRequest;

import java.io.OutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AuthService {
    private final String baseUrl = "https://transporte-ecug.onrender.com/auth/login";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AuthResponse login(LoginRequest request) throws Exception {
        URL url = new URL(baseUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            objectMapper.writeValue(os, request);
        }

        InputStream responseStream = conn.getInputStream();
        return objectMapper.readValue(responseStream, AuthResponse.class);
    }
}
