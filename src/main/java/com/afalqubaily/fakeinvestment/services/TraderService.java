package com.afalqubaily.fakeinvestment.services;

import com.afalqubaily.fakeinvestment.models.Quote;
import com.afalqubaily.fakeinvestment.models.Trader;
import com.afalqubaily.fakeinvestment.models.Transaction;
import com.afalqubaily.fakeinvestment.repositories.TraderRepository;
import com.afalqubaily.fakeinvestment.repositories.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class TraderService {

    private final TraderRepository traderRepository;
    private final TransactionService transactionService;
    private final Map<Long, Double> lastPrices = new HashMap<>();
    private static final Logger logger = Logger.getLogger(TraderService.class.getName());
    private final TransactionRepository transactionRepository;

    public TraderService(TraderRepository traderRepository, TransactionService transactionService, TransactionRepository transactionRepository) {
        this.traderRepository = traderRepository;
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
    }

    @PostConstruct
    public void initializeTraders() {
        if (traderRepository.count() == 0) {
            traderRepository.save(new Trader("Value Investor", 10000.0));
            traderRepository.save(new Trader("Momentum Trader", 10000.0));
            traderRepository.save(new Trader("Contrarian", 10000.0));
            traderRepository.save(new Trader("Mean Reversion Trader", 10000.0));
            traderRepository.save(new Trader("Technical Analyst", 10000.0));
        }
    }

    @KafkaListener(topics = "stock-quotes", groupId = "trader-group")
    public void processQuote(String quoteJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Quote quote = objectMapper.readValue(quoteJson, Quote.class);

        for (Trader trader : traderRepository.findAll()) {
            logger.info("Processing trader " + trader.getName() + trader.getBalance());
            if (shouldBuy(trader, quote)) {
                logger.info("Buying trader " + trader.getName() + trader.getBalance());
                transactionService.buy(trader, quote);
            } else if (shouldSell(trader, quote)) {
                logger.info("Selling trader " + trader.getName() + trader.getBalance());
                transactionService.sell(trader, quote);
            }
            lastPrices.put(trader.getId(), quote.getPrice());
        }
    }

    private boolean shouldSell(Trader trader, Quote quote) {
        switch (trader.getName()) {
            case "Value Investor" -> { return quote.getPrice() > 150; }
            case "Momentum Trader" -> {
                return quote.getPrice() < (lastPrices.getOrDefault(trader.getId(), Double.MAX_VALUE) * 0.95);
            }
            case "Contrarian" -> {
                return quote.getPrice() < (lastPrices.getOrDefault(trader.getId(), 0.0) * 1.05);
            }
            case "Mean Reversion Trader" -> { return quote.getPrice() > 130; }
            case "Technical Analyst" -> { return quote.getPrice() < getSimpleMovingAverage(quote.getSymbol()); }
            case null, default -> { return false; }
        }
    }

    private boolean shouldBuy(Trader trader, Quote quote) {
        switch (trader.getName()) {
            case "Value Investor" -> { return quote.getPrice() < 100; }
            case "Momentum Trader" -> {
                return quote.getPrice() > (lastPrices.getOrDefault(trader.getId(), 0.0) * 1.05);
            }
            case "Contrarian" -> {
                return quote.getPrice() < (lastPrices.getOrDefault(trader.getId(), Double.MAX_VALUE) * 0.95);
            }
            case "Mean Reversion Trader" -> { return quote.getPrice() < 120; }
            case "Technical Analyst" -> { return quote.getPrice() > getSimpleMovingAverage(quote.getSymbol()); }
            case null, default -> { return false; }
        }
    }

    public List<Trader> getAllTraders() {
        return traderRepository.findAll();
    }

    private double getSimpleMovingAverage(String symbol) {
        return transactionRepository.findTop10BySymbolOrderByTimestampDesc(symbol)
                .stream()
                .mapToDouble(Transaction::getPrice)
                .average()
                .orElse(0.0);
    }
}
