package com.jp.testify.service;

import com.jp.testify.dto.QuestionDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Override
    public int evaluate(List<QuestionDTO> questions,
                        Map<Integer, String> userAnswers) {

        int score = 0;

        for (int i = 0; i < questions.size(); i++) {

            String correctAnswer = questions.get(i).getCorrectAnswer();
            String userAnswer = userAnswers.get(i);

            if (correctAnswer != null && correctAnswer.equals(userAnswer)) {
                score++;
            }
        }

        return score;
    }
}
