package com.master1.planningpoker.controllers;


import com.master1.planningpoker.dtos.request.VoteRequest;
import com.master1.planningpoker.service.Vote.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VoteController {

    public final VoteService voteService;
    /**
     * Endpoint pour voter.
     *
     * @param request L'objet contenant les données du jeu.
     * @return La jeu nouvellement créée.
     */
    @PostMapping("/submit")
    public ResponseEntity<String> addGame(@RequestBody @Valid VoteRequest request) {
        String response = voteService.submitVote(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
