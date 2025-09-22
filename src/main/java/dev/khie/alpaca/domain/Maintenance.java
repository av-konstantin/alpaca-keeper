package dev.khie.alpaca.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Maintenance {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    private LocalDate dueDate;

    @ManyToMany
    @JoinTable(
            name = "alpaca_maintenance",
            joinColumns = @JoinColumn(name = "maintenance_id"),
            inverseJoinColumns = @JoinColumn(name = "alpaca_id")
    )
    private Set<Alpaca> alpacas = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Set<Alpaca> getAlpacas() {
        return alpacas;
    }

    public void setAlpacas(Set<Alpaca> alpacas) {
        this.alpacas = alpacas;
    }
}
