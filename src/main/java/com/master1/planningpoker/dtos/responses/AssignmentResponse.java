package com.master1.planningpoker.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.master1.planningpoker.models.Game;
import com.master1.planningpoker.models.Vote;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AssignmentResponse {
    private Long id;

    private String libelle;

    private String description;

    private Integer difficulty;

    @JsonIgnore
    private Game game;

    private List<Vote> votes;
}
