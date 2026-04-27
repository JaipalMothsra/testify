package com.jp.testify.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jp.testify.client.AiServiceClient;
import com.jp.testify.dto.QuestionDTO;
import com.jp.testify.enums.Difficulty;
import com.jp.testify.enums.Topic;
import com.jp.testify.exception.AiClientException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiClientServiceImpl implements AiClientService {

    private final AiServiceClient aiServiceClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AiClientServiceImpl(AiServiceClient aiServiceClient) {
        this.aiServiceClient = aiServiceClient;
    }

    @Override
    public List<QuestionDTO> generateQuestions(
            Topic topic,
            Difficulty difficulty
    ) {

        try {

            // ✅ Prompt for Gemini
            String prompt = """
Generate 10 MCQ questions on topic %s with difficulty %s.

Return ONLY valid JSON array like this:

[
{
"question": "...",
"options": ["A","B","C","D"],
"correctAnswer": "..."
}
]
""".formatted(topic, difficulty);


            // ✅ Call Gemini API
            String rawResponse = aiServiceClient.callAi(prompt);


            // ✅ Parse Gemini wrapper JSON
            JsonNode root = objectMapper.readTree(rawResponse);

            String jsonText = root
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();


            // ✅ Remove markdown formatting if present
            jsonText = jsonText
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();


            // ✅ Convert cleaned JSON string → QuestionDTO list
            return objectMapper.readValue(
                    jsonText,
                    new TypeReference<List<QuestionDTO>>() {}
            );

        } catch (Exception ex) {

            throw new AiClientException(
                    "Failed to generate questions from AI service",
                    ex
            );
        }
    }
}