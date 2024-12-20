package com.master1.planningpoker.dtos.request.assignmentRequest;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @class AddAssignmentRequest
 * @brief Représente une requête pour ajouter ou modifier une tâche (assignment) dans le jeu.
 *
 * Cette classe est utilisée pour encapsuler les données nécessaires lors de la création
 * ou de la modification d'une tâche (assignment) associée à un jeu. Les informations incluent
 * des détails sur la tâche, comme son libellé, sa description, sa difficulté et l'ID du jeu auquel
 * elle est liée.
 */
@Data
@Builder
public class AddAssignmentRequest {
    private Long id;
    private String libelle;

    public AddAssignmentRequest(Long id, String libelle, String description, Integer difficulty, Long gameId) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
        this.difficulty = difficulty;
        this.gameId = gameId;
    }

    private String description;
    private Integer difficulty;
    private Long gameId;

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

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public AddAssignmentRequest() {

    }
}
