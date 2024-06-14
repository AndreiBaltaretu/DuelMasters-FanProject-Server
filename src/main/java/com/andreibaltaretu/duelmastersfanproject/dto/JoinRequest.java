package com.andreibaltaretu.duelmastersfanproject.dto;

import com.andreibaltaretu.duelmastersfanproject.model.Player;
import lombok.Data;

@Data
public class JoinRequest {
    String gameId;
    Player player;
}
