package com.example.game.mapper;

import com.example.game.controller.dto.EquipSkillReq;
import com.example.game.controller.dto.RegisterReq;
import com.example.game.controller.dto.ShowReq;
import com.example.game.controller.dto.UpgradeReq;
import com.example.game.pojo.PlayerSkill;
import com.example.game.pojo.PlayerSkillSlot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SkillMapper {



    @Select("select id, skill_name, limited_lv, upgrade_cost, basic_atk, basic_mp_cost, type ,sl.lv from skill_define sd join skill_lv sl on sd.id = sl.skill_id where player_id = #{playerId};")
    List<PlayerSkill> show(ShowReq showReq);

    @Select("select player_id, slot_index, skill_id from skill_equip where player_id = #{playerId}")
    List<PlayerSkillSlot> showSlot(ShowReq showReq);

    @Select("select id, skill_name, limited_lv, upgrade_cost, basic_atk, basic_mp_cost, type,sl.lv from skill_define sd join skill_lv sl on sd.id = sl.skill_id where id = #{skillId} and player_id = #{playerId}")
    PlayerSkill getSkill(UpgradeReq upgradeReq);

    @Update("update skill_lv set lv = lv + 1 where skill_id = #{skillId} and player_id = #{playerId};")
    void upgrade(UpgradeReq upgradeReq);

    @Update("update skill_equip set skill_id = #{skillId} where player_id = #{playerId} and slot_index = #{slotIndex};")
    void equipSkill(EquipSkillReq equipSkillReq);
}
