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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Assignment> getBacklog() {
        return backlog;
    }

    public void setBacklog(List<Assignment> backlog) {
        this.backlog = backlog;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

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

    public Game(Rule rule, List<Assignment> backlog, List<Player> players, int maxPlayers, String code, Long id) {
        this.rule = rule;
        this.backlog = backlog;
        this.players = players;
        this.maxPlayers = maxPlayers;
        this.code = code;
        this.id = id;
    }
}
