package com.example.game.pojo;

import lombok.Data;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.concurrent.ConcurrentHashMap;

@Data
@Repository
public class FightMap {
    private ConcurrentHashMap<Integer,Fight>fightConcurrentHashMap = new ConcurrentHashMap<>();
}
