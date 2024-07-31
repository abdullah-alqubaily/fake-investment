package com.afalqubaily.fakeinvestment.controllers;

import com.afalqubaily.fakeinvestment.services.AnalyzerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analyzer")
public class AnalyzerController {
    private final AnalyzerService analyzerService;

    public AnalyzerController(AnalyzerService analyzerService) {
        this.analyzerService = analyzerService;
    }

    @GetMapping("/trader-performance")
    public List<Map<String, Object>> analyze() {
        return analyzerService.getTraderPerformance();
    }
}
