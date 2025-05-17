package com.mendoza.transporte.ui.choferes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;


public class ChoferService {
    private final String baseUrl = "https://transporte-ecug.onrender.com/api/chofer";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<ChoferResponse> obtenerTodos(String token) {
        try {
            URL url = new URL(baseUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setRequestProperty("Accept", "application/json");
            InputStream response = connection.getInputStream();
            return objectMapper.readValue(
                    response,
                    new TypeReference<List<ChoferResponse>>() {
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}

