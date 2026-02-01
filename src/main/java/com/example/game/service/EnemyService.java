package com.example.game.service;

import com.example.game.pojo.Result;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Service
public interface EnemyService {
    Result showEnemy();
}
