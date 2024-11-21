package com.master1.planningpoker.service.Player;

import com.master1.planningpoker.models.Player;

import java.util.List;

public interface IPlayerService {
    public Player registerPlayer(String email, String pseudo, boolean isAdmin, Long gameId);
    public List<Player> getPlayersInGame(Long gameId);
    void  removePlayer(Long playerId);
    boolean isAdmin(Long playerId);
}
