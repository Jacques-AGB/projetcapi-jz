package com.master1.planningpoker.service.Game;

import com.master1.planningpoker.dtos.request.gameRequests.createGameRequest;
import com.master1.planningpoker.dtos.responses.gameResponses.GameResponse;

import java.util.List;

/**
 * Interface définissant les services liés à la gestion des jeux dans le système de planification de poker.
 * Cette interface expose des méthodes permettant de créer, éditer, récupérer et supprimer des jeux.
 */
public interface IGameService {

    /**
     * Crée ou modifie un jeu en fonction des informations fournies dans la requête.
     * Si un jeu existe déjà avec l'ID fourni, il sera mis à jour. Sinon, un nouveau jeu sera créé.
     *
     * @param request La requête contenant les informations nécessaires pour créer ou modifier un jeu.
     * @return Un objet {@link GameResponse} représentant les détails du jeu créé ou mis à jour.
     * @throws IllegalArgumentException si le nombre maximal de joueurs est inférieur ou égal à zéro,
     *                                  ou si la règle spécifiée dans la requête n'existe pas.
     */
    public GameResponse createEditGame(createGameRequest request);

    /**
     * Récupère la liste de tous les jeux existants dans le système.
     *
     * @return Une liste d'objets {@link GameResponse} représentant tous les jeux.
     */
    public List<GameResponse> getGames();

    /**
     * Récupère les détails d'un jeu spécifique en utilisant son code unique.
     *
     * @param code Le code unique du jeu à récupérer.
     * @return Un objet {@link GameResponse} représentant les détails du jeu.
     * @throws IllegalArgumentException si aucun jeu n'est trouvé pour le code donné.
     */
    public GameResponse getGameDetails(String code);

    /**
     * Supprime un jeu du système en utilisant son identifiant.
     *
     * @param id L'identifiant du jeu à supprimer.
     * @return Un message confirmant la suppression du jeu.
     */
    public String deleteGame(Long id);
}
