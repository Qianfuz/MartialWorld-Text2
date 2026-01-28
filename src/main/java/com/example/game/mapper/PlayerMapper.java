package com.example.game.mapper;

import com.example.game.controller.dto.LoginReq;
import com.example.game.controller.dto.RegisterReq;
import com.example.game.controller.dto.ShowReq;
import com.example.game.pojo.Player;
import com.example.game.pojo.PlayerSkill;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PlayerMapper {

    @Insert("insert ignore into player (username, password) values (#{userName},#{password})")
    Integer register(RegisterReq registerReq);

    @Select("select * from player where username = #{userName} && password = #{password}")
    Player login(LoginReq loginReq);

    @Insert("insert ignore into skill_lv (player_id, skill_id, lv) SELECT #{playerId},id,0 from skill_define")
    void init(Integer playerId);

    @Select("select id from player where username = #{userName}")
    Integer getPlayerId(RegisterReq registerReq);

    @Select("select ID, SKILL_NAME, LIMITED_LV, UPGRADE_COST, BASIC_ATK, BASIC_MP_COST, TYPE ,sl.lv from skill_define sd join game.skill_lv sl on sd.id = sl.skill_id where player_id = #{playerId};")
    List<PlayerSkill> show(ShowReq showReq);
}
