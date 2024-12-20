package com.master1.planningpoker.dtos.responses.gameResponses;


import com.master1.planningpoker.models.Assignment;
import com.master1.planningpoker.models.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;


/**
 * @class GameResponse
 * @brief Représente la réponse détaillée d'un jeu.
 *
 * Cette classe encapsule les informations principales d'un jeu, telles que son identifiant, son code,
 * les joueurs associés et les tâches (assignments) dans le backlog du jeu.
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class GameResponse {

    private Long id;
    private String code;
    private int maxPlayers;
    private Long ruleId;
    private List<Player> players; // Pseudos des joueurs
    private List<Assignment> backlog;

    public GameResponse(Long id, String code, int maxPlayers, Long ruleId, List<Player> players, List<Assignment> backlog) {
        this.id = id;
        this.code = code;
        this.maxPlayers = maxPlayers;
        this.ruleId = ruleId;
        this.players = players;
        this.backlog = backlog;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
