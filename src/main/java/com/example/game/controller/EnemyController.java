package com.example.game.controller;


import com.example.game.pojo.Result;
import com.example.game.service.EnemyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnemyController {

    private final EnemyService enemyService;

    @Autowired
    public EnemyController(EnemyService enemyService){
        this.enemyService=enemyService;
    }

    @GetMapping("enemys/showenemy")
    public Result showEnemy(){
        return enemyService.showEnemy();
    }
}
