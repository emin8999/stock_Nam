package com.backend.dto;

import java.math.BigDecimal;
import java.util.List;

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
public class ARAPDetailDTO {
    private Long id;
    private String counterparty;
    private String date;
    private BigDecimal amountAzn;
    private BigDecimal remaining;
    private String status;
    private String note;
    private List<PaymentDTO> payments;
}
