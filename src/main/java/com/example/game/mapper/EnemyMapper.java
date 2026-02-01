package com.example.game.mapper;

import com.example.game.pojo.EnemyDefine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EnemyMapper {

    @Select("select id, basic_hp, name, enemy_lv from enemy_define ed join enemy_init ei on ed.id = ei.enemy_id")
    List<EnemyDefine> showEnemy();

}
