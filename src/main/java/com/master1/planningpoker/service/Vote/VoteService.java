package com.master1.planningpoker.service.Vote;


import com.master1.planningpoker.dtos.request.voteRequests.VoteRequest;
import com.master1.planningpoker.dtos.responses.voteResponses.VoteResponse;
import com.master1.planningpoker.mappers.voteMapper.VoteMapper;
import com.master1.planningpoker.models.Assignment;
import com.master1.planningpoker.models.Player;
import com.master1.planningpoker.models.Vote;
import com.master1.planningpoker.repositories.AssignmentRepository;
import com.master1.planningpoker.repositories.PlayerRepository;
import com.master1.planningpoker.repositories.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteService implements IVoteService{
    @Autowired
    private final AssignmentRepository assignmentRepository;
    @Autowired
    private final PlayerRepository playerRepository;
    @Autowired
    private final VoteRepository voteRepository;
    @Autowired
    private final VoteMapper voteMapper;


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
        return "Player " + request.getPlayerId() + " has voted";
    }

    @Override
    public List<VoteResponse> getVotes() {
         return voteRepository.findAll().
                 stream().map(voteMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<VoteResponse> getVotesByGameCode(String code) {
        return voteRepository.findVotesByGameCode(code).
                stream().map(voteMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public VoteResponse getVote(Long id) {
        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vote with id : "+ id +" doesn't exists."));
        return voteMapper.toResponse(vote);
    }

    @Override
    public List<VoteResponse> getVotesForAssignment(Long assignmentId) {
        List<Vote> vote = voteRepository.findByAssignmentId(assignmentId);
        return vote.stream()
                .map(voteMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public String deleteVote(Long id) {
        voteRepository.deleteById(id);
        return "Vote : "+ id+" deleted successfully";
    }


}
