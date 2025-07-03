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

    @PostMapping("/upload-and-transcribe")
    public ResponseEntity<String> uploadAndTranscribe(@RequestParam("file") MultipartFile file) throws Exception {
        byte[] audioBytes = file.getBytes();
        String uploadUrl = assemblyAiSTT.uploadAudioFile(audioBytes);
        System.out.println("Upload URL: " + uploadUrl);

        String transcriptionId = assemblyAiSTT.transcribeAudio(uploadUrl);
        System.out.println("Transcription ID: " + transcriptionId);

        Map<String, Object> result;
        int maxRetries = 10;
        int retryInterval = 5000;
        int attempts = 0;

        do {
            Thread.sleep(retryInterval);
            result = assemblyAiSTT.getTranscriptionResult(transcriptionId);
            System.out.println("Transcription Status: " + result.get("status"));
            attempts++;
        } while (!"completed".equals(result.get("status")) && attempts < maxRetries);

        if (!"completed".equals(result.get("status"))) {
            return ResponseEntity.status(202).body("Transcription is still processing. Please try again later.");
        }

        String transcribedText = (String) result.get("text");
        System.out.println("Transcribed Text: " + transcribedText);

        return ResponseEntity.ok(transcribedText);
    }
}