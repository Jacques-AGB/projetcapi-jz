package com.master1.planningpoker.dtos.responses.ruleResponses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class RuleResponse {
    private Long id;

    private String name;
}
