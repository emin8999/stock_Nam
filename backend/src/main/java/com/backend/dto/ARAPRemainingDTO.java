package com.backend.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ARAPRemainingDTO {
    private String id;
    private String counterparty;
    private BigDecimal amount;
    private BigDecimal remaining;
    private String status;
}
