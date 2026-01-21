package com.jp.testify.repository;

import com.jp.testify.Entity.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepo extends JpaRepository<OtpEntity,Long> {
   Optional<OtpEntity> findByEmail(String email);
    void deleteByEmail(String email);
}
