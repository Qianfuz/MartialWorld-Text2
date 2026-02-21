package com.example.game.pojo;

import lombok.Data;

@Data
public class Medicine {
    private Integer id;
    private String name;
    private Integer restoreHp;
    private Integer restoreMp;
    private Integer price;

    private Integer number;
}
