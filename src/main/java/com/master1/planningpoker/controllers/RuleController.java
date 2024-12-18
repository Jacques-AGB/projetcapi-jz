package com.master1.planningpoker.controllers;

import com.master1.planningpoker.dtos.request.ruleRequests.AddRuleRequest;
import com.master1.planningpoker.dtos.responses.ruleResponses.RuleResponse;
import com.master1.planningpoker.service.Player.PlayerService;
import com.master1.planningpoker.service.Rule.RuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/**
 * @class RuleController
 * @brief Gestion des requêtes HTTP liées aux règles (rules).
 *
 * Ce contrôleur permet de gérer les opérations CRUD pour les règles associées à l'application,
 * telles que la création, la modification, la récupération et la suppression des règles.
 */
@RestController
@RequestMapping("/api/rules")
@RequiredArgsConstructor
public class RuleController {


    public final RuleService ruleService;

    /**
     * @brief Crée ou modifie une règle.
     * Cette méthode permet d'ajouter une nouvelle règle ou de modifier une règle existante dans le système.
     *
     * @param request La requête contenant les informations de la règle à créer ou modifier.
     * @return Une réponse HTTP avec le statut 201 (Créé) et les informations de la règle créée/modifiée.
     */
    @PostMapping("/createEdit")
    public ResponseEntity<RuleResponse> createEditRule(@RequestBody @Valid AddRuleRequest request) {
        RuleResponse response = ruleService.addEditRule(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    /**
     * @brief Récupère la liste de toutes les règles.
     * Cette méthode permet de récupérer toutes les règles disponibles dans le système.
     *
     * @return Une liste des règles enregistrées.
     */
    @GetMapping("")
    public ResponseEntity<List<RuleResponse>> getRules() {
        List<RuleResponse> response = ruleService.getRules();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * @brief Récupère une règle spécifique en fonction de son ID.
     * Cette méthode permet de récupérer les informations d'une règle à partir de son identifiant unique.
     *
     * @param id L'identifiant unique de la règle à récupérer.
     * @return Les informations de la règle spécifiée.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RuleResponse> getRule(@PathVariable Long id) {
        RuleResponse response = ruleService.getRule(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * @brief Supprime une règle spécifique en fonction de son ID.
     * Cette méthode permet de supprimer une règle du système en utilisant son identifiant unique.
     *
     * @param id L'identifiant unique de la règle à supprimer.
     * @return Un message de confirmation de la suppression de la règle.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeRule(@PathVariable Long id) {
        String response = ruleService.deleteRule(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
