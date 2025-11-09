package com.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "settings")
@Data
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "low_stock_global", precision = 10, scale = 3)
    private BigDecimal lowStockGlobal = BigDecimal.valueOf(5);
}
