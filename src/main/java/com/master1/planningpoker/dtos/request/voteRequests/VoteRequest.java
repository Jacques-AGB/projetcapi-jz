package com.master1.planningpoker.dtos.request.voteRequests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


/**
 * @class VoteRequest
 * @brief Représente une requête pour soumettre un vote dans le système de Planning Poker.
 *
 * Cette classe est utilisée pour encapsuler les informations nécessaires lorsqu'un joueur
 * soumet un vote pour une tâche (assignment) dans une partie.
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class VoteRequest {
    private Long playerId;
    private Long assignmentId;
    private int value;
}