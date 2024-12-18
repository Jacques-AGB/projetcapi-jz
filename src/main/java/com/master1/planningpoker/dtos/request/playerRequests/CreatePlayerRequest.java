package com.master1.planningpoker.dtos.request.playerRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;



/**
 * @class CreatePlayerRequest
 * @brief Représente une requête pour créer ou modifier un joueur dans le système de Planning Poker.
 *
 * Cette classe est utilisée pour encapsuler les informations nécessaires lors de l'inscription
 * ou de la mise à jour des détails d'un joueur. Elle contient des informations sur le joueur,
 * telles que son pseudo, son rôle, et le code du jeu auquel il est associé.
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CreatePlayerRequest {
    private Long id;
    private String pseudo;
    private String code;
    private boolean isAdmin;
}
