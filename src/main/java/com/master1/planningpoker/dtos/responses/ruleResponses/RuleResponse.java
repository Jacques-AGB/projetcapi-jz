package com.master1.planningpoker.dtos.responses.ruleResponses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @class RuleResponse
 * @brief Représente la réponse détaillée d'une règle dans le jeu.
 *
 * Cette classe encapsule les informations principales d'une règle, telles que son identifiant
 * et son nom.
 */

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class RuleResponse {
    private Long id;

    private String name;
}
