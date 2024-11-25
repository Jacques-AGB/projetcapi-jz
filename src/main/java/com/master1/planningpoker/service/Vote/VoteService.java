package com.master1.planningpoker.service.Vote;


import com.master1.planningpoker.dtos.request.VoteRequest;
import com.master1.planningpoker.models.Assignment;
import com.master1.planningpoker.models.Player;
import com.master1.planningpoker.models.Vote;
import com.master1.planningpoker.repositories.AssignmentRepository;
import com.master1.planningpoker.repositories.PlayerRepository;
import com.master1.planningpoker.repositories.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService implements IVoteService{
    private final AssignmentRepository assignmentRepository;
    private final PlayerRepository playerRepository;
    private final VoteRepository voteRepository;


    @Override
    public String submitVote(VoteRequest request) {
        Optional<Assignment> assignment = assignmentRepository.findById(request.getAssignmentId());
        if (assignment.isEmpty()) {
            throw new IllegalArgumentException("An assignment doesn't exists.");
        }
        Optional<Player> playerExists = playerRepository.findById(request.getPlayerId());
        if (playerExists.isEmpty()) {
            throw new IllegalArgumentException("A player doesn't exists.");
        }
        Vote vote = new Vote();
        vote.setAssignment(assignment.get());
        vote.setPlayer(playerExists.get());
        vote.setValue(request.getValue());

        voteRepository.save(vote);
        return "Vote fait";
    }

    @Override
    public List<Vote> getVotesForAssignment(Long assignmentId) {
        return List.of();
    }

    @Override
    public int calculateResult(Long assignmentId, String ruleType) {
        return 0;
    }

    @Override
    public void resetVotes(Long assignmentId) {

    }
}
