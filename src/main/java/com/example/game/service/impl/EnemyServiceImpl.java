package com.example.game.service.impl;

import com.example.game.mapper.EnemyMapper;
import com.example.game.pojo.Result;
import com.example.game.service.EnemyService;
import org.springframework.stereotype.Service;

@Service
public class EnemyServiceImpl implements EnemyService {

    private final EnemyMapper enemyMapper;

    public EnemyServiceImpl(EnemyMapper enemyMapper){
        this.enemyMapper=enemyMapper;
    }

    @Override
    public Result showEnemy() {
        return Result.success(enemyMapper.showEnemy());
    }
}
