package com.backend.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "materials")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Material {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false, unique = true)
    private String code;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String uom = "kg";
    
    @Column(name = "min_level")
    private BigDecimal minLevel = BigDecimal.ZERO;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
}
