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

@Component
@RequiredArgsConstructor
public class VoteMapper {

    private final AssignmentRepository assignmentRepository;
    private final PlayerRepository playerRepository;

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

    public VoteResponse toResponse(Vote vote) {
        return VoteResponse.builder()
                .id(vote.getId())
                .value(vote.getValue())
                .assignment(vote.getAssignment())
                .player(vote.getPlayer())
                .build();
    }






}
