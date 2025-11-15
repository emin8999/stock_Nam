package com.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "store_sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreSale {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(name = "product_id", nullable = false)
    private String productId;
    
    @Column(nullable = false)
    private BigDecimal quantity; 
    
    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice; 
    
    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount; 
    
    private String customer; 
    
    @Column(length = 500)
    private String note;
}
