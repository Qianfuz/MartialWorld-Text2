package com.example.game.controller.dto;

import lombok.Data;

@Data
public class EquipSkillReq {
    private Integer playerId;
    private Integer skillId;
    private Integer slotIndex;
}
