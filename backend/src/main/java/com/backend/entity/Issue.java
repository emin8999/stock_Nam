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
@Table(name = "issues")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Issue {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(name = "material_id", nullable = false)
    private String materialId;
    
    @Column(name = "qty_kg", nullable = false)
    private BigDecimal qtyKg;
    
    @Column(name = "avg_cost_used", nullable = false)
    private BigDecimal avgCostUsed;
    
    @Column(name = "total_cost_used", nullable = false)
    private BigDecimal totalCostUsed;
    
    @Column(name = "job_ref")
    private String jobRef;
    
    @Column(length = 1000)
    private String note;
}

