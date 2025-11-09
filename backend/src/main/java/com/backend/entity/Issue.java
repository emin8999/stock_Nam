package com.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "issues")
@Data
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(name = "material_id", nullable = false)
    private Long materialId;
    
    @Column(name = "qty_kg", nullable = false, precision = 12, scale = 3)
    private BigDecimal qtyKg;
    
    @Column(name = "avg_cost_used", precision = 12, scale = 2)
    private BigDecimal avgCostUsed;
    
    @Column(name = "total_cost_used", precision = 15, scale = 2)
    private BigDecimal totalCostUsed;
    
    @Column(name = "job_ref")
    private String jobRef;
    
    @Column(length = 1000)
    private String note;
}
