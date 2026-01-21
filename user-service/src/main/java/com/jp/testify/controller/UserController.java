package com.jp.testify.controller;

import com.jp.testify.service.OtpService;
import com.jp.testify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserService userService;

    // ==================================================
    // 1️⃣ REGISTER (GENERATE OTP ONLY)
    // ==================================================
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody Map<String, String> data) {
//
//        try {
//            String email = data.get("email");
//            String password = data.get("password");
//
//            if (email == null || password == null) {
//                return ResponseEntity
//                        .status(HttpStatus.BAD_REQUEST)
//                        .body("Email or password missing");
//            }
//
//            // 👉 OTP generate + save + email send
//            otpService.generateAndSaveOtp(email, password);
//
//            return ResponseEntity
//                    .status(HttpStatus.OK)
//                    .body("OTP sent to your email");
//
//        } catch (RuntimeException ex) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(ex.getMessage());
//        }
//    }

    // ==================================================
    // 2️⃣ LOGIN (ONLY VERIFIED USERS)
    // ==================================================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> data) {

        try {
            String email = data.get("email");
            String password = data.get("password");

            if (email == null || password == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Email or password missing");
            }

            userService.loginUser(email, password);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Login successful");

        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ex.getMessage());
        }
    }
}
