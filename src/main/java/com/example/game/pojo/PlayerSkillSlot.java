package com.example.game.pojo;

import lombok.Data;

@Data
public class PlayerSkillSlot {
    private Integer playerId;
    private Integer slotIndex;
    private Integer skillId;
}
