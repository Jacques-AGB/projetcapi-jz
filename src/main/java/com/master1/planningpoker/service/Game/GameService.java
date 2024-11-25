package com.master1.planningpoker.service.Game;


import com.master1.planningpoker.dtos.request.gameRequests.createGameRequest;
import com.master1.planningpoker.dtos.responses.GameResponse;
import com.master1.planningpoker.mappers.gameMapper.GameMapper;
import com.master1.planningpoker.models.Assignment;
import com.master1.planningpoker.models.Game;
import com.master1.planningpoker.models.Rule;
import com.master1.planningpoker.repositories.GameRepository;
import com.master1.planningpoker.repositories.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService implements IGameService{

    private final RuleRepository ruleRepository;
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    @Override
    public GameResponse createEditGame(createGameRequest request) {
        if (request.getMaxPlayers() <= 0) {
            throw new IllegalArgumentException("The maximum number of players must be greater than 0.");
        }
        boolean exists = ruleRepository.existsById(request.getRuleId());
        if (!exists) {
            throw new IllegalArgumentException("The rule : " + request.getRuleId() + " doesn't exists.");
        }
        if (request.getId() == null){
            String code = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
            request.setCode(code);
            Game newGame = gameMapper.toEntity(request);
            Game savedGame = gameRepository.save(newGame);
            return gameMapper.toResponse(savedGame);
        }else {
            Game existingGame = gameRepository.findById(request.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Game not found with id: " + request.getId()));

            Rule rule = ruleRepository.findById(request.getRuleId())
                    .orElseThrow(() -> new IllegalArgumentException("Rule not found with id: " + request.getRuleId()));

            existingGame.setCode(request.getCode());
            existingGame.setMaxPlayers(request.getMaxPlayers());
            existingGame.setRule(rule);
            Game updatedGame = gameRepository.save(existingGame);
            return gameMapper.toResponse(updatedGame);

        }



    }

    @Override
    public List<GameResponse> getGames() {
        List<Game> games = gameRepository.findAll();
        return games.stream()
                .map(gameMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public GameResponse getGameDetails(String code) {
        Game game =  gameRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Game not found with code : " + code));
        return gameMapper.toResponse(game);
    }

    @Override
    public String deleteGame(Long id) {

        boolean exist = gameRepository.existsById(id);
        if (!exist) {
            throw new IllegalArgumentException("Game : " + id + " doesn't exists.");
        }
        gameRepository.deleteById(id);
        return "Game with id : " + id + " deleted successfully";
    }

}
