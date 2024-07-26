package org.newdawn.spaceinvaders.jdbcdb;

import org.newdawn.spaceinvaders.login.Member;
import org.newdawn.spaceinvaders.stage.SettingValue;
import org.newdawn.spaceinvaders.stage.shop.Coin;

public class WDB {

    public static void main(String[] args) throws Exception {
        //드라이버 로드

        SettingValue value = new SettingValue();
        GameInfo info = new GameInfo(20,  50, 2,500);
        Coin coin = new Coin();
        Member member = new Member();


        info.setStage(2);
        coin.setCoin(50);

        member.setName("testId");
        member.setPassword("testPw");
        System.out.printf("insert 쿼리 -> 시간: %d 스테이지 : %d 킬카운트 : %d 코인 : %d 이름 : %s 비밀번호 : %s",
                info.getPlayTime(), info.getStage(),
                info.getKillCount(), coin.getCoin(),
                member.getName(), member.getPassword());

        ConnectDB db = new ConnectDB();
        db.setConnection();
        db.insertResult();

        info.setPlayTime(40);
        info.setStage(4);
        info.setKillCount(20);
        coin.setCoin(30);
        info.setScore(1100);

        System.out.printf("update 쿼리 -> 시간: %d 스테이지 : %d 킬카운트 : %d 코인 : %d 이름 : %s 비밀번호 : %s ",
                info.getPlayTime(), info.getStage(),
                info.getKillCount(), coin.getCoin(),
                member.getName(), member.getPassword());

        db.updateResult();
        db.showResult();
        int n = db.showBestRecord();
        System.out.println("n = " + n);
        db.currentRecord();
    }
}