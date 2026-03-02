
package com.example.game.mapper;

import com.example.game.controller.dto.ShowReq;
import com.example.game.controller.dto.UseMedicineReq;
import com.example.game.pojo.Medicine;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MedicineMapper {

    @Select("select id, name, restore_hp, restore_mp, price, mn.number from medicine md join medicine_number mn on md.id = mn.medicine_id where player_id = #{playerId} and medicine_id = #{medicineId};")
    Medicine getPlayerMedicine(UseMedicineReq useMedicineReq);

    @Select("select id, name, restore_hp, restore_mp, price, mn.number from medicine md join medicine_number mn on md.id = mn.medicine_id where player_id = #{playerId};")
    List<Medicine> showMedicine(ShowReq showReq);

    @Update("update medicine_number set number = #{medicine.number} where player_id = #{playerId} and medicine_id = #{medicine.id};")
    void updateMedicine(Medicine medicine,Integer playerId);

}

