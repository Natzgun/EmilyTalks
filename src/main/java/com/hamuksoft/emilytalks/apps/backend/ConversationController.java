package com.hamuksoft.emilytalks.apps.backend;

import com.hamuksoft.emilytalks.modules.conversation.application.dto.ConversationDTO;
import com.hamuksoft.emilytalks.modules.conversation.application.dto.ConversationSetupDTO;
import com.hamuksoft.emilytalks.modules.conversation.infrastructure.client.DeepseekClient;
import com.hamuksoft.emilytalks.modules.conversation.application.dto.UserUtteranceDTO;
import com.hamuksoft.emilytalks.modules.conversation.application.service.ConversationUseCase;
import com.hamuksoft.emilytalks.modules.conversation.domain.UserUtterance;
import com.hamuksoft.emilytalks.modules.conversation.infrastructure.config.AgentContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

@RestController
@RequestMapping("/api/conversation")
public class ConversationController {

    private final DeepseekClient deepseekClient;
    private final ConversationUseCase conversationUseCase;
    private final AgentContext agentContext;

    public ConversationController(ConversationUseCase conversationUseCase, DeepseekClient deepseekClient, AgentContext agentContext) {
        this.conversationUseCase = conversationUseCase;
        this.deepseekClient = deepseekClient;
        this.agentContext = agentContext;
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

    @PostMapping("/converse")
    public ResponseEntity<ConversationDTO> converse(@RequestParam("file") MultipartFile file) throws Exception {
        File tempFile = File.createTempFile("audio", ".ogg");
        file.transferTo(tempFile);
        ConversationDTO dto = conversationUseCase.execute(tempFile);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/deepseek")
    public ResponseEntity<String> converseWithDeepseek(@RequestBody Map<String, String> requestBody) {
        String message = requestBody.get("message");
        String response = deepseekClient.sendMessage(message);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/prepare-agent")
    public ResponseEntity<Map<String, String>> prepareAgent(@RequestBody ConversationSetupDTO setupDto) {
        agentContext.configure(setupDto.getScenario(), setupDto.getTopic());
        Map<String, String> response = Map.of("status", "success");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/deepseek-feedback")
    public ResponseEntity<String> deepseekFeedback() {
        String response = deepseekClient.getFeedBack();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-agent")
    public ResponseEntity<Map<String, String>> resetAgent() {
        agentContext.clear();
        Map<String, String> response = Map.of("status", "success");
        return ResponseEntity.ok(response);
    }

}