package com.master1.planningpoker.mappers.playerMapper;


import com.master1.planningpoker.dtos.request.playerRequests.CreatePlayerRequest;
import com.master1.planningpoker.dtos.responses.playerResponses.PlayerResponse;
import com.master1.planningpoker.models.Player;
import com.master1.planningpoker.repositories.GameRepository;
import org.springframework.stereotype.Component;



/**
 * @class PlayerMapper
 * @brief Mapper pour la conversion entre les entités Player, les requêtes DTO (CreatePlayerRequest),
 *        et les réponses DTO (PlayerResponse).
 *
 * Cette classe est utilisée pour la transformation des objets `Player` vers des DTOs et vice-versa,
 * facilitant ainsi l'interaction avec les différentes couches de l'application (comme les contrôleurs et les services).
 */
@Component
public class PlayerMapper {

    private final GameRepository gameRepository;

    public PlayerMapper(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }


    /**
     * Méthode pour convertir un objet `Player` en un objet `PlayerResponse`.
     * Cette méthode prend un objet `Player` et le convertit en une réponse DTO contenant toutes les informations nécessaires.
     *
     * @param player L'entité `Player` à convertir.
     * @return Un objet `PlayerResponse` contenant les informations du `Player`.
     *
     * @note Cette méthode est utilisée pour transmettre les informations sur le joueur à travers les réponses API.
     */
    public PlayerResponse toResponse(Player player) {
        return PlayerResponse.builder()
                .Id(player.getId())
                .pseudo(player.getPseudo())
                .isAdmin(player.isAdmin())
                .build();
    }

    /**
     * Méthode pour convertir un `CreatePlayerRequest` en une entité `Player`.
     * Cette méthode prend un objet DTO `CreatePlayerRequest`, crée un nouvel objet `Player` et le remplit avec les données correspondantes.
     *
     * @param request Le DTO contenant les informations du joueur à créer.
     * @return Un objet `Player` contenant les informations du DTO.
     */
    public Player toEntity(CreatePlayerRequest request) {
        Player player = new Player();
        player.setPseudo(request.getPseudo());
        player.setAdmin(request.isAdmin());
        return  player;
    }
}
