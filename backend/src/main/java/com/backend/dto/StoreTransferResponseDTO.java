package com.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

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
public class StoreTransferResponseDTO {

    private String id;
    private String productId;
    private String productCode;
    private String productName;
    private String category;
    private BigDecimal quantity;
    private LocalDate date;
    private String note;
}
