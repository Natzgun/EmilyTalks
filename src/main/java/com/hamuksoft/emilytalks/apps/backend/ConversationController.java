package com.hamuksoft.emilytalks.apps.backend;

import com.hamuksoft.emilytalks.modules.conversation.infrastructure.client.AssemblyAiSTT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/conversation")
public class ConversationController {

    private final AssemblyAiSTT assemblyAiSTT;

    @Autowired
    public ConversationController(AssemblyAiSTT assemblyAiSTT) {
        this.assemblyAiSTT = assemblyAiSTT;
    }

    @PostMapping("/upload-audio")
    public ResponseEntity<String> uploadAudio(@RequestParam("file") MultipartFile file) throws Exception {
        byte[] audioBytes = file.getBytes();
        String uploadUrl = assemblyAiSTT.uploadAudioFile(audioBytes);
        return ResponseEntity.ok(uploadUrl);
    }

    @PostMapping("/transcribe")
    public ResponseEntity<String> transcribeAudio(@RequestBody Map<String, String> requestBody) {
        String uploadUrl = requestBody.get("uploadUrl");
        String transcriptionId = assemblyAiSTT.transcribeAudio(uploadUrl);
        return ResponseEntity.ok(transcriptionId);
    }

    @GetMapping("/transcription-result/{id}")
    public ResponseEntity<String> getTranscriptionResult(@PathVariable("id") String transcriptionId) {
        Map<String, Object> result = assemblyAiSTT.getTranscriptionResult(transcriptionId);

        String transcribedText = (String) result.get("text");

        return ResponseEntity.ok(transcribedText);
    }
}