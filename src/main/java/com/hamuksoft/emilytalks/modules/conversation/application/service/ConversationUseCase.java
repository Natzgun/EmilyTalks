package com.hamuksoft.emilytalks.modules.conversation.application.service;

import com.hamuksoft.emilytalks.modules.conversation.domain.UserUtterance;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ConversationUseCase {
    private final SpeechToTextClient speechToTextClient;

    public ConversationUseCase(SpeechToTextClient speechToTextClient) {
        this.speechToTextClient = speechToTextClient;
    }

    public UserUtterance processAudioFile(File audioFile) {
        return speechToTextClient.transcribe(audioFile);
    }
}
