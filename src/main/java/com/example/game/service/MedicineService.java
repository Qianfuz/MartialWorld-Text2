package com.example.game.service;

import com.example.game.controller.dto.BuyMedicineReq;
import com.example.game.controller.dto.ShowReq;
import com.example.game.pojo.Result;
import org.springframework.transaction.annotation.Transactional;

public interface MedicineService {

    Result showMedicine(ShowReq showReq);

    @Transactional
    Result buyMedicine(BuyMedicineReq buyMedicineReq);
}
