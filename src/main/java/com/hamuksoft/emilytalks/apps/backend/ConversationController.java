package com.hamuksoft.emilytalks.apps.backend;

import com.hamuksoft.emilytalks.modules.conversation.infrastructure.client.DeepseekClient;
import com.hamuksoft.emilytalks.modules.conversation.application.dto.UserUtteranceDTO;
import com.hamuksoft.emilytalks.modules.conversation.application.service.ConversationUseCase;
import com.hamuksoft.emilytalks.modules.conversation.domain.UserUtterance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/api/conversation")
public class ConversationController {

    private final DeepseekClient deepseekClient;
    public final ConversationUseCase conversationUseCase;

    public ConversationController(ConversationUseCase conversationUseCase, DeepseekClient deeseekClient) {
        this.conversationUseCase = conversationUseCase;
        this.deepseekClient = deepseekClient;
    }

    @PostMapping("/speach-to-text")
    public ResponseEntity<UserUtteranceDTO> convertSpeachToText(@RequestParam("file") MultipartFile file) throws Exception {
        File tempFile = File.createTempFile("audio", ".ogg");
        file.transferTo(tempFile);

        UserUtterance utterance = conversationUseCase.processAudioFile(tempFile);
        UserUtteranceDTO transcribedText = UserUtteranceDTO.builder()
                .text(utterance.getText()).build();

        return ResponseEntity.ok(transcribedText);
    }

    @PostMapping("/deepseek")
    public ResponseEntity<String> converseWithDeepseek(@RequestBody Map<String, String> requestBody) {
        String message = requestBody.get("message");
        String response = deepseekClient.sendMessage(message);
        return ResponseEntity.ok(response);
    }
}