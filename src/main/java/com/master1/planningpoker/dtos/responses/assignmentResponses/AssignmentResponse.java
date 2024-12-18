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
}
