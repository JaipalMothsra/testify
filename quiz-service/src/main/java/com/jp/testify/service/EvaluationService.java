package com.jp.testify.service;

import com.jp.testify.dto.QuestionDTO;

import java.util.List;
import java.util.Map;

public interface EvaluationService {

    int evaluate(List<QuestionDTO> questions,
                 Map<Integer, String> userAnswers);

}
