package com.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.entity.StoreSale;
import com.backend.service.StoreSaleService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/store-sales")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StoreSaleController {
    
    private final StoreSaleService storeSaleService;
    
    @GetMapping
    public ResponseEntity<List<StoreSale>> getAll() {
        return ResponseEntity.ok(storeSaleService.findAll());
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<StoreSale>> getByDateRange(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(storeSaleService.findByDateRange(from, to));
    }
    
    @PostMapping
    public ResponseEntity<StoreSale> create(@RequestBody StoreSale sale) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(storeSaleService.create(sale));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        storeSaleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
