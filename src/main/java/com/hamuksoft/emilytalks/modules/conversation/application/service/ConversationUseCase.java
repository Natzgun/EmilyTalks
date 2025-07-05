package com.hamuksoft.emilytalks.modules.conversation.application.service;

import com.hamuksoft.emilytalks.modules.conversation.application.dto.ConversationDTO;
import com.hamuksoft.emilytalks.modules.conversation.domain.AgentResponse;
import com.hamuksoft.emilytalks.modules.conversation.domain.UserUtterance;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ConversationUseCase {
    private final SpeechToTextClient speechToTextClient;
    private final ConversationalAgentClient conversationalAgentClient;

    public ConversationUseCase(SpeechToTextClient speechToTextClient, ConversationalAgentClient conversationalAgentClient) {
        this.speechToTextClient = speechToTextClient;
        this.conversationalAgentClient = conversationalAgentClient;
    }

    public UserUtterance processAudioFile(File audioFile) {
        return speechToTextClient.transcribe(audioFile);
    }


    public ConversationDTO execute(File audioFile) {
        UserUtterance  userInput = speechToTextClient.transcribe(audioFile);
        AgentResponse agentResponse = conversationalAgentClient.chat(userInput.getText());
        return ConversationDTO.builder()
                .userUtterance(userInput.getText())
                .agentResponse(agentResponse.getText())
                .build();
    }

}
