import com.master1.planningpoker.dtos.request.voteRequests.VoteRequest;
import com.master1.planningpoker.dtos.responses.voteResponses.VoteResponse;
import com.master1.planningpoker.models.Assignment;
import com.master1.planningpoker.models.Game;
import com.master1.planningpoker.models.Player;
import com.master1.planningpoker.service.Vote.IVoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IVoteServiceTest {

    @Mock
    private IVoteService voteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSubmitVote() {
        // Arrange
        Player player = new Player(1L, "JohnDoe", false, null); // Création d'un joueur
        Game game = new Game(); // Crée un objet Game vide (si nécessaire dans la relation)
        Assignment assignment = new Assignment();
        assignment.setId(1L);
        assignment.setLibelle("Task 1");
        assignment.setDescription("Task description");
        assignment.setGame(game); // Associe un jeu vide
        assignment.setVotes(new ArrayList<>()); // Initialiser la liste de votes vide

        VoteRequest request = new VoteRequest(1L, 1L, 5); // Requête de vote
        String expectedMessage = "Vote successfully submitted.";

        when(voteService.submitVote(request)).thenReturn(expectedMessage);

        // Act
        String response = voteService.submitVote(request);

        // Assert
        assertNotNull(response);
        assertEquals(expectedMessage, response);
        verify(voteService, times(1)).submitVote(request);
    }

    @Test
    void testGetVotes() {
        // Arrange
        Player player = new Player(1L, "JohnDoe", false, null); // Création d'un joueur
        Game game = new Game(); // Crée un objet Game vide
        Assignment assignment = new Assignment();
        assignment.setId(1L);
        assignment.setLibelle("Task 1");
        assignment.setDescription("Task description");
        assignment.setGame(game); // Associe un jeu vide
        assignment.setVotes(new ArrayList<>()); // Initialiser la liste de votes vide

        List<VoteResponse> expectedVotes = new ArrayList<>();

        VoteResponse vote1 = VoteResponse.builder()
                .id(1L)
                .value(5)
                .player(player)
                .assignment(assignment)
                .build();

        expectedVotes.add(vote1);

        when(voteService.getVotes()).thenReturn(expectedVotes);

        // Act
        List<VoteResponse> votes = voteService.getVotes();

        // Assert
        assertNotNull(votes);
        assertEquals(1, votes.size());
        assertEquals(1L, votes.get(0).getId());
        assertEquals(5, votes.get(0).getValue());
        assertEquals(player, votes.get(0).getPlayer());
        assertEquals(assignment, votes.get(0).getAssignment());
        verify(voteService, times(1)).getVotes();
    }

    @Test
    void testGetVotesByGameCode() {
        // Arrange
        String gameCode = "GAME123";
        Player player = new Player(1L, "JohnDoe", false, null); // Création d'un joueur
        Game game = new Game(); // Crée un objet Game vide
        Assignment assignment = new Assignment();
        assignment.setId(1L);
        assignment.setLibelle("Task 1");
        assignment.setDescription("Task description");
        assignment.setGame(game); // Associe un jeu vide
        assignment.setVotes(new ArrayList<>()); // Initialiser la liste de votes vide

        List<VoteResponse> expectedVotes = new ArrayList<>();

        VoteResponse vote = VoteResponse.builder()
                .id(1L)
                .value(4)
                .player(player)
                .assignment(assignment)
                .build();

        expectedVotes.add(vote);

        when(voteService.getVotesByGameCode(gameCode)).thenReturn(expectedVotes);

        // Act
        List<VoteResponse> votes = voteService.getVotesByGameCode(gameCode);

        // Assert
        assertNotNull(votes);
        assertEquals(1, votes.size());
        assertEquals(4, votes.get(0).getValue());
        assertEquals(player, votes.get(0).getPlayer());
        assertEquals(assignment, votes.get(0).getAssignment());
        verify(voteService, times(1)).getVotesByGameCode(gameCode);
    }

    @Test
    void testGetVote() {
        // Arrange
        Long voteId = 1L;
        Player player = new Player(1L, "JohnDoe", false, null); // Création d'un joueur
        Game game = new Game(); // Crée un objet Game vide
        Assignment assignment = new Assignment();
        assignment.setId(1L);
        assignment.setLibelle("Task 1");
        assignment.setDescription("Task description");
        assignment.setGame(game); // Associe un jeu vide
        assignment.setVotes(new ArrayList<>()); // Initialiser la liste de votes vide

        VoteResponse expectedResponse = VoteResponse.builder()
                .id(voteId)
                .value(3)
                .player(player)
                .assignment(assignment)
                .build();

        when(voteService.getVote(voteId)).thenReturn(expectedResponse);

        // Act
        VoteResponse response = voteService.getVote(voteId);

        // Assert
        assertNotNull(response);
        assertEquals(voteId, response.getId());
        assertEquals(3, response.getValue());
        assertEquals(player, response.getPlayer());
        assertEquals(assignment, response.getAssignment());
        verify(voteService, times(1)).getVote(voteId);
    }

    @Test
    void testGetVotesForAssignment() {
        // Arrange
        Long assignmentId = 1L;
        Player player = new Player(1L, "JohnDoe", false, null); // Création d'un joueur
        Game game = new Game(); // Crée un objet Game vide
        Assignment assignment = new Assignment();
        assignment.setId(assignmentId);
        assignment.setLibelle("Task 1");
        assignment.setDescription("Task description");
        assignment.setGame(game); // Associe un jeu vide
        assignment.setVotes(new ArrayList<>()); // Initialiser la liste de votes vide

        List<VoteResponse> expectedVotes = new ArrayList<>();

        VoteResponse vote = VoteResponse.builder()
                .id(1L)
                .value(5)
                .player(player)
                .assignment(assignment)
                .build();

        expectedVotes.add(vote);

        when(voteService.getVotesForAssignment(assignmentId)).thenReturn(expectedVotes);

        // Act
        List<VoteResponse> votes = voteService.getVotesForAssignment(assignmentId);

        // Assert
        assertNotNull(votes);
        assertEquals(1, votes.size());
        assertEquals(5, votes.get(0).getValue());
        assertEquals(player, votes.get(0).getPlayer());
        assertEquals(assignment, votes.get(0).getAssignment());
        verify(voteService, times(1)).getVotesForAssignment(assignmentId);
    }

    @Test
    void testDeleteVote() {
        // Arrange
        Long assignmentId = 1L;
        String expectedMessage = "Vote successfully deleted.";
        when(voteService.deleteVote(assignmentId)).thenReturn(expectedMessage);

        // Act
        String response = voteService.deleteVote(assignmentId);

        // Assert
        assertNotNull(response);
        assertEquals(expectedMessage, response);
        verify(voteService, times(1)).deleteVote(assignmentId);
    }

    @Test
    void testEvaluateAverage() {
        // Arrange
        Assignment assignment = new Assignment();
        assignment.setId(1L);
        assignment.setLibelle("Task 1");
        assignment.setDescription("Task description");
        assignment.setVotes(new ArrayList<>()); // Initialiser la liste de votes vide
        String expectedAverage = "Average: 4.5";
        when(voteService.evaluateAverage(assignment)).thenReturn(expectedAverage);

        // Act
        String result = voteService.evaluateAverage(assignment);

        // Assert
        assertNotNull(result);
        assertEquals(expectedAverage, result);
        verify(voteService, times(1)).evaluateAverage(assignment);
    }

    @Test
    void testEvaluateUnanimity() {
        // Arrange
        Assignment assignment = new Assignment();
        assignment.setId(1L);
        assignment.setLibelle("Task 1");
        assignment.setDescription("Task description");
        assignment.setVotes(new ArrayList<>()); // Initialiser la liste de votes vide
        String expectedResult = "Unanimity achieved.";
        when(voteService.evaluateUnanimity(assignment)).thenReturn(expectedResult);

        // Act
        String result = voteService.evaluateUnanimity(assignment);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResult, result);
        verify(voteService, times(1)).evaluateUnanimity(assignment);
    }
}
