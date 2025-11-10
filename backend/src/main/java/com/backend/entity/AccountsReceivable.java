package com.backend.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts_receivable")
@Data
public class AccountsReceivable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private String counterparty;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(name = "amount_azn", nullable = false)
    private BigDecimal amountAzn;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.open;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ar_id")
    private List<Payment> payments = new ArrayList<>();
    
    @Column(length = 1000)
    private String note;
    
    public enum Status {
        open, partial, closed
    }
}
