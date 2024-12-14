package com.master1.planningpoker.controllers;


import com.master1.planningpoker.dtos.request.voteRequests.VoteRequest;
import com.master1.planningpoker.dtos.responses.voteResponses.VoteResponse;
import com.master1.planningpoker.service.Vote.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VoteController {

    public final VoteService voteService;

    @PostMapping("/submit")
    public ResponseEntity<Map<String, String>> submitVote(@RequestBody VoteRequest request) {
        // Créer une réponse au format JSON
        Map<String, String> response = new HashMap<>();

        try {
            // Appeler la fonction submitVote du service (voteService)
            String voteResponse = voteService.submitVote(request);

            // Ajouter la réponse du vote dans le JSON
            response.put("message", "Player " + request.getPlayerId() + " has voted");


            // Retourner la réponse avec un statut 201 CREATED
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            // En cas d'erreur, vous pouvez gérer l'exception ici et envoyer une réponse d'erreur
            response.put("error", "An error occurred while submitting the vote.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<VoteResponse>> getVotes() {
        List<VoteResponse> responses = voteService.getVotes();
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoteResponse> getVote(@PathVariable Long id) {
        VoteResponse response = voteService.getVote(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{assignmentId}")
    public ResponseEntity<List<VoteResponse>> getVotesForAssignment(@PathVariable Long assignmentId) {
        List<VoteResponse> responses = voteService.getVotesForAssignment(assignmentId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    @GetMapping("/game/{gameCode}")
    public ResponseEntity<List<VoteResponse>> getVotesByGameCode(@PathVariable String gameCode) {
        List<VoteResponse> votes = voteService.getVotesByGameCode(gameCode);
        return ResponseEntity.ok(votes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVote(@PathVariable Long id) {
        String response = voteService.deleteVote(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
