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

/**
 * Service qui gère la logique métier relative aux joueurs, notamment l'ajout d'un joueur à un jeu, la création ou la mise à jour
 * d'un joueur, la récupération des informations des joueurs, ainsi que la suppression d'un joueur.
 * Implémente les méthodes définies dans l'interface {@link IPlayerService}.
 */
@Service
@RequiredArgsConstructor
public class PlayerService implements IPlayerService {

    @Autowired
    public final GameRepository gameRepository;
    @Autowired
    public final PlayerRepository playerRepository;
    @Autowired
    public final PlayerMapper playerMapper;

    /**
     * Permet à un joueur de rejoindre un jeu en fonction du code du jeu et de son pseudo.
     * Vérifie si le jeu existe et si le joueur n'est pas déjà présent dans le jeu. Vérifie également si le nombre maximal
     * de joueurs n'a pas été atteint.
     *
     * @param request La requête contenant le pseudo du joueur et le code du jeu.
     * @return Un message de confirmation indiquant que le joueur a bien rejoint le jeu.
     * @throws IllegalArgumentException si le pseudo ou le code du jeu est invalide ou si le joueur existe déjà dans le jeu.
     * @throws RuntimeException si le nombre maximal de joueurs a été atteint.
     */
    @Override
    public String joinGame(JoinGameRequest request) {
        // Vérification des valeurs des champs
        if (request.getPseudo() == null || request.getPseudo().isEmpty()) {
            throw new IllegalArgumentException("Pseudo cannot be null or empty");
        }

        if (request.getCode() == null || request.getCode().isEmpty()) {
            throw new IllegalArgumentException("Game code cannot be null or empty");
        }
        System.out.println("Received isAdmin value: " + request.isAdmin());  // Affiche la valeur de isAdmin

        // Récupérer le jeu par son code
        Game game = gameRepository.findByCode(request.getCode())
                .orElseThrow(() -> new IllegalArgumentException("Game not found for code: " + request.getCode()));

        // Vérifier si le jeu a atteint le nombre maximum de joueurs
        int numPlayer = game.getPlayers().size();
        if (numPlayer == game.getMaxPlayers()) {
            throw new RuntimeException("The limit of players is reached");
        }

        // Vérifier si le joueur existe déjà dans le jeu
        boolean playerExist = game.getPlayers().stream()
                .anyMatch(player -> player.getPseudo().equals(request.getPseudo()));

        if (playerExist) {
            throw new IllegalArgumentException("A player with this pseudo is already in the game");
        }

        // Créer un nouveau joueur
        Player newPlayer = new Player();
        newPlayer.setPseudo(request.getPseudo());
        newPlayer.setGame(game);

        // Assigner le rôle admin au joueur en fonction de `isAdmin` dans la requête
        newPlayer.setAdmin(request.isAdmin());  // Utiliser directement request.isAdmin()

        // Ajouter le nouveau joueur au jeu
        game.getPlayers().add(newPlayer);

        // Sauvegarder le jeu avec le nouveau joueur
        gameRepository.save(game);

        return "Player " + request.getPseudo() + " has successfully joined the game with code: " + request.getCode();
    }
    /**
     * Crée ou met à jour un joueur en fonction des informations fournies dans la requête.
     * Si un joueur avec le même pseudo existe déjà, une exception est lancée.
     *
     * @param request La requête contenant les informations du joueur à créer ou mettre à jour.
     * @return Un objet {@link PlayerResponse} représentant les détails du joueur créé ou modifié.
     * @throws IllegalArgumentException si un joueur avec le même pseudo existe déjà.
     */
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

    /**
     * Récupère la liste de tous les joueurs dans le système.
     *
     * @return Une liste d'objets {@link PlayerResponse} représentant tous les joueurs.
     */
    @Override
    public List<PlayerResponse> getPlayers() {
        return playerRepository.findAll()
                .stream().map(playerMapper::toResponse).collect(Collectors.toList());
    }

    /**
     * Récupère les informations d'un joueur spécifique en utilisant son pseudo.
     *
     * @param pseudo Le pseudo du joueur à récupérer.
     * @return Un objet {@link PlayerResponse} représentant les détails du joueur.
     * @throws IllegalArgumentException si aucun joueur n'est trouvé pour le pseudo donné.
     */
    @Override
    public PlayerResponse getPlayer(String pseudo) {
        Player player = playerRepository.findByPseudo(pseudo)
                .orElseThrow(() -> new IllegalArgumentException("Player " + pseudo + " doesn't exist"));
        return playerMapper.toResponse(player);
    }

    /**
     * Supprime un joueur du système en utilisant son identifiant.
     *
     * @param id L'identifiant du joueur à supprimer.
     * @return Un message confirmant la suppression du joueur.
     */
    @Override
    public String removePlayer(Long id) {
        playerRepository.deleteById(id);
        return "Player: " + id + " has been deleted successfully";
    }
}
