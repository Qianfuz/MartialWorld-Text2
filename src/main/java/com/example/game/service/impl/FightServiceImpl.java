package com.example.game.service.impl;


import com.example.game.controller.dto.FightReq;
import com.example.game.mapper.EnemyMapper;
import com.example.game.mapper.PlayerMapper;
import com.example.game.pojo.*;
import com.example.game.service.FightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FightServiceImpl implements FightService {

    private final FightMap fightMap;
    private final PlayerMapper playerMapper;
    private final EnemyMapper enemyMapper;

    @Autowired
    public FightServiceImpl(FightMap fightMap, PlayerMapper playerMapper, EnemyMapper enemyMapper){
        this.fightMap=fightMap;
        this.enemyMapper=enemyMapper;
        this.playerMapper=playerMapper;
    }

    @Override
    public Result initFight(FightReq fightReq) {
        if(!fightMap.getFightConcurrentHashMap().containsKey(fightReq.getPlayerId())){
            fightMap.getFightConcurrentHashMap().put(fightReq.getPlayerId(),new Fight());
        }
        Fight f = fightMap.getFightConcurrentHashMap().get(fightReq.getPlayerId());
        Player p = playerMapper.getPlayer(fightReq.getPlayerId());
        EnemyDefine e = enemyMapper.getEnemyDefine(fightReq.getEnemyId());
        f.setCurPlayerHp(p.getHp());
        f.setCurPlayerMp(p.getMp());
        f.setCurEnemyHp(e.getBasicHp()*(20+fightReq.getEnemyLv())/20);
        return Result.success(f);
    }
}
