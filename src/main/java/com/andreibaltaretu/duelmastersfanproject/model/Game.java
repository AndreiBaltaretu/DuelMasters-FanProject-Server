package com.andreibaltaretu.duelmastersfanproject.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Game {

    private String gameId;
    private GameStatus gameStatus;
    private List<Player> players = new ArrayList<>();

}
