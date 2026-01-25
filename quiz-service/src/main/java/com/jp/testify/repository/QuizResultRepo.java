package com.jp.testify.repository;

import com.jp.testify.entity.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizResultRepo extends JpaRepository<QuizResult, Long> {

    Optional<QuizResult> findByQuizAttemptId(Long quizAttemptId);

    List<QuizResult> findByUserId(Long userId);
}
