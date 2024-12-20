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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public VoteResponse(Assignment assignment, Player player, int value, Long id) {
        this.assignment = assignment;
        this.player = player;
        this.value = value;
        this.id = id;
    }
}
