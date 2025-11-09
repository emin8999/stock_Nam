package com.backend.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockDTO {
    
    private Long materialId;
    private String code;
    private String name;
    private BigDecimal onHand;
    private BigDecimal avgCost;
    private BigDecimal inventoryValue;
    private BigDecimal minLevel;
    private String status;
}
