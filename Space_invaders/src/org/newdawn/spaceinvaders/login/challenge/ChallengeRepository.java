package org.newdawn.spaceinvaders.login.challenge;

public class ChallengeRepository {
    public ChallengeRepository() {
    }

    private static int C_remove;
    private static int C_timeAtk;
    private static int C_noItem;

    public static int getC_remove() {
        return C_remove;
    }

    public static void setC_remove(int c_remove) {
        C_remove = c_remove;
    }

    public static int getC_timeAtk() {
        return C_timeAtk;
    }

    public static void setC_timeAtk(int c_timeAtk) {
        C_timeAtk = c_timeAtk;
    }

    public static int getC_noItem() {
        return C_noItem;
    }

    public static void setC_noItem(int c_noItem) {
        C_noItem = c_noItem;
    }

    public ChallengeRepository(int C_remove, int C_timeAtk, int C_noItem) {
        this.C_remove = C_remove;
        this.C_timeAtk = C_timeAtk;
        this.C_noItem =C_noItem;

    }


}
