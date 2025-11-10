package com.backend.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dto.KpiDTO;
import com.backend.dto.MaterialMovementDTO;
import com.backend.dto.ProfitLossDTO;
import com.backend.dto.StockSnapshotDTO;
import com.backend.service.StockService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StockController {
    
    private final StockService stockService;
    
    @GetMapping("/snapshot")
    public ResponseEntity<List<StockSnapshotDTO>> getSnapshot() {
        return ResponseEntity.ok(stockService.getStockSnapshot());
    }
    
    @GetMapping("/kpi")
    public ResponseEntity<KpiDTO> getKpi(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(stockService.getKpi(from, to));
    }
    
    @GetMapping("/profit-loss")
    public ResponseEntity<ProfitLossDTO> getProfitLoss(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(stockService.getProfitLoss(from, to));
    }
    
    @GetMapping("/movements")
    public ResponseEntity<List<MaterialMovementDTO>> getMovements() {
        return ResponseEntity.ok(stockService.getMaterialMovements());
    }
}
