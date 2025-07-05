package com.hamuksoft.emilytalks.modules.conversation.infrastructure.client;

import com.hamuksoft.emilytalks.modules.conversation.application.service.SpeechToTextClient;
import com.hamuksoft.emilytalks.modules.conversation.domain.UserUtterance;
import com.hamuksoft.emilytalks.modules.conversation.infrastructure.config.AssemblyAIConfig;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.time.Instant;
import java.util.Map;

@Component
public class AssemblyAISpeachToTextClient implements SpeechToTextClient {

    private final AssemblyAIConfig assemblyAiSTT;

    public AssemblyAISpeachToTextClient(AssemblyAIConfig assemblyAiSTT) {
        this.assemblyAiSTT = assemblyAiSTT;
    }

    @Override
    public UserUtterance transcribe(File audioFile) {
        try {
            byte[] audioBytes = Files.readAllBytes(audioFile.toPath());
            String uploadUrl = assemblyAiSTT.uploadAudioFile(audioBytes);
            String transcriptionID = assemblyAiSTT.transcribeAudio(uploadUrl);

            Map<String, Object> result;
            int attempts = 0;
            int maxRetries = 10;
            int retryInterval = 5000;

            do {
                Thread.sleep(retryInterval);
                result =  assemblyAiSTT.getTranscriptionResult(transcriptionID);
                attempts++;
            } while (!"completed".equals(result.get("status")) && attempts < maxRetries);

            if(!"completed".equals(result.get("status"))) {
                throw new RuntimeException("Transcription failed or timed out");
            }

            String transcribedText = result.get("text").toString();
            return new UserUtterance(transcribedText, Instant.now());
        } catch (Exception e) {
            throw new RuntimeException("Error during transcription: " + e.getMessage(), e);
        }
    }
}
