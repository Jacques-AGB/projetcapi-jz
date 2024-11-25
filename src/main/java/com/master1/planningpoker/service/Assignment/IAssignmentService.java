package com.master1.planningpoker.service.Assignment;

import com.master1.planningpoker.dtos.request.assignmentRequest.AddAssignmentRequest;
import com.master1.planningpoker.dtos.responses.assignmentResponses.AssignmentResponse;

import java.util.List;

public interface IAssignmentService {
    AssignmentResponse addEditAssignment(AddAssignmentRequest request);

    String removeAssignment(Long assignmentId);

    String saveBacklogToJson(Long gameId);

    AssignmentResponse getAssignment(Long id);

    List<AssignmentResponse> getAssignments();

    List<AssignmentResponse> getBacklog(Long gameId);

    //Todo: correct loadBacklog method
    void loadBacklogFromJson(Long gameId, String filePath);


}

