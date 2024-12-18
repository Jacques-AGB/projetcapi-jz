package com.master1.planningpoker.dtos.responses.voteResponses;


import com.master1.planningpoker.models.Assignment;
import com.master1.planningpoker.models.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @class VoteResponse
 * @brief Représente la réponse détaillée d'un vote effectué par un joueur sur une tâche.
 *
 * Cette classe encapsule les informations relatives à un vote spécifique,
 * telles que la valeur du vote, le joueur ayant voté, et la tâche (assignment)
 * sur laquelle le vote a été effectué.
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class VoteResponse {
    private Long id;

    private int value;

    private Player player;

    private Assignment assignment;
}
