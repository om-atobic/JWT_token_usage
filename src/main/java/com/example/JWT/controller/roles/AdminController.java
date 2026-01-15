package com.example.JWT.controller.roles;

import com.example.JWT.model.Role;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        Role userRole = (Role) request.getAttribute("userRole");
        
        return ResponseEntity.ok().body(
            "Admin Dashboard - User ID: " + userId + ", Role: " + userRole
        );
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return ResponseEntity.ok().body("All users data - Accessed by: " + userId);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return ResponseEntity.ok().body("User " + id + " deleted by admin: " + userId);
    }
}