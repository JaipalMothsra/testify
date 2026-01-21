package com.jp.testify.service;

import com.jp.testify.Entity.OtpEntity;
import com.jp.testify.repository.OtpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private OtpRepo otpRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    // ==============================
    // 1️⃣ Generate OTP
    // ==============================
    @Transactional
    public void generateAndSaveOtp(String email, String password) {

        // 🔴 VERY IMPORTANT: old OTP delete
        otpRepo.deleteByEmail(email);

        String encodedPassword = passwordEncoder.encode(password);
        String otp = generateOtp();
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);

        OtpEntity otpEntity = new OtpEntity(
                email,
                otp,
                encodedPassword,
                expiryTime
        );

        otpRepo.save(otpEntity);

        emailService.sendOtpEmail(email, otp);
    }

    // ==============================
    // 2️⃣ Verify OTP
    // ==============================
    @Transactional
    public boolean verifyOtp(String email, String userOtp) {

        Optional<OtpEntity> optional = otpRepo.findByEmail(email);

        if (optional.isEmpty()) {
            return false;
        }

        OtpEntity otpEntity = optional.get();

        // ❌ expired
        if (otpEntity.getExpiryTime().isBefore(LocalDateTime.now())) {
            otpRepo.deleteByEmail(email);
            return false;
        }

        // ❌ mismatch
        if (!otpEntity.getOtp().equals(userOtp)) {
            return false;
        }

        // ✅ save user
        userService.saveVerifiedUser(
                otpEntity.getEmail(),
                otpEntity.getPassword()
        );

        // 🧹 delete OTP
        otpRepo.deleteByEmail(email);

        return true;
    }

    // ==============================
    // OTP Generator
    // ==============================
    private String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
}
