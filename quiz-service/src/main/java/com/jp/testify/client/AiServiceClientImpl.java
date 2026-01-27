package com.jp.testify.client;

import com.jp.testify.configuration.AiProperties;
import com.jp.testify.exception.AiClientException;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class AiServiceClientImpl implements AiServiceClient {

    private final RestTemplate restTemplate;
    private final AiProperties aiProperties;

    public AiServiceClientImpl(RestTemplate restTemplate, AiProperties aiProperties) {
        this.restTemplate = restTemplate;
        this.aiProperties = aiProperties;
    }

    @Override
    public String callAi(String prompt) {
        try {
            // 1. Headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(aiProperties.getApiKey());

            // 2. Request Body
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", aiProperties.getModel());
            requestBody.put("prompt", prompt);
            requestBody.put("max_tokens", 1000);

            HttpEntity<Map<String, Object>> entity =
                    new HttpEntity<>(requestBody, headers);

            // 3. API Call
            ResponseEntity<String> response = restTemplate.exchange(
                    aiProperties.getBaseUrl(),
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            // 4. Response validation
            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                throw new AiClientException("Invalid response from AI service");
            }

            return response.getBody();

        } catch (RestClientException ex) {
            throw new AiClientException("Error while calling AI service", ex);
        }
    }
}
