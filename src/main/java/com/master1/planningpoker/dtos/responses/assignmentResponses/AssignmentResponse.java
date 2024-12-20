package com.master1.planningpoker.dtos.responses.assignmentResponses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.master1.planningpoker.models.Game;
import com.master1.planningpoker.models.Vote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;


/**
 * @class AssignmentResponse
 * @brief Représente la réponse d'une tâche (assignment) dans le système.
 *
 * Cette classe est utilisée pour encapsuler les informations détaillées d'une tâche, y compris son
 * libellé, sa description, sa difficulté et les votes associés à cette tâche.
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AssignmentResponse {
    private Long id;

    private String libelle;

    private String description;

    private Integer difficulty;

    @JsonIgnore
    private Game game;

    private List<Vote> votes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public AssignmentResponse(Long id, String libelle, String description, Integer difficulty, Game game, List<Vote> votes) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
        this.difficulty = difficulty;
        this.game = game;
        this.votes = votes;
    }
}
