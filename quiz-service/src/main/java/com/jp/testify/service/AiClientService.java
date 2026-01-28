package com.jp.testify.service;

import com.jp.testify.enums.Difficulty;
import com.jp.testify.enums.Topic;

public interface AiClientService {


    String generateQuestions(Topic topic, Difficulty difficulty);

}
