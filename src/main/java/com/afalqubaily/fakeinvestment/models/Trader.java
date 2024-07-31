package com.afalqubaily.fakeinvestment.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity(name = "Traders")
public class Trader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double balance;
    private double totalProfit;
    private double initialInvestment;
    private boolean isFirstTransaction = true;


    public Trader(Long id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.totalProfit = 0;
    }

    public Trader() {

    }

    public Trader(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public void setTotalProfit(double newTotalProfit) {
        this.totalProfit = newTotalProfit;
    }

    public double getTotalProfit() {
        return this.totalProfit;
    }

    public void updateTotalProfit(double profitFromTransaction) {
        this.totalProfit += profitFromTransaction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getInitialInvestment() {
        return initialInvestment;
    }

    public void setInitialInvestment(double initialInvestment) {
        this.initialInvestment = initialInvestment;
    }

    public boolean isFirstTransaction() {
        return isFirstTransaction;
    }

    public void setFirstTransaction(boolean firstTransaction) {
        isFirstTransaction = firstTransaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trader trader = (Trader) o;
        return Double.compare(balance, trader.balance) == 0 && Objects.equals(id, trader.id) && Objects.equals(name, trader.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, balance);
    }


}


