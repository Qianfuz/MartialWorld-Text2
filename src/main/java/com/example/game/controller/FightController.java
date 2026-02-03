package com.example.game.controller;

import com.example.game.controller.dto.FightReq;
import com.example.game.controller.dto.UseSkillReq;
import com.example.game.pojo.Result;
import com.example.game.service.FightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FightController {

    private final FightService fightService;

    @Autowired
    public FightController(FightService fightService){
        this.fightService=fightService;
    }


    @PostMapping("initfight")
    public Result initFight(@RequestBody FightReq fightReq){
        return fightService.initFight(fightReq);
    }

    @PostMapping("useskill")
    public Result useSkill(@RequestBody UseSkillReq useSkillReq){
        return fightService.useSkill(useSkillReq);
    }

}
