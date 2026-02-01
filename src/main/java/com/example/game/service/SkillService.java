package com.example.game.service;

import com.example.game.controller.dto.EquipSkillReq;
import com.example.game.controller.dto.ShowReq;
import com.example.game.controller.dto.UpgradeReq;
import com.example.game.pojo.Result;

public interface SkillService {

    Result show(ShowReq showReq);

    Result showSlot(ShowReq showReq);

    Result upgrade(UpgradeReq upgradeReq);

    Result equipSkill(EquipSkillReq equipSkillReq);
}
