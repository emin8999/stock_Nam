package com.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.dto.StoreStockDTO;
import com.backend.service.StoreStockService;

import java.util.List;

@RestController
@RequestMapping("/api/store-stock")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StoreStockController {
    
    private final StoreStockService storeStockService;
    
    @GetMapping
    public ResponseEntity<List<StoreStockDTO>> getStock() {
        return ResponseEntity.ok(storeStockService.getStockSnapshot());
    }
}
