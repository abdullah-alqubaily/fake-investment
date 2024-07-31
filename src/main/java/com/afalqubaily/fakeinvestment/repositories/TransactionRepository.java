package com.afalqubaily.fakeinvestment.repositories;

import com.afalqubaily.fakeinvestment.models.Trader;
import com.afalqubaily.fakeinvestment.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByTraderAndSymbol(Trader trader, String symbol);
    List<Transaction> findTop10BySymbolOrderByTimestampDesc(String symbol);

    int countByTrader(Trader trader);
    List<Transaction> findByTraderAndType(Trader trader, Transaction.TransactionType transactionType);
}
