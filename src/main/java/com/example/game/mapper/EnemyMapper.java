package com.example.game.mapper;

import com.example.game.pojo.EnemyDefine;
import com.example.game.pojo.EnemySkill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EnemyMapper {

    @Select("select id, basic_hp, name, enemy_lv from enemy_define ed join enemy_init ei on ed.id = ei.enemy_id")
    List<EnemyDefine> showEnemy();

    @Select("select id, basic_hp, name from enemy_define where id = #{enemyId}")
    EnemyDefine getEnemyDefine(Integer enemyId);

    @Select("select enemy_id, skill_id, basic_atk, name from enemy_skill where enemy_id = #{enemyId} and skill_id = #{index}")
    EnemySkill getEnemySkill(Integer enemyId,Integer index);
}
