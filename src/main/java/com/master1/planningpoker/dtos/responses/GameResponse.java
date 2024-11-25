package com.master1.planningpoker.dtos.responses;


import com.master1.planningpoker.models.Assignment;
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
    private List<String> players; // Pseudos des joueurs
    private List<Assignment> backlog;
}
