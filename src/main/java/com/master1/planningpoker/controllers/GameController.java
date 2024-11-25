package com.master1.planningpoker.controllers;


import com.master1.planningpoker.dtos.request.gameRequests.createGameRequest;
import com.master1.planningpoker.dtos.responses.GameResponse;
import com.master1.planningpoker.models.Game;
import com.master1.planningpoker.service.Game.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/addEdit")
    public ResponseEntity<GameResponse> addGame(@RequestBody @Valid createGameRequest request) {
        GameResponse newGame = gameService.createEditGame(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newGame);
    }

    @GetMapping("/{code}")
    public ResponseEntity<GameResponse> getGameDetails(@PathVariable String code) {
        GameResponse gameDetails = gameService.getGameDetails(code);
        return ResponseEntity.ok(gameDetails);
    }

    @GetMapping("/")
    public ResponseEntity<List<GameResponse>> getGames() {
        List<GameResponse> gameDetails = gameService.getGames();
        return ResponseEntity.ok(gameDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> getGameDetails(@PathVariable Long id) {
        String response = gameService.deleteGame(id);
        return ResponseEntity.ok(response);
    }
}
