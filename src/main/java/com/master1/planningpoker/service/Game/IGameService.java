package com.master1.planningpoker.service.Game;

import com.master1.planningpoker.dtos.request.gameRequests.createGameRequest;
import com.master1.planningpoker.dtos.responses.gameResponses.GameResponse;

import java.util.List;

public interface IGameService {
    public GameResponse createEditGame(createGameRequest request);
    public List<GameResponse> getGames();
    public GameResponse getGameDetails(String code);
    public String deleteGame(Long id);
}
