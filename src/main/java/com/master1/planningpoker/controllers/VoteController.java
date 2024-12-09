package com.master1.planningpoker.controllers;


import com.master1.planningpoker.dtos.request.voteRequests.VoteRequest;
import com.master1.planningpoker.dtos.responses.voteResponses.VoteResponse;
import com.master1.planningpoker.service.Vote.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VoteController {

    public final VoteService voteService;

    @PostMapping("/submit")
    public ResponseEntity<String> submitVote(@RequestBody @Valid VoteRequest request) {
        String response = voteService.submitVote(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVote(@PathVariable Long id) {
        String response = voteService.deleteVote(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
