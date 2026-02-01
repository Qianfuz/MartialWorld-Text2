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
    private Integer curEnemyHp;


    //private List<String> log=new ArrayList<>();
}
