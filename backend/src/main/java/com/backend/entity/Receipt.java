package com.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "receipts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(name = "material_id", nullable = false)
    private String materialId;
    
    @Column(name = "qty_kg", nullable = false)
    private BigDecimal qtyKg;
    
    @Column(name = "price_per_kg", nullable = false)
    private BigDecimal pricePerKg;
    
    @Column(name = "total_cost", nullable = false)
    private BigDecimal totalCost;
    
    private String supplier;
    
    @Column(length = 1000)
    private String note;
}

