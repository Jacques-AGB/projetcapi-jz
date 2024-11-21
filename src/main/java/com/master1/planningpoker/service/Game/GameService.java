package com.master1.planningpoker.service.Game;


import com.master1.planningpoker.models.Game;
import org.springframework.stereotype.Service;

@Service
public class GameService implements IGameService{
    @Override
    public Game createGame(String mode, int maxPlayers, Long ruleId) {
        return null;
    }

    @Override
    public Game joinGame(String code, String email, String pseudo) {
        return null;
    }

    @Override
    public Game getGameDetails(String code) {
        return null;
    }

    @Override
    public void endGame(Long gameId) {

    }

    @Override
    public Game resumeGame(String jsonFilePath) {
        return null;
    }
}
