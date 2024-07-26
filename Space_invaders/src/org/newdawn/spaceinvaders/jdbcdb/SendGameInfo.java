package org.newdawn.spaceinvaders.jdbcdb;

public class SendGameInfo {

    // 마이페이지에서 db에서 게임목록 받아올때 값을 전달해주는 클래스
    public SendGameInfo() {

    }

    private int playTime = 0;
    private int stage = 0;
    private int killCount = 0;
    private int score = 0;

    public int getPlayTime() {
        return playTime;
    }
    public int getStage() {
        return stage;
    }
    public int getKillCount() {
        return killCount;
    }
    public int getScore() {
        return score;
    }
    public SendGameInfo(int playTime, int killCount, int stage, int score) {
        this.playTime = playTime;
        this.killCount = killCount;
        this.stage = stage;
        this.score = score;

    }


}
