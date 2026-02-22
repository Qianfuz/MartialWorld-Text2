package com.example.game.service.impl;

import com.example.game.controller.dto.EquipSkillReq;
import com.example.game.controller.dto.ShowReq;
import com.example.game.controller.dto.UpgradeReq;
import com.example.game.mapper.PlayerMapper;
import com.example.game.mapper.SkillMapper;
import com.example.game.pojo.Player;
import com.example.game.pojo.PlayerSkill;
import com.example.game.pojo.Result;
import com.example.game.service.SkillService;
import com.example.game.util.FightUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SkillServiceImpl implements SkillService {


    private final SkillMapper skillMapper;
    private final PlayerMapper playerMapper;
    @Autowired
    public  SkillServiceImpl(SkillMapper skillMapper,PlayerMapper playerMapper){
        this.skillMapper=skillMapper;
        this.playerMapper=playerMapper;
    }


    @Override
    public Result showSkill(ShowReq showReq) {
        List<PlayerSkill> playerSkillList = skillMapper.showSkill(showReq);
        for (int i = 0; i < playerSkillList.size(); i++) {
            playerSkillList.get(i).setCurATK(FightUtil.playerAtk(playerSkillList.get(i).getBasicAtk(),playerSkillList.get(i).getLv()));
            playerSkillList.get(i).setCurMpCost(FightUtil.playerMpCost(playerSkillList.get(i).getBasicMpCost(),playerSkillList.get(i).getLv()));
            playerSkillList.get(i).setCurUpgradeCost(FightUtil.skillUpgradeCost(playerSkillList.get(i).getUpgradeCost(),playerSkillList.get(i).getLv()));
        }
        return Result.success(playerSkillList);
    }

    @Override
    public Result showSlot(ShowReq showReq) {
        return Result.success(skillMapper.showSlot(showReq));
    }



    @Override
    public Result upgradeSkill(UpgradeReq upgradeReq) {
        Player p = playerMapper.getPlayer(upgradeReq.getPlayerId());
        PlayerSkill ps = skillMapper.getSkill(upgradeReq);
        Integer curSkillUpgradeCost=FightUtil.skillUpgradeCost(ps.getUpgradeCost(),ps.getLv());
        if(p.getMoney()>=curSkillUpgradeCost && p.getLv()>=ps.getLimitedLv()){
            p.setMoney(p.getMoney()-curSkillUpgradeCost);
            skillMapper.upgradeSkill(upgradeReq);
            playerMapper.updatePlayer(p);
            return Result.success("升级成功");
        } else {
            return Result.error("升级失败");
        }

    }

    @Override
    public Result equipSkill(EquipSkillReq equipSkillReq) {
        skillMapper.equipSkill(equipSkillReq);
        return Result.success("装备成功");
    }
}
