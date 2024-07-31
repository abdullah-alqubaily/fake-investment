package com.afalqubaily.fakeinvestment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FakeInvestmentApplication {

    public static void main(String[] args) {
		SpringApplication.run(FakeInvestmentApplication.class, args);
	}
}
