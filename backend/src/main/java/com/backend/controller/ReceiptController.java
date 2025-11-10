package com.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.entity.Receipt;
import com.backend.service.ReceiptService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/receipts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReceiptController {
    
    private final ReceiptService receiptService;
    
    @GetMapping
    public ResponseEntity<List<Receipt>> getAll() {
        return ResponseEntity.ok(receiptService.findAll());
    }
    
    @GetMapping("/material/{materialId}")
    public ResponseEntity<List<Receipt>> getByMaterial(@PathVariable String materialId) {
        return ResponseEntity.ok(receiptService.findByMaterial(materialId));
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<Receipt>> getByDateRange(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(receiptService.findByDateRange(from, to));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Receipt> getById(@PathVariable String id) {
        return ResponseEntity.ok(receiptService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<Receipt> create(@RequestBody Receipt receipt) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(receiptService.create(receipt));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        receiptService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
