package com.master1.planningpoker.service.Player;


import com.master1.planningpoker.dtos.request.playerRequests.CreatePlayerRequest;
import com.master1.planningpoker.dtos.request.playerRequests.JoinGameRequest;
import com.master1.planningpoker.dtos.responses.playerResponses.PlayerResponse;
import com.master1.planningpoker.mappers.playerMapper.PlayerMapper;
import com.master1.planningpoker.models.Game;
import com.master1.planningpoker.models.Player;
import com.master1.planningpoker.repositories.GameRepository;
import com.master1.planningpoker.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService implements IPlayerService {
    @Autowired
    public final GameRepository gameRepository;
    @Autowired
    public final PlayerRepository playerRepository;
    @Autowired
    public final PlayerMapper playerMapper;

    @Override
    public String joinGame(JoinGameRequest request) {

        if (request.getPseudo() == null || request.getPseudo().isEmpty()) {
            throw new IllegalArgumentException("Pseudo cannot be null or empty");
        }

        if (request.getCode() == null || request.getCode().isEmpty()) {
            throw new IllegalArgumentException("Game code cannot be null or empty");
        }
        Game game = gameRepository.findByCode(request.getCode())
                .orElseThrow(() -> new IllegalArgumentException("Game not found for code: " + request.getCode()));

        int numPlayer =  game.getPlayers().size();
        if (numPlayer == game.getMaxPlayers()){
            throw new RuntimeException("The limit of player is reached");
        }
        boolean playerExist = game.getPlayers().stream()
                .anyMatch(player -> player.getPseudo().equals(request.getPseudo()));

        if (playerExist) {
            throw new IllegalArgumentException("A player with this pseudo is already in the game");
        }
        Player newPlayer = new Player();
        newPlayer.setPseudo(request.getPseudo());
        newPlayer.setGame(game);
        game.getPlayers().add(newPlayer);
        gameRepository.save(game);
        return "Player " + request.getPseudo() + " has successfully joined the game with code: " + request.getCode();
    }

    @Override
    public PlayerResponse createEditPlayer(CreatePlayerRequest request) {
        boolean exist = playerRepository.existsByPseudo(request.getPseudo());
        if (exist) {
            throw new IllegalArgumentException("Player with pseudo: " + request.getPseudo() + " already exists.");
        }
        if (request.getId() == null) {
            Player newPlayer = playerMapper.toEntity(request);
            Player savedPlayer = playerRepository.save(newPlayer);
            return playerMapper.toResponse(savedPlayer);
        } else {
            Player existingPlayer = playerRepository.findById(request.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Player not found with id: " + request.getId()));
            existingPlayer.setPseudo(request.getPseudo());
            existingPlayer.setAdmin(request.isAdmin());
            Player player = playerRepository.save(existingPlayer);
            return playerMapper.toResponse(player);
        }
    }

    @Override
    public List<PlayerResponse> getPlayers() {
        return playerRepository.findAll()
                .stream().map(playerMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public PlayerResponse getPlayer(String pseudo) {
        Player player = playerRepository.findByPseudo(pseudo)
                .orElseThrow(() -> new IllegalArgumentException("Player "+ pseudo + " doesn't exist"));
        return  playerMapper.toResponse(player);
    }

    @Override
    public String removePlayer(Long id) {
        playerRepository.deleteById(id);
        return "Player : " + id + " has been deleted successfully";
    }

}
