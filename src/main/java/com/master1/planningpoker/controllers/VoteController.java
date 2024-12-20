package com.master1.planningpoker.controllers;


import com.master1.planningpoker.dtos.request.voteRequests.VoteRequest;
import com.master1.planningpoker.dtos.responses.voteResponses.VoteResponse;
import com.master1.planningpoker.service.Vote.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * @class VoteController
 * @brief Gestion des requêtes HTTP liées aux votes.
 *
 * Ce contrôleur permet de gérer les opérations sur les votes dans le système.
 * Il fournit des fonctionnalités pour soumettre un vote, récupérer des votes
 * pour un joueur, une tâche ou un jeu, ainsi que supprimer un vote.
 */
@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VoteController {

    public final VoteService voteService;


    /**
     * @brief Soumet un vote pour un joueur sur une tâche spécifique.
     *
     * Cette méthode permet de soumettre un vote pour une tâche spécifique.
     * Elle vérifie la validité de la requête et traite les erreurs éventuelles.
     *
     * @param request L'objet contenant les informations du vote à soumettre.
     * @return Une réponse JSON contenant un message de succès ou une erreur.
     */
    @PostMapping("/submit")
    public ResponseEntity<Map<String, String>> submitVote(@RequestBody VoteRequest request) {
        // Créer une réponse au format JSON
        Map<String, String> response = new HashMap<>();

        try {
            // Log d'entrée pour déboguer les données envoyées
            System.out.println("Submitting vote: " + request);

            // Appeler la fonction submitVote du service (voteService)
            String voteResponse = voteService.submitVote(request);

            // Ajouter la réponse du vote dans le JSON
            response.put("message", voteResponse);

            // Retourner la réponse avec un statut 201 CREATED
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException e) {
            // Gestion des erreurs liées aux données invalides
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (Exception e) {
            // Log l'exception pour un meilleur débogage
            e.printStackTrace();

            // Ajouter des détails sur l'erreur pour les développeurs
            response.put("error", "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * @brief Récupère la liste de tous les votes.
     *
     * Cette méthode permet de récupérer tous les votes enregistrés dans le système.
     *
     * @return Une liste des votes sous forme d'objets `VoteResponse`.
     */
    @GetMapping("")
    public ResponseEntity<List<VoteResponse>> getVotes() {
        List<VoteResponse> responses = voteService.getVotes();
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    /**
     * @brief Récupère un vote spécifique en fonction de son ID.
     *
     * Cette méthode permet de récupérer les informations d'un vote via son identifiant unique.
     *
     * @param id L'ID du vote à récupérer.
     * @return Les détails du vote sous forme d'un objet `VoteResponse`.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VoteResponse> getVote(@PathVariable Long id) {
        VoteResponse response = voteService.getVote(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    /**
     * @brief Récupère les votes pour une tâche spécifique.
     *
     * Cette méthode permet de récupérer tous les votes associés à une tâche particulière.
     *
     * @param assignmentId L'identifiant unique de la tâche.
     * @return Une liste des votes associés à la tâche donnée.
     */
    @GetMapping("/{assignmentId}")
    public ResponseEntity<List<VoteResponse>> getVotesForAssignment(@PathVariable Long assignmentId) {
        List<VoteResponse> responses = voteService.getVotesForAssignment(assignmentId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    /**
     * @brief Récupère les votes pour un jeu spécifique via son code.
     *
     * Cette méthode permet de récupérer les votes associés à un jeu, identifiés par un code unique.
     *
     * @param gameCode Le code unique du jeu.
     * @return Une liste des votes associés au jeu spécifié.
     */
    @GetMapping("/game/{gameCode}")
    public ResponseEntity<List<VoteResponse>> getVotesByGameCode(@PathVariable String gameCode) {
        List<VoteResponse> votes = voteService.getVotesByGameCode(gameCode);
        return ResponseEntity.ok(votes);
    }


    /**
     * @brief Supprime un vote spécifique en fonction de son ID.
     *
     * Cette méthode permet de supprimer un vote en utilisant son identifiant unique.
     *
     * @param id L'ID du vote à supprimer.
     * @return Un message de confirmation de la suppression.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVote(@PathVariable Long id) {
        String response = voteService.deleteVote(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
