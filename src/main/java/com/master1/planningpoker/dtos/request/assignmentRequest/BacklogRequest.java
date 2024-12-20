package com.master1.planningpoker.dtos.request.assignmentRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class BacklogRequest {
    private List<AssignmentRequest> assignments;
}