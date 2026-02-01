package com.example.game.controller;

import com.example.game.controller.dto.EquipSkillReq;
import com.example.game.controller.dto.ShowReq;
import com.example.game.controller.dto.UpgradeReq;
import com.example.game.pojo.Result;
import com.example.game.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SkillController {

    @Autowired
    private SkillService skillService;

    @PostMapping("/players/skills/showskill")
    public Result show(@RequestBody ShowReq showReq){
        return skillService.show(showReq);
    }

    @PostMapping("/players/skills/showslot")
    public Result showSlot(@RequestBody ShowReq showReq){
        return skillService.showSlot(showReq);
    }

    @PostMapping("players/skills/upgrade")
    public Result upgrade(@RequestBody UpgradeReq upgradeReq){
        return skillService.upgrade(upgradeReq);
    }

    @PostMapping("players/skills/equip")
    public Result equipSkill(@RequestBody EquipSkillReq equipSkillReq){
        return skillService.equipSkill(equipSkillReq);
    }
}
