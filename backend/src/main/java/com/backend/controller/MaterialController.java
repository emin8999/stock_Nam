package com.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.dto.KpiDTO;
import com.backend.dto.PaymentDTO;
import com.backend.dto.ProfitLossDTO;
import com.backend.dto.StockDTO;
import com.backend.entity.AccountsPayable;
import com.backend.entity.AccountsReceivable;
import com.backend.entity.Finance;
import com.backend.entity.Issue;
import com.backend.entity.Material;
import com.backend.entity.Receipt;
import com.backend.entity.Settings;
import com.backend.service.MaterialService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MaterialController {
    
    private final MaterialService service;
    
    
    @GetMapping("/materials")
    public ResponseEntity<List<Material>> getAllMaterials() {
        return ResponseEntity.ok(service.getAllMaterials());
    }
    
    @PostMapping("/materials")
    public ResponseEntity<Material> createMaterial(@RequestBody Material material) {
        return ResponseEntity.ok(service.createMaterial(material));
    }
    
    @PutMapping("/materials/{id}/toggle")
    public ResponseEntity<Material> toggleMaterial(@PathVariable Long id) {
        return ResponseEntity.ok(service.toggleMaterialStatus(id));
    }
    
    @GetMapping("/receipts")
    public ResponseEntity<List<Receipt>> getAllReceipts() {
        return ResponseEntity.ok(service.getAllReceipts());
    }
    
    @PostMapping("/receipts")
    public ResponseEntity<Receipt> createReceipt(@RequestBody Receipt receipt) {
        return ResponseEntity.ok(service.createReceipt(receipt));
    }
    
   
    @GetMapping("/issues")
    public ResponseEntity<List<Issue>> getAllIssues() {
        return ResponseEntity.ok(service.getAllIssues());
    }
    
    @PostMapping("/issues")
    public ResponseEntity<Issue> createIssue(@RequestBody Issue issue) {
        return ResponseEntity.ok(service.createIssue(issue));
    }
    
   
    @GetMapping("/stock")
    public ResponseEntity<List<StockDTO>> getStock() {
        return ResponseEntity.ok(service.getStockSnapshot());
    }
    
    
    @GetMapping("/kpi")
    public ResponseEntity<KpiDTO> getKPI() {
        return ResponseEntity.ok(service.getKPI());
    }
    
   
    @GetMapping("/finances")
    public ResponseEntity<List<Finance>> getAllFinances() {
        return ResponseEntity.ok(service.getAllFinances());
    }
    
    @PostMapping("/finances")
    public ResponseEntity<Finance> createFinance(@RequestBody Finance finance) {
        return ResponseEntity.ok(service.createFinance(finance));
    }
    
  
    @GetMapping("/reports/pl")
    public ResponseEntity<ProfitLossDTO> getProfitLoss(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(service.getProfitLoss(from, to));
    }
    

    @GetMapping("/ar")
    public ResponseEntity<List<AccountsReceivable>> getAllAR() {
        return ResponseEntity.ok(service.getAllAR());
    }
    
    @PostMapping("/ar")
    public ResponseEntity<AccountsReceivable> createAR(@RequestBody AccountsReceivable ar) {
        return ResponseEntity.ok(service.createAR(ar));
    }
    
    @PostMapping("/ar/{id}/payment")
    public ResponseEntity<Void> addARPayment(
        @PathVariable Long id,
        @RequestBody Map<String, Object> payload
    ) {
        BigDecimal amount = new BigDecimal(payload.get("amount").toString());
        LocalDate date = LocalDate.parse(payload.get("date").toString());
        service.addARPayment(id, amount, date);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/ar/{id}/payments")
    public ResponseEntity<List<PaymentDTO>> getARPayments(@PathVariable Long id) {
        return ResponseEntity.ok(service.getARPayments(id));
    }
    
   
    @GetMapping("/ap")
    public ResponseEntity<List<AccountsPayable>> getAllAP() {
        return ResponseEntity.ok(service.getAllAP());
    }
    
    @PostMapping("/ap")
    public ResponseEntity<AccountsPayable> createAP(@RequestBody AccountsPayable ap) {
        return ResponseEntity.ok(service.createAP(ap));
    }
    
    @PostMapping("/ap/{id}/payment")
    public ResponseEntity<Void> addAPPayment(
        @PathVariable Long id,
        @RequestBody Map<String, Object> payload
    ) {
        BigDecimal amount = new BigDecimal(payload.get("amount").toString());
        LocalDate date = LocalDate.parse(payload.get("date").toString());
        service.addAPPayment(id, amount, date);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/ap/{id}/payments")
    public ResponseEntity<List<PaymentDTO>> getAPPayments(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAPPayments(id));
    }
    
   
    @GetMapping("/settings")
    public ResponseEntity<Settings> getSettings() {
        return ResponseEntity.ok(service.getSettings());
    }
    
    @PutMapping("/settings")
    public ResponseEntity<Settings> updateSettings(@RequestBody Settings settings) {
        return ResponseEntity.ok(service.updateSettings(settings));
    }
}

