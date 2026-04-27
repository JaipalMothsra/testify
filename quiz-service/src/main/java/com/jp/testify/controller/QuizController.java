package com.jp.testify.controller;

import com.jp.testify.dto.QuestionDTO;
import com.jp.testify.dto.QuizStartRequestDTO;
import com.jp.testify.dto.QuizSubmitRequestDTO;
import com.jp.testify.dto.QuizResultResponseDTO;
import com.jp.testify.service.QuestionService;
import com.jp.testify.service.QuizService;
import com.jp.testify.service.ResultService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;
    private final QuestionService questionService;
    private final ResultService resultService;

    public QuizController(QuizService quizService,
                          QuestionService questionService,
                          ResultService resultService) {
        this.quizService = quizService;
        this.questionService = questionService;
        this.resultService = resultService;
    }

    // Temporary in-memory question store (later DB/cache use hoga)
    private final Map<Long, List<QuestionDTO>> questionStore = new HashMap<>();


    // 🔹 START QUIZ
    @PostMapping("/start")
    public Long startQuiz(@RequestBody QuizStartRequestDTO request) {

        List<QuestionDTO> questions =
                questionService.generateQuestions(
                        request.getTopic(),
                        request.getDifficulty()
                );

        Long attemptId = quizService.startQuiz(request);

        questionStore.put(attemptId, questions);

        return attemptId;
    }


    // 🔹 GET QUESTION BY attemptId
    @GetMapping("/question/{attemptId}")
    public List<QuestionDTO> getQuestions(@PathVariable Long attemptId) {

        List<QuestionDTO> questions = questionStore.get(attemptId);

        if (questions == null) {
            throw new RuntimeException("Questions not found for attemptId");
        }

        return questions;
    }


    // 🔹 SUBMIT QUIZ
    @PostMapping("/submit")
    public QuizResultResponseDTO submitQuiz(
            @RequestBody QuizSubmitRequestDTO request) {

        List<QuestionDTO> questions =
                questionStore.get(request.getAttemptId());

        if (questions == null) {
            throw new RuntimeException("Quiz not found for attemptId");
        }

        return resultService.submitQuiz(request, questions);
    }


    // 🔹 GET RESULT BY attemptId
    @GetMapping("/result/{attemptId}")
    public QuizResultResponseDTO getResult(
            @PathVariable Long attemptId) {

        return resultService.getResult(attemptId);
    }
}