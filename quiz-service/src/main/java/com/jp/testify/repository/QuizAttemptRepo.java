package com.jp.testify.repository;

import com.jp.testify.entity.QuizAttempt;
import com.jp.testify.enums.Difficulty;
import com.jp.testify.enums.Status;
import com.jp.testify.enums.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizAttemptRepo extends JpaRepository<QuizAttempt, Long> {


    List<QuizAttempt> findByUserId(Long userId);

    Optional<QuizAttempt> findTopByUserIdOrderByStartedAtDesc(Long userId);

    List<QuizAttempt> findByTopicAndDifficulty(
            Topic topic,
            Difficulty difficulty
    );

    List<QuizAttempt> findByStatus(Status status);
}
