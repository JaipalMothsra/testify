package com.jp.testify.service;

import com.jp.testify.dto.QuestionDTO;
import com.jp.testify.dto.QuizResultResponseDTO;
import com.jp.testify.dto.QuizSubmitRequestDTO;
import com.jp.testify.entity.QuizAttempt;
import com.jp.testify.entity.QuizResult;
import com.jp.testify.enums.Status;
import com.jp.testify.repository.QuizAttemptRepo;
import com.jp.testify.repository.QuizResultRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {

    private final EvaluationService evaluationService;
    private final QuizAttemptRepo quizAttemptRepo;
    private final QuizResultRepo quizResultRepo;

    public ResultServiceImpl(EvaluationService evaluationService,
                             QuizAttemptRepo quizAttemptRepo,
                             QuizResultRepo quizResultRepo) {
        this.evaluationService = evaluationService;
        this.quizAttemptRepo = quizAttemptRepo;
        this.quizResultRepo = quizResultRepo;
    }


    // 🔹 SUBMIT QUIZ
    @Override
    public QuizResultResponseDTO submitQuiz(
            QuizSubmitRequestDTO request,
            List<QuestionDTO> questions) {

        // 1️⃣ Calculate score
        int score = evaluationService.evaluate(
                questions,
                request.getAnswers()
        );

        // 2️⃣ Calculate percentage
        double percentage = ((double) score / questions.size()) * 100;

        // 3️⃣ Decide level
        String level;
        if (percentage >= 80) {
            level = "EXPERT";
        } else if (percentage >= 50) {
            level = "INTERMEDIATE";
        } else {
            level = "BEGINNER";
        }

        // 4️⃣ Fetch attempt
        QuizAttempt attempt = quizAttemptRepo
                .findById(request.getAttemptId())
                .orElseThrow(() ->
                        new RuntimeException("Attempt not found"));

        attempt.setStatus(Status.COMPLETED);
        quizAttemptRepo.save(attempt);

        // 5️⃣ Save result
        QuizResult result = new QuizResult();
        result.setScore(score);
        result.setPercentage(percentage);
        result.setLevel(level);
        result.setAttempt(attempt);

        quizResultRepo.save(result);

        // 6️⃣ Return response
        return new QuizResultResponseDTO(
                score,
                percentage,
                level
        );
    }


    // 🔹 GET RESULT BY attemptId
    @Override
    public QuizResultResponseDTO getResult(Long attemptId) {

        QuizAttempt attempt = quizAttemptRepo
                .findById(attemptId)
                .orElseThrow(() ->
                        new RuntimeException("Attempt not found"));

        QuizResult result = quizResultRepo
                .findByAttempt(attempt)
                .orElseThrow(() ->
                        new RuntimeException("Result not found"));

        return new QuizResultResponseDTO(
                result.getScore(),
                result.getPercentage(),
                result.getLevel()
        );
    }
}