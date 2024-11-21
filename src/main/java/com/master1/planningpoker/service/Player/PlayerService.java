package com.master1.planningpoker.service.Player;


import com.master1.planningpoker.models.Player;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService implements IPlayerService{
    @Override
    public Player registerPlayer(String email, String pseudo, boolean isAdmin, Long gameId) {
        return null;
    }

    @Override
    public List<Player> getPlayersInGame(Long gameId) {
        return List.of();
    }

    @Override
    public void removePlayer(Long playerId) {

    }

    @Override
    public boolean isAdmin(Long playerId) {
        return false;
    }
}
