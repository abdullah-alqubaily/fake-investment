package com.afalqubaily.fakeinvestment.services;

import com.afalqubaily.fakeinvestment.models.Trader;
import com.afalqubaily.fakeinvestment.models.Transaction;
import com.afalqubaily.fakeinvestment.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalyzerService {

    private final TransactionRepository transactionRepository;

    public AnalyzerService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Map<String, Object> analyzeTrader(Trader trader) {
        List<Transaction> transactions = transactionRepository.findByTrader(trader);

        Map<String, List<Transaction>> transactionsBySymbol = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getSymbol));

        double totalProfit = 0;
        int completedTrades = 0;
        int totalTransactions = transactions.size();

        for (List<Transaction> symbolTransactions : transactionsBySymbol.values()) {
            totalProfit += calculateProfitForSymbol(symbolTransactions);
            completedTrades += countCompletedTrades(symbolTransactions);
        }

        double totalInvested = calculateTotalInvested(transactions);
        double totalProfitPercentage = (totalProfit / totalInvested) * 100;
        double averageProfitPerTrade = completedTrades > 0 ? totalProfit / completedTrades : 0;

        Map<String, Object> analysis = new HashMap<>();
        analysis.put("traderName", trader.getName());
        analysis.put("totalProfitPercentage", formatPercentage(totalProfitPercentage));
        analysis.put("averageProfitPerTrade", formatCurrency(averageProfitPerTrade));
        analysis.put("completedTrades", completedTrades);
        analysis.put("totalTransactions", totalTransactions);
        analysis.put("totalProfit", formatCurrency(totalProfit));

        return analysis;
    }

    private double calculateProfitForSymbol(List<Transaction> transactions) {
        double totalBuyCost = 0;
        int totalBuyQuantity = 0;
        double totalSellRevenue = 0;
        int totalSellQuantity = 0;

        for (Transaction t : transactions) {
            if (t.getType() == Transaction.TransactionType.BUY) {
                totalBuyCost += t.getPrice() * t.getQuantity();
                totalBuyQuantity += t.getQuantity();
            } else {
                totalSellRevenue += t.getPrice() * t.getQuantity();
                totalSellQuantity += t.getQuantity();
            }
        }

        int closedQuantity = Math.min(totalBuyQuantity, totalSellQuantity);
        double avgBuyPrice = totalBuyQuantity > 0 ? totalBuyCost / totalBuyQuantity : 0;
        double avgSellPrice = totalSellQuantity > 0 ? totalSellRevenue / totalSellQuantity : 0;

        return (avgSellPrice - avgBuyPrice) * closedQuantity;
    }

    private int countCompletedTrades(List<Transaction> transactions) {
        int buyQuantity = 0;
        int sellQuantity = 0;
        for (Transaction t : transactions) {
            if (t.getType() == Transaction.TransactionType.BUY) {
                buyQuantity += t.getQuantity();
            } else {
                sellQuantity += t.getQuantity();
            }
        }
        return Math.min(buyQuantity, sellQuantity);
    }

    private double calculateTotalInvested(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getType() == Transaction.TransactionType.BUY)
                .mapToDouble(t -> t.getPrice() * t.getQuantity())
                .sum();
    }

    private String formatPercentage(double value) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value) + "%";
    }

    private String formatCurrency(double value) {
        DecimalFormat df = new DecimalFormat("0.00");
        return "$" + df.format(value);
    }
}