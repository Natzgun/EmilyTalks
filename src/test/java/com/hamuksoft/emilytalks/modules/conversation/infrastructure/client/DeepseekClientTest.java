package com.hamuksoft.emilytalks.modules.conversation.infrastructure.client;

import com.hamuksoft.emilytalks.modules.conversation.infrastructure.config.AgentContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeepseekClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private AgentContext agentContext;

    private DeepseekClient deepseekClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deepseekClient = new DeepseekClient(restTemplate, agentContext);
    }

    @Test
    void testSendMessage_WithSystemPrompt() {
        // Mocking dependencies
        when(agentContext.getSystemPrompt()).thenReturn("You are a helpful assistant.");
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(Map.class)))
                .thenReturn(new ResponseEntity<>(Map.of(
                        "choices", List.of(Map.of(
                                "message", Map.of("content", "Hello, how can I help you?")
                        ))
                ), HttpStatus.OK));

        // Execute the method
        String response = deepseekClient.sendMessage("Hi");

        // Verify the response
        assertEquals("Hello, how can I help you?", response);

        // Capture and verify the request
        ArgumentCaptor<HttpEntity> captor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(restTemplate).postForEntity(eq("https://openrouter.ai/api/v1/chat/completions"), captor.capture(), eq(Map.class));

        HttpEntity<Map<String, Object>> capturedEntity = captor.getValue();
        assertNotNull(capturedEntity);
        assertEquals("Bearer null", capturedEntity.getHeaders().getFirst("Authorization"));
        assertEquals(MediaType.APPLICATION_JSON, capturedEntity.getHeaders().getContentType());

        Map<String, Object> requestBody = capturedEntity.getBody();
        assertNotNull(requestBody);
        assertEquals("deepseek/deepseek-chat-v3-0324:free", requestBody.get("model"));

        List<Map<String, String>> messages = (List<Map<String, String>>) requestBody.get("messages");
        assertEquals(2, messages.size());
        assertEquals("system", messages.get(0).get("role"));
        assertEquals("You are a helpful assistant.", messages.get(0).get("content"));
        assertEquals("user", messages.get(1).get("role"));
        assertEquals("Hi", messages.get(1).get("content"));
    }

    @Test
    void testSendMessage_WithoutSystemPrompt() {
        // Mocking dependencies
        when(agentContext.getSystemPrompt()).thenReturn(null);
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(Map.class)))
                .thenReturn(new ResponseEntity<>(Map.of(
                        "choices", List.of(Map.of(
                                "message", Map.of("content", "Hello!")
                        ))
                ), HttpStatus.OK));

        // Execute the method
        String response = deepseekClient.sendMessage("Hi");

        // Verify the response
        assertEquals("Hello!", response);

        // Capture and verify the request
        ArgumentCaptor<HttpEntity<Map<String, Object>>> captor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(restTemplate).postForEntity(eq("https://openrouter.ai/api/v1/chat/completions"), captor.capture(), eq(Map.class));

        HttpEntity<Map<String, Object>> capturedEntity = captor.getValue();
        assertNotNull(capturedEntity);

        Map<String, Object> requestBody = capturedEntity.getBody();
        assertNotNull(requestBody);

        List<Map<String, String>> messages = (List<Map<String, String>>) requestBody.get("messages");
        assertEquals(1, messages.size());
        assertEquals("user", messages.get(0).get("role"));
        assertEquals("Hi", messages.get(0).get("content"));
    }
}