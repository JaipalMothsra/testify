package com.jp.testify.service;

import com.jp.testify.dto.QuestionDTO;
import com.jp.testify.enums.Difficulty;
import com.jp.testify.enums.Topic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final AiClientService aiClientService;

    public QuestionServiceImpl(AiClientService aiClientService) {
        this.aiClientService = aiClientService;
    }

    @Override
    public List<QuestionDTO> generateQuestions(Topic topic, Difficulty difficulty) {

        // Directly pass topic and difficulty
        return aiClientService.generateQuestions(topic, difficulty);
    }
}
