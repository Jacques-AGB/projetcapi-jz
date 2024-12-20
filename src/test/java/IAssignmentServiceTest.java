
import com.master1.planningpoker.dtos.request.assignmentRequest.AddAssignmentRequest;
import com.master1.planningpoker.dtos.request.assignmentRequest.AssignmentRequest;
import com.master1.planningpoker.dtos.responses.assignmentResponses.AssignmentResponse;
import com.master1.planningpoker.models.Assignment;
import com.master1.planningpoker.service.Assignment.IAssignmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IAssignmentServiceTest {

    @Mock
    private IAssignmentService assignmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddEditAssignment() {
        // Arrange
        AddAssignmentRequest request = new AddAssignmentRequest();
        request.setLibelle("New Task");
        request.setDescription("Task Description");
        request.setDifficulty(3);

        AssignmentResponse expectedResponse = new AssignmentResponse();
        expectedResponse.setLibelle("New Task");
        expectedResponse.setDescription("Task Description");
        expectedResponse.setDifficulty(3);

        when(assignmentService.addEditAssignment(request)).thenReturn(expectedResponse);

        // Act
        AssignmentResponse response = assignmentService.addEditAssignment(request);

        // Assert
        assertNotNull(response);
        assertEquals("New Task", response.getLibelle());
        assertEquals("Task Description", response.getDescription());
        verify(assignmentService, times(1)).addEditAssignment(request);
    }

    @Test
    void testRemoveAssignment() {
        // Arrange
        Long assignmentId = 1L;
        String expectedMessage = "Assignment successfully removed.";
        when(assignmentService.removeAssignment(assignmentId)).thenReturn(expectedMessage);

        // Act
        String message = assignmentService.removeAssignment(assignmentId);

        // Assert
        assertNotNull(message);
        assertEquals(expectedMessage, message);
        verify(assignmentService, times(1)).removeAssignment(assignmentId);
    }

    @Test
    void testSaveBacklogToJson() {
        // Arrange
        Long gameId = 1L;
        String expectedFilePath = "/path/to/backlog.json";
        when(assignmentService.saveBacklogToJson(gameId)).thenReturn(expectedFilePath);

        // Act
        String filePath = assignmentService.saveBacklogToJson(gameId);

        // Assert
        assertNotNull(filePath);
        assertEquals(expectedFilePath, filePath);
        verify(assignmentService, times(1)).saveBacklogToJson(gameId);
    }

    @Test
    void testGetAssignment() {
        // Arrange
        Long assignmentId = 1L;
        AssignmentResponse expectedResponse = new AssignmentResponse();
        expectedResponse.setLibelle("Sample Task");

        when(assignmentService.getAssignment(assignmentId)).thenReturn(expectedResponse);

        // Act
        AssignmentResponse response = assignmentService.getAssignment(assignmentId);

        // Assert
        assertNotNull(response);
        assertEquals("Sample Task", response.getLibelle());
        verify(assignmentService, times(1)).getAssignment(assignmentId);
    }

    @Test
    void testGetAssignments() {
        // Arrange
        List<AssignmentResponse> expectedAssignments = new ArrayList<>();
        AssignmentResponse response1 = new AssignmentResponse();
        response1.setLibelle("Task 1");
        AssignmentResponse response2 = new AssignmentResponse();
        response2.setLibelle("Task 2");
        expectedAssignments.add(response1);
        expectedAssignments.add(response2);

        when(assignmentService.getAssignments()).thenReturn(expectedAssignments);

        // Act
        List<AssignmentResponse> assignments = assignmentService.getAssignments();

        // Assert
        assertNotNull(assignments);
        assertEquals(2, assignments.size());
        verify(assignmentService, times(1)).getAssignments();
    }

    @Test
    void testGetAssignmentsByGameCode() {
        // Arrange
        String gameCode = "GAME123";
        List<AssignmentResponse> expectedAssignments = new ArrayList<>();
        AssignmentResponse response = new AssignmentResponse();
        response.setLibelle("Task 1");
        expectedAssignments.add(response);

        when(assignmentService.getAssignmentsByGameCode(gameCode)).thenReturn(expectedAssignments);

        // Act
        List<AssignmentResponse> assignments = assignmentService.getAssignmentsByGameCode(gameCode);

        // Assert
        assertNotNull(assignments);
        assertEquals(1, assignments.size());
        verify(assignmentService, times(1)).getAssignmentsByGameCode(gameCode);
    }

    @Test
    void testGetBacklog() {
        // Arrange
        Long gameId = 1L;
        List<AssignmentResponse> expectedBacklog = new ArrayList<>();
        AssignmentResponse response = new AssignmentResponse();
        response.setLibelle("Backlog Task");
        expectedBacklog.add(response);

        when(assignmentService.getBacklog(gameId)).thenReturn(expectedBacklog);

        // Act
        List<AssignmentResponse> backlog = assignmentService.getBacklog(gameId);

        // Assert
        assertNotNull(backlog);
        assertEquals(1, backlog.size());
        verify(assignmentService, times(1)).getBacklog(gameId);
    }

    @Test
    void testSaveBacklog() {
        // Arrange
        List<AssignmentRequest> backlogRequests = new ArrayList<>();
        AssignmentRequest request = new AssignmentRequest();
        request.setLibelle("Backlog Task");
        backlogRequests.add(request);

        Long gameId = 1L;

        doNothing().when(assignmentService).saveBacklog(backlogRequests, gameId);

        // Act
        assignmentService.saveBacklog(backlogRequests, gameId);

        // Assert
        verify(assignmentService, times(1)).saveBacklog(backlogRequests, gameId);
    }
}
