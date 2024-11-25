package com.master1.planningpoker.mappers.voteMapper;


import com.master1.planningpoker.dtos.request.VoteRequest;
import com.master1.planningpoker.dtos.responses.PlayerResponse;
import com.master1.planningpoker.models.Assignment;
import com.master1.planningpoker.models.Game;
import com.master1.planningpoker.models.Player;
import com.master1.planningpoker.models.Vote;
import com.master1.planningpoker.repositories.AssignmentRepository;
import com.master1.planningpoker.repositories.GameRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VoteMapper {

    private final AssignmentRepository assignmentRepository;

    public VoteMapper(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }




}
