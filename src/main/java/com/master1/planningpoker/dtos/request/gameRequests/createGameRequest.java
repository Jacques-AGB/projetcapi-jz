package com.master1.planningpoker.dtos.request.gameRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class createGameRequest {
        private Long id;
        private String code;
        private int maxPlayers;
        private Long ruleId;

}
