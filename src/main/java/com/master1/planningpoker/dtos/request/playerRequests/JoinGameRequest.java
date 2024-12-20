package com.master1.planningpoker.dtos.request.playerRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @class JoinGameRequest
 * @brief Représente une requête pour qu'un joueur rejoigne une partie spécifique dans le système de Planning Poker.
 *
 * Cette classe est utilisée pour encapsuler les informations nécessaires à un joueur qui souhaite rejoindre
 * une partie existante. Elle contient le pseudonyme du joueur et le code de la partie à rejoindre.
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class JoinGameRequest {
    private String pseudo;
    private String code;
    @JsonProperty("isAdmin")
    private boolean isAdmin; // Champs isAdmin

}