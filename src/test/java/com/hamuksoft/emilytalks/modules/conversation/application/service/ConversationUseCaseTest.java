package com.hamuksoft.emilytalks.modules.conversation.application.service;

import com.hamuksoft.emilytalks.modules.conversation.application.dto.ConversationDTO;
import com.hamuksoft.emilytalks.modules.conversation.domain.AgentResponse;
import com.hamuksoft.emilytalks.modules.conversation.domain.UserUtterance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConversationUseCaseTest {

    @Mock
    private SpeechToTextClient speechToTextClient;

    @Mock
    private ConversationalAgentClient conversationalAgentClient;

    private ConversationUseCase conversationUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        conversationUseCase = new ConversationUseCase(speechToTextClient, conversationalAgentClient);
    }

    @Test
    void testProcessAudioFile() {
        // Mock input and output
        File mockAudioFile = mock(File.class);
        UserUtterance mockUserUtterance = new UserUtterance("Hello", Instant.now());
        when(speechToTextClient.transcribe(mockAudioFile)).thenReturn(mockUserUtterance);

        // Call the method
        UserUtterance result = conversationUseCase.processAudioFile(mockAudioFile);

        // Verify the result
        assertNotNull(result);
        assertEquals("Hello", result.getText());

        // Verify interaction
        verify(speechToTextClient, times(1)).transcribe(mockAudioFile);
    }

    @Test
    void testExecute() {
        // Mock input and output
        File mockAudioFile = mock(File.class);
        UserUtterance mockUserUtterance = new UserUtterance("Hi", Instant.now());
        AgentResponse mockAgentResponse = new AgentResponse("Hello, how can I help you?", Instant.now());
        when(speechToTextClient.transcribe(mockAudioFile)).thenReturn(mockUserUtterance);
        when(conversationalAgentClient.chat("Hi")).thenReturn(mockAgentResponse);

        // Call the method
        ConversationDTO result = conversationUseCase.execute(mockAudioFile);

        // Verify the result
        assertNotNull(result);
        assertEquals("Hi", result.getUserUtterance());
        assertEquals("Hello, how can I help you?", result.getAgentResponse());

        // Verify interactions
        verify(speechToTextClient, times(1)).transcribe(mockAudioFile);
        verify(conversationalAgentClient, times(1)).chat("Hi");
    }
}