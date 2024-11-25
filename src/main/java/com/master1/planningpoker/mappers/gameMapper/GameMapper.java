package com.master1.planningpoker.mappers.gameMapper;

import com.master1.planningpoker.dtos.request.gameRequests.createGameRequest;
import com.master1.planningpoker.dtos.responses.GameResponse;
import com.master1.planningpoker.models.Game;
import com.master1.planningpoker.models.Player;
import com.master1.planningpoker.models.Rule;
import com.master1.planningpoker.repositories.RuleRepository;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    private final RuleRepository ruleRepository;

    public GameMapper(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public Game toEntity(createGameRequest request) {
        Game game = new Game();
        game.setCode(request.getCode());
        game.setMaxPlayers(request.getMaxPlayers());
        Rule rule = ruleRepository.findById(request.getRuleId())
                .orElseThrow(() -> new IllegalArgumentException("Rule not found with ID: " + request.getRuleId()));
        game.setRule(rule);

        return game;
    }


    public GameResponse toResponse(Game game) {
        return GameResponse.builder()
                .id(game.getId())
                .code(game.getCode())
                .maxPlayers(game.getMaxPlayers())
                .ruleId(game.getRule().getId())
                .backlog(game.getBacklog())
                .build();
    }

    private String mapPlayerToResponse(Player player) {
        return player.getPseudo();
    }
}