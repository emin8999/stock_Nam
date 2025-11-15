package com.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.dto.WarehouseStockDTO;
import com.backend.entity.WarehouseAdjustment;
import com.backend.service.WarehouseStockService;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse-stock")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class WarehouseStockController {
    
    private final WarehouseStockService warehouseStockService;
    
    @GetMapping
    public ResponseEntity<List<WarehouseStockDTO>> getStock() {
        return ResponseEntity.ok(warehouseStockService.getStockSnapshot());
    }
    
    @PostMapping("/adjust")
    public ResponseEntity<WarehouseAdjustment> adjustStock(@RequestBody WarehouseAdjustment adjustment) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(warehouseStockService.adjustStock(adjustment));
    }
    
    @GetMapping("/adjustments")
    public ResponseEntity<List<WarehouseAdjustment>> getAllAdjustments() {
        return ResponseEntity.ok(warehouseStockService.getAllAdjustments());
    }
}
