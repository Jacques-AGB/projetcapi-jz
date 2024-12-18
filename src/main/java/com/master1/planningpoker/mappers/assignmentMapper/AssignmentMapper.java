package com.master1.planningpoker.mappers.assignmentMapper;

import com.master1.planningpoker.dtos.request.assignmentRequest.AddAssignmentRequest;
import com.master1.planningpoker.dtos.responses.assignmentResponses.AssignmentResponse;
import com.master1.planningpoker.models.Assignment;
import com.master1.planningpoker.models.Game;
import com.master1.planningpoker.repositories.GameRepository;
import org.springframework.stereotype.Component;

/**
 * @class AssignmentMapper
 * @brief Mapper pour la conversion entre les entités Assignment, les requêtes DTO (AddAssignmentRequest),
 *        et les réponses DTO (AssignmentResponse).
 *
 * Cette classe est utilisée pour la transformation des objets `Assignment` vers des DTOs et vice-versa,
 * facilitant ainsi l'interaction avec les différentes couches de l'application (comme les contrôleurs et les services).
 */
@Component
public class AssignmentMapper {

    private final GameRepository gameRepository;

    public AssignmentMapper(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Méthode pour convertir un `AddAssignmentRequest` en une entité `Assignment`.
     * Cette méthode prend un objet DTO `AddAssignmentRequest`, crée un nouvel objet `Assignment` et le remplit avec les données correspondantes.
     *
     * @param request Le DTO contenant les informations de la tâche à créer.
     * @return Un objet `Assignment` contenant les informations du DTO.
     * @throws IllegalArgumentException Si l'ID du jeu fourni n'existe pas dans la base de données.
     */
    public Assignment toEntity(AddAssignmentRequest request) {
        Assignment assignment = new Assignment();
        assignment.setLibelle(request.getLibelle());
        assignment.setDescription(request.getDescription());
        assignment.setDifficulty(request.getDifficulty());
        Game game = gameRepository.findById(request.getGameId())
                .orElseThrow(() -> new IllegalArgumentException("Game not found with ID: " + request.getGameId()));
        assignment.setGame(game);
        return assignment;
    }


    /**
     * Méthode pour convertir un objet `Assignment` en un objet `AssignmentResponse`.
     * Cette méthode prend un objet `Assignment` et le convertit en une réponse DTO contenant toutes les informations nécessaires.
     *
     * @param assignment L'entité `Assignment` à convertir.
     * @return Un objet `AssignmentResponse` contenant les informations du `Assignment`.
     */
    public AssignmentResponse toResponse(Assignment assignment) {
        return AssignmentResponse.builder()
                .id(assignment.getId())
                .libelle(assignment.getLibelle())
                .description(assignment.getDescription())
                .difficulty(assignment.getDifficulty())
                .game(assignment.getGame())
                .votes(assignment.getVotes())
                .build();
    }
}
