package com.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(name = "amount_azn", nullable = false)
    private BigDecimal amountAzn;
    
    @Column(length = 500)
    private String note;

    @Column(name = "ar_id")
    private String arId;

    @Column(name = "ap_id")
    private String apId; 

}
