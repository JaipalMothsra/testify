package com.jp.testify.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "otp_verification")
public class OtpEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String otp;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDateTime expiryTime;

    // Required by JPA
    public OtpEntity() {
    }

    // Used while creating OTP
    public OtpEntity(String email, String otp, String password, LocalDateTime expiryTime) {
        this.email = email;
        this.otp = otp;
        this.password = password;
        this.expiryTime = expiryTime;
    }

    public Long getId() {
        return id;
    }

    // (optional – DB khud set karta hai)
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }
}
