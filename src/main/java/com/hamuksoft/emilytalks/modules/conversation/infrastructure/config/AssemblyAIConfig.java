package com.hamuksoft.emilytalks.modules.conversation.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class AssemblyAIConfig {

    private static final String BASE_URL = "https://api.assemblyai.com";
    private static final String UPLOAD_ENDPOINT = BASE_URL + "/v2/upload";
    private static final String TRANSCRIPTION_ENDPOINT = BASE_URL + "/v2/transcript";

    @Value("${assemblyai.api.key}")
    private String API_KEY;

    private final RestTemplate restTemplate;

    public AssemblyAIConfig(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String uploadAudioFile(byte[] audioFile) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", API_KEY);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(audioFile, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(UPLOAD_ENDPOINT, requestEntity, Map.class);
        return response.getBody().get("upload_url").toString();
    }

    public String transcribeAudio(String uploadUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = Map.of("audio_url", uploadUrl);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(TRANSCRIPTION_ENDPOINT, requestEntity, Map.class);
        return response.getBody().get("id").toString();
    }

    public Map<String, Object> getTranscriptionResult(String transcriptionId) {
        String url = TRANSCRIPTION_ENDPOINT + "/" + transcriptionId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", API_KEY);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Map.class);
        return response.getBody();
    }
}
