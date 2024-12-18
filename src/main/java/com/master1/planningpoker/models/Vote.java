package com.master1.planningpoker.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;



/**
 * @class Vote
 * @brief Représente un vote effectué par un joueur pour une tâche (assignment) dans une partie de Planning Poker.
 *
 * La classe `Vote` représente un vote effectué par un joueur pour une tâche (assignment) dans un jeu de Planning Poker.
 * Chaque vote contient une valeur (par exemple, une estimation de la tâche), ainsi que des références à l'entité `Player` (le joueur qui a voté) et à l'entité `Assignment` (la tâche pour laquelle le vote a été effectué).
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int value;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;
}
