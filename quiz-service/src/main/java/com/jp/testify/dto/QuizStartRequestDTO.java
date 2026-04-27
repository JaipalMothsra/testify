package com.jp.testify.dto;

import com.jp.testify.enums.Difficulty;
import com.jp.testify.enums.Topic;

public class QuizStartRequestDTO {

    private Topic topic;
    private Difficulty difficulty;

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
