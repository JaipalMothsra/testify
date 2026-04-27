package com.jp.testify.client;

import com.jp.testify.configuration.AiProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class AiServiceClientImpl implements AiServiceClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final AiProperties aiProperties;

    public AiServiceClientImpl(AiProperties aiProperties) {
        this.aiProperties = aiProperties;
    }

    @Override
    public String callAi(String prompt) {

        // ✅ API key URL me append karo
        String url = aiProperties.getApiUrl()
                + "?key=" + aiProperties.getApiKey();

        Map<String, Object> part = new HashMap<>();
        part.put("text", prompt);

        Map<String, Object> content = new HashMap<>();
        content.put("parts", List.of(part));

        Map<String, Object> body = new HashMap<>();
        body.put("contents", List.of(content));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(body, headers);

        ResponseEntity<String> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        String.class
                );

        return response.getBody();
    }
}