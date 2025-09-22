package dev.khie.alpaca.repo;

import dev.khie.alpaca.domain.Alpaca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AlpacaRepository extends JpaRepository<Alpaca, UUID> {
    List<Alpaca> findAllByOrderByNameDesc();
}
