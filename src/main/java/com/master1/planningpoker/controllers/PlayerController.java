package com.master1.planningpoker.controllers;

import com.master1.planningpoker.dtos.request.playerRequests.CreatePlayerRequest;
import com.master1.planningpoker.dtos.request.playerRequests.JoinGameRequest;
import com.master1.planningpoker.dtos.responses.playerResponses.PlayerResponse;
import com.master1.planningpoker.service.Player.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * @class PlayerController
 * @brief Gestion des requêtes HTTP liées aux joueurs (players).
 *
 * Ce contrôleur permet de gérer les actions liées aux joueurs, telles que l'ajout d'un joueur à un jeu,
 * la création ou modification des informations d'un joueur, la récupération des joueurs, et la suppression des joueurs.
 */
@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {

    public final PlayerService playerService;


    /**
     * @brief Permet à un joueur de rejoindre une partie.
     * Cette méthode permet à un joueur de rejoindre un jeu spécifique en fournissant le code du jeu.
     * Elle retourne une réponse confirmant l'ajout du joueur au jeu.
     *
     * @param request La requête contenant les informations du joueur et du jeu à rejoindre.
     * @return Une réponse HTTP avec le statut 201 (Créé) contenant un message de succès et le code du jeu.
     */
    @PostMapping("/join")
    public ResponseEntity<Map<String, Object>> joinGame(@RequestBody @Valid JoinGameRequest request) {
        // Appel au service pour ajouter le joueur
        String response = playerService.joinGame(request);

        // Création de la réponse contenant le message et le code de la partie
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", "success");
        responseMap.put("message", response);
        responseMap.put("gameCode", request.getCode());  // Ajout du code de la partie dans la réponse

        // Retourner une réponse HTTP avec le corps contenant le message et le code
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
    }


    /**
     * @brief Crée ou modifie un joueur.
     * Cette méthode permet d'ajouter un joueur ou de modifier ses informations dans le système.
     *
     * @param request La requête contenant les informations du joueur à créer ou modifier.
     * @return Une réponse HTTP avec le statut 201 (Créé) et les informations du joueur.
     */
    @PostMapping("/createEdit")
    public ResponseEntity<PlayerResponse> createEditPlayer(@RequestBody @Valid CreatePlayerRequest request) {
        PlayerResponse response = playerService.createEditPlayer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * @brief Récupère la liste de tous les joueurs.
     * Cette méthode permet de récupérer tous les joueurs présents dans le système.
     *
     * @return Une liste des joueurs enregistrés dans le système.
     */
    @GetMapping("")
    public ResponseEntity<List<PlayerResponse>> getPlayers() {
        List<PlayerResponse> response = playerService.getPlayers();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    /**
     * @brief Récupère un joueur spécifique en fonction de son pseudo.
     * Cette méthode permet de récupérer les informations d'un joueur en utilisant son pseudo.
     *
     * @param pseudo Le pseudo du joueur pour lequel on souhaite récupérer les informations.
     * @return Les informations du joueur spécifié par son pseudo.
     */
    @GetMapping("/{pseudo}")
    public ResponseEntity<PlayerResponse> getPlayer(@PathVariable String pseudo) {
        PlayerResponse response = playerService.getPlayer(pseudo);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    /**
     * @brief Supprime un joueur spécifique en fonction de son ID.
     * Cette méthode permet de supprimer un joueur en utilisant son identifiant unique.
     *
     * @param id L'ID du joueur à supprimer.
     * @return Un message de confirmation de la suppression du joueur.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removePlayer(@PathVariable Long id) {
        String response = playerService.removePlayer(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
