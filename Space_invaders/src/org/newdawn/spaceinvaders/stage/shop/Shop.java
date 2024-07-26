package org.newdawn.spaceinvaders.stage.shop;

import org.newdawn.spaceinvaders.main.Menu;
import org.newdawn.spaceinvaders.stage.SettingValue;

import javax.swing.*;
import java.awt.*;

public class Shop extends JFrame {

    private JButton attackDamageInhenceButton;
    private JButton attackSpeedInhenceButton;
    private JButton alienSpeedDecreaseButton;
    private JButton goMenu;
    private static int BUTTON_SIZE = 6;
    ShopService shopService = new ShopService();

    Coin coin = new Coin();

    public Shop() {

        super("Space Invader 102");

        JFrame frame = new JFrame();
        frame.setBounds(50, 50, 800, 600); // 전체 창 크기
        frame.setTitle("상점");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫기 버튼 누르면 꺼지게 설정

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // 그리드 X 좌표
        gbc.gridy = 0; // 그리드 Y 좌표
        gbc.fill = GridBagConstraints.VERTICAL; // 수직으로 채우기
        gbc.insets = new Insets(20, 0, 10, 0); // 위, 오른쪽, 아래, 왼쪽 여백 설정

        JLabel label1 = new JLabel("현재 잔액 : " + Coin.getCoin() +"원");

        JLabel label = new JLabel(
                "공격속도 증가 레벨 : " + (int) ((1 - SettingValue.getChangeInterval()) * 10 + 1) + " / 10 " +
                        "적 하강속도 감소 레벨 : " + (int) ((1 - SettingValue.getSlowInvaderSpeed()) * 10 + 1) + " / 10 ");
        panel.add(label1, gbc);
        gbc.gridy++; // Y 좌표 증가
        panel.add(label, gbc);
        gbc.gridy++; // Y 좌표 증가

        // 공격속도 증가버튼
        attackSpeedInhenceButton = new JButton("공격속도 증가 : 5원");
        attackSpeedInhenceButton.setPreferredSize(new Dimension(210, 50)); // 버튼의 크기 설정
        attackSpeedInhenceButton.addActionListener(e -> {

            shopService.Increase();
            System.out.println("상점 구매 - 공격속도 증가버튼");
            frame.dispose();
            new Shop();
            frame.setVisible(false);
        });
        panel.add(attackSpeedInhenceButton, gbc);

        gbc.gridy++; // Y 좌표 증가

        // 적 하강속도 감소버튼
        alienSpeedDecreaseButton = new JButton("적 하강속도 감소 : 10원");
        alienSpeedDecreaseButton.setPreferredSize(new Dimension(210, 50)); // 버튼의 크기 설정

        alienSpeedDecreaseButton.addActionListener(e -> {
            shopService.Decrease();
            System.out.println("상점 구매 - 적 하강속도 감소버튼");
            frame.dispose();
            new Shop();
            frame.setVisible(false);
        });
        panel.add(alienSpeedDecreaseButton, gbc);

        gbc.gridy++; // Y 좌표 증가

        JButton init = new JButton("능력치 초기화");
        init.setPreferredSize(new Dimension(210, 50)); // 버튼의 크기 설정

        init.addActionListener(e -> {
            SettingValue.setChangeInterval(1);
            SettingValue.setSlowInvaderSpeed(1);
            System.out.println("초기화");
            JOptionPane.showMessageDialog(frame, "지금까지 구매한 능력치를 초기화 합니다. \n 코인은 환불되지 않습니다.");

            frame.dispose();
            new Shop();
            frame.setVisible(false);
        });
        panel.add(init, gbc);

        gbc.gridy++; // Y 좌표 증가

        // exit

        goMenu = new JButton("나가기");
        goMenu.setPreferredSize(new Dimension(210, 50)); // 버튼의 크기 설정

        goMenu.addActionListener(e -> {
            frame.dispose();
            new Menu();
            frame.setVisible(false);
        });
        panel.add(goMenu, gbc);

        gbc.gridy++; // Y 좌표 증가

        frame.add(panel);
        frame.setVisible(true);
    }
}

