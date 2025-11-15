package com.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseStockDTO {
    private String productId;
    private String productCode;
    private String productName;
    private String category;
    private BigDecimal quantity;
}
