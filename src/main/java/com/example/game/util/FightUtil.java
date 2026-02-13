package com.example.game.util;

public class FightUtil {

    public static int playerAtk(int basicAtk,int lv){
        return basicAtk*(20+3*lv)/20;
    }

    public static int playerMpCost(int basicMpCost,int lv){
        return basicMpCost*(20+3*lv)/20;
    }

    public static int skillUpgradeCost(int basicSkillUpgradeCost,int lv){
        return basicSkillUpgradeCost*(20+3*lv)/20;
    }

    public static int enemyAtk(int basicAtk,int lv){
        return basicAtk*(20+lv)/20;
    }
}
