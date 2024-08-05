package com.afalqubaily.fakeinvestment.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@Setter
@Entity(name = "Transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "trader_id")
    private Trader trader;

    private String symbol;
    private double price;
    private int quantity;
    private LocalDateTime timestamp;

    @Enumerated(EnumType.ORDINAL)
    private TransactionType type;

    public Transaction() {

    }

    public enum TransactionType {
        BUY, SELL
    }

}
