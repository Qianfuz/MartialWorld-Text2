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

    public static int rewardExp(int enemyLv){
        return enemyLv*10+50;
    }

    public static int rewardMoney(int enemyLv){
        return enemyLv*13+71;
    }

    public static int nextLvHp(int curHp){
        return curHp*23/20;
    }

    public static int nextLvMp(int curMp){
        return curMp*23/20;
    }

}
