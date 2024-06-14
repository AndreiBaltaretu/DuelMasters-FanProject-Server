package com.andreibaltaretu.duelmastersfanproject.service;

import com.andreibaltaretu.duelmastersfanproject.model.Game;
import com.andreibaltaretu.duelmastersfanproject.model.GameStatus;
import com.andreibaltaretu.duelmastersfanproject.model.Player;
import com.andreibaltaretu.duelmastersfanproject.storage.GameStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GameService {
    public Game createGame(Player player) {
        Game game = new Game();
        game.setGameId(UUID.randomUUID().toString());
        game.setGameStatus(GameStatus.WAITING);
        game.getPlayers().add(player);

        GameStorage.getInstance().setGame(game);

        return game;
    }

    public Game joinGame(String gameId, Player player){
        if(!GameStorage.getInstance().getGames().containsKey(gameId)) {
            System.out.println("Game with provided id doesn't exist");
        }

        Game game = GameStorage.getInstance().getGames().get(gameId);
        game.getPlayers().add(player);

        return game;
    }
}
