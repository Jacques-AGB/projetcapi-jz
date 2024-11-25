package com.master1.planningpoker.mappers.playerMapper;


import com.master1.planningpoker.dtos.request.playerRequests.CreatePlayerRequest;
import com.master1.planningpoker.dtos.responses.PlayerResponse;
import com.master1.planningpoker.models.Player;
import com.master1.planningpoker.repositories.GameRepository;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    private final GameRepository gameRepository;

    public PlayerMapper(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }


    public PlayerResponse toResponse(Player player) {
        return PlayerResponse.builder()
                .Id(player.getId())
                .pseudo(player.getPseudo())
                .isAdmin(player.isAdmin())
                .build();
    }

    public Player toEntity(CreatePlayerRequest request) {
        Player player = new Player();
        player.setPseudo(request.getPseudo());
        player.setAdmin(request.isAdmin());
        return  player;
    }
}
