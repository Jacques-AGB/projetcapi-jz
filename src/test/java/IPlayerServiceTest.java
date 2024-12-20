import com.master1.planningpoker.dtos.request.playerRequests.CreatePlayerRequest;
import com.master1.planningpoker.dtos.request.playerRequests.JoinGameRequest;
import com.master1.planningpoker.dtos.responses.playerResponses.PlayerResponse;
import com.master1.planningpoker.service.Player.IPlayerService;
import com.master1.planningpoker.models.Game;
import com.master1.planningpoker.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IPlayerServiceTest {

    @Mock
    private IPlayerService playerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testJoinGame() {
        // Arrange
        String pseudo = "JohnDoe";
        String code = "GAME123";
        boolean isAdmin = false;

        JoinGameRequest request = new JoinGameRequest(pseudo, code, isAdmin);
        String expectedMessage = "Player successfully joined the game.";

        when(playerService.joinGame(request)).thenReturn(expectedMessage);

        // Act
        String response = playerService.joinGame(request);

        // Assert
        assertNotNull(response);
        assertEquals(expectedMessage, response);
        verify(playerService, times(1)).joinGame(request);
    }

    @Test
    void testCreateEditPlayer() {
        // Arrange
        CreatePlayerRequest request = new CreatePlayerRequest(1L, "JohnDoe", "GAME123", false);
        PlayerResponse expectedResponse = new PlayerResponse(1L, "JohnDoe", false);

        when(playerService.createEditPlayer(request)).thenReturn(expectedResponse);

        // Act
        PlayerResponse response = playerService.createEditPlayer(request);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getPseudo(), response.getPseudo());
        assertEquals(expectedResponse.isAdmin(), response.isAdmin());
        verify(playerService, times(1)).createEditPlayer(request);
    }

    @Test
    void testGetPlayers() {
        // Arrange
        List<PlayerResponse> expectedPlayers = new ArrayList<>();
        expectedPlayers.add(new PlayerResponse(1L, "JohnDoe", false));

        when(playerService.getPlayers()).thenReturn(expectedPlayers);

        // Act
        List<PlayerResponse> players = playerService.getPlayers();

        // Assert
        assertNotNull(players);
        assertEquals(1, players.size());
        assertEquals("JohnDoe", players.get(0).getPseudo());
        verify(playerService, times(1)).getPlayers();
    }

    @Test
    void testGetPlayer() {
        // Arrange
        String pseudo = "JohnDoe";
        PlayerResponse expectedResponse = new PlayerResponse(1L, "JohnDoe", false);

        when(playerService.getPlayer(pseudo)).thenReturn(expectedResponse);

        // Act
        PlayerResponse response = playerService.getPlayer(pseudo);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getPseudo(), response.getPseudo());
        assertEquals(expectedResponse.isAdmin(), response.isAdmin());
        verify(playerService, times(1)).getPlayer(pseudo);
    }

    @Test
    void testRemovePlayer() {
        // Arrange
        Long playerId = 1L;
        String expectedMessage = "Player successfully removed.";

        when(playerService.removePlayer(playerId)).thenReturn(expectedMessage);

        // Act
        String response = playerService.removePlayer(playerId);

        // Assert
        assertNotNull(response);
        assertEquals(expectedMessage, response);
        verify(playerService, times(1)).removePlayer(playerId);
    }
}
