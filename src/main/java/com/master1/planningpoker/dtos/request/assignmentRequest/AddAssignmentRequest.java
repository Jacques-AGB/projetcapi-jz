package com.master1.planningpoker.dtos.request.assignmentRequest;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddAssignmentRequest {
    private Long id;
    private String libelle;
    private String description;
    private Integer difficulty;
    private Long gameId;

}
