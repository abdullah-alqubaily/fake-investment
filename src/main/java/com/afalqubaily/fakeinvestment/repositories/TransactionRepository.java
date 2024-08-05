package com.afalqubaily.fakeinvestment.repositories;

import com.afalqubaily.fakeinvestment.models.Trader;
import com.afalqubaily.fakeinvestment.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Find all transactions for a specific trader
    List<Transaction> findByTrader(Trader trader);

    // Find the top 10 most recent transactions for a symbol
    List<Transaction> findTop10BySymbolOrderByTimestampDesc(String symbol);

    // Find transactions for a specific trader and symbol
    List<Transaction> findByTraderAndSymbol(Trader trader, String symbol);
}
