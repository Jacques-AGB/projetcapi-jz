package com.master1.planningpoker.mappers.voteMapper;


import com.master1.planningpoker.dtos.request.gameRequests.createGameRequest;
import com.master1.planningpoker.dtos.request.voteRequests.VoteRequest;
import com.master1.planningpoker.dtos.responses.gameResponses.GameResponse;
import com.master1.planningpoker.dtos.responses.voteResponses.VoteResponse;
import com.master1.planningpoker.models.*;
import com.master1.planningpoker.repositories.AssignmentRepository;
import com.master1.planningpoker.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @class VoteMapper
 * @brief Mapper pour la conversion entre les entités Vote, les requêtes DTO (VoteRequest),
 *        et les réponses DTO (VoteResponse).
 *
 * Cette classe facilite la transformation des objets `Vote` vers des DTOs et vice-versa,
 * assurant une communication fluide entre les différentes couches de l'application, telles que les contrôleurs et les services.
 */
@Component
@RequiredArgsConstructor
public class VoteMapper {

    private final AssignmentRepository assignmentRepository;
    private final PlayerRepository playerRepository;

    /**
     * Méthode pour convertir un objet `VoteRequest` en une entité `Vote`.
     * Cette méthode prend un DTO `VoteRequest`, crée un nouvel objet `Vote`, et le remplit avec les données correspondantes.
     *
     * Elle récupère l'assignation et le joueur à partir de leur ID respectif dans la base de données.
     *
     * @param request Le DTO contenant les informations du vote à créer.
     * @return Un objet `Vote` contenant les informations du DTO.
     * @throws IllegalArgumentException Si l'assignation ou le joueur spécifié par l'ID n'existe pas dans la base de données.
     */
    public Vote toEntity(VoteRequest request) {
        Vote vote = new Vote();
        vote.setValue(request.getValue());

        Assignment assignment = assignmentRepository.findById(request.getAssignmentId())
                .orElseThrow(() -> new IllegalArgumentException("Rule not found with ID: " + request.getAssignmentId()));
        vote.setAssignment(assignment);

        Player player = playerRepository.findById(request.getPlayerId())
                .orElseThrow(() -> new IllegalArgumentException("Player not found with ID: " + request.getPlayerId()));
        vote.setPlayer(player);
        return vote;
    }

    /**
     * Méthode pour convertir un objet `Vote` en un objet `VoteResponse`.
     * Cette méthode prend une entité `Vote` et la convertit en une réponse DTO contenant toutes les informations nécessaires.
     *
     * @param vote L'entité `Vote` à convertir.
     * @return Un objet `VoteResponse` contenant les informations du `Vote`.
     */
    public VoteResponse toResponse(Vote vote) {
        return VoteResponse.builder()
                .id(vote.getId())
                .value(vote.getValue())
                .assignment(vote.getAssignment())
                .player(vote.getPlayer())
                .build();
    }






}
