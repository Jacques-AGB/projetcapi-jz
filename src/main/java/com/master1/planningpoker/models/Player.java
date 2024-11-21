package com.master1.planningpoker.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String pseudo;

    @Column(nullable = false)
    private boolean isAdmin;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;
}

