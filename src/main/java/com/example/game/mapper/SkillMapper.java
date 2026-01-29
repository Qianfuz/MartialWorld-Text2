package com.example.game.mapper;

import com.example.game.controller.dto.RegisterReq;
import com.example.game.controller.dto.ShowReq;
import com.example.game.pojo.PlayerSkill;
import com.example.game.pojo.PlayerSkillSlot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SkillMapper {

    @Select("select id from player where username = #{userName}")
    Integer getPlayerId(RegisterReq registerReq);

    @Select("select ID, SKILL_NAME, LIMITED_LV, UPGRADE_COST, BASIC_ATK, BASIC_MP_COST, TYPE ,sl.lv from skill_define sd join game.skill_lv sl on sd.id = sl.skill_id where player_id = #{playerId};")
    List<PlayerSkill> show(ShowReq showReq);

    @Select("select player_id, slot_index, skill_id from skill_equip where player_id = #{playerId}")
    List<PlayerSkillSlot> showSlot(ShowReq showReq);
}
