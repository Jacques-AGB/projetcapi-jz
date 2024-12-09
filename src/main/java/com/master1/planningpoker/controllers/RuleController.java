package com.master1.planningpoker.controllers;

import com.master1.planningpoker.dtos.request.ruleRequests.AddRuleRequest;
import com.master1.planningpoker.dtos.responses.ruleResponses.RuleResponse;
import com.master1.planningpoker.service.Player.PlayerService;
import com.master1.planningpoker.service.Rule.RuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rules")
@RequiredArgsConstructor
public class RuleController {

    public final RuleService ruleService;
    @PostMapping("/createEdit")
    public ResponseEntity<RuleResponse> createEditRule(@RequestBody @Valid AddRuleRequest request) {
        RuleResponse response = ruleService.addEditRule(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("")
    public ResponseEntity<List<RuleResponse>> getRules() {
        List<RuleResponse> response = ruleService.getRules();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RuleResponse> getRule(@PathVariable Long id) {
        RuleResponse response = ruleService.getRule(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeRule(@PathVariable Long id) {
        String response = ruleService.deleteRule(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
