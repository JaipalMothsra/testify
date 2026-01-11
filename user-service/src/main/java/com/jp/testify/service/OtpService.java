package com.jp.testify.service;

import com.jp.testify.Entity.OtpEntity;
import com.jp.testify.repository.OtpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    // ==================================================
    // 1️⃣ GENERATE OTP + SAVE + SEND EMAIL
    // ==================================================
    public void generateAndSaveOtp(String email, String password) {

        // 🔐 password encrypt
        String encodedPassword = passwordEncoder.encode(password);

        // 🔢 generate otp
        String otp = generateOtp();

        // ⏱ expiry time (5 minutes)
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);

        // 🧱 create entity
        OtpEntity otpEntity = new OtpEntity(
                email,
                otp,
                encodedPassword,
                expiryTime
        );

        // 💾 save to DB
        otpRepo.save(otpEntity);

        // 📧 send OTP email
        emailService.sendOtpEmail(email, otp);
    }

    // ==================================================
    // 2️⃣ VERIFY OTP + SAVE USER
    // ==================================================
    public boolean verifyOtp(String email, String userOtp) {

        Optional<OtpEntity> otpOptional = otpRepo.findByEmail(email);

        // ❌ OTP record not found
        if (otpOptional.isEmpty()) {
            return false;
        }

        OtpEntity otpEntity = otpOptional.get();

        // ❌ OTP expired
        if (otpEntity.getExpiryTime().isBefore(LocalDateTime.now())) {
            otpRepo.deleteByEmail(email);
            return false;
        }

        // ❌ OTP mismatch
        if (!otpEntity.getOtp().equals(userOtp)) {
            return false;
        }

        // ✅ OTP verified → SAVE USER
        userService.saveVerifiedUser(
                otpEntity.getEmail(),
                otpEntity.getPassword()   // already encrypted
        );

        // 🧹 delete OTP (one-time use)
        otpRepo.deleteByEmail(email);

        return true;
    }

    // ==================================================
    // 3️⃣ OTP GENERATOR (HELPER)
    // ==================================================
    private String generateOtp() {
        Random random = new Random();
        int otp = random.nextInt(900000) + 100000; // 6-digit
        return String.valueOf(otp);
    }
}
