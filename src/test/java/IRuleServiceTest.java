import com.master1.planningpoker.dtos.request.ruleRequests.AddRuleRequest;
import com.master1.planningpoker.dtos.responses.ruleResponses.RuleResponse;
import com.master1.planningpoker.service.Rule.IRuleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IRuleServiceTest {

    @Mock
    private IRuleService ruleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddEditRule() {
        // Arrange
        AddRuleRequest request = new AddRuleRequest();
        request.setId(1L);
        request.setName("Planning Poker Rule");

        RuleResponse expectedResponse = new RuleResponse();
        expectedResponse.setId(1L);
        expectedResponse.setName("Planning Poker Rule");

        when(ruleService.addEditRule(request)).thenReturn(expectedResponse);

        // Act
        RuleResponse response = ruleService.addEditRule(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Planning Poker Rule", response.getName());
        verify(ruleService, times(1)).addEditRule(request);
    }

    @Test
    void testGetRule() {
        // Arrange
        Long ruleId = 1L;
        RuleResponse expectedResponse = new RuleResponse();
        expectedResponse.setId(1L);
        expectedResponse.setName("Planning Poker Rule");

        when(ruleService.getRule(ruleId)).thenReturn(expectedResponse);

        // Act
        RuleResponse response = ruleService.getRule(ruleId);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Planning Poker Rule", response.getName());
        verify(ruleService, times(1)).getRule(ruleId);
    }

    @Test
    void testDeleteRule() {
        // Arrange
        Long ruleId = 1L;
        String expectedMessage = "Rule successfully deleted.";
        when(ruleService.deleteRule(ruleId)).thenReturn(expectedMessage);

        // Act
        String message = ruleService.deleteRule(ruleId);

        // Assert
        assertNotNull(message);
        assertEquals(expectedMessage, message);
        verify(ruleService, times(1)).deleteRule(ruleId);
    }

    @Test
    void testGetRules() {
        // Arrange
        List<RuleResponse> expectedRules = new ArrayList<>();
        RuleResponse rule1 = new RuleResponse();
        rule1.setId(1L);
        rule1.setName("Rule 1");
        RuleResponse rule2 = new RuleResponse();
        rule2.setId(2L);
        rule2.setName("Rule 2");
        expectedRules.add(rule1);
        expectedRules.add(rule2);

        when(ruleService.getRules()).thenReturn(expectedRules);

        // Act
        List<RuleResponse> rules = ruleService.getRules();

        // Assert
        assertNotNull(rules);
        assertEquals(2, rules.size());
        assertEquals(1L, rules.get(0).getId());
        assertEquals("Rule 1", rules.get(0).getName());
        assertEquals(2L, rules.get(1).getId());
        assertEquals("Rule 2", rules.get(1).getName());
        verify(ruleService, times(1)).getRules();
    }
}
