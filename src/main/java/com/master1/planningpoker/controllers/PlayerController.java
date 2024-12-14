package com.master1.planningpoker.controllers;

import com.master1.planningpoker.dtos.request.playerRequests.CreatePlayerRequest;
import com.master1.planningpoker.dtos.request.playerRequests.JoinGameRequest;
import com.master1.planningpoker.dtos.responses.playerResponses.PlayerResponse;
import com.master1.planningpoker.service.Player.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {

    public final PlayerService playerService;

    @PostMapping("/join")
    public ResponseEntity<Map<String, Object>> joinGame(@RequestBody @Valid JoinGameRequest request) {
        // Appel au service pour ajouter le joueur
        String response = playerService.joinGame(request);

        // Création de la réponse contenant le message et le code de la partie
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", "success");
        responseMap.put("message", response);
        responseMap.put("gameCode", request.getCode());  // Ajout du code de la partie dans la réponse

        // Retourner une réponse HTTP avec le corps contenant le message et le code
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
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

    @GetMapping("/{pseudo}")
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
