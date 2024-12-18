package com.master1.planningpoker.service.Game;

import com.master1.planningpoker.dtos.request.gameRequests.createGameRequest;
import com.master1.planningpoker.dtos.responses.gameResponses.GameResponse;
import com.master1.planningpoker.mappers.gameMapper.GameMapper;
import com.master1.planningpoker.models.Game;
import com.master1.planningpoker.models.Rule;
import com.master1.planningpoker.repositories.GameRepository;
import com.master1.planningpoker.repositories.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service qui gère les opérations relatives aux jeux dans le système de planification de poker.
 * Cette classe implémente l'interface {@link IGameService} pour créer, éditer, récupérer et supprimer des jeux.
 */
@Service
@RequiredArgsConstructor
public class GameService implements IGameService{

    @Autowired
    private final RuleRepository ruleRepository;

    @Autowired
    private final GameRepository gameRepository;

    @Autowired
    private final GameMapper gameMapper;

    /**
     * Crée ou modifie un jeu selon les informations spécifiées dans la requête.
     * Si un jeu existe déjà avec l'ID fourni, il sera mis à jour. Sinon, un nouveau jeu sera créé.
     *
     * @param request La requête contenant les informations nécessaires pour créer ou modifier un jeu.
     * @return Un objet {@link GameResponse} contenant les détails du jeu créé ou mis à jour.
     * @throws IllegalArgumentException si le nombre maximal de joueurs est inférieur ou égal à zéro,
     *                                  ou si la règle spécifiée dans la requête n'existe pas.
     */
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
            // Générer un code unique pour le jeu
            String code = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
            request.setCode(code);
            Game newGame = gameMapper.toEntity(request);
            Game savedGame = gameRepository.save(newGame);
            return gameMapper.toResponse(savedGame);
        } else {
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

    /**
     * Récupère tous les jeux existants dans le système.
     *
     * @return Une liste des objets {@link GameResponse} représentant tous les jeux.
     */
    @Override
    public List<GameResponse> getGames() {
        List<Game> games = gameRepository.findAll();
        return games.stream()
                .map(gameMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les détails d'un jeu spécifique en utilisant son code unique.
     *
     * @param code Le code unique du jeu à récupérer.
     * @return Un objet {@link GameResponse} contenant les détails du jeu.
     * @throws IllegalArgumentException si le jeu n'est pas trouvé pour le code donné.
     */
    @Override
    public GameResponse getGameDetails(String code) {
        Game game = gameRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Game not found with code : " + code));
        return gameMapper.toResponse(game);
    }

    /**
     * Supprime un jeu du système en utilisant son identifiant.
     *
     * @param id L'identifiant du jeu à supprimer.
     * @return Un message confirmant que le jeu a été supprimé avec succès.
     */
    @Override
    public String deleteGame(Long id) {
        gameRepository.deleteById(id);
        return "Game with id : " + id + " deleted successfully";
    }
}
