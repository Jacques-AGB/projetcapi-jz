package com.master1.planningpoker.service.Rule;


import com.master1.planningpoker.models.Rule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleService implements  IRuleService{
    @Override
    public Rule createRule(String name, String description) {
        return null;
    }

    @Override
    public Rule getRule(Long ruleId) {
        return null;
    }

    @Override
    public List<Rule> getAllRules() {
        return List.of();
    }

    @Override
    public void updateRule(Long ruleId, String name, String description) {

    }
}
