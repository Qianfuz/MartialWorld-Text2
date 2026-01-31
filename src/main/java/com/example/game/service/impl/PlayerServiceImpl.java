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
    public void init(RegisterReq registerReq){
        playerMapper.init1(playerMapper.getPlayerId(registerReq));
        playerMapper.init2(playerMapper.getPlayerId(registerReq));
    }

    @Override
    public Result updatePlayer(UpdatePlayerReq updatePlayerReq) {
        return Result.success(playerMapper.getPlayer(updatePlayerReq.playerId));
    }


}
