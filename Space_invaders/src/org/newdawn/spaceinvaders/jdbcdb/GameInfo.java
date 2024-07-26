package org.newdawn.spaceinvaders.jdbcdb;

public class GameInfo {

    public GameInfo(){

    }

    private static int playTime = 0;
    private static int stage = 0;
    private static int killCount = 0;
    private static int score = 0;



    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        GameInfo.score = score;
    }

    public static int getPlayTime() {
        return playTime;
    }

    public static void setPlayTime(int playTime) {
        GameInfo.playTime = playTime;
    }

    public static int getStage() {
        return stage;
    }

    public static void setStage(int stage) {
        GameInfo.stage = stage;
    }

    public static int getKillCount() {
        return killCount;
    }

    public static void setKillCount(int killCount) {
        GameInfo.killCount = killCount;
    }



   public GameInfo(int playTime, int killCount,int stage, int score){
        this.playTime = playTime;
        this.killCount = killCount;
        this.stage = stage;
        this.score = score;

    }


}
