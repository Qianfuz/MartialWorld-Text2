
package com.example.game.controller;

import com.example.game.controller.dto.BuyMedicineReq;
import com.example.game.controller.dto.ShowReq;
import com.example.game.pojo.Result;
import com.example.game.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BaseMultiResolutionImage;

@RestController
public class MedicineController {

    private final MedicineService medicineService;

    @Autowired
    public MedicineController(MedicineService medicineService){
        this.medicineService=medicineService;
    }

    @PostMapping("/players/showmedicine")
    public Result showMedicine(@RequestBody ShowReq showReq){
        return medicineService.showMedicine(showReq);
    }

    @PostMapping("/players/buymedicine")
    public Result buyMedicine(@RequestBody BuyMedicineReq buyMedicineReq){
        return medicineService.buyMedicine(buyMedicineReq);
    }
}
