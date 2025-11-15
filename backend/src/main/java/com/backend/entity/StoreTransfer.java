package com.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "store_transfers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreTransfer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(name = "product_id", nullable = false)
    private String productId;
    
    @Column(nullable = false)
    private BigDecimal quantity; 
    
    @Column(length = 500)
    private String note;
}

