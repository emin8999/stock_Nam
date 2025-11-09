package com.backend.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "accounts_receivable")
@Data
public class AccountsReceivable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String counterparty;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(name = "amount_azn", nullable = false, precision = 15, scale = 2)
    private BigDecimal amountAzn;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.open;
    
    @Column(length = 1000)
    private String note;
    
    public enum PaymentStatus {
        open, partial, closed
    }
}
