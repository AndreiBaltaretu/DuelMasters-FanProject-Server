package com.andreibaltaretu.duelmastersfanproject.controller;

import com.andreibaltaretu.duelmastersfanproject.dto.JoinRequest;
import com.andreibaltaretu.duelmastersfanproject.model.Player;
import com.andreibaltaretu.duelmastersfanproject.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("")
public class GameController {

    private final GameService gameService;

    @PostMapping("/create")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> createGame(@RequestBody Player player) {
        System.out.println("Create game request " + player);
        return new ResponseEntity<>(gameService.createGame(player), HttpStatus.CREATED);
    }

    @PostMapping("/join")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> joinGame(@RequestBody JoinRequest joinRequest) {
        System.out.println("Join game request " + joinRequest);
        return new ResponseEntity<>(gameService.joinGame(joinRequest.getGameId(), joinRequest.getPlayer()), HttpStatus.CREATED);
    }
}
