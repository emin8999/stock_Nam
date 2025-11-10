package com.backend.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialMovementDTO {
    private String materialId;
    private String code;
    private String name;
    private BigDecimal inQty;
    private BigDecimal inSum;
    private BigDecimal outQty;
    private BigDecimal outSum;
}
