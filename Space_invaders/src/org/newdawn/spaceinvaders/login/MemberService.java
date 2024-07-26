package org.newdawn.spaceinvaders.login;

import org.newdawn.spaceinvaders.jdbcdb.ConnectDB;
import org.newdawn.spaceinvaders.stage.SettingValue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MemberService extends JFrame {

    private static int BUTTON_SIZE = 6;
    JTextField id, password, findId;//클래스 변수로 선언

    Member member = new Member();
    MemberRepository memberRepository = new MemberRepository();

    public MemberService() {

        super("회원가입 창");

        SettingValue value = new SettingValue();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        // get hold the content of the frame and set up the resolution of the game
        setContentPane(new JPanel());
        setIgnoreRepaint(false);
        GridBagConstraints[] gbc = new GridBagConstraints[BUTTON_SIZE];
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);

        // 입력창 배치
        JPanel panel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxLayout);

        panel.add(new JLabel("아이디:"));
        id = new JTextField(20);
        panel.add(id);

        panel.add(new JLabel("비밀번호:"));
        password = new JTextField(20);
        panel.add(password);

        JButton button = new JButton("확인");
        button.addActionListener(new SignUpListener(this));
        panel.add(button);

        panel.add(Box.createVerticalStrut(20)); // 수직 간격 20픽셀

        panel.add(new JLabel("찾으려는 아이디:"));
        findId = new JTextField(20);
        panel.add(findId);

        JButton button1 = new JButton("비밀번호 확인");
        button1.addActionListener(new MemberConfirmListener(this));
        panel.add(button1);

        panel.add(Box.createVerticalStrut(20)); // 수직 간격 20픽셀

        // 클릭하면 새창
        JButton checkList = new JButton("회원목록 확인");
        checkList.setBounds(350, 300, 200, 50);
        gbc[1] = new GridBagConstraints();
        gbc[1].gridx = 1;
        gbc[1].gridy = 1;
        getContentPane().add(checkList, gbc[1]);
        checkList.addActionListener(new MemberListListener(this));

        JButton goMenu = new JButton("뒤로가기");
        goMenu.setBounds(350, 600, 200, 50);
        gbc[4] = new GridBagConstraints();
        gbc[4].gridx = 1;
        gbc[4].gridy = 4;
        getContentPane().add(goMenu, gbc[4]);
        goMenu.addActionListener(e -> {
            dispose();
            new LoginService();
            setVisible(false);
        });

        this.add(panel);
        this.setVisible(true);

    }

    //회원가입 로직
    class SignUpListener implements ActionListener {
        JFrame frame;

        public SignUpListener(JFrame f) {
            frame = f;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            //버튼을 누르면 이쪽으로 이동
            System.out.println(arg0.getActionCommand());
            ConnectDB db = new ConnectDB();
            db.setConnection();

            /*
                1. 아이디/비밀번호는 비어있지 않은가?
                2. 아이디는 이미 존재하지 않는가?
                3.
             */
            String name = id.getText();
            String ps = password.getText();

            if (name.equals("")) {
                JOptionPane.showMessageDialog(frame, "아이디는 비어있을 수 없습니다.");
            } else if (ps.equals("")) {
                JOptionPane.showMessageDialog(frame, "비밀번호는 비어있을 수 없습니다.");
            } else if (db.checkPassword(id.getText()) != "") {
                JOptionPane.showMessageDialog(frame, "이미 존재하는 아이디 입니다.");
            } else {

                System.out.println("id: " + id.getText());
                System.out.println("password: " + password.getText());

                //DB에 아이디/비밀번호를 저장
                memberRepository.save(id.getText(), password.getText());

                //다이얼로그
                JOptionPane.showMessageDialog(frame, "회원가입을 환영합니다, " + id.getText() + "님.");

                dispose();
                new LoginService();
                setVisible(false);
            }

        }
    }

    class MemberConfirmListener implements ActionListener {
        JFrame frame;

        public MemberConfirmListener(JFrame f) {
            frame = f;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            //버튼을 누르면 이쪽으로 이동
            System.out.println(arg0.getActionCommand());
            //String memId = id.getText();
            System.out.println("id: " + findId.getText());

            String info = memberRepository.findPassword(findId.getText());
            if (info == null) {
                JOptionPane.showMessageDialog(frame, "존재하지 않는 회원입니다.");
            } else {
                JOptionPane.showMessageDialog(frame, findId.getText() + "님, 비밀번호는 " + info + "입니다.");
            }
            //다이얼로그

        }
    }

    class MemberListListener implements ActionListener {
        JFrame frame;

        public MemberListListener(JFrame f) {
            frame = f;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            //버튼을 누르면 이쪽으로 이동
            ConnectDB db = new ConnectDB();
            db.setConnection();
            ArrayList<Member> map = db.memberList();

            JFrame frame = new JFrame();
            frame.setBounds(50, 50, 500, 330); // 전체 창 크기
            frame.setTitle("회원목록");
            frame.setLocationRelativeTo(null);
            frame.setAlwaysOnTop(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫기 버튼 누르면 꺼지게 설정
            frame.setVisible(true);

            JLabel[] txt = new JLabel[map.size()];
            JLabel initTxt = new JLabel("회원번호 \t 회원 아이디 \t 회원 비밀번호 \n");
            JLabel blank = new JLabel("");
            initTxt.setAlignmentX(Component.CENTER_ALIGNMENT);
            blank.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            frame.add(panel);

            panel.add(blank);
            panel.add(Box.createVerticalStrut(30)); // 수직 간격 20픽셀

            panel.add(initTxt);

            for (int i = 0; i < map.size(); i++) {
                txt[i] = new JLabel("\n" + map.get(i).getId() + " \t " + map.get(i).getName() + " \t " + map.get(i).getPassword() + "\n");
                txt[i].setAlignmentX(Component.CENTER_ALIGNMENT);
                panel.add(txt[i]);
                panel.add(Box.createVerticalStrut(10)); // 수직 간격 20픽셀
            }

            // 스크롤 만드는 함수
            JScrollPane scrollPane = new JScrollPane(panel);
            frame.add(scrollPane);


            setVisible(true);

        }
    }

}