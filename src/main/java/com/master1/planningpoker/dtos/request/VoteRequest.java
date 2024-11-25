package com.master1.planningpoker.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class VoteRequest {
    private Long playerId;
    private Long assignmentId;
    private int value;
}
