package com.jp.testify.repository;

import com.jp.testify.entity.QuizAttempt;
import com.jp.testify.entity.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizResultRepo extends JpaRepository<QuizResult, Long> {

    Optional<QuizResult> findByAttempt(QuizAttempt attempt);

}