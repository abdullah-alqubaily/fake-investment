package com.afalqubaily.fakeinvestment.services;

import com.afalqubaily.fakeinvestment.models.Trader;
import com.afalqubaily.fakeinvestment.models.Transaction;
import com.afalqubaily.fakeinvestment.repositories.TraderRepository;
import com.afalqubaily.fakeinvestment.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalyzerService {

    private final TraderRepository traderRepository;
    private final TransactionRepository transactionRepository;

    public AnalyzerService(TraderRepository traderRepository, TransactionRepository transactionRepository) {
        this.traderRepository = traderRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<Map<String, Object>> getTraderPerformance() {
        List<Trader> traders = traderRepository.findAll();
        return traders.stream()
                .map(trader -> {
                    Map<String, Object> performance = new HashMap<>();
                    double totalValue = trader.getBalance() + trader.getTotalProfit();
                    int totalTrades = calculateTotalTrades(trader);
                    double averageProfitPerTrade = calculateAverageProfitPerTrade(trader);
                    double profitPercentage = calculateProfitPercentage(trader);

                    performance.put("name", trader.getName());
                    performance.put("totalProfit", trader.getTotalProfit());
                    performance.put("balance", trader.getBalance());
                    performance.put("totalValue", totalValue);
                    performance.put("profitPercentage", profitPercentage);
                    performance.put("averageProfitPerTrade", averageProfitPerTrade);
                    performance.put("totalTrades", totalTrades);
                    return performance;
                })
                .sorted((a, b) -> Double.compare((Double) b.get("totalValue"), (Double) a.get("totalValue")))
                .collect(Collectors.toList());
    }


    private int calculateTotalTrades(Trader trader) {
        return transactionRepository.countByTrader(trader);
    }

    private double calculateAverageProfitPerTrade(Trader trader) {
        int totalTrades = calculateTotalTrades(trader);
        return totalTrades > 0 ? trader.getTotalProfit() / totalTrades : 0;
    }

    private double calculateProfitPercentage(Trader trader) {
        List<Transaction> buyTransactions = transactionRepository.findByTraderAndType(trader, Transaction.TransactionType.BUY);
        double totalInvestment = buyTransactions.stream()
                .mapToDouble(t -> t.getPrice() * t.getQuantity())
                .sum();

        return totalInvestment > 0 ? (trader.getTotalProfit() / totalInvestment) * 100 : 0;
    }
}
