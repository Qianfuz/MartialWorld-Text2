package com.example.game.service.impl;


import com.example.game.controller.dto.*;
import com.example.game.mapper.EnemyMapper;
import com.example.game.mapper.MedicineMapper;
import com.example.game.mapper.PlayerMapper;
import com.example.game.mapper.SkillMapper;
import com.example.game.pojo.*;
import com.example.game.service.FightService;
import com.example.game.util.FightUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class FightServiceImpl implements FightService {

    private final FightMap fightMap;
    private final PlayerMapper playerMapper;
    private final EnemyMapper enemyMapper;
    private final SkillMapper skillMapper;
    private final MedicineMapper medicineMapper;

    @Autowired
    public FightServiceImpl(MedicineMapper medicineMapper, FightMap fightMap, PlayerMapper playerMapper, EnemyMapper enemyMapper, SkillMapper skillMapper){
        this.fightMap=fightMap;
        this.enemyMapper=enemyMapper;
        this.playerMapper=playerMapper;
        this.skillMapper = skillMapper;
        this.medicineMapper=medicineMapper;
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
        f.setPlayerHpMax(p.getHp());
        f.setPlayerMpMax(p.getMp());
        f.setPlayerLv(p.getLv());
        f.setPlayerName(p.getUserName());

        f.setCurEnemyHp(e.getBasicHp()*(20+fightReq.getEnemyLv())/20);
        f.setEnemyHpMax(e.getBasicHp()*(20+fightReq.getEnemyLv())/20);
        f.setEnemyName(e.getName());
        f.setEnemyLv(fightReq.getEnemyLv());
        f.setEnemyId(e.getId());
        f.getLog().clear();
        return Result.success(f);
    }

    @Override
    public Result useSkill(UseSkillReq useSkillReq) {

        //玩家回合
        PlayerSkill playerSkill = skillMapper.getSkill(new UpgradeReq(useSkillReq.getPlayerId(),useSkillReq.getSkillId()));
        Fight fight = fightMap.getFightConcurrentHashMap().get(useSkillReq.getPlayerId());
        StringBuilder sb = new StringBuilder();
        Integer playerAtk = FightUtil.playerAtk(playerSkill.getBasicAtk(), playerSkill.getLv());
        Integer playerMpCost = FightUtil.playerMpCost(playerSkill.getBasicMpCost(), playerSkill.getLv());

        if(fight.getCurPlayerMp()<playerMpCost){
            return Result.error("蓝量不足");
        }
        fight.setCurPlayerMp(fight.getCurPlayerMp()-playerMpCost);

        sb.append("你 使用 ").append("[").append(playerSkill.getSkillName()).append("] ")
                .append("造成了 ").append(playerAtk).append(" 点 伤害")
                .append("\n");

        //判断是否结束
        if(fight.getCurEnemyHp()-playerAtk<=0){
            fight.setCurEnemyHp(0);
            fight.getLog().add(sb.toString());
            return Result.success(fight);
        }
        fight.setCurEnemyHp(fight.getCurEnemyHp()-playerAtk);

        //怪物回合
        enemyTurn(fight,sb);

        return Result.success(fight);
    }

    @Override
    public Result useMedicine(UseMedicineReq useMedicineReq) {

        Medicine medicine = medicineMapper.getMedicine(useMedicineReq);
        Fight fight = fightMap.getFightConcurrentHashMap().get(useMedicineReq.getPlayerId());
        StringBuilder sb = new StringBuilder();

        if(medicine.getNumber()<=0){
            return Result.error("药品不足");
        }
        //使用药品
        medicineMapper.useMedicine(useMedicineReq);

        if(medicine.getRestoreHp()>0){
            fight.setCurPlayerHp(fight.getCurPlayerHp()+medicine.getRestoreHp());
            sb.append("你 使用 ").append("[").append(medicine.getName()).append("] ")
                    .append("恢复了 ").append(medicine.getRestoreHp()).append(" 点 血量")
                    .append("\n");
            if(fight.getCurPlayerHp()>fight.getPlayerHpMax()){
                fight.setCurPlayerHp(fight.getPlayerHpMax());
            }
        }
        if(medicine.getRestoreMp()>0){
            fight.setCurPlayerMp(fight.getCurPlayerMp()+medicine.getRestoreMp());
            sb.append("你 使用 ").append("[").append(medicine.getName()).append("] ")
                    .append("恢复了 ").append(medicine.getRestoreMp()).append(" 点 蓝量")
                    .append("\n");
            if(fight.getCurPlayerMp()> fight.getPlayerMpMax()){
                fight.setCurPlayerMp(fight.getPlayerMpMax());
            }
        }
        //怪物回合
        enemyTurn(fight,sb);

        return Result.success(fight);
    }

    public void enemyTurn(Fight fight,StringBuilder sb){
        Random random = new Random();
        Integer index = random.nextInt(4)+1;
        EnemySkill enemySkill = enemyMapper.getEnemySkill(fight.getEnemyId(),index);
        Integer enemyAtk = FightUtil.enemyAtk(enemySkill.getBasicAtk(),fight.getEnemyLv());

        if(enemyAtk>=0){
            sb.append("[").append(fight.getEnemyName()).append("] ")
                    .append("使用 ").append("[").append(enemySkill.getName()).append("] ")
                    .append("造成了 ").append(enemyAtk).append(" 点 伤害").append("\n");
            fight.getLog().add(sb.toString());
            fight.setCurPlayerHp(fight.getCurPlayerHp()-enemyAtk);
            if(fight.getCurPlayerHp()<0){
                fight.setCurPlayerHp(0);
            }
        } else {
            sb.append("[").append(fight.getEnemyName()).append("] ")
                    .append("使用 ").append("[").append(enemySkill.getName()).append("] ")
                    .append("恢复了 ").append(-enemyAtk).append(" 点 生命值").append("\n");
            fight.getLog().add(sb.toString());
            fight.setCurEnemyHp(fight.getCurEnemyHp()-enemyAtk);
            if(fight.getCurEnemyHp()>fight.getEnemyHpMax()){
                fight.setCurEnemyHp(fight.getEnemyHpMax());
            }
        }
    }


    @Transactional
    @Override
    public Result reward(RewardReq rewardReq) {
        Player player = playerMapper.getPlayer(rewardReq.getPlayerId());
        Fight fight = fightMap.getFightConcurrentHashMap().get(rewardReq.getPlayerId());
        Integer rewardExp = FightUtil.rewardExp(fight.getEnemyLv());
        Integer rewardMoney = FightUtil.rewardMoney(fight.getEnemyLv());
        player.setMoney(player.getMoney()+rewardMoney);
        player.setExp(player.getExp()+rewardExp);
        //升级
        while (player.getExp()>=player.getExpMax()){
            player.setExp(player.getExp()-player.getExpMax());
            player.setExpMax(player.getExpMax()*21/20);
            player.setLv(player.getLv()+1);
            player.setHp(FightUtil.nextLvHp(player.getHp()));
            player.setMp(FightUtil.nextLvMp(player.getMp()));
        }
        //
        playerMapper.updatePlayer(player);
        return Result.success();
    }


}
