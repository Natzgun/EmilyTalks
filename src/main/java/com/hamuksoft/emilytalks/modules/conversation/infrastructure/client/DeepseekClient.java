package com.hamuksoft.emilytalks.modules.conversation.infrastructure.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class DeepseekClient {

    @Value("${deepseek.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";

    public DeepseekClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String sendMessage(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = Map.of(
                "model", "deepseek/deepseek-chat-v3-0324:free",
                "messages", new Object[] {
                        Map.of("role", "user", "content", message)
                }
        );

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(API_URL, requestEntity, Map.class);

        // Extraer la respuesta del modelo
        Map<String, Object> choices = ((java.util.List<Map<String, Object>>) response.getBody().get("choices")).get(0);
        Map<String, Object> messageObj = (Map<String, Object>) choices.get("message");
        return messageObj.get("content").toString();
    }
}

