package com.master1.planningpoker.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


/**
 * @class Assignment
 * @brief Représente une tâche ou un élément de backlog dans un jeu de Planning Poker.
 *
 * La classe `Assignment` est utilisée pour modéliser une tâche qui fait partie d'un jeu,
 * avec un libellé, une description, une difficulté et une association avec un jeu spécifique.
 * Elle est également liée à plusieurs votes effectués par les joueurs pendant l'estimation de la tâche.
 */
@Entity
@Data
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String libelle;

    @Column(nullable = false)
    private String description;

    private Integer difficulty;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    @JsonIgnore
    private Game game;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> votes;
}
