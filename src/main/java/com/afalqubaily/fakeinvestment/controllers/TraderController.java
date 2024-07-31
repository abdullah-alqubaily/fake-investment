package com.afalqubaily.fakeinvestment.controllers;

import com.afalqubaily.fakeinvestment.models.Trader;
import com.afalqubaily.fakeinvestment.services.TraderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/traders")
public class TraderController {

    private final TraderService traderService;

    public TraderController(TraderService traderService) {
        this.traderService = traderService;
    }

    @GetMapping
    public List<Trader> getTraders() {
        return traderService.getAllTraders();
    }
}
