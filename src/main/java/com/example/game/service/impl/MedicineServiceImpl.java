/*package com.example.game.service.impl;

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
}*/



package com.example.game.service.impl;

import com.example.game.controller.dto.BuyMedicineReq;
import com.example.game.controller.dto.ShowReq;
import com.example.game.controller.dto.UseMedicineReq;
import com.example.game.mapper.MedicineMapper;
import com.example.game.mapper.PlayerMapper;
import com.example.game.pojo.Medicine;
import com.example.game.pojo.Player;
import com.example.game.pojo.Result;
import com.example.game.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {

    private final MedicineMapper medicineMapper;
    private final PlayerMapper playerMapper;

    @Autowired
    public MedicineServiceImpl(MedicineMapper medicineMapper,PlayerMapper playerMapper){
        this.medicineMapper=medicineMapper;
        this.playerMapper=playerMapper;
    }

    @Override
    public Result showMedicine(ShowReq showReq) {
        List<Medicine>medicineList=medicineMapper.showMedicine(showReq);
        return Result.success(medicineList);
    }

    @Transactional
    @Override
    public Result buyMedicine(BuyMedicineReq buyMedicineReq) {
        Player player = playerMapper.getPlayer(buyMedicineReq.getPlayerId());
        UseMedicineReq useMedicineReq = new UseMedicineReq();
        useMedicineReq.setMedicineId(buyMedicineReq.getMedicineId());
        useMedicineReq.setPlayerId(buyMedicineReq.getPlayerId());
        Medicine medicine = medicineMapper.getPlayerMedicine(useMedicineReq);
        if(player.getMoney()<medicine.getPrice()){
            return Result.error("金钱不足");
        }
        player.setMoney(player.getMoney()-medicine.getPrice());
        medicine.setNumber(medicine.getNumber()+1);
        playerMapper.updatePlayer(player);
        medicineMapper.updateMedicine(medicine,buyMedicineReq.getPlayerId());
        return Result.success("购买成功");
    }
}

