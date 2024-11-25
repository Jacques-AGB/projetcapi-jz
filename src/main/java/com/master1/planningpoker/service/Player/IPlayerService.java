package com.master1.planningpoker.service.Player;

import com.master1.planningpoker.dtos.request.playerRequests.CreatePlayerRequest;
import com.master1.planningpoker.dtos.request.playerRequests.JoinGameRequest;
import com.master1.planningpoker.dtos.responses.PlayerResponse;

import java.util.List;

public interface IPlayerService {
    public String joinGame(JoinGameRequest request);
    public PlayerResponse createEditPlayer(CreatePlayerRequest request);
    public List<PlayerResponse> getPlayers();
    public PlayerResponse getPlayer(String pseudo);
    public String removePlayer(Long id);
}
