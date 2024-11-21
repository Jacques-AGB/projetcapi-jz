package com.master1.planningpoker.mappers.assignmentMapper;

import com.master1.planningpoker.dtos.request.AssignmentRequest;
import com.master1.planningpoker.dtos.responses.AssignmentResponse;
import com.master1.planningpoker.models.Assignment;
import com.master1.planningpoker.models.Game;
import com.master1.planningpoker.repositories.GameRepository;
import org.springframework.stereotype.Component;
@Component
public class AssignmentMapper {

    private final GameRepository gameRepository;

    public AssignmentMapper(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Assignment toEntity(AssignmentRequest request) {
        Assignment assignment = new Assignment();
        assignment.setLibelle(request.getLibelle());
        assignment.setDescription(request.getDescription());
        assignment.setDifficulty(request.getDifficulty());
        Game game = gameRepository.findById(request.getGameId())
                .orElseThrow(() -> new IllegalArgumentException("Game not found with ID: " + request.getGameId()));
        assignment.setGame(game);

        return assignment;
    }

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
