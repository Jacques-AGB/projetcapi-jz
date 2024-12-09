package com.master1.planningpoker.service.Rule;

import com.master1.planningpoker.dtos.request.ruleRequests.AddRuleRequest;
import com.master1.planningpoker.dtos.responses.ruleResponses.RuleResponse;
import com.master1.planningpoker.models.Rule;

import java.util.List;

public interface IRuleService {

    public RuleResponse addEditRule(AddRuleRequest request);
    public RuleResponse getRule(Long id);
    public String deleteRule(Long id);
    List<RuleResponse> getRules();
}
