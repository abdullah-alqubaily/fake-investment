package com.afalqubaily.fakeinvestment.repositories;

import com.afalqubaily.fakeinvestment.models.Trader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TraderRepository extends JpaRepository<Trader, Long> {
}
