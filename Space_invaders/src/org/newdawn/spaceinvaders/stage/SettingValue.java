package org.newdawn.spaceinvaders.stage;

//난이도 조절하는 클래스

public class SettingValue {
    public SettingValue() {

    }

    //등장하는 적의 수 조절
    private static float changeFiringInterval = 500;


    //발사속도 간격 감소 비율
    private static float changeInterval = 1L;
    //적이 내려오는 속도 조절
    private static float AlienY = 10;
    //스테이지 단계

    private static float slowInvaderSpeed = 1L;

    private static int changeShip = 1;
    private static int changeShip2 = 1;

    public SettingValue(float changeInterval, float AlienY, float slowInvaderSpeed) {

        this.changeInterval = changeInterval;
        this.AlienY = AlienY;

        this.slowInvaderSpeed = slowInvaderSpeed;
    }

    public static float getChangeInterval() {
        return changeInterval;
    }

    public static void setChangeInterval(float changeInterval) {
        SettingValue.changeInterval = changeInterval;
    }



    public static int getChangeShip() {
        return changeShip;
    }

    public static void setChangeShip(int changeShip) {
        SettingValue.changeShip = changeShip;
    }

    //적 하강속도 비율
    public static float getSlowInvaderSpeed() {
        return slowInvaderSpeed;
    }

    public static void setSlowInvaderSpeed(float slowInvaderSpeed) {
        SettingValue.slowInvaderSpeed = slowInvaderSpeed;
    }

    public static float getChangeFiringInterval() {
        return changeFiringInterval;
    }

    public static void setChangeFiringInterval(float changeFiringInterval) {
        SettingValue.changeFiringInterval = changeFiringInterval;
    }

    public static int getChangeShip2() {
        return changeShip2;
    }

    public static void setChangeShip2(int changeShip2) {
        SettingValue.changeShip2 = changeShip2;
    }

    public static float getAlienY() {
        return AlienY;
    }

    public static void setAlienY(float alienY) {
        AlienY = alienY;
    }


}
