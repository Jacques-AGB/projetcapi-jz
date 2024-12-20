package com.master1.planningpoker.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * @class Rule
 * @brief Représente une règle associée à une partie de Planning Poker.
 *
 * La classe `Rule` représente une règle qui peut être appliquée à une partie de Planning Poker.
 * Chaque partie peut être associée à une règle spécifique, et cette règle peut définir la manière dont le jeu sera joué.
 * Une règle a simplement un nom qui la décrit.
 */
@Entity
@Data
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
