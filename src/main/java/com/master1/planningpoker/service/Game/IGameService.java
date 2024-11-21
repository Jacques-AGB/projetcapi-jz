package com.master1.planningpoker.service.Game;

import com.master1.planningpoker.models.Game;

public interface IGameService {
    public Game createGame(String mode, int maxPlayers, Long ruleId);
    public Game joinGame(String code, String email, String pseudo);
    public Game getGameDetails(String code);
    public void endGame(Long gameId);
    public Game resumeGame(String jsonFilePath);
}
