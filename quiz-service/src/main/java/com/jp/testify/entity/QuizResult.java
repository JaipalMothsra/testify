package com.jp.testify.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "quiz_results",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"attempt_id"})
        }
)
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @OneToOne
    @JoinColumn(name = "attempt_id", nullable = false)
    private QuizAttempt quizAttempt;

    @Column(name = "total_questions", nullable = false)
    private Integer totalQuestions;

    @Column(name = "correct_answers", nullable = false)
    private Integer correctAnswers;

    @Column(nullable = false)
    private Integer score;

    @Column(nullable = false)
    private Double percentage;

    @Column(nullable = false)
    private String level;   // Beginner / Intermediate / Advanced

    @Column(nullable = false)
    private Boolean passed;

    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;

    public QuizResult() {
    }

    public QuizResult(Long userId,
                      QuizAttempt quizAttempt,
                      Integer totalQuestions,
                      Integer correctAnswers,
                      Integer score,
                      Double percentage,
                      String level,
                      Boolean passed,
                      LocalDateTime submittedAt) {
        this.userId = userId;
        this.quizAttempt = quizAttempt;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.score = score;
        this.percentage = percentage;
        this.level = level;
        this.passed = passed;
        this.submittedAt = submittedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public QuizAttempt getQuizAttempt() {
        return quizAttempt;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public Integer getCorrectAnswers() {
        return correctAnswers;
    }

    public Integer getScore() {
        return score;
    }

    public Double getPercentage() {
        return percentage;
    }

    public String getLevel() {
        return level;
    }

    public Boolean getPassed() {
        return passed;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setQuizAttempt(QuizAttempt quizAttempt) {
        this.quizAttempt = quizAttempt;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public void setCorrectAnswers(Integer correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
