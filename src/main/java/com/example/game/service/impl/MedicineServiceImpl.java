package com.example.game.service.impl;

import com.example.game.controller.dto.ShowReq;
import com.example.game.mapper.MedicineMapper;
import com.example.game.pojo.Medicine;
import com.example.game.pojo.Result;
import com.example.game.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {

    private final MedicineMapper medicineMapper;

    @Autowired
    public MedicineServiceImpl(MedicineMapper medicineMapper){
        this.medicineMapper=medicineMapper;
    }

    @Override
    public Result showMedicine(ShowReq showReq) {
        List<Medicine>medicineList=medicineMapper.showMedicine(showReq);
        return Result.success(medicineList);
    }
}
