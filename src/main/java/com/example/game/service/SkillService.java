package com.example.game.service;

import com.example.game.controller.dto.ShowReq;
import com.example.game.pojo.Result;

public interface SkillService {

    Result show(ShowReq showReq);

    Result showSlot(ShowReq showReq);
}
