package com.devpulse.ai.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.devpulse.ai.dto.AIAnalysisResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;

@Component
public class GeminiProvider implements AIProvider {

    private final ObjectMapper objectMapper;

    @Value("${gemini.api.key}")
    private String apiKey;
    
    @Value("${gemini.model}")
    private String model;

    public GeminiProvider(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public AIAnalysisResponse analyze(String prompt) {

        try {

            Client client = Client.builder()
                    .apiKey(apiKey)
                    .build();

            GenerateContentConfig config =
                    GenerateContentConfig.builder()
                            .responseMimeType("application/json")
                            .build();

            		GenerateContentResponse response =
                    client.models.generateContent(
                            model,
                            prompt,
                            config
                    );

            String json = response.text();
            System.out.println("========== GEMINI RESPONSE ==========");
            System.out.println(json);
            System.out.println("=====================================");

            return objectMapper.readValue(
                    json,
                    AIAnalysisResponse.class
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    e.getMessage(),
                    e
            );
        }
    }
}