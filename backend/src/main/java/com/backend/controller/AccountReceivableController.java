package com.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.entity.AccountsReceivable;
import com.backend.entity.Payment;
import com.backend.service.AccountReceivableService;

import java.util.List;

@RestController
@RequestMapping("/api/ar")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AccountReceivableController {
    
    private final AccountReceivableService arService;
    
    @GetMapping
    public ResponseEntity<List<AccountsReceivable>> getAll() {
        return ResponseEntity.ok(arService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AccountsReceivable> getById(@PathVariable String id) {
        return ResponseEntity.ok(arService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<AccountsReceivable> create(@RequestBody AccountsReceivable ar) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(arService.create(ar));
    }
    
    @PostMapping("/{id}/payments")
    public ResponseEntity<AccountsReceivable> addPayment(
        @PathVariable String id,
        @RequestBody Payment payment
    ) {
        return ResponseEntity.ok(arService.addPayment(id, payment));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        arService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
