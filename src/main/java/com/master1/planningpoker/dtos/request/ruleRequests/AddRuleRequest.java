package com.master1.planningpoker.dtos.request.ruleRequests;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @class AddRuleRequest
 * @brief Représente une requête pour ajouter ou modifier une règle dans le système de Planning Poker.
 *
 * Cette classe est utilisée pour encapsuler les informations nécessaires à la création ou à l'édition
 * d'une règle dans le système.
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class AddRuleRequest {
    private Long id;
    private String name;
}
