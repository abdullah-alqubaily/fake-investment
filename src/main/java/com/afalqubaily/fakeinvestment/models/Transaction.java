package com.afalqubaily.fakeinvestment.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "Transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Trader trader;
    private String symbol;
    private double price;
    private int quantity;
    private LocalDateTime timestamp;
    private TransactionType type;

    public Transaction() {

    }

    public Transaction(Trader trader, double price, int quantity) {
        this.trader = trader;
        this.price = price;
        this.quantity = quantity;
    }

    public enum TransactionType {
        BUY, SELL
    }

    public Transaction(Long id, Trader trader, String symbol, double price, int quantity, LocalDateTime timestamp, TransactionType type) {
        this.id = id;
        this.trader = trader;
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
        this.timestamp = timestamp;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Trader getTrader() {
        return trader;
    }

    public void setTrader(Trader trader) {
        this.trader = trader;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(price, that.price) == 0 && quantity == that.quantity && Objects.equals(id, that.id) && Objects.equals(trader, that.trader) && Objects.equals(symbol, that.symbol) && Objects.equals(timestamp, that.timestamp) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trader, symbol, price, quantity, timestamp, type);
    }


}
