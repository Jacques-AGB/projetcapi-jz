package com.master1.planningpoker.dtos.responses.playerResponses;


import com.master1.planningpoker.models.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @class PlayerResponse
 * @brief Représente la réponse détaillée d'un joueur dans le jeu.
 *
 * Cette classe encapsule les informations principales d'un joueur, telles que son identifiant,
 * son pseudonyme et son rôle d'administrateur dans le jeu.
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PlayerResponse {
    private Long Id;
    private String pseudo;
    private boolean isAdmin;
}

