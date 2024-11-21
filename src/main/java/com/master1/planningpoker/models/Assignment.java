package com.master1.planningpoker.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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
