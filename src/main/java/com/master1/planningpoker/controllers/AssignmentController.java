package com.master1.planningpoker.controllers;

import com.master1.planningpoker.dtos.request.AssignmentRequest;
import com.master1.planningpoker.dtos.responses.AssignmentResponse;
import com.master1.planningpoker.models.Assignment;
import com.master1.planningpoker.service.Assignment.AssignmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    /**
     * Endpoint pour ajouter une nouvelle tâche (Assignment).
     *
     * @param request L'objet contenant les données de la tâche.
     * @return La tâche nouvellement créée.
     */
    @PostMapping("/add")
    public ResponseEntity<AssignmentResponse> addAssignment(@RequestBody @Valid AssignmentRequest request) {
        AssignmentResponse newAssignment = assignmentService.addAssignment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAssignment);
    }

    /**
     * Endpoint to retrieve the backlog for a specific game.
     *
     * @param gameId The ID of the game.
     * @return The list of assignments (backlog).
     */
    @GetMapping("/backlog/{gameId}")
    public ResponseEntity<List<Assignment>> getBacklog(@PathVariable Long gameId) {
        List<Assignment> backlog = assignmentService.getBacklog(gameId);
        return ResponseEntity.ok(backlog);
    }

    /**
     * Endpoint to delete an assignment by ID.
     *
     * @param assignmentId The ID of the assignment to delete.
     * @return A success message.
     */
    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<String> removeAssignment(@PathVariable Long assignmentId) {
        assignmentService.removeAssignment(assignmentId);
        return ResponseEntity.ok("Assignment successfully deleted.");
    }

    /**
     * Endpoint to save the backlog of a game to a JSON file.
     *
     * @param gameId The ID of the game.
     * @return A success message.
     */
    @PostMapping("/backlog/save/{gameId}")
    public ResponseEntity<String> saveBacklogToJson(
            @PathVariable Long gameId) {
        assignmentService.saveBacklogToJson(gameId);
        return ResponseEntity.ok("Backlog successfully saved" );
    }

    /**
     * Endpoint to load assignments from a JSON file into a game.
     *
     * @param gameId The ID of the game.
     * @param filePath The file path from where the backlog should be loaded.
     * @return A success message.
     */
    @PostMapping("/backlog/load")
    public ResponseEntity<String> loadBacklogFromJson(
            @RequestParam Long gameId,
            @RequestParam String filePath) {
        assignmentService.loadBacklogFromJson(gameId, filePath);
        return ResponseEntity.ok("Backlog successfully loaded from " + filePath);
    }

}
