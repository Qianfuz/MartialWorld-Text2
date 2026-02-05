package com.example.game.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fight {
    private Integer curPlayerHp;
    private Integer curPlayerMp;
    private String playerName;
    private Integer playerLv;
    private Integer playerHpMax;
    private Integer playerMpMax;

    private Integer curEnemyHp;
    private String enemyName;
    private Integer enemyLv;
    private Integer enemyHpMax;
    private Integer enemyId;


    private List<String> log=new ArrayList<>();

}
