package com.example.game.mapper;

import com.example.game.controller.dto.LoginReq;
import com.example.game.controller.dto.RegisterReq;
import com.example.game.pojo.Player;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PlayerMapper {

    @Insert("insert ignore into player (username, password) values (#{userName},#{password})")
    Integer register(RegisterReq registerReq);

    @Select("select * from player where username = #{userName} && password = #{password}")
    Player login(LoginReq loginReq);
}
