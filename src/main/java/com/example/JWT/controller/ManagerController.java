package com.example.JWT.controller;

import com.example.JWT.model.Role;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager")
@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
public class ManagerController {

    @GetMapping("/reports")
    public ResponseEntity<?> getReports(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        Role userRole = (Role) request.getAttribute("userRole");
        
        return ResponseEntity.ok().body(
            "Manager Reports - User ID: " + userId + ", Role: " + userRole
        );
    }

    @GetMapping("/team")
    public ResponseEntity<?> getTeamData(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return ResponseEntity.ok().body("Team data - Accessed by manager: " + userId);
    }

    @PostMapping("/approve/{requestId}")
    public ResponseEntity<?> approveRequest(@PathVariable String requestId, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return ResponseEntity.ok().body("Request " + requestId + " approved by manager: " + userId);
    }
}