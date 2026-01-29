package com.example.game.service.impl;

import com.example.game.controller.dto.ShowReq;
import com.example.game.mapper.PlayerMapper;
import com.example.game.mapper.SkillMapper;
import com.example.game.pojo.PlayerSkill;
import com.example.game.pojo.Result;
import com.example.game.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SkillServiceImpl implements SkillService {


    @Autowired
    private SkillMapper skillMapper;

    @Override
    public Result show(ShowReq showReq) {
        List<PlayerSkill> playerSkillList = skillMapper.show(showReq);
        for (int i = 0; i < playerSkillList.size(); i++) {
            if(playerSkillList.get(i).getLv()==0){
                playerSkillList.get(i).setCurATK(playerSkillList.get(i).getBasicAtk());
                playerSkillList.get(i).setCurMpCost(playerSkillList.get(i).getBasicMpCost());
                playerSkillList.get(i).setCurUpgradeCost(playerSkillList.get(i).getUpgradeCost());
            } else {
                playerSkillList.get(i).setCurATK(playerSkillList.get(i).getBasicAtk()*(20+playerSkillList.get(i).getLv()*3)/20);
                playerSkillList.get(i).setCurMpCost(playerSkillList.get(i).getBasicMpCost()*(20+playerSkillList.get(i).getLv()*3)/20);
                playerSkillList.get(i).setCurUpgradeCost(playerSkillList.get(i).getUpgradeCost()*(20+playerSkillList.get(i).getLv()*3)/20);
            }
        }
        return Result.success(playerSkillList);
    }

    @Override
    public Result showSlot(ShowReq showReq) {
        return Result.success(skillMapper.showSlot(showReq));
    }
}
