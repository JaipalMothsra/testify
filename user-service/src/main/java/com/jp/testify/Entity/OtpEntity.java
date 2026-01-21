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

    @Column(name = "expiry_time", nullable = false)
    private LocalDateTime expiryTime;

    public OtpEntity() {
    }

    public OtpEntity(String email, String otp, String password, LocalDateTime expiryTime) {
        this.email = email;
        this.otp = otp;
        this.password = password;
        this.expiryTime = expiryTime;
    }

    // ===== getters & setters =====
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getOtp() {
        return otp;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }
}
