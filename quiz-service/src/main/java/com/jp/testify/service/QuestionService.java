package com.jp.testify.service;

import com.jp.testify.enums.Difficulty;
import com.jp.testify.enums.Topic;
import com.jp.testify.dto.QuestionDTO;

import java.util.List;

public interface QuestionService {

    List<QuestionDTO> generateQuestions(Topic topic, Difficulty difficulty);

}
