package com.jp.testify.controller;

import com.jp.testify.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class OtpController {

    @Autowired
    private OtpService otpService;

    // =========================
    // 1️⃣ Generate OTP API
    // =========================
    @PostMapping("/generate-otp")
    public ResponseEntity<?> generateOtp(@RequestBody Map<String, String> request) {

        String email = request.get("email");
        String password = request.get("password");

        // basic validation
        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("Email or Password missing");
        }

        otpService.generateAndSaveOtp(email, password);

        return ResponseEntity.ok("OTP sent successfully to your email");
    }

    // =========================
    // 2️⃣ Verify OTP API
    // =========================
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {

        String email = request.get("email");
        String otp = request.get("otp");

        if (email == null || otp == null) {
            return ResponseEntity.badRequest().body("Email or OTP missing");
        }

        boolean verified = otpService.verifyOtp(email, otp);

        if (verified) {
            return ResponseEntity.ok("OTP verified successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired OTP");
        }
    }
}
