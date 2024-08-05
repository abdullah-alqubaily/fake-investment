package com.afalqubaily.fakeinvestment.services;

import com.afalqubaily.fakeinvestment.models.Quote;
import com.afalqubaily.fakeinvestment.models.Trader;
import com.afalqubaily.fakeinvestment.models.Transaction;
import com.afalqubaily.fakeinvestment.repositories.TraderRepository;
import com.afalqubaily.fakeinvestment.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Service
public class TransactionService {

    private final TraderRepository traderRepository;
    private final TransactionRepository transactionRepository;
    private static final Logger logger = Logger.getLogger(TransactionService.class.getName());

    public TransactionService(
            TraderRepository traderRepository,
            TransactionRepository transactionRepository
    ) {
        this.traderRepository = traderRepository;
        this.transactionRepository = transactionRepository;
    }


    @Transactional
    public void buy(Trader trader, Quote quote) {
        int quantity = 1;
        double cost = quote.getPrice() * quantity;

        if (trader.getBalance() >= cost) {
            trader.setBalance(trader.getBalance() - cost);

            saveTraderAndRecordTransaction(
                    trader,
                    quote,
                    quantity,
                    Transaction.TransactionType.BUY
            );

            logger.info("Buy Transaction built successfully");
        }
    }

    @Transactional
    public void sell(Trader trader, Quote quote) {
        int quantity = 1;
        double revenue = quote.getPrice() * quantity;

        if (hasShares(trader, quote.getSymbol())) {
            trader.setBalance(trader.getBalance() + revenue);

            saveTraderAndRecordTransaction(
                    trader,
                    quote,
                    quantity,
                    Transaction.TransactionType.SELL
            );

            logger.info("Sell Transaction built successfully");
        }
    }

    private boolean hasShares(Trader trader, String symbol) {
        return transactionRepository.findByTraderAndSymbol(trader, symbol)
                .stream()
                .mapToInt(t -> t.getType() == Transaction.TransactionType.BUY ? t.getQuantity() : -t.getQuantity())
                .sum() > 0;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }


    private void saveTraderAndRecordTransaction(Trader trader, Quote quote, int quantity, Transaction.TransactionType type) {
        traderRepository.save(trader);

        Transaction transaction = new Transaction();
        transaction.setTrader(trader);
        transaction.setSymbol(quote.getSymbol());
        transaction.setPrice(quote.getPrice());
        transaction.setQuantity(quantity);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setType(type);
        transactionRepository.save(transaction);
    }

}
