package com.master1.planningpoker.service.Vote;


import com.master1.planningpoker.models.Vote;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService implements IVoteService{
    @Override
    public void submitVote(Long playerId, Long assignmentId, int value) {

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
