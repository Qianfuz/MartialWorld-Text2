package com.example.game.pojo;

import lombok.Data;

@Data
public class PlayerSkill {
    private Integer id;
    private String skillName;
    private Integer limitedLv;
    private Integer upgradeCost;
    private Integer basicATK;
    private Integer basicMpCost;
    private Integer type;

    private Integer lv;
    private Integer curATK;
    private Integer curMpCost;
    private Integer curUpgradeCost;

}
