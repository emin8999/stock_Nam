package com.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "store_stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreStock {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "product_id", nullable = false, unique = true)
    private String productId;
    
    @Column(nullable = false)
    private BigDecimal quantity = BigDecimal.ZERO; 
}