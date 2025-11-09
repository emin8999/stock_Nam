package com.backend.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.backend.dto.KpiDTO;
import com.backend.dto.PaymentDTO;
import com.backend.dto.ProfitLossDTO;
import com.backend.dto.StockDTO;
import com.backend.entity.AccountsPayable;
import com.backend.entity.AccountsReceivable;
import com.backend.entity.Finance;
import com.backend.entity.Issue;
import com.backend.entity.Material;
import com.backend.entity.Payment;
import com.backend.entity.Receipt;
import com.backend.entity.Settings;
import com.backend.repository.AccountsPayableRepository;
import com.backend.repository.AccountsReceivableRepository;
import com.backend.repository.FinanceRepository;
import com.backend.repository.IssueRepository;
import com.backend.repository.MaterialRepository;
import com.backend.repository.PaymentRepository;
import com.backend.repository.ReceiptRepository;
import com.backend.repository.SettingsRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MaterialService {
    
    private final MaterialRepository materialRepository;
    private final ReceiptRepository receiptRepository;
    private final IssueRepository issueRepository;
    private final FinanceRepository financeRepository;
    private final AccountsReceivableRepository arRepository;
    private final AccountsPayableRepository apRepository;
    private final PaymentRepository paymentRepository;
    private final SettingsRepository settingsRepository;
    
    
    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }
    
    public Material createMaterial(Material material) {
        if (materialRepository.existsByCode(material.getCode())) {
            throw new RuntimeException("Material code already exists");
        }
        return materialRepository.save(material);
    }
    
    public Material toggleMaterialStatus(Long id) {
        Material material = materialRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Material not found"));
        material.setIsActive(!material.getIsActive());
        return materialRepository.save(material);
    }
    
   
    public List<Receipt> getAllReceipts() {
        return receiptRepository.findAllByOrderByDateDesc();
    }
    
    @Transactional
    public Receipt createReceipt(Receipt receipt) {
        receipt.setTotalCost(receipt.getQtyKg().multiply(receipt.getPricePerKg()));
        return receiptRepository.save(receipt);
    }
    
   
    public List<Issue> getAllIssues() {
        return issueRepository.findAllByOrderByDateDesc();
    }
    
    @Transactional
    public Issue createIssue(Issue issue) {
        BigDecimal avgCost = calculateAvgCost(issue.getMaterialId());
        issue.setAvgCostUsed(avgCost);
        issue.setTotalCostUsed(avgCost.multiply(issue.getQtyKg()));
        return issueRepository.save(issue);
    }
    
 
    public List<StockDTO> getStockSnapshot() {
        List<Material> materials = materialRepository.findByIsActiveTrue();
        return materials.stream().map(mat -> {
            StockDTO dto = new StockDTO();
            dto.setMaterialId(mat.getId());
            dto.setCode(mat.getCode());
            dto.setName(mat.getName());
            dto.setMinLevel(mat.getMinLevel());
            
            BigDecimal inQty = receiptRepository.findByMaterialId(mat.getId())
                .stream()
                .map(Receipt::getQtyKg)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
                
            BigDecimal outQty = issueRepository.findByMaterialId(mat.getId())
                .stream()
                .map(Issue::getQtyKg)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
                
            BigDecimal onHand = inQty.subtract(outQty);
            BigDecimal avgCost = calculateAvgCost(mat.getId());
            BigDecimal invValue = onHand.multiply(avgCost);
            
            dto.setOnHand(onHand);
            dto.setAvgCost(avgCost);
            dto.setInventoryValue(invValue);
            dto.setStatus(onHand.compareTo(mat.getMinLevel()) <= 0 ? "LOW" : "OK");
            
            return dto;
        }).collect(Collectors.toList());
    }
    
    private BigDecimal calculateAvgCost(Long materialId) {
        List<Receipt> receipts = receiptRepository.findByMaterialId(materialId);
        
        BigDecimal totalQty = receipts.stream()
            .map(Receipt::getQtyKg)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        BigDecimal totalCost = receipts.stream()
            .map(Receipt::getTotalCost)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        if (totalQty.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        
        return totalCost.divide(totalQty, 2, RoundingMode.HALF_UP);
    }
    
   
    public KpiDTO getKPI() {
        List<StockDTO> stock = getStockSnapshot();
        
        BigDecimal onHandKg = stock.stream()
            .map(StockDTO::getOnHand)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        BigDecimal invValue = stock.stream()
            .map(StockDTO::getInventoryValue)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        KpiDTO kpi = new KpiDTO();
        kpi.setOnHandKg(onHandKg);
        kpi.setInventoryValue(invValue);
        
        return kpi;
    }

    public List<Finance> getAllFinances() {
        return financeRepository.findAllByOrderByDateDesc();
    }
    
    public Finance createFinance(Finance finance) {
        return financeRepository.save(finance);
    }
    
   
    public ProfitLossDTO getProfitLoss(LocalDate from, LocalDate to) {
        List<Finance> finances = financeRepository.findByDateBetweenOrderByDateDesc(from, to);
        
        BigDecimal revenue = finances.stream()
            .filter(f -> f.getType() == Finance.FinanceType.income)
            .map(Finance::getAmountAzn)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        BigDecimal expenses = finances.stream()
            .filter(f -> f.getType() == Finance.FinanceType.expense)
            .map(Finance::getAmountAzn)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        List<Issue> issues = issueRepository.findAll().stream()
            .filter(i -> !i.getDate().isBefore(from) && !i.getDate().isAfter(to))
            .collect(Collectors.toList());
            
        BigDecimal cogs = issues.stream()
            .map(Issue::getTotalCostUsed)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        ProfitLossDTO pl = new ProfitLossDTO();
        pl.setRevenue(revenue);
        pl.setCogs(cogs);
        pl.setGross(revenue.subtract(cogs));
        pl.setExpenses(expenses);
        pl.setNet(revenue.subtract(cogs).subtract(expenses));
        
        return pl;
    }
    
   
    public List<AccountsReceivable> getAllAR() {
        return arRepository.findAllByOrderByDateDesc();
    }
    
    public AccountsReceivable createAR(AccountsReceivable ar) {
        return arRepository.save(ar);
    }
    
    public List<AccountsPayable> getAllAP() {
        return apRepository.findAllByOrderByDateDesc();
    }
    
    public AccountsPayable createAP(AccountsPayable ap) {
        return apRepository.save(ap);
    }
    
    @Transactional
    public void addARPayment(Long arId, BigDecimal amount, LocalDate date) {
        AccountsReceivable ar = arRepository.findById(arId)
            .orElseThrow(() -> new RuntimeException("AR not found"));
            
        Payment payment = new Payment();
        payment.setReferenceId(arId);
        payment.setReferenceType(Payment.ReferenceType.AR);
        payment.setDate(date);
        payment.setAmountAzn(amount);
        paymentRepository.save(payment);
        
        BigDecimal totalPaid = getARPayments(arId).stream()
            .map(PaymentDTO::getAmountAzn)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        BigDecimal remaining = ar.getAmountAzn().subtract(totalPaid);
        
        if (remaining.compareTo(BigDecimal.ZERO) <= 0) {
            ar.setStatus(AccountsReceivable.PaymentStatus.closed);
        } else {
            ar.setStatus(AccountsReceivable.PaymentStatus.partial);
        }
        arRepository.save(ar);
    }
    
    @Transactional
    public void addAPPayment(Long apId, BigDecimal amount, LocalDate date) {
        AccountsPayable ap = apRepository.findById(apId)
            .orElseThrow(() -> new RuntimeException("AP not found"));
            
        Payment payment = new Payment();
        payment.setReferenceId(apId);
        payment.setReferenceType(Payment.ReferenceType.AP);
        payment.setDate(date);
        payment.setAmountAzn(amount);
        paymentRepository.save(payment);
        
        BigDecimal totalPaid = getAPPayments(apId).stream()
            .map(PaymentDTO::getAmountAzn)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        BigDecimal remaining = ap.getAmountAzn().subtract(totalPaid);
        
        if (remaining.compareTo(BigDecimal.ZERO) <= 0) {
            ap.setStatus(AccountsPayable.PaymentStatus.closed);
        } else {
            ap.setStatus(AccountsPayable.PaymentStatus.partial);
        }
        apRepository.save(ap);
    }
    
    public List<PaymentDTO> getARPayments(Long arId) {
        return paymentRepository.findByReferenceIdAndReferenceType(arId, Payment.ReferenceType.AR)
            .stream()
            .map(p -> {
                PaymentDTO dto = new PaymentDTO();
                dto.setDate(p.getDate().toString());
                dto.setAmountAzn(p.getAmountAzn());
                dto.setNote(p.getNote());
                return dto;
            })
            .collect(Collectors.toList());
    }
    
    public List<PaymentDTO> getAPPayments(Long apId) {
        return paymentRepository.findByReferenceIdAndReferenceType(apId, Payment.ReferenceType.AP)
            .stream()
            .map(p -> {
                PaymentDTO dto = new PaymentDTO();
                dto.setDate(p.getDate().toString());
                dto.setAmountAzn(p.getAmountAzn());
                dto.setNote(p.getNote());
                return dto;
            })
            .collect(Collectors.toList());
    }
    
   
    public Settings getSettings() {
        return settingsRepository.findAll().stream()
            .findFirst()
            .orElseGet(() -> {
                Settings s = new Settings();
                return settingsRepository.save(s);
            });
    }
    
    public Settings updateSettings(Settings settings) {
        Settings existing = getSettings();
        existing.setLowStockGlobal(settings.getLowStockGlobal());
        return settingsRepository.save(existing);
    }
}
