package com.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.entity.AccountsPayable;
import com.backend.entity.Payment;
import com.backend.service.AccountsPayableService;

import java.util.List;

@RestController
@RequestMapping("/api/ap")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AccountPayableController {
    
    private final AccountsPayableService apService;
    
    @GetMapping
    public ResponseEntity<List<AccountsPayable>> getAll() {
        return ResponseEntity.ok(apService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AccountsPayable> getById(@PathVariable String id) {
        return ResponseEntity.ok(apService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<AccountsPayable> create(@RequestBody AccountsPayable ap) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(apService.create(ap));
    }
    
    @PostMapping("/{id}/payments")
    public ResponseEntity<AccountsPayable> addPayment(
        @PathVariable String id,
        @RequestBody Payment payment
    ) {
        return ResponseEntity.ok(apService.addPayment(id, payment));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        apService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
