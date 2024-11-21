package com.master1.planningpoker.service.Rule;

import com.master1.planningpoker.models.Rule;

import java.util.List;

public interface IRuleService {

    public Rule createRule(String name, String description);
    public Rule getRule(Long ruleId);
    List<Rule> getAllRules();
    void updateRule(Long ruleId, String name, String description);
}
