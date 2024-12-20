package com.master1.planningpoker.service.Rule;

import com.master1.planningpoker.dtos.request.ruleRequests.AddRuleRequest;
import com.master1.planningpoker.dtos.responses.ruleResponses.RuleResponse;

import java.util.List;

/**
 * Interface définissant les services relatifs à la gestion des règles dans l'application.
 * Elle contient des méthodes pour ajouter ou éditer des règles, récupérer une règle spécifique,
 * obtenir toutes les règles et supprimer une règle.
 */
public interface IRuleService {

    /**
     * Crée ou met à jour une règle dans le système en fonction des informations fournies dans la requête.
     *
     * @param request La requête contenant les informations de la règle à créer ou mettre à jour.
     * @return Un objet {@link RuleResponse} représentant les détails de la règle créée ou modifiée.
     */
    public RuleResponse addEditRule(AddRuleRequest request);

    /**
     * Récupère une règle spécifique en fonction de son identifiant.
     *
     * @param id L'identifiant de la règle à récupérer.
     * @return Un objet {@link RuleResponse} représentant les détails de la règle.
     * @throws IllegalArgumentException si aucune règle n'est trouvée pour l'identifiant donné.
     */
    public RuleResponse getRule(Long id);

    /**
     * Supprime une règle du système en fonction de son identifiant.
     *
     * @param id L'identifiant de la règle à supprimer.
     * @return Un message confirmant la suppression de la règle.
     */
    public String deleteRule(Long id);

    /**
     * Récupère la liste de toutes les règles disponibles dans le système.
     *
     * @return Une liste d'objets {@link RuleResponse} représentant toutes les règles.
     */
    List<RuleResponse> getRules();
}
