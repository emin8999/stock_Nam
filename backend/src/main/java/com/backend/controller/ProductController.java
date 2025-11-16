package com.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.dto.QuantityUpdateRequest;
import com.backend.entity.Product;
import com.backend.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {
    
    private final ProductService productService;
    
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.findAll());
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Product>> getActive() {
        return ResponseEntity.ok(productService.findActive());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable String id) {
        return ResponseEntity.ok(productService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(productService.create(product));
    }
    
     @PutMapping("/{id}/quantity")
    public ResponseEntity<Product> updateQuantity(
            @PathVariable String id,
            @RequestBody QuantityUpdateRequest request) {
        
        if (request.getQuantity() == null || request.getQuantity().compareTo(BigDecimal.ZERO) < 0) {
            return ResponseEntity.badRequest().build();
        }
        
        Product updated = productService.updateProductQuantity(id, request.getQuantity());
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

