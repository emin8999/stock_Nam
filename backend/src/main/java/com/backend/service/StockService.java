package com.backend.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.backend.dto.KpiDTO;
import com.backend.dto.MaterialMovementDTO;
import com.backend.dto.ProfitLossDTO;
import com.backend.dto.StockSnapshotDTO;
import com.backend.entity.Issue;
import com.backend.entity.Material;
import com.backend.entity.Receipt;
import com.backend.entity.Finance;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService {
    
    private final MaterialService materialService;
    private final ReceiptService receiptService;
    private final IssueService issueService;
    private final FinanceService financeService;
    
    public List<StockSnapshotDTO> getStockSnapshot() {
        List<Material> materials = materialService.findActive();
        
        return materials.stream()
            .map(this::calculateMaterialStock)
            .collect(Collectors.toList());
    }
    
    private StockSnapshotDTO calculateMaterialStock(Material material) {
        List<Receipt> receipts = receiptService.findByMaterial(material.getId());
        List<Issue> issues = issueService.findByMaterial(material.getId());
        
        BigDecimal inQty = receipts.stream()
            .map(Receipt::getQtyKg)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal outQty = issues.stream()
            .map(Issue::getQtyKg)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal onHand = inQty.subtract(outQty);
        BigDecimal avgCost = calculateAvgCost(material.getId());
        BigDecimal invValue = onHand.multiply(avgCost);
        
        String status = onHand.compareTo(material.getMinLevel()) <= 0 ? "low" : "ok";
        
        return new StockSnapshotDTO(
            material.getId(),
            material.getCode(),
            material.getName(),
            onHand,
            avgCost,
            invValue,
            material.getMinLevel(),
            status
        );
    }
    
    public BigDecimal calculateAvgCost(String materialId) {
        List<Receipt> receipts = receiptService.findByMaterial(materialId);
        
        BigDecimal totalQty = receipts.stream()
            .map(Receipt::getQtyKg)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalCost = receipts.stream()
            .map(Receipt::getTotalCost)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        if (totalQty.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        
        return totalCost.divide(totalQty, 4, RoundingMode.HALF_UP);
    }
    
    public KpiDTO getKpi(LocalDate from, LocalDate to) {
        List<StockSnapshotDTO> stock = getStockSnapshot();
        
        BigDecimal onHandKg = stock.stream()
            .map(StockSnapshotDTO::getOnHandKg)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal invValue = stock.stream()
            .map(StockSnapshotDTO::getInventoryValue)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        ProfitLossDTO pl = getProfitLoss(from, to);
        
        return new KpiDTO(onHandKg, invValue, pl.getRevenue(), pl.getNetProfit());
    }
    
    public ProfitLossDTO getProfitLoss(LocalDate from, LocalDate to) {
        var finances = financeService.findByDateRange(from, to);
        var issues = issueService.findByDateRange(from, to);
        
        BigDecimal revenue = finances.stream()
            .filter(f -> f.getType() == com.backend.entity.Finance.FinanceType.income)
            .map(com.backend.entity.Finance::getAmountAzn)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal expenses = finances.stream()
            .filter(f -> f.getType() ==com.backend.entity.Finance.FinanceType.expense)
            .map(com.backend.entity.Finance::getAmountAzn)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal cogs = issues.stream()
            .map(Issue::getTotalCostUsed)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal gross = revenue.subtract(cogs);
        BigDecimal net = gross.subtract(expenses);
        
        return new ProfitLossDTO(revenue, cogs, gross, expenses, net);
    }
    
    public List<MaterialMovementDTO> getMaterialMovements() {
        List<Material> materials = materialService.findActive();
        
        return materials.stream()
            .map(this::calculateMovement)
            .collect(Collectors.toList());
    }
    
    private MaterialMovementDTO calculateMovement(Material material) {
        List<Receipt> receipts = receiptService.findByMaterial(material.getId());
        List<Issue> issues = issueService.findByMaterial(material.getId());
        
        BigDecimal inQty = receipts.stream()
            .map(Receipt::getQtyKg)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal inSum = receipts.stream()
            .map(Receipt::getTotalCost)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal outQty = issues.stream()
            .map(Issue::getQtyKg)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal outSum = issues.stream()
            .map(Issue::getTotalCostUsed)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return new MaterialMovementDTO(
            material.getId(),
            material.getCode(),
            material.getName(),
            inQty,
            inSum,
            outQty,
            outSum
        );
    }
}