package com.master1.planningpoker.service.Assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.master1.planningpoker.dtos.request.assignmentRequest.AddAssignmentRequest;
import com.master1.planningpoker.dtos.responses.assignmentResponses.AssignmentResponse;
import com.master1.planningpoker.mappers.assignmentMapper.AssignmentMapper;
import com.master1.planningpoker.models.Assignment;
import com.master1.planningpoker.models.Game;
import com.master1.planningpoker.repositories.AssignmentRepository;
import com.master1.planningpoker.repositories.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentService implements IAssignmentService{
    @Autowired
    private final AssignmentRepository assignmentRepository;
    @Autowired
    private final AssignmentMapper assignmentMapper;
    @Autowired
    private final GameRepository gameRepository;

    @Override
    public AssignmentResponse addEditAssignment(AddAssignmentRequest request) {
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
        if (request.getId() == null){
            Assignment newAssignment = assignmentMapper.toEntity(request);
            Assignment savedAssignment = assignmentRepository.save(newAssignment);
            return assignmentMapper.toResponse(savedAssignment);
        }else {
            Assignment existingAssignment = assignmentRepository.findById(request.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Assignment not found with id: " + request.getId()));

            Game game = gameRepository.findById(request.getGameId())
                    .orElseThrow(() -> new IllegalArgumentException("Game not found with id: " + request.getGameId()));

            // Update fields
            existingAssignment.setLibelle(request.getLibelle());
            existingAssignment.setDescription(request.getDescription());
            existingAssignment.setDifficulty(request.getDifficulty());
            existingAssignment.setGame(game);

            // Save updated assignment
            Assignment updatedAssignment = assignmentRepository.save(existingAssignment);
            return assignmentMapper.toResponse(updatedAssignment);
        }

    }

    @Override
    public List<AssignmentResponse> getBacklog(Long gameId) {
        // Check if the game exists
        boolean gameExists = gameRepository.existsById(gameId);
        if (!gameExists) {
            throw new IllegalArgumentException("Game not found for the given ID: " + gameId);
        }
        return  assignmentRepository.findAllByGameId(gameId)
                .stream().map(assignmentMapper::toResponse).collect(Collectors.toList());

    }



    @Override
    public String removeAssignment(Long assignmentId) {
        assignmentRepository.deleteById(assignmentId);
        return "Assignment with id " + assignmentId + "removed successfully";
    }

    @Override
    public String saveBacklogToJson(Long gameId) {
        // Check if game exist
        boolean gameExists = gameRepository.existsById(gameId);
        if (!gameExists) {
            throw new IllegalArgumentException("Game not found for the given ID: " + gameId);
        }
        // get a specific game backlog
        List<Assignment> backlog = assignmentRepository.findAllByGameId(gameId);
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
    public AssignmentResponse getAssignment(Long id) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Assignment with id: " + id + "not found "));
        return assignmentMapper.toResponse(assignment);
    }

    @Override
    public List<AssignmentResponse> getAssignments() {
         List<Assignment> assignments = assignmentRepository.findAll();
        return assignments.stream()
                .map(assignmentMapper::toResponse)
                .collect(Collectors.toList());
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



