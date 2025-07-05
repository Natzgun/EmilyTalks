package com.hamuksoft.emilytalks.apps.backend;

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

    public final ConversationUseCase conversationUseCase;

    public ConversationController(ConversationUseCase conversationUseCase) {
        this.conversationUseCase = conversationUseCase;
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
}