package com.master1.planningpoker.service.Vote;

import com.master1.planningpoker.dtos.request.voteRequests.VoteRequest;
import com.master1.planningpoker.dtos.responses.voteResponses.VoteResponse;
import com.master1.planningpoker.models.Vote;

import java.util.List;

public interface IVoteService {
    public String submitVote(VoteRequest request);
    public List<VoteResponse> getVotes();
    public VoteResponse getVote(Long id);
    public List<VoteResponse> getVotesForAssignment(Long assignmentId);
    public String deleteVote(Long assignmentId);
}
