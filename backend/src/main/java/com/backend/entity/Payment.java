package com.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "reference_id", nullable = false)
    private Long referenceId;
    
    @Column(name = "reference_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReferenceType referenceType;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(name = "amount_azn", nullable = false, precision = 15, scale = 2)
    private BigDecimal amountAzn;
    
    @Column(length = 500)
    private String note;
    
    public enum ReferenceType {
        AR, AP
    }
}
