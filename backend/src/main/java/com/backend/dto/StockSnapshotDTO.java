package com.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockSnapshotDTO {
    private String materialId;
    private String code;
    private String name;
    private BigDecimal onHandKg;
    private BigDecimal avgCostPerKg;
    private BigDecimal inventoryValue;
    private BigDecimal minLevel;
    private String status; 
}