package com.master1.planningpoker.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.scheduling.config.Task;

import java.util.List;

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
