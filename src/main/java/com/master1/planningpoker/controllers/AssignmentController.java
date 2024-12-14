package com.master1.planningpoker.controllers;

import com.master1.planningpoker.dtos.request.assignmentRequest.AddAssignmentRequest;
import com.master1.planningpoker.dtos.responses.assignmentResponses.AssignmentResponse;
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

    @PostMapping("/addEdit")
    public ResponseEntity<AssignmentResponse> addAssignment(@RequestBody @Valid AddAssignmentRequest request) {
        AssignmentResponse newAssignment = assignmentService.addEditAssignment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAssignment);
    }


    @GetMapping("/backlog/{gameId}")
    public ResponseEntity<List<AssignmentResponse>> getBacklog(@PathVariable Long gameId) {
        List<AssignmentResponse> backlog = assignmentService.getBacklog(gameId);
        return ResponseEntity.ok(backlog);
    }

    @GetMapping("")
    public ResponseEntity<List<AssignmentResponse>> getAssignments() {
        List<AssignmentResponse> responses = assignmentService.getAssignments();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/game/{gameCode}")
    public ResponseEntity<List<AssignmentResponse>> getAssignmentsByGameCode(@PathVariable String gameCode) {
        List<AssignmentResponse> assignments = assignmentService.getAssignmentsByGameCode(gameCode);
        return ResponseEntity.ok(assignments);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AssignmentResponse> getAssignment(@PathVariable Long id) {
        AssignmentResponse response = assignmentService.getAssignment(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeAssignment(@PathVariable Long id) {
        String responses = assignmentService.removeAssignment(id);
        return ResponseEntity.ok(responses);
    }


    @PostMapping("/backlog/save/{gameId}")
    public ResponseEntity<String> saveBacklogToJson(
            @PathVariable Long gameId) {
        String response = assignmentService.saveBacklogToJson(gameId);
        return ResponseEntity.ok("Backlog successfully saved here :"+ response );
    }


    @PostMapping("/backlog/load")
    public ResponseEntity<String> loadBacklogFromJson(
            @RequestParam Long gameId,
            @RequestParam String filePath) {
        assignmentService.loadBacklogFromJson(gameId, filePath);
        return ResponseEntity.ok("Backlog successfully loaded from " + filePath);
    }

}
