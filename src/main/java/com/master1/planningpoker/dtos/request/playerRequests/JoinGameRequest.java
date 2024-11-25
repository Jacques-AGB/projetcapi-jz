package com.master1.planningpoker.dtos.request.playerRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class JoinGameRequest {
    private String pseudo;
    private String code;
}
