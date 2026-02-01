package com.example.game.pojo;

import lombok.Data;

@Data
public class EnemySkill {
    private Integer enemyId;
    private Integer skillId;
    private Integer basicAtk;
    private String name;
}
