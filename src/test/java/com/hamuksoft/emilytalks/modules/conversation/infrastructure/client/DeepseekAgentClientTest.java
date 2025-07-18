package com.hamuksoft.emilytalks.modules.conversation.infrastructure.client;

import com.hamuksoft.emilytalks.modules.conversation.domain.AgentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeepseekAgentClientTest {

    @Mock
    private DeepseekClient deepseekClient;

    private DeepseekAgentClient deepseekAgentClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deepseekAgentClient = new DeepseekAgentClient(deepseekClient);
    }

    @Test
    void testChat_ReturnsAgentResponse() {
        // Mock the behavior of DeepseekClient
        String mockReply = "Hello, how can I assist you?";
        when(deepseekClient.sendMessage("Hi")).thenReturn(mockReply);

        // Call the chat method
        AgentResponse response = deepseekAgentClient.chat("Hi");

        // Verify the response
        assertNotNull(response);
        assertEquals(mockReply, response.getText());
        assertNotNull(response.getTimestamp());
        assertTrue(response.getTimestamp().isBefore(Instant.now()) || response.getTimestamp().equals(Instant.now()));

        // Verify that the sendMessage method was called once
        verify(deepseekClient, times(1)).sendMessage("Hi");
    }
}