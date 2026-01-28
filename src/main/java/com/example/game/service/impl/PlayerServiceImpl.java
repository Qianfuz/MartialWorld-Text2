package com.example.game.service.impl;

import com.example.game.controller.dto.LoginReq;
import com.example.game.controller.dto.RegisterReq;
import com.example.game.controller.dto.ShowReq;
import com.example.game.mapper.PlayerMapper;
import com.example.game.pojo.PlayerSkill;
import com.example.game.pojo.Result;
import com.example.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerMapper playerMapper;

    @Override
    public Integer register(RegisterReq registerReq) {
        return playerMapper.register(registerReq);
    }

    @Override
    public Result login(LoginReq loginReq) {
        if(playerMapper.login(loginReq)==null){
            return Result.error("用户名或密码错误");
        } else {
            return Result.success(playerMapper.login(loginReq));
        }
    }

    @Override
    public void initSkill(RegisterReq registerReq){
        playerMapper.init(playerMapper.getPlayerId(registerReq));
    }

    @Override
    public Result show(ShowReq showReq) {
        List<PlayerSkill>playerSkillList = playerMapper.show(showReq);
        for (int i = 0; i < playerSkillList.size(); i++) {
            if(playerSkillList.get(i).getLv()==0){
                playerSkillList.get(i).setCurATK(playerSkillList.get(i).getBasicATK());
                playerSkillList.get(i).setCurMpCost(playerSkillList.get(i).getBasicMpCost());
                playerSkillList.get(i).setCurUpgradeCost(playerSkillList.get(i).getUpgradeCost());
            } else {
                playerSkillList.get(i).setCurATK(playerSkillList.get(i).getBasicATK()*(20+playerSkillList.get(i).getLv()*3)/20);
                playerSkillList.get(i).setCurMpCost(playerSkillList.get(i).getBasicMpCost()*(20+playerSkillList.get(i).getLv()*3)/20);
                playerSkillList.get(i).setCurUpgradeCost(playerSkillList.get(i).getUpgradeCost()*(20+playerSkillList.get(i).getLv()*3)/20);
            }
        }
        return Result.success(playerSkillList);
    }
}
