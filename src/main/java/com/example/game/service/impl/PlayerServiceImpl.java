package com.example.game.service.impl;

import com.example.game.controller.dto.LoginReq;
import com.example.game.controller.dto.RegisterReq;
import com.example.game.controller.dto.ShowReq;
import com.example.game.controller.dto.UpdatePlayerReq;
import com.example.game.mapper.PlayerMapper;
import com.example.game.pojo.PlayerSkill;
import com.example.game.pojo.Result;
import com.example.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {


    private PlayerMapper playerMapper;

    @Autowired
    public PlayerServiceImpl(PlayerMapper playerMapper){
        this.playerMapper=playerMapper;
    }

    @Transactional
    @Override
    public Result register(RegisterReq registerReq) {
        if(playerMapper.register(registerReq)>0){
            init(registerReq);
            return Result.success();
        } else {
            return Result.error("注册失败");
        }
    }

    @Transactional
    @Override
    public Result login(LoginReq loginReq) {
        if(playerMapper.login(loginReq)==null){
            return Result.error("用户名或密码错误");
        } else {
            RegisterReq registerReq = new RegisterReq();
            registerReq.setUserName(loginReq.getUserName());
            registerReq.setPassword(loginReq.getPassword());
            init(registerReq);
            return Result.success(playerMapper.login(loginReq));
        }
    }

    @Transactional
    @Override
    public void init(RegisterReq registerReq){
        playerMapper.initSkillLv(playerMapper.getPlayerId(registerReq));
        playerMapper.initSkillSlot(playerMapper.getPlayerId(registerReq));
        playerMapper.initMedicineNumber(playerMapper.getPlayerId(registerReq));
    }

    @Override
    public Result updatePlayer(UpdatePlayerReq updatePlayerReq) {
        return Result.success(playerMapper.getPlayer(updatePlayerReq.getPlayerId()));
    }


}
