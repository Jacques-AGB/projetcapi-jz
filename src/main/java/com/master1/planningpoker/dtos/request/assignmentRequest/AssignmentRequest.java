package com.master1.planningpoker.dtos.request.assignmentRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AssignmentRequest {
    private String libelle;
    private String description;
    private Integer difficulty;
    private Long gameId;

    public AssignmentRequest() {

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

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public AssignmentRequest(String libelle, String description, Integer difficulty, Long gameId) {
        this.libelle = libelle;
        this.description = description;
        this.difficulty = difficulty;
        this.gameId = gameId;
    }
}
