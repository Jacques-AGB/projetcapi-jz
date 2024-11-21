package com.master1.planningpoker.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @OneToOne(mappedBy = "rule", cascade = CascadeType.ALL, orphanRemoval = true)
    private Game game;
}
