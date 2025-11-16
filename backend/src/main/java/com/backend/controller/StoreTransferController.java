package com.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.dto.StoreTransferResponseDTO;
import com.backend.entity.StoreTransfer;
import com.backend.service.StoreTransferService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/store-transfers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StoreTransferController {
    
    private final StoreTransferService storeTransferService;
    
    // @GetMapping
    // public ResponseEntity<List<StoreTransfer>> getAll() {
    //     return ResponseEntity.ok(storeTransferService.findAll());
    // }

        @GetMapping
    public ResponseEntity<List<StoreTransferResponseDTO>> getAllDTO() {
        return ResponseEntity.ok(storeTransferService.findAllDTO());
    }
    
    
    @GetMapping("/date-range")
    public ResponseEntity<List<StoreTransfer>> getByDateRange(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(storeTransferService.findByDateRange(from, to));
    }
    
    @PostMapping
    public ResponseEntity<StoreTransfer> create(@RequestBody StoreTransfer transfer) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(storeTransferService.create(transfer));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        storeTransferService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
