package com.master1.planningpoker.service.Rule;


import com.master1.planningpoker.dtos.request.ruleRequests.AddRuleRequest;
import com.master1.planningpoker.dtos.responses.ruleResponses.RuleResponse;
import com.master1.planningpoker.mappers.ruleMapper.RuleMapper;
import com.master1.planningpoker.models.Game;
import com.master1.planningpoker.models.Rule;
import com.master1.planningpoker.repositories.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RuleService implements  IRuleService{
    @Autowired
    public final RuleRepository ruleRepository;
    @Autowired
    public RuleMapper ruleMapper;

    @Override
    public RuleResponse addEditRule(AddRuleRequest request) {
        boolean exists = ruleRepository.existsByName(request.getName());
        if (exists) {
            throw new IllegalArgumentException("The rule : " + request.getName() + " Already exist.");
        }
        if (request.getId() == null){
            Rule newRule = ruleMapper.toEntity(request);
            Rule savedRule = ruleRepository.save(newRule);
            return ruleMapper.toResponse(savedRule);
        }else {
            Rule existingRule = ruleRepository.findById(request.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Rule not found with id: " + request.getId()));
            existingRule.setName(request.getName());
            Rule updatedRule = ruleRepository.save(existingRule);
            return ruleMapper.toResponse(updatedRule);

        }


    }

    @Override
    public RuleResponse getRule(Long id) {
        Rule rule = ruleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rule not found with id: " + id));
        return ruleMapper.toResponse(rule);
    }

    @Override
    public List<RuleResponse> getRules() {
        List<Rule> rules = ruleRepository.findAll();
        return rules.stream()
                .map(ruleMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public String deleteRule(Long id) {
        ruleRepository.deleteById(id);
        return " Rule "+ id + "deleted successfully";
    }


}
