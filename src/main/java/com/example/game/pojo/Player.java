package com.example.game.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private Integer id;
    private String userName;
    private String password;
    private Integer hp;
    private Integer mp;
    private Integer money;
    private Integer exp;
    private Integer expMax;
    private Integer lv;
}
