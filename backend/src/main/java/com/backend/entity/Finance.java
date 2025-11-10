package com.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "finances")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Finance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FinanceType type;
    
    @Column(nullable = false)
    private String category;
    
    @Column(name = "amount_azn", nullable = false)
    private BigDecimal amountAzn;
    
    private String counterparty;
    
    @Column(length = 1000)
    private String note;
    
    public enum FinanceType {
        income, expense
    }
}
