package com.jp.testify.service;

import com.jp.testify.dto.QuestionDTO;
import com.jp.testify.dto.QuizResultResponseDTO;
import com.jp.testify.dto.QuizSubmitRequestDTO;

import java.util.List;

public interface ResultService {

    // submit quiz and calculate result
    QuizResultResponseDTO submitQuiz(
            QuizSubmitRequestDTO request,
            List<QuestionDTO> questions
    );

    // get result by attemptId
    QuizResultResponseDTO getResult(Long attemptId);
}