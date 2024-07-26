package org.newdawn.spaceinvaders.login.challenge;

import org.newdawn.spaceinvaders.stage.SettingValue;

public class Challenge {

    ChallengeRepository cr = new ChallengeRepository();

    //게임 실행시
    public String isSatisfy(int kill) {

        //1번 도전과제 - 특정 적수 처치

        if (kill == 10) {
            cr.setC_remove(1);
            return "도전과제 달성! = 인베이더 10마리 처치 =";
        }

        if (kill == 50) {
            cr.setC_remove(2);
            return "도전과제 달성! = 인베이더 50마리 처치 =";
        }
        if (kill == 100) {
            cr.setC_remove(3);
            return "도전과제 달성! = 인베이더 100마리 처치 =";
        }

        if (kill == 500) {
            cr.setC_remove(4);
            return "도전과제 달성! = 인베이더 500마리 처치 =";
        }
        return "";
    }

    //게임 진행 안할 시
    public String isSatisfy(int stage, int time) {

        //2번 도전과제 - 특정 시간 안에 특정 스테이지 도달
        if (stage == 4 && time < 70) {
            cr.setC_timeAtk(1);
            return "도전과제 달성! = 70초 안에 스테이지 3 클리어 =";
        }

        if (stage == 6 && time < 120) {
            cr.setC_timeAtk(2);
            return "도전과제 달성! = 120초 안에 스테이지 5 클리어 =";
        }

        if (stage == 8 && time < 200) {
            cr.setC_timeAtk(3);
            return "도전과제 달성! = 200초 안에 스테이지 7 클리어 =";
        }

        //3번 도전과제 - 상점 미사용 + 특정 스테이지 클리어
        if ((SettingValue.getChangeInterval() == 1) && (SettingValue.getSlowInvaderSpeed() == 1) && (stage == 5)) {
            cr.setC_noItem(1);
            return "도전과제 달성! = 상점 미사용하고 스테이지 4 클리어 =";
        }

        if ((SettingValue.getChangeInterval() == 1) && (SettingValue.getSlowInvaderSpeed() == 1) && (stage == 9)) {
            cr.setC_noItem(2);
            return "도전과제 달성! = 상점 미사용하고 스테이지 8 클리어 =";
        }

        if ((SettingValue.getChangeInterval() == 1) && (SettingValue.getSlowInvaderSpeed() == 1) && (stage == 13)) {
            cr.setC_noItem(3);
            return "도전과제 달성! = 상점 미사용하고 스테이지 12 클리어 =";
        }

        return "";
    }


}
