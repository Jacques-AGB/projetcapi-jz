package com.master1.planningpoker.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @class Player
 * @brief Représente un joueur dans une partie de Planning Poker.
 *
 * La classe `Player` modélise un joueur qui participe à une partie de Planning Poker. Elle contient des informations
 * sur le pseudonyme du joueur, son rôle (administrateur ou non), ainsi que la partie à laquelle il est associé.
 * Un joueur peut participer à une ou plusieurs parties et être l'administrateur d'une partie.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String pseudo;

    @Column(nullable = false)
    private boolean isAdmin;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = true)
    @JsonIgnore
    private Game game;
}

