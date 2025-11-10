package com.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.entity.Finance;
import com.backend.service.FinanceService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/finances")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FinanceController {
    
    private final FinanceService financeService;
    
    @GetMapping
    public ResponseEntity<List<Finance>> getAll() {
        return ResponseEntity.ok(financeService.findAll());
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<Finance>> getByDateRange(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(financeService.findByDateRange(from, to));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Finance> getById(@PathVariable String id) {
        return ResponseEntity.ok(financeService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<Finance> create(@RequestBody Finance finance) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(financeService.create(finance));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        financeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

