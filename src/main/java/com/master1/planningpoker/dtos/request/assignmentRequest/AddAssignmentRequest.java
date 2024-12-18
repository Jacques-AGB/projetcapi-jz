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
@AllArgsConstructor
public class AddAssignmentRequest {
    private Long id;
    private String libelle;
    private String description;
    private Integer difficulty;
    private Long gameId;

}
