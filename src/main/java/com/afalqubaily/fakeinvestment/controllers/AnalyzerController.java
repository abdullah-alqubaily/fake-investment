package com.afalqubaily.fakeinvestment.controllers;

import com.afalqubaily.fakeinvestment.models.Trader;
import com.afalqubaily.fakeinvestment.repositories.TraderRepository;
import com.afalqubaily.fakeinvestment.services.AnalyzerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/analyzer")
public class AnalyzerController {

    private final AnalyzerService analyzerService;
    private final TraderRepository traderRepository;

    public AnalyzerController(AnalyzerService analyzerService, TraderRepository traderRepository) {
        this.analyzerService = analyzerService;
        this.traderRepository = traderRepository;
    }

    @GetMapping("/traders")
    public ResponseEntity<List<Map<String, Object>>> getAllTradersAnalysis() {
        List<Trader> traders = traderRepository.findAll();
        List<Map<String, Object>> analysisResults = traders.stream()
                .map(analyzerService::analyzeTrader)
                .collect(Collectors.toList());
        return ResponseEntity.ok(analysisResults);
    }

    @GetMapping("/trader/{traderId}")
    public ResponseEntity<Map<String, Object>> getTraderAnalysis(@PathVariable Long traderId) {
        Trader trader = traderRepository.findById(traderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trader not found"));
        Map<String, Object> analysis = analyzerService.analyzeTrader(trader);
        return ResponseEntity.ok(analysis);
    }
}
