package com.example.game.mapper;

import com.example.game.controller.dto.ShowReq;
import com.example.game.pojo.Medicine;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MedicineMapper {

    @Select("select id, name, restore_hp, restore_mp, price, mn.number from medicine md join medicine_number mn on md.id = mn.medicine_id where player_id = #{playerId};")
    List<Medicine> showMedicine(ShowReq showReq);
}
