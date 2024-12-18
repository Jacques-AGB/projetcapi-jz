package com.master1.planningpoker.service.Rule;

import com.master1.planningpoker.dtos.request.ruleRequests.AddRuleRequest;
import com.master1.planningpoker.dtos.responses.ruleResponses.RuleResponse;
import com.master1.planningpoker.mappers.ruleMapper.RuleMapper;
import com.master1.planningpoker.models.Rule;
import com.master1.planningpoker.repositories.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service de gestion des règles du jeu dans l'application.
 * Fournit des méthodes pour créer, éditer, supprimer et récupérer des règles.
 */
@Service
@RequiredArgsConstructor
public class RuleService implements IRuleService {

    @Autowired
    public final RuleRepository ruleRepository;

    @Autowired
    public RuleMapper ruleMapper;

    /**
     * Crée ou met à jour une règle dans l'application en fonction des informations fournies dans la requête.
     * Si la règle existe déjà, une exception est levée.
     *
     * @param request Les informations de la règle à ajouter ou à modifier.
     * @return Un objet {@link RuleResponse} représentant la règle créée ou mise à jour.
     * @throws IllegalArgumentException si la règle existe déjà ou si l'identifiant n'est pas trouvé.
     */
    @Override
    public RuleResponse addEditRule(AddRuleRequest request) {
        boolean exists = ruleRepository.existsByName(request.getName());
        if (exists) {
            throw new IllegalArgumentException("The rule : " + request.getName() + " Already exist.");
        }
        if (request.getId() == null) {
            Rule newRule = ruleMapper.toEntity(request);
            Rule savedRule = ruleRepository.save(newRule);
            return ruleMapper.toResponse(savedRule);
        } else {
            Rule existingRule = ruleRepository.findById(request.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Rule not found with id: " + request.getId()));
            existingRule.setName(request.getName());
            Rule updatedRule = ruleRepository.save(existingRule);
            return ruleMapper.toResponse(updatedRule);
        }
    }

    /**
     * Récupère une règle spécifique en fonction de son identifiant.
     *
     * @param id L'identifiant de la règle à récupérer.
     * @return Un objet {@link RuleResponse} représentant la règle demandée.
     * @throws IllegalArgumentException si aucune règle n'est trouvée pour l'identifiant donné.
     */
    @Override
    public RuleResponse getRule(Long id) {
        Rule rule = ruleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rule not found with id: " + id));
        return ruleMapper.toResponse(rule);
    }

    /**
     * Récupère toutes les règles présentes dans l'application.
     *
     * @return Une liste d'objets {@link RuleResponse} représentant toutes les règles.
     */
    @Override
    public List<RuleResponse> getRules() {
        List<Rule> rules = ruleRepository.findAll();
        return rules.stream()
                .map(ruleMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Supprime une règle en fonction de son identifiant.
     *
     * @param id L'identifiant de la règle à supprimer.
     * @return Un message confirmant la suppression de la règle.
     */
    @Override
    public String deleteRule(Long id) {
        ruleRepository.deleteById(id);
        return " Rule " + id + " deleted successfully";
    }
}
