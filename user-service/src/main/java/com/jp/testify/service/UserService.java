////package com.jp.testify.service;
////
////import com.jp.testify.Entity.User;
////import com.jp.testify.repository.UserRepo;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.security.crypto.password.PasswordEncoder;
////import org.springframework.stereotype.Service;
////
////@Service
////public class UserService {
////
////    @Autowired
////    private UserRepo userRepo;
////    @Autowired
////    private PasswordEncoder passwordEncoder;
////
////
////    // ================= REGISTER =================
////    public User resisteruser(User user) {
////
////        if (user.getEmail() == null || user.getEmail().isEmpty()) {
////            throw new RuntimeException("Email is required");
////        }
////
////        if (user.getPassword() == null || user.getPassword().isEmpty()) {
////            throw new RuntimeException("Password is required");
////        }
////
////        if (userRepo.findByEmail(user.getEmail()) != null) {
////            throw new RuntimeException("User already exists");
////        }
////
////        // yahan koi encoding nahi (as discussed)
////        return userRepo.save(user);
////    }
////
////    // ================= LOGIN =================
////    public User loginUser(String email, String password) {
////
////        if (email == null || email.isEmpty()) {
////            throw new RuntimeException("Email is required");
////        }
////
////        if (password == null || password.isEmpty()) {
////            throw new RuntimeException("Password is required");
////        }
////
////        // 1️⃣ DB se user lao
////        User user = userRepo.findByEmail(email);
////
////        // 2️⃣ User exist check
////        if (user == null) {
////            throw new RuntimeException("User not found");
////        }
////
////        // 3️⃣ Password check (SAFE)
////        if (!password.equals(user.getPassword())) {
////            throw new RuntimeException("Password is wrong");
////        }
////
////        // 4️⃣ Login success
////        return user;
////    }
////}
//package com.jp.testify.service;
//
//import com.jp.testify.Entity.User;
//import com.jp.testify.repository.UserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepo userRepo;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    // ================= REGISTER =================
//    public User registerUser(User user) {
//
//        if (userRepo.findByEmail(user.getEmail()) != null) {
//            throw new RuntimeException("User already exists");
//        }
//
//        // 🔐 PASSWORD ENCRYPT HERE
//        user.setPassword(
//                passwordEncoder.encode(user.getPassword())
//        );
//
//        return userRepo.save(user);
//    }
//
//    // ================= LOGIN =================
//    public User loginUser(String email, String password) {
//
//        User user = userRepo.findByEmail(email);
//
//        if (user == null) {
//            throw new RuntimeException("User not found");
//        }
//
//        // 🔐 PASSWORD MATCH HERE
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new RuntimeException("Invalid password");
//        }
//
//        return user;
//    }
//}
//
package com.jp.testify.service;

import com.jp.testify.Entity.User;
import com.jp.testify.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ==================================================
    // 1️⃣ SAVE USER AFTER OTP VERIFICATION ONLY
    // ==================================================
    public void saveVerifiedUser(String email, String encryptedPassword) {

        // Agar user already exist karta hai to dobara save nahi karna
        if (userRepo.existsByEmail(email)) {
            return;
        }

        User user = new User(email,encryptedPassword,true);
//        user.setEmail(email);
//        user.setPassword(encryptedPassword); // already encrypted
//        user.setVerified(true);              // OTP verified

        userRepo.save(user);
    }

    // ==================================================
    // 2️⃣ LOGIN (ONLY VERIFIED USERS)
    // ==================================================
    public User loginUser(String email, String password) {

        Optional<User> userOptional = userRepo.findByEmail(email);

        // ❌ User exist nahi karta
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();

        // ❌ OTP verify nahi hua
        if (!user.isVerified()) {
            throw new RuntimeException("Please verify your email first");
        }

        // ❌ Password galat
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // ✅ Login success
        return user;
    }
}

