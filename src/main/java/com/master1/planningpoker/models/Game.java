package com.master1.planningpoker.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.scheduling.config.Task;

import java.util.List;

/**
 * @class Game
 * @brief Représente une partie de Planning Poker, un jeu où des joueurs estiment la difficulté des tâches.
 *
 * La classe `Game` est utilisée pour modéliser une partie de Planning Poker. Elle contient des informations sur le code du jeu,
 * le nombre maximum de joueurs, la liste des joueurs participants, un backlog de tâches à estimer, et les règles du jeu.
 */
@Entity
@Data
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private int maxPlayers;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> players;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assignment> backlog;

    @ManyToOne
    @JoinColumn(name = "rule_id", referencedColumnName = "id")
    private Rule rule;
}
