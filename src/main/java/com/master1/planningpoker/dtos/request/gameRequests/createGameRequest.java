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

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getCode() {
                return code;
        }

        public void setCode(String code) {
                this.code = code;
        }

        public int getMaxPlayers() {
                return maxPlayers;
        }

        public void setMaxPlayers(int maxPlayers) {
                this.maxPlayers = maxPlayers;
        }

        public Long getRuleId() {
                return ruleId;
        }

        public void setRuleId(Long ruleId) {
                this.ruleId = ruleId;
        }

        public List<AssignmentRequest> getBacklog() {
                return backlog;
        }

        public void setBacklog(List<AssignmentRequest> backlog) {
                this.backlog = backlog;
        }
}
