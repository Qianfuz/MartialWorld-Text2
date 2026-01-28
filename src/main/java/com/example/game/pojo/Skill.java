package com.example.game.pojo;

import lombok.Data;

@Data
public class Skill {
    private Integer id;
    private String skillName;
    private Integer limitedLv;
    private Integer upgradeCost;
    private Integer basicATK;
    private Integer basicMpCost;
    private Integer type;

}
