package com.jp.testify.service;

import com.jp.testify.dto.QuestionDTO;
import com.jp.testify.dto.QuizStartRequestDTO;
import com.jp.testify.entity.QuizAttempt;
import com.jp.testify.enums.Status;
import com.jp.testify.repository.QuizAttemptRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuestionService questionService;
    private final QuizAttemptRepo quizAttemptRepo;

    public QuizServiceImpl(QuestionService questionService,
                           QuizAttemptRepo quizAttemptRepo) {
        this.questionService = questionService;
        this.quizAttemptRepo = quizAttemptRepo;
    }

    @Override
    public Long startQuiz(QuizStartRequestDTO request) {

        List<QuestionDTO> questions =
                questionService.generateQuestions(
                        request.getTopic(),
                        request.getDifficulty()
                );

        QuizAttempt attempt = new QuizAttempt();

        // ✅ REQUIRED FIELD (temporary testing)
        attempt.setUserId(1L);

        attempt.setTopic(request.getTopic());
        attempt.setDifficulty(request.getDifficulty());
        attempt.setStatus(Status.STARTED);
        attempt.setTotalQuestions(questions.size());

        // ✅ REQUIRED FIELD
        attempt.setStartTime(LocalDateTime.now());

        // ✅ REQUIRED FIELD
        switch (request.getDifficulty()) {

            case EASY -> attempt.setDurationInMinutes(5);

            case MEDIUM -> attempt.setDurationInMinutes(10);

            case HARD -> attempt.setDurationInMinutes(15);
        }

        quizAttemptRepo.save(attempt);

        return attempt.getId();
    }
}