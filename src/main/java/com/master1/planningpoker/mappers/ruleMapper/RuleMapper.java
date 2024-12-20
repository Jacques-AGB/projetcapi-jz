package com.master1.planningpoker.mappers.ruleMapper;

import com.master1.planningpoker.dtos.request.playerRequests.CreatePlayerRequest;
import com.master1.planningpoker.dtos.request.ruleRequests.AddRuleRequest;
import com.master1.planningpoker.dtos.responses.playerResponses.PlayerResponse;
import com.master1.planningpoker.dtos.responses.ruleResponses.RuleResponse;
import com.master1.planningpoker.models.Player;
import com.master1.planningpoker.models.Rule;
import org.springframework.stereotype.Component;


/**
 * @class RuleMapper
 * @brief Mapper pour la conversion entre les entités Rule, les requêtes DTO (AddRuleRequest),
 *        et les réponses DTO (RuleResponse).
 *
 * Cette classe facilite la transformation des objets `Rule` vers des DTOs et vice-versa,
 * permettant ainsi une interaction plus fluide entre les différentes couches de l'application, telles que les contrôleurs et les services.
 */
@Component
public class RuleMapper {

    /**
     * Méthode pour convertir un objet `Rule` en un objet `RuleResponse`.
     * Cette méthode prend une entité `Rule` et la convertit en une réponse DTO contenant toutes les informations nécessaires.
     *
     * @param rule L'entité `Rule` à convertir.
     * @return Un objet `RuleResponse` contenant les informations du `Rule`.
     *
     * @note Cette méthode est utilisée pour transmettre les informations sur la règle à travers les réponses API.
     */
    public RuleResponse toResponse(Rule rule) {
        return RuleResponse.builder()
                .id(rule.getId())
                .name(rule.getName())
                .build();
    }


    /**
     * Méthode pour convertir un `AddRuleRequest` en une entité `Rule`.
     * Cette méthode prend un objet DTO `AddRuleRequest`, crée un nouvel objet `Rule` et le remplit avec les données correspondantes.
     *
     * @param request Le DTO contenant les informations de la règle à créer.
     * @return Un objet `Rule` contenant les informations du DTO.
     */
    public Rule toEntity(AddRuleRequest request) {
        Rule rule = new Rule();
        rule.setName(request.getName());
        return  rule;
    }
}
