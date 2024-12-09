package com.master1.planningpoker.dtos.responses.voteResponses;


import com.master1.planningpoker.models.Assignment;
import com.master1.planningpoker.models.Player;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class VoteResponse {
    private Long id;

    private int value;

    private Player player;

    private Assignment assignment;
}
