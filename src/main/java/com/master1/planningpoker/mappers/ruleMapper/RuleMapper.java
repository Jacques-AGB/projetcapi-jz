package com.master1.planningpoker.mappers.ruleMapper;

import com.master1.planningpoker.dtos.request.playerRequests.CreatePlayerRequest;
import com.master1.planningpoker.dtos.request.ruleRequests.AddRuleRequest;
import com.master1.planningpoker.dtos.responses.playerResponses.PlayerResponse;
import com.master1.planningpoker.dtos.responses.ruleResponses.RuleResponse;
import com.master1.planningpoker.models.Player;
import com.master1.planningpoker.models.Rule;
import org.springframework.stereotype.Component;

@Component
public class RuleMapper {


    public RuleResponse toResponse(Rule rule) {
        return RuleResponse.builder()
                .id(rule.getId())
                .name(rule.getName())
                .build();
    }

    public Rule toEntity(AddRuleRequest request) {
        Rule rule = new Rule();
        rule.setName(request.getName());
        return  rule;
    }
}
