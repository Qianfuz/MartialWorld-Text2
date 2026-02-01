package com.example.game.service;

import com.example.game.controller.dto.FightReq;
import com.example.game.pojo.Result;
import org.springframework.stereotype.Service;

@Service
public interface FightService {
    Result initFight(FightReq fightReq);
}
