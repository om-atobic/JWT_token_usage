package com.example.JWT.controller;

import com.example.JWT.dto.request.CreateProductRequest;
import com.example.JWT.model.Role;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
public class ProductController {


    @GetMapping("/list")
    public ResponseEntity<?> getProducts(@RequestParam(required = true) String productId,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(required = false) String category,
                                       HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        
        return ResponseEntity.ok("Products for user: " + userId + 
                               ", page: " + page + 
                               ", size: " + size + 
                               ", category: " + category);
    }

}