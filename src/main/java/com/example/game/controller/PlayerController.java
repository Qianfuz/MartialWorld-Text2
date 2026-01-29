package com.example.game.controller;

import com.example.game.controller.dto.LoginReq;
import com.example.game.controller.dto.RegisterReq;
import com.example.game.controller.dto.ShowReq;
import com.example.game.pojo.Result;
import com.example.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("/players/register")
    public Result register(@RequestBody RegisterReq registerReq){
        if(playerService.register(registerReq)>0){
            playerService.init(registerReq);
            return Result.success();
        } else {
            return Result.error("注册失败");
        }
    }

    @PostMapping("/players/login")
    public Result login(@RequestBody LoginReq loginReq){
        return playerService.login(loginReq);
    }


}
