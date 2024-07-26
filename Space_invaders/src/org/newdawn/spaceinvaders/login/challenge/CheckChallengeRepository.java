package org.newdawn.spaceinvaders.login.challenge;

public class CheckChallengeRepository {
    public CheckChallengeRepository() {

    }



    private int Chk_remove;
    private int Chk_timeAtk;
    private int Chk_noItem;

    public int getChk_remove() {
        return Chk_remove;
    }

    public void setChk_remove(int chk_remove) {
        Chk_remove = chk_remove;
    }

    public int getChk_timeAtk() {
        return Chk_timeAtk;
    }

    public void setChk_timeAtk(int chk_timeAtk) {
        Chk_timeAtk = chk_timeAtk;
    }

    public int getChk_noItem() {
        return Chk_noItem;
    }

    public void setChk_noItem(int chk_noItem) {
        Chk_noItem = chk_noItem;
    }

    public CheckChallengeRepository(int Chk_remove, int Chk_timeAtk, int Chk_noItem) {
        this.Chk_remove = Chk_remove;
        this.Chk_timeAtk = Chk_timeAtk;
        this.Chk_noItem = Chk_noItem;
    }
}
