package com.master1.planningpoker.controllers;

import com.master1.planningpoker.dtos.request.assignmentRequest.AddAssignmentRequest;
import com.master1.planningpoker.dtos.responses.assignmentResponses.AssignmentResponse;
import com.master1.planningpoker.service.Assignment.AssignmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


/**
 * @class AssignmentController
 * @brief Gestion des requêtes HTTP liées aux assignments (tâches).
 *
 * Ce contrôleur permet de gérer l'ajout, la modification, la récupération et la suppression des assignments.
 * Il fournit également des fonctionnalités pour récupérer le backlog d'un jeu et importer/Exporter les backlogs en JSON.
 */
@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    /**
     * @brief Ajoute ou modifie une tâche (assignment).
     * Cette méthode permet d'ajouter ou de modifier une tâche dans le système.
     * Elle prend une requête contenant les informations de la tâche et
     * retourne la réponse de l'ajout/modification.
     *
     * @param request La requête contenant les données de la tâche à ajouter/éditer.
     * @return Une réponse HTTP avec le statut 201 (Créé) et la tâche créée/modifiée.
     */
    @PostMapping("/addEdit")
    public ResponseEntity<AssignmentResponse> addAssignment(@RequestBody @Valid AddAssignmentRequest request) {
        AssignmentResponse newAssignment = assignmentService.addEditAssignment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAssignment);
    }


    /**
     * @brief Récupère le backlog des tâches d'un jeu spécifique.
     *
     * Cette méthode permet de récupérer toutes les tâches associées à un jeu, en fonction de l'ID du jeu.
     *
     * @param gameId L'ID du jeu pour lequel on souhaite récupérer le backlog.
     * @return Une liste des tâches (assignments) du backlog associées au jeu spécifié.
     */
    @GetMapping("/backlog/{gameId}")
    public ResponseEntity<List<AssignmentResponse>> getBacklog(@PathVariable Long gameId) {
        List<AssignmentResponse> backlog = assignmentService.getBacklog(gameId);
        return ResponseEntity.ok(backlog);
    }

    @PostMapping("/backlog/upload/{gameId}")
    public ResponseEntity<String> uploadBacklog(@PathVariable Long gameId, @RequestParam("file") MultipartFile file) {
        try {
            assignmentService.loadBacklogFromJson(gameId, file);
            return ResponseEntity.ok("Backlog uploaded and assignments added successfully to game ID: " + gameId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error uploading backlog: " + e.getMessage());
        }
    }

    /**
     * @brief Récupère la liste de toutes les tâches (assignments).
     *
     * Cette méthode permet de récupérer toutes les tâches (assignments) disponibles dans le système.
     *
     * @return Une liste de toutes les tâches disponibles.
     */
    @GetMapping("")
    public ResponseEntity<List<AssignmentResponse>> getAssignments() {
        List<AssignmentResponse> responses = assignmentService.getAssignments();
        return ResponseEntity.ok(responses);
    }

    /**
     * @brief Récupère les tâches associées à un jeu spécifique en fonction de son code.
     *
     * Cette méthode permet de récupérer toutes les tâches (assignments) associées à un jeu en utilisant le code du jeu.
     *
     * @param gameCode Le code du jeu pour lequel on souhaite récupérer les tâches.
     * @return Une liste des tâches (assignments) associées au jeu spécifié par son code.
     */
    @GetMapping("/game/{gameCode}")
    public ResponseEntity<List<AssignmentResponse>> getAssignmentsByGameCode(@PathVariable String gameCode) {
        List<AssignmentResponse> assignments = assignmentService.getAssignmentsByGameCode(gameCode);
        return ResponseEntity.ok(assignments);
    }

    /**
     * @brief Récupère une tâche spécifique en fonction de son ID.
     *
     * Cette méthode permet de récupérer les informations d'une tâche spécifique en utilisant son identifiant unique.
     *
     * @param id L'ID de la tâche à récupérer.
     * @return Les informations de la tâche spécifiée.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AssignmentResponse> getAssignment(@PathVariable Long id) {
        AssignmentResponse response = assignmentService.getAssignment(id);
        return ResponseEntity.ok(response);
    }


    /**
     * @brief Supprime une tâche spécifique en fonction de son ID.
     *
     * Cette méthode permet de supprimer une tâche en utilisant son identifiant unique.
     *
     * @param id L'ID de la tâche à supprimer.
     * @return Un message de confirmation de la suppression.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeAssignment(@PathVariable Long id) {
        String responses = assignmentService.removeAssignment(id);
        return ResponseEntity.ok(responses);
    }

    /**
     * @brief Sauvegarde le backlog des tâches d'un jeu spécifique dans un fichier JSON.
     *
     * Cette méthode permet de sauvegarder les tâches associées à un jeu spécifique dans un fichier JSON.
     *
     * @param gameId L'ID du jeu dont le backlog des tâches doit être sauvegardé.
     * @return Un message de confirmation de la sauvegarde du backlog.
     */



    /**
     * @brief Charge un backlog de tâches pour un jeu spécifique depuis un fichier JSON.
     *
     * Cette méthode permet de charger un backlog de tâches pour un jeu spécifique depuis un fichier JSON.
     *
     * @param gameId L'ID du jeu pour lequel le backlog doit être chargé.
     * @param filePath Le chemin du fichier JSON contenant les tâches à charger.
     * @return Un message de confirmation de l'importation du backlog.
     */


}
