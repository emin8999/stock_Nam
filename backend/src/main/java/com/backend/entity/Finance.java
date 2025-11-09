package com.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "finances")
@Data
public class Finance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FinanceType type;
    
    private String category;
    
    @Column(name = "amount_azn", nullable = false, precision = 15, scale = 2)
    private BigDecimal amountAzn;
    
    private String counterparty;
    
    @Column(length = 1000)
    private String note;
    
    public enum FinanceType {
        income, expense
    }
}
