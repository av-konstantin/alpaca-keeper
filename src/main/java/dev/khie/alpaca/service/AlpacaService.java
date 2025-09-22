package dev.khie.alpaca.service;

import dev.khie.alpaca.domain.Alpaca;
import dev.khie.alpaca.repo.AlpacaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AlpacaService {
    private final AlpacaRepository repository;
    private final JdbcTemplate jdbcTemplate;

    public AlpacaService(AlpacaRepository repository, JdbcTemplate jdbcTemplate) {
        this.repository = repository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(readOnly = true)
    public List<Alpaca> listAll() {
        return repository.findAllByOrderByNameDesc();
    }

    @Transactional
    public Alpaca add(String name) {
        Alpaca m = new Alpaca();
        m.setName(name);
        Alpaca saved = repository.saveAndFlush(m);

        Map<String, LocalDate> schedule = Map.of(
                "Annual Shearing", LocalDate.now().plusMonths(8),
                "Hoof Trimming", LocalDate.now().plusMonths(3),
                "Vaccination: CDT", LocalDate.now().plusYears(1),
                "Deworming (FAMACHA)", LocalDate.now().plusMonths(3),
                "Teeth Check/Trimming", LocalDate.now().plusMonths(10),
                "Vitamin D Injection", LocalDate.now().plusMonths(2),
                "Body Condition Scoring", LocalDate.now().plusMonths(1),
                "Pasture Rotation Setup", LocalDate.now().plusWeeks(2),
                "Fecal Egg Count Test", LocalDate.now().plusMonths(2),
                "Halters/Fit Check", LocalDate.now().plusMonths(1)
        );

        UUID alpacaId = saved.getId();

        schedule.forEach((maintenanceName, dueDate) -> {
            UUID maintenanceId = jdbcTemplate.queryForObject(
                    "SELECT id FROM maintenance WHERE name = ?",
                    (rs, rowNum) -> (UUID) rs.getObject("id"),
                    maintenanceName
            );
            jdbcTemplate.update(
                    "INSERT INTO alpaca_maintenance (maintenance_id, alpaca_id, due_date) VALUES (?, ?, ?)",
                    maintenanceId, alpacaId, dueDate
            );
        });

        return saved;
    }
}
