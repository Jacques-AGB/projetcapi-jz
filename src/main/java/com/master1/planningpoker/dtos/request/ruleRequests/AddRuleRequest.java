package com.master1.planningpoker.dtos.request.ruleRequests;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class AddRuleRequest {
    private Long id;
    private String name;
}
