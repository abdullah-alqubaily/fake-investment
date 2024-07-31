package com.afalqubaily.fakeinvestment.controllers;

import com.afalqubaily.fakeinvestment.services.QuoteService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuoteController {
    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }
}
