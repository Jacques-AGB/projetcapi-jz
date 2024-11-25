package com.master1.planningpoker.controllers;

import com.master1.planningpoker.dtos.request.playerRequests.CreatePlayerRequest;
import com.master1.planningpoker.dtos.request.playerRequests.JoinGameRequest;
import com.master1.planningpoker.dtos.responses.PlayerResponse;
import com.master1.planningpoker.service.Player.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {

    public final PlayerService playerService;

    @PostMapping("/join")
    public ResponseEntity<String> addPlayer(@RequestBody @Valid JoinGameRequest request) {
         String response = playerService.joinGame(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/createEdit")
    public ResponseEntity<PlayerResponse> createEditPlayer(@RequestBody @Valid CreatePlayerRequest request) {
        PlayerResponse response = playerService.createEditPlayer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("")
    public ResponseEntity<List<PlayerResponse>> getPlayers() {
        List<PlayerResponse> response = playerService.getPlayers();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponse> getPlayer(@PathVariable String pseudo) {
        PlayerResponse response = playerService.getPlayer(pseudo);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removePlayer(@PathVariable Long id) {
        String response = playerService.removePlayer(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
