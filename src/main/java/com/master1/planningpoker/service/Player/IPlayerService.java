package com.master1.planningpoker.service.Player;

import com.master1.planningpoker.dtos.request.playerRequests.CreatePlayerRequest;
import com.master1.planningpoker.dtos.request.playerRequests.JoinGameRequest;
import com.master1.planningpoker.dtos.responses.playerResponses.PlayerResponse;

import java.util.List;

/**
 * Interface définissant les services liés à la gestion des joueurs dans le système de planification de poker.
 * Cette interface expose des méthodes permettant aux joueurs de rejoindre un jeu, de créer ou modifier leur profil,
 * de récupérer la liste des joueurs, et de supprimer un joueur.
 */
public interface IPlayerService {

    /**
     * Permet à un joueur de rejoindre un jeu en fournissant les informations nécessaires dans la requête.
     * Cette méthode vérifie si le jeu existe et si le joueur peut rejoindre le jeu en fonction des règles définies.
     *
     * @param request La requête contenant les informations nécessaires pour qu'un joueur rejoigne un jeu.
     * @return Un message confirmant l'ajout du joueur au jeu.
     * @throws IllegalArgumentException si le jeu n'existe pas, si le joueur dépasse le nombre maximum de joueurs
     *                                  ou si d'autres règles de validation sont violées.
     */
    public String joinGame(JoinGameRequest request);

    /**
     * Crée ou modifie un joueur en fonction des informations fournies dans la requête.
     * Si un joueur existe déjà avec l'ID fourni, il sera mis à jour. Sinon, un nouveau joueur sera créé.
     *
     * @param request La requête contenant les informations nécessaires pour créer ou modifier un joueur.
     * @return Un objet {@link PlayerResponse} représentant les détails du joueur créé ou modifié.
     * @throws IllegalArgumentException si des informations obligatoires manquent ou sont invalides.
     */
    public PlayerResponse createEditPlayer(CreatePlayerRequest request);

    /**
     * Récupère la liste de tous les joueurs existants dans le système.
     *
     * @return Une liste d'objets {@link PlayerResponse} représentant tous les joueurs.
     */
    public List<PlayerResponse> getPlayers();

    /**
     * Récupère les détails d'un joueur spécifique en utilisant son pseudonyme unique.
     *
     * @param pseudo Le pseudonyme du joueur à récupérer.
     * @return Un objet {@link PlayerResponse} représentant les détails du joueur.
     * @throws IllegalArgumentException si aucun joueur n'est trouvé pour le pseudonyme donné.
     */
    public PlayerResponse getPlayer(String pseudo);

    /**
     * Supprime un joueur du système en utilisant son identifiant.
     *
     * @param id L'identifiant du joueur à supprimer.
     * @return Un message confirmant la suppression du joueur.
     */
    public String removePlayer(Long id);
}
