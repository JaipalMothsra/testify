package com.jp.testify.service;

import com.jp.testify.Entity.User;
import com.jp.testify.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    // ================= REGISTER =================
    public User resisteruser(User user) {

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new RuntimeException("Email is required");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new RuntimeException("Password is required");
        }

        if (userRepo.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("User already exists");
        }

        // yahan koi encoding nahi (as discussed)
        return userRepo.save(user);
    }

    // ================= LOGIN =================
    public User loginUser(String email, String password) {

        if (email == null || email.isEmpty()) {
            throw new RuntimeException("Email is required");
        }

        if (password == null || password.isEmpty()) {
            throw new RuntimeException("Password is required");
        }

        // 1️⃣ DB se user lao
        User user = userRepo.findByEmail(email);

        // 2️⃣ User exist check
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // 3️⃣ Password check (SAFE)
        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("Password is wrong");
        }

        // 4️⃣ Login success
        return user;
    }
}
