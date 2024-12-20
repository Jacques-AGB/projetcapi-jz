package com.master1.planningpoker.dtos.request.gameRequests;

import com.master1.planningpoker.dtos.request.assignmentRequest.AssignmentRequest;
import lombok.*;

import java.util.List;


/**
 * @class createGameRequest
 * @brief Représente une requête pour créer ou modifier un jeu dans le système de Planning Poker.
 *
 * Cette classe est utilisée pour encapsuler les informations nécessaires lors de la création
 * ou de la modification d'un jeu, telles que son code, le nombre maximum de joueurs, et la règle associée.
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class createGameRequest {
        private Long id;
        private String code;
        private int maxPlayers;
        private Long ruleId;
        private List<AssignmentRequest> backlog;

}
