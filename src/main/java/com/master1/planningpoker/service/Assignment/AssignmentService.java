package com.master1.planningpoker.service.Assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.master1.planningpoker.dtos.request.AssignmentRequest;
import com.master1.planningpoker.dtos.responses.AssignmentResponse;
import com.master1.planningpoker.mappers.assignmentMapper.AssignmentMapper;
import com.master1.planningpoker.models.Assignment;
import com.master1.planningpoker.models.Game;
import com.master1.planningpoker.repositories.AssignmentRepository;
import com.master1.planningpoker.repositories.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService implements IAssignmentService{

    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper assignmentMapper;
    private final GameRepository gameRepository;

    @Override
    public AssignmentResponse addAssignment(AssignmentRequest request) {
        boolean exists = assignmentRepository.existsByLibelle(request.getLibelle());
        if (exists) {
            throw new IllegalArgumentException("An assignment with the same libelle already exists.");
        }
        // Check if game exist
        boolean exist = gameRepository.existsById(request.getGameId());
        if(!exist){
            throw new IllegalArgumentException("This game doesn't exits");
        }

        // Create and save assignment
        Assignment newAssignment = assignmentMapper.toEntity(request);
        Assignment savedAssignment = assignmentRepository.save(newAssignment);
        return assignmentMapper.toResponse(savedAssignment);
    }

    @Override
    public List<Assignment> getBacklog(Long gameId) {
        // Check if the game exists
        boolean gameExists = gameRepository.existsById(gameId);
        if (!gameExists) {
            throw new IllegalArgumentException("Game not found for the given ID: " + gameId);
        }

        // Retrieve all assignments linked to the game
        return assignmentRepository.findByGameId(gameId);
    }

    @Override
    public void removeAssignment(Long assignmentId) {
        // Check if the assignment exist
        boolean assignmentExist = assignmentRepository.existsById(assignmentId);
        if (!assignmentExist) {
            throw new IllegalArgumentException("Assignment not found for the given ID: " + assignmentId);
        }
        assignmentRepository.deleteById(assignmentId);
    }

    @Override
    public String saveBacklogToJson(Long gameId) {
        // Check if game exist
        boolean gameExists = gameRepository.existsById(gameId);
        if (!gameExists) {
            throw new IllegalArgumentException("Game not found for the given ID: " + gameId);
        }
        // get a specific game backlog
        List<Assignment> backlog = assignmentRepository.findByGameId(gameId);
        // build and save backlog in a json file
        Path downloadDir = Paths.get(System.getProperty("user.home"), "Downloads", "backlog");
        File backlogDir = downloadDir.toFile();
        if (!backlogDir.exists() && !backlogDir.mkdirs()) {
            throw new RuntimeException("Failed to create backlog directory: " + backlogDir.getAbsolutePath());
        }
        String fileName = "backlog_" + gameId + ".json";
        File filePath = new File(backlogDir, fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(filePath, backlog);
            return filePath.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write backlog to file: " + e.getMessage(), e);
        }
    }

    @Override
    public void loadBacklogFromJson(Long gameId, String filePath) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found for the given ID: " + gameId));

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Assignment> assignments = List.of(objectMapper.readValue(new File(filePath), Assignment[].class));

            for (Assignment assignment : assignments) {
                assignment.setGame(game);
                assignmentRepository.save(assignment);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load backlog from file: " + e.getMessage());
        }
    }
}



