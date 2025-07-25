package com.hamuksoft.emilytalks.modules.conversation.infrastructure.client;

import com.hamuksoft.emilytalks.modules.conversation.infrastructure.config.AgentContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class DeepseekClient {

    @Value("${deepseek.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final AgentContext agentContext;

    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";

    public DeepseekClient(RestTemplate restTemplate, AgentContext agentContext) {
        this.restTemplate = restTemplate;
        this.agentContext = agentContext;
    }
    public String getFeedBack(){
        String feedbackPromptBase = "Hello I want to get some feedback in spanish and also in english (separated) about ALL THE CONVERSATION so far. ";
        String feedbackPoints = "Every grammar mistakes, vocabulary mistakes, some suggestions about have more natural sentences, show what topics should I practice more";
        String fullFeedbackPrompt = feedbackPromptBase + "I want some feedback in the following points: " + feedbackPoints;
        String extra = "además me gustaria que la respuesta NO TENGA FORMATO como los dobles asteriscos para resaltar el contenido y similares está bien los bullets y los numeros";
        fullFeedbackPrompt += " " + extra;
        return(this.sendMessage(fullFeedbackPrompt));
    }

    public String sendMessage(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<Map<String, String>> messages = new ArrayList<>();

        // Prompt del sistema (rol + tema)
        String systemPrompt = agentContext.getSystemPrompt();
        if (systemPrompt != null && !systemPrompt.isBlank()) {
            messages.add(Map.of("role", "system", "content", systemPrompt));
        }

        // Mensaje del usuario
        messages.add(Map.of("role", "user", "content", message));

        Map<String, Object> requestBody = Map.of(
                "model", "deepseek/deepseek-chat-v3-0324:free",
                "messages", messages
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(API_URL, entity, Map.class);

        Map<String, Object> choices = ((List<Map<String, Object>>) response.getBody().get("choices")).get(0);
        Map<String, Object> messageObj = (Map<String, Object>) choices.get("message");
        return messageObj.get("content").toString();
    }}

