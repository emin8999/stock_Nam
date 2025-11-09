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
public class ProfitLossDTO {
    private BigDecimal revenue;
    private BigDecimal cogs;
    private BigDecimal gross;
    private BigDecimal expenses;
    private BigDecimal net;
}