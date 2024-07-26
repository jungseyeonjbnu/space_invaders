package org.newdawn.spaceinvaders;

import org.newdawn.spaceinvaders.stage.SettingValue;

import javax.swing.*;
import java.awt.*;

public class SelectCharacter {
    public SelectCharacter(){

    }
    SettingValue value = new SettingValue(); //전역변수로 변경

    public void selectCharacter(int v){
        JFrame frame = new JFrame();
        frame.setBounds(50, 50, 700, 550); // 전체 창 크기
        frame.setTitle("캐릭터 목록");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫기 버튼 누르면 꺼지게 설정

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // 그리드 X 좌표
        gbc.gridy = 0; // 그리드 Y 좌표
        gbc.fill = GridBagConstraints.VERTICAL; // 수직으로 채우기
        gbc.insets = new Insets(20, 0, 10, 0); // 위, 오른쪽, 아래, 왼쪽 여백 설정


        // 첫 번째 버튼
        JButton changeButton = new JButton("basic");
        changeButton.setPreferredSize(new Dimension(200, 50)); // 버튼의 크기 설정

        changeButton.addActionListener(e -> {

            if (v == 1) {
                value.setChangeShip(1);
            } else if (v == 2) {
                value.setChangeShip2(1);
            }
            JOptionPane.showMessageDialog(frame, "캐릭터 변경 : basic");
            System.out.println("캐릭터 변경 : basic");
        });
        panel.add(changeButton, gbc);

        gbc.gridy++; // Y 좌표 증가

        // 첫 번째 버튼
        JButton changeButton1 = new JButton("Tank");
        changeButton1.setPreferredSize(new Dimension(200, 50)); // 버튼의 크기 설정

        changeButton1.addActionListener(e -> {
            if (v == 1) {
                value.setChangeShip(2);
            } else if (v == 2) {
                value.setChangeShip2(2);
            }
            JOptionPane.showMessageDialog(frame, "캐릭터 변경 : Tank");
            System.out.println("캐릭터 변경 : Tank");
        });
        panel.add(changeButton1, gbc);

        gbc.gridy++; // Y 좌표 증가

        // 두 번째 버튼
        JButton changeButton2 = new JButton("Fighter Plane");
        changeButton2.setPreferredSize(new Dimension(200, 50)); // 버튼의 크기 설정

        changeButton2.addActionListener(e -> {
            if (v == 1) {
                value.setChangeShip(3);
            } else if (v == 2) {
                value.setChangeShip2(3);
            }
            JOptionPane.showMessageDialog(frame, "캐릭터 변경 : Fighter Plane");
            System.out.println("캐릭터 변경 : Fighter Plane");
        });
        panel.add(changeButton2, gbc);

        gbc.gridy++; // Y 좌표 증가

        // 세 번째 버튼
        JButton changeButton3 = new JButton("Rocket");
        changeButton3.setPreferredSize(new Dimension(200, 50)); // 버튼의 크기 설정

        changeButton3.addActionListener(e -> {
            if (v == 1) {
                value.setChangeShip(4);
            } else if (v == 2) {
                value.setChangeShip2(4);
            }
            JOptionPane.showMessageDialog(frame, "캐릭터 변경 : Rocket");
            System.out.println("캐릭터 변경 : Rocket");
        });
        panel.add(changeButton3, gbc);
        gbc.gridy++; // Y 좌표 증가

        // 뒤로가기
        JButton changeButton4 = new JButton("뒤로가기");
        changeButton4.setPreferredSize(new Dimension(150, 40)); // 버튼의 크기 설정

        changeButton4.addActionListener(e -> {
            frame.dispose();
        });
        panel.add(changeButton4, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }
}
