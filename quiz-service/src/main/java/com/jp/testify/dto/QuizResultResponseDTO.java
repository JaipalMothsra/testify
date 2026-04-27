package com.jp.testify.dto;

public class QuizResultResponseDTO {

    private int score;
    private double percentage;
    private String level;

    public QuizResultResponseDTO() {
    }

    public QuizResultResponseDTO(int score, double percentage, String level) {
        this.score = score;
        this.percentage = percentage;
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
