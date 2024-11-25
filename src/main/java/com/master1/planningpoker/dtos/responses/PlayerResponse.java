package com.master1.planningpoker.dtos.responses;


import com.master1.planningpoker.models.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PlayerResponse {
    private Long Id;
    private String pseudo;
    private boolean isAdmin;
    private Player player;
}

