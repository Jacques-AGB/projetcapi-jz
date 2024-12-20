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

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    private int value;

    public VoteRequest(Long playerId, Long assignmentId, int value) {
        this.playerId = playerId;
        this.assignmentId = assignmentId;
        this.value = value;
    }
}
