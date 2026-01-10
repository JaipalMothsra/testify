package com.jp.testify.controller;

import com.jp.testify.Entity.User;
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
    UserService userService;

    @PostMapping("/regis")
    public ResponseEntity<?> userregister(@RequestBody Map<String, String> data) {
        try {
            User user = new User();
            user.setEmail(data.get("email"));
            user.setPassword(data.get("password"));
            User saveUser = userService.registerUser(user);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("User registered successfully");

        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }


    }
    @PostMapping("/login")
    public ResponseEntity<?>userlogin(@RequestBody Map<String,String>data){
        try{
         User user=userService.loginUser(data.get("email"),data.get("password"));
            return ResponseEntity
                    .ok("Login successful");
        }
        catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }
}
