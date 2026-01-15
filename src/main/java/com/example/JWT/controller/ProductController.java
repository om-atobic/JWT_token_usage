package com.example.JWT.controller;

import com.example.JWT.dto.request.CreateProductRequest;
import com.example.JWT.dto.response.ProductListResponse;
import com.example.JWT.model.Product;
import com.example.JWT.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request,
                                                HttpServletRequest httpRequest) {
        String userId = (String) httpRequest.getAttribute("userId");
        //for now fetch userId from jwt will be here

        //call the service to store the given data in db for now no validation on input data
        Product product = productService.createProduct(
            request.getName(),
            request.getDescription(),
            request.getPrice(),
            request.getCategory(),
            userId
        );
        
        return ResponseEntity.ok(product);
    }

    @GetMapping("/list")
    public ResponseEntity<ProductListResponse> getProducts(
        @RequestParam(required = false) String category,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        HttpServletRequest request) {
        
        String userId = (String) request.getAttribute("userId");
        //no use of userId for now

        ProductListResponse response;
        if (category != null && !category.isEmpty()) {
            response = productService.getProductsByCategory(category, page, size);
        } else {
            response = productService.getAllProducts(page, size);
        }
        
        return ResponseEntity.ok(response);
    }
}
