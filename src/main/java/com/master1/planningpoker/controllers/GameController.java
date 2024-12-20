package com.master1.planningpoker.controllers;


import com.master1.planningpoker.dtos.request.gameRequests.createGameRequest;
import com.master1.planningpoker.dtos.responses.gameResponses.GameResponse;
import com.master1.planningpoker.service.Game.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @class GameController
 * @brief Gestion des requêtes HTTP liées aux jeux (games).
 *
 * Ce contrôleur permet de gérer l'ajout, la récupération, la suppression des jeux, ainsi que la modification de leurs informations.
 */
@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    /**
     * @brief Ajoute ou modifie un jeu (game).
     * Cette méthode permet d'ajouter ou de modifier un jeu dans le système.
     * Elle prend une requête contenant les informations du jeu et retourne la réponse de l'ajout/modification.
     *
     * @param request La requête contenant les données du jeu à ajouter/éditer.
     * @return Une réponse HTTP avec le statut 201 (Créé) et le jeu créé/modifié.
     */
    @PostMapping("/addEdit")
    public ResponseEntity<GameResponse> addGame(@RequestBody @Valid createGameRequest request) {
        GameResponse newGame = gameService.createEditGame(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newGame);
    }


    /**
     * @brief Récupère les détails d'un jeu spécifique en fonction de son code.
     *
     * Cette méthode permet de récupérer les informations d'un jeu en utilisant son code.
     *
     * @param code Le code du jeu pour lequel on souhaite récupérer les détails.
     * @return Les détails du jeu spécifié par son code.
     */
    @GetMapping("/{code}")
    public ResponseEntity<GameResponse> getGameDetails(@PathVariable String code) {
        GameResponse gameDetails = gameService.getGameDetails(code);
        return ResponseEntity.ok(gameDetails);
    }

    /**
     * @brief Récupère la liste de tous les jeux.
     *
     * Cette méthode permet de récupérer tous les jeux disponibles dans le système.
     *
     * @return Une liste de tous les jeux disponibles.
     */
    @GetMapping("")
    public ResponseEntity<List<GameResponse>> getGames() {
        List<GameResponse> gameDetails = gameService.getGames();
        return ResponseEntity.ok(gameDetails);
    }

    /**
     * @brief Supprime un jeu spécifique en fonction de son ID.
     *
     * Cette méthode permet de supprimer un jeu en utilisant son identifiant unique.
     *
     * @param id L'ID du jeu à supprimer.
     * @return Un message de confirmation de la suppression du jeu.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable Long id) {
        String response = gameService.deleteGame(id);
        return ResponseEntity.ok(response);
    }
}
