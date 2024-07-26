package org.newdawn.spaceinvaders.stage.shop;

import org.newdawn.spaceinvaders.stage.SettingValue;

import javax.swing.*;

public class ShopService {
JFrame frame;

    /*
    -발사속도 증가 능력치 증가 로직-
        기본 발사속도 * 발사속도 증가값
        -> 발사간격을 줄이는 로직
    */
    public void Increase() {
        SettingValue value = new SettingValue();

        System.out.println("상점 메소드 하기 전 = " + value.getChangeFiringInterval());
        //price = 능력치 구매 가격

        boolean pur = purchase(5);

        //purchase 함수에서 받은 리턴 값으로 아래 능력치 증.감 로직을 실행시킬지 안시킬지를 정함
        if (pur) {
            if (value.getChangeInterval() <= 0.1) {
                System.out.println("발사속도 최대 증가");
                JOptionPane.showMessageDialog(frame, "최대 수치 도달 - 더 이상 구매할 수 없습니다.");
            } else {

                value.setChangeInterval((float)(value.getChangeInterval() - 0.1));
                System.out.println("발사 속도 증가 성공 - 현재 속도 감사비율: " + value.getChangeInterval());

            }
        }
    }


    /*
    -적 하강속도 감소 로직-
        적 하강속도의 경우 다음의 공식을 따른다
        (10 + 5 * 현재 스테이지 단계) * 하강 속도 감소 비율
    */
    public void Decrease() {
        SettingValue value = new SettingValue();
        // price = 능력치 구매 가격
        boolean pur = purchase(10);

        //purchase 함수에서 받은 리턴 값으로 아래 능력치 증.감 로직을 실행시킬지 안시킬지를 정함
        if (pur) {
            if (value.getSlowInvaderSpeed() <= 0.1) {
                System.out.println("적 하강속도 최대 감소");
                JOptionPane.showMessageDialog(frame, "최대 수치 도달 - 더 이상 구매할 수 없습니다.");
            } else {
                value.setSlowInvaderSpeed((float) (value.getSlowInvaderSpeed() - 0.1));

                System.out.println("적 하강속도 감소 성공 - 현재 감소비율: " + value.getSlowInvaderSpeed());
            }
        }
    }

    //상점 능력치 구매로직
    public boolean purchase(int price) {
        Coin coin = new Coin();

        //잔액이 부족하면 false를 리턴
        if ((coin.getCoin() - price) < 0) {
            System.out.println("잔액부족 - 현재 코인 : " + coin.getCoin());
            JOptionPane.showMessageDialog(frame, "잔액이 부족합니다. 현재 코인 : " + coin.getCoin());
            return false;
        }
        //잔액이 충분하면 true를 리턴
        else {
            coin.setCoin(coin.getCoin() - price);
            System.out.println("구매성공 - 남은 코인 : " + coin.getCoin());
            return true;
        }
    }


}
