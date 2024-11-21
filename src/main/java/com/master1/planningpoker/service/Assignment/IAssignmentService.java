package com.master1.planningpoker.service.Assignment;

import com.master1.planningpoker.dtos.request.AssignmentRequest;
import com.master1.planningpoker.dtos.responses.AssignmentResponse;
import com.master1.planningpoker.models.Assignment;

import java.util.List;

public interface IAssignmentService {
    AssignmentResponse addAssignment(AssignmentRequest request);

    List<Assignment> getBacklog(Long gameId);

    void removeAssignment(Long assignmentId);

    String saveBacklogToJson(Long gameId);

    void loadBacklogFromJson(Long gameId, String filePath);
}

