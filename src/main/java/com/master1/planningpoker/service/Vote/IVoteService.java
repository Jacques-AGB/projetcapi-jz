package com.master1.planningpoker.service.Vote;

import com.master1.planningpoker.models.Player;
import com.master1.planningpoker.models.Vote;

import java.util.List;

public interface IVoteService {
    public void submitVote(Long playerId, Long assignmentId, int value);
    public List<Vote> getVotesForAssignment(Long assignmentId);
    public int calculateResult(Long assignmentId, String ruleType);
    public void resetVotes(Long assignmentId);
}
