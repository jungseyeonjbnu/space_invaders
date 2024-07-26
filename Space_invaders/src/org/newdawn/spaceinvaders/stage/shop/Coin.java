package org.newdawn.spaceinvaders.stage.shop;

public class Coin {
    //초기 코인은 50원 넣어놨음
    private static int coin = 50;

    public Coin(){

    }

    public Coin(int coin){
        this.coin = coin;
    }

    public static int getCoin() {
        return coin;
    }
    public static void setCoin(int coin) {
        Coin.coin = coin;
    }
}
