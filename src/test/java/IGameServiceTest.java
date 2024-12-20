import com.master1.planningpoker.dtos.request.gameRequests.createGameRequest;
import com.master1.planningpoker.dtos.responses.gameResponses.GameResponse;
import com.master1.planningpoker.service.Game.IGameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IGameServiceTest {

    @Mock
    private IGameService gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEditGame() {
        // Arrange
        createGameRequest request = new createGameRequest();
        request.setCode("GAME123");
        request.setMaxPlayers(5);
        request.setRuleId(1L);

        GameResponse expectedResponse = new GameResponse();
        expectedResponse.setCode("GAME123");
        expectedResponse.setMaxPlayers(5);
        expectedResponse.setRuleId(1L);

        when(gameService.createEditGame(request)).thenReturn(expectedResponse);

        // Act
        GameResponse response = gameService.createEditGame(request);

        // Assert
        assertNotNull(response);
        assertEquals("GAME123", response.getCode());
        assertEquals(5, response.getMaxPlayers());
        assertEquals(1L, response.getRuleId());
        verify(gameService, times(1)).createEditGame(request);
    }

    @Test
    void testGetGames() {
        // Arrange
        List<GameResponse> expectedGames = new ArrayList<>();
        GameResponse game1 = new GameResponse();
        game1.setCode("GAME123");
        game1.setMaxPlayers(5);
        GameResponse game2 = new GameResponse();
        game2.setCode("GAME456");
        game2.setMaxPlayers(10);
        expectedGames.add(game1);
        expectedGames.add(game2);

        when(gameService.getGames()).thenReturn(expectedGames);

        // Act
        List<GameResponse> games = gameService.getGames();

        // Assert
        assertNotNull(games);
        assertEquals(2, games.size());
        assertEquals("GAME123", games.get(0).getCode());
        assertEquals(5, games.get(0).getMaxPlayers());
        assertEquals("GAME456", games.get(1).getCode());
        assertEquals(10, games.get(1).getMaxPlayers());
        verify(gameService, times(1)).getGames();
    }

    @Test
    void testGetGameDetails() {
        // Arrange
        String gameCode = "GAME123";
        GameResponse expectedResponse = new GameResponse();
        expectedResponse.setCode("GAME123");
        expectedResponse.setMaxPlayers(5);

        when(gameService.getGameDetails(gameCode)).thenReturn(expectedResponse);

        // Act
        GameResponse response = gameService.getGameDetails(gameCode);

        // Assert
        assertNotNull(response);
        assertEquals("GAME123", response.getCode());
        assertEquals(5, response.getMaxPlayers());
        verify(gameService, times(1)).getGameDetails(gameCode);
    }

    @Test
    void testDeleteGame() {
        // Arrange
        Long gameId = 1L;
        String expectedMessage = "Game successfully deleted.";
        when(gameService.deleteGame(gameId)).thenReturn(expectedMessage);

        // Act
        String message = gameService.deleteGame(gameId);

        // Assert
        assertNotNull(message);
        assertEquals(expectedMessage, message);
        verify(gameService, times(1)).deleteGame(gameId);
    }
}
