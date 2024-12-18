package com.master1.planningpoker.mappers.gameMapper;

import com.master1.planningpoker.dtos.request.gameRequests.createGameRequest;
import com.master1.planningpoker.dtos.responses.gameResponses.GameResponse;
import com.master1.planningpoker.models.Game;
import com.master1.planningpoker.models.Player;
import com.master1.planningpoker.models.Rule;
import com.master1.planningpoker.repositories.RuleRepository;
import org.springframework.stereotype.Component;

/**
 * @class GameMapper
 * @brief Mapper pour la conversion entre les entités Game, les requêtes DTO (createGameRequest),
 *        et les réponses DTO (GameResponse).
 *
 * Cette classe est utilisée pour la transformation des objets `Game` vers des DTOs et vice-versa,
 * facilitant ainsi l'interaction avec les différentes couches de l'application (comme les contrôleurs et les services).
 */
@Component
public class GameMapper {

    private final RuleRepository ruleRepository;

    public GameMapper(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    /**
     * Méthode pour convertir un `createGameRequest` en une entité `Game`.
     * Cette méthode prend un objet DTO `createGameRequest`, crée un nouvel objet `Game` et le remplit avec les données correspondantes.
     *
     * @param request Le DTO contenant les informations du jeu à créer.
     * @return Un objet `Game` contenant les informations du DTO.
     * @throws IllegalArgumentException Si l'ID de la règle fourni n'existe pas dans la base de données.
     *
     * @note Cette méthode récupère une entité `Rule` à partir de la base de données en utilisant l'ID de la règle passé dans le DTO.
     *       Si aucune règle n'est trouvée, une exception `IllegalArgumentException` est levée.
     */
    public Game toEntity(createGameRequest request) {
        Game game = new Game();
        game.setCode(request.getCode());
        game.setMaxPlayers(request.getMaxPlayers());
        Rule rule = ruleRepository.findById(request.getRuleId())
                .orElseThrow(() -> new IllegalArgumentException("Rule not found with ID: " + request.getRuleId()));
        game.setRule(rule);

        return game;
    }

    /**
     * Méthode pour convertir un objet `Game` en un objet `GameResponse`.
     * Cette méthode prend un objet `Game` et le convertit en une réponse DTO contenant toutes les informations nécessaires.
     *
     * @param game L'entité `Game` à convertir.
     * @return Un objet `GameResponse` contenant les informations du `Game`.
     *
     * @note Cette méthode prend un objet `Game` et le mappe dans un DTO `GameResponse`,
     *       qui est généralement utilisé pour transmettre des informations à l'utilisateur via l'API.
     */
    public GameResponse toResponse(Game game) {
        return GameResponse.builder()
                .id(game.getId())
                .code(game.getCode())
                .maxPlayers(game.getMaxPlayers())
                .players(game.getPlayers())
                .ruleId(game.getRule().getId())
                .backlog(game.getBacklog())
                .build();
    }

    private String mapPlayerToResponse(Player player) {
        return player.getPseudo();
    }
}