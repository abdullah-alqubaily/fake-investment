package com.afalqubaily.fakeinvestment.services;

import com.afalqubaily.fakeinvestment.models.Quote;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class QuoteService {

    private final KafkaTemplate<String, Quote> kafkaTemplate;
    private final Random random = new Random();
    private final List<String> companies = List.of("AAPL", "GOOGL", "MSFT", "AMZN", "FB");
    private final Map<String, Double> lastPrices = new HashMap<>();

    public QuoteService(KafkaTemplate<String, Quote> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        for (String company : companies) {
            lastPrices.put(company, 100.0); // All stocks starts at $100
        }
    }

    /**
     * This method generates quotes every 5 seconds and send them to stock-quote topic.
     */
    @Scheduled(fixedRate = 5000)
    public void generateQuotes() {
        for (String company : companies) {
            Quote quote = new Quote();
            quote.setSymbol(company);
            quote.setPrice(generatePrice(company));
            quote.setTimestamp(LocalDateTime.now());

            kafkaTemplate.send("stock-quotes", quote);

        }
    }

    /**
     * This method
     * @param company the company symbol
     * @return the new price
     */
    private double generatePrice(String company) {
        double lastPrice = lastPrices.get(company);
        double changePercentage = generateChangePercentage();
        double newPrice = lastPrice * (1 + changePercentage);

        if (random.nextInt(100) == 0) {
            newPrice *= ThreadLocalRandom.current().nextDouble(0.5, 2.0);
        }

        newPrice = Math.max(1, newPrice);

        newPrice = Math.round(newPrice * 100.0) / 100.0;

        lastPrices.put(company, newPrice);
        return newPrice;
    }

    private double generateChangePercentage() {
        double baseChange = (random.nextDouble() - 0.5) * 0.1;

        if (random.nextInt(10) == 0) {
            baseChange *= ThreadLocalRandom.current().nextDouble(2, 5);
        }

        return baseChange;
    }
}
