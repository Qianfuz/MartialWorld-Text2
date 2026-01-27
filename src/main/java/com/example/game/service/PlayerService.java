package com.example.game.service;

import com.example.game.controller.dto.LoginReq;
import com.example.game.controller.dto.RegisterReq;
import com.example.game.pojo.Result;


public interface PlayerService {
    Integer register(RegisterReq registerReq);

    Result login(LoginReq loginReq);
}

