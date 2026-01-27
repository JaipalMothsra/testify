package com.jp.testify.service;

import com.jp.testify.enums.Difficulty;
import com.jp.testify.enums.Topic;

public interface AiClientService {

    /**
     * Generate quiz questions using AI based on topic and difficulty.
     *
     * @param topic      Quiz topic (JAVA, DSA, etc.)
     * @param difficulty Quiz difficulty (EASY, MEDIUM, HARD)
     * @return AI generated questions in raw String / JSON format
     */
    String generateQuestions(Topic topic, Difficulty difficulty);

}
