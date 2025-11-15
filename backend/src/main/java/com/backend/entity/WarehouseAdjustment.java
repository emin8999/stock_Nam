package com.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "warehouse_adjustments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseAdjustment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(name = "product_id", nullable = false)
    private String productId;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type; 
    
    @Column(nullable = false)
    private BigDecimal quantity;
    
    @Column(length = 1000)
    private String note;
    
    public enum Type {
        add, subtract
    }
}
