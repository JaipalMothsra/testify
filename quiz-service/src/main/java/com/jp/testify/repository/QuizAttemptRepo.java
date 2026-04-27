package com.jp.testify.repository;

import com.jp.testify.entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizAttemptRepo extends JpaRepository<QuizAttempt, Long> {
}
