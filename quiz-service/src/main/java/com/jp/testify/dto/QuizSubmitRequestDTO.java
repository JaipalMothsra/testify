package com.jp.testify.dto;

import java.util.Map;

public class QuizSubmitRequestDTO {

    private Long attemptId;

    // key = question index
    // value = selected answer
    private Map<Integer, String> answers;

    public QuizSubmitRequestDTO() {
    }

    public QuizSubmitRequestDTO(Long attemptId, Map<Integer, String> answers) {
        this.attemptId = attemptId;
        this.answers = answers;
    }

    public Long getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(Long attemptId) {
        this.attemptId = attemptId;
    }

    public Map<Integer, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Integer, String> answers) {
        this.answers = answers;
    }
}
