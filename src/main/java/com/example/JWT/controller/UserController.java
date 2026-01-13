package com.example.JWT.controller;

import com.example.JWT.model.Role;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
public class UserController {

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        Role userRole = (Role) request.getAttribute("userRole");
        
        return ResponseEntity.ok().body(
            "User Profile - User ID: " + userId + ", Role: " + userRole
        );
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody Object profileData, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return ResponseEntity.ok().body("Profile updated for user: " + userId);
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getOrders(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return ResponseEntity.ok().body("Orders for user: " + userId);
    }
}