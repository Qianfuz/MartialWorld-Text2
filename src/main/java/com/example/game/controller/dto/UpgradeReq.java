package com.example.game.controller.dto;

import lombok.Data;

@Data
public class UpgradeReq {
    private Integer playerId;
    private Integer skillId;
}
