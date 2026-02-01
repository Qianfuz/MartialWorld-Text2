package com.example.game.controller.dto;

import lombok.Data;

@Data
public class FightReq {
    private Integer playerId;
    private Integer enemyId;
    private Integer enemyLv;
}
