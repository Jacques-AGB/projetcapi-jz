package com.master1.planningpoker.dtos.responses.gameResponses;


import com.master1.planningpoker.models.Assignment;
import com.master1.planningpoker.models.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
}
