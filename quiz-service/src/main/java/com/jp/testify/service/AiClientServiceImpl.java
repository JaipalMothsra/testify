package com.jp.testify.service;

import com.jp.testify.client.AiServiceClient;
import com.jp.testify.enums.Difficulty;
import com.jp.testify.enums.Topic;
import com.jp.testify.exception.AiClientException;
import org.springframework.stereotype.Service;

@Service
public class AiClientServiceImpl implements AiClientService {

    private final AiServiceClient aiServiceClient;

    public AiClientServiceImpl(AiServiceClient aiServiceClient) {
        this.aiServiceClient = aiServiceClient;
    }

    @Override
    public String generateQuestions(Topic topic, Difficulty difficulty) {

        try {
            // 1️⃣ Prompt build (business logic)
            String prompt = buildPrompt(topic, difficulty);

            // 2️⃣ External AI call
            return aiServiceClient.callAi(prompt);

        } catch (Exception ex) {
            throw new AiClientException("Failed to generate questions using AI", ex);
        }
    }

    /**
     * Build AI prompt based on topic and difficulty
     */
    private String buildPrompt(Topic topic, Difficulty difficulty) {

        return String.format(
                "Generate 10 multiple choice quiz questions on the topic '%s'. " +
                        "Difficulty level should be '%s'. " +
                        "Each question should have 4 options and one correct answer. " +
                        "Return the response in JSON format.",
                topic.name(),
                difficulty.name()
        );
    }
}
