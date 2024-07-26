package org.newdawn.spaceinvaders.login;

import org.newdawn.spaceinvaders.login.challenge.ChallengeRepository;
import org.newdawn.spaceinvaders.jdbcdb.ConnectDB;
import org.newdawn.spaceinvaders.jdbcdb.GameInfo;
import org.newdawn.spaceinvaders.jdbcdb.SendGameInfo;
import org.newdawn.spaceinvaders.login.challenge.CheckChallengeRepository;
import org.newdawn.spaceinvaders.main.Game;
import org.newdawn.spaceinvaders.main.Menu;
import org.newdawn.spaceinvaders.stage.SettingValue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginService extends JFrame {

    private static int BUTTON_SIZE = 6;
    JFrame frame;
    JTextField id, password, findId;//클래스 변수로 선언
    Member member = new Member();
    MemberRepository memberRepository = new MemberRepository();

    public LoginService() {

        super("로그인 창");

        SettingValue value = new SettingValue();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        // get hold the content of the frame and set up the resolution of the game
        setContentPane(new JPanel());
        setIgnoreRepaint(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // 그리드 X 좌표
        gbc.gridy = GridBagConstraints.CENTER; // 그리드 Y 좌표
        gbc.fill = GridBagConstraints.VERTICAL; // 수직으로 채우기
        gbc.insets = new Insets(10, 0, 10, 0); // 위, 오른쪽, 아래, 왼쪽 여백 설정


        JPanel panel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxLayout);


        if (member.isLoginCookie()) {

            panel.add(Box.createVerticalStrut(150)); // 수직 간격 100픽셀

            panel.add(new JLabel(member.getLoginName() + "님의 마이 페이지"));
            panel.add(Box.createVerticalStrut(20)); // 수직 간격 20픽셀

            JButton button = new JButton("게임기록 확인");
            button.addActionListener(new LoginService.RecordListListener(this));
            panel.add(button);
            panel.add(Box.createVerticalStrut(20)); // 수직 간격 20픽셀

            JButton button1 = new JButton("도전과제 확인");
            button1.addActionListener(new LoginService.ChallengeListener(this));
            panel.add(button1);
            panel.add(Box.createVerticalStrut(20)); // 수직 간격 20픽셀


            JButton button2 = new JButton("로그아웃");
            button2.addActionListener(e -> {

                Member member = new Member();
                member.setLoginCookie(false);
                member.setLoginName("");
                member.setLoginPassword("");

                GameInfo info = new GameInfo();
                info.setStage(0);
                //알림창 구현
                JOptionPane.showMessageDialog(frame, "로그아웃 되었습니다.");

                dispose();
                new Menu();
                setVisible(false);

            });
            panel.add(button2);

            panel.add(Box.createVerticalStrut(20)); // 수직 간격 20픽셀


        } else {

            panel.add(Box.createVerticalStrut(100)); // 수직 간격 20픽셀


            panel.add(new JLabel("로그인 창"));

            panel.add(Box.createVerticalStrut(20)); // 수직 간격 20픽셀

            panel.add(new JLabel("아이디:"));
            id = new JTextField(20);
            panel.add(id);
            panel.add(Box.createVerticalStrut(10)); // 수직 간격 20픽셀

            panel.add(new JLabel("비밀번호:"));
            password = new JTextField(20);
            panel.add(password);
            panel.add(Box.createVerticalStrut(10)); // 수직 간격 20픽셀

            JButton button = new JButton("확인");
            button.addActionListener(new LoginService.SignInListener(this));
            panel.add(button);

            panel.add(Box.createVerticalStrut(40)); // 수직 간격 40픽셀

            JButton button1 = new JButton("게스트 로그인");
            button1.addActionListener(new LoginService.GuestLoginListener(this));
            panel.add(button1);

            panel.add(Box.createVerticalStrut(20)); // 수직 간격 20픽셀

            JButton signUp = new JButton("회원가입/비밀번호 찾기");
            signUp.addActionListener(e -> {
                dispose();
                new MemberService();
                setVisible(false);
            });
            panel.add(signUp);

        }

        panel.add(Box.createVerticalStrut(20)); // 수직 간격 20픽셀

        JButton goMenu = new JButton("뒤로가기");
        goMenu.addActionListener(e -> {
            dispose();
            new Menu();
            setVisible(false);
        });
        panel.add(goMenu);

        this.add(panel);
        this.setVisible(true);

    }

    /*
    로그인 로직
        입력 받은 아이디와 비밀번호를 통해 DB에 저장되어있는 비밀번호와 비교
     */

    class SignInListener implements ActionListener {
        JFrame frame;

        public SignInListener(JFrame f) {
            frame = f;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            //버튼을 누르면 이쪽으로 이동
            System.out.println(arg0.getActionCommand());


            System.out.println("id: " + id.getText());
            System.out.println("password: " + password.getText());

            //아이디를 통해 저장되어 있는 비밀번호를 찾은후, 입력 받은 비밀번호와 동일한지 확인
            String checkPassword = memberRepository.findPassword(id.getText());
            System.out.println("checkPassword = " + checkPassword);
            if (checkPassword.equals("")) {
                System.out.println("로그인 실패");
                JOptionPane.showMessageDialog(frame, "로그인 실패 - 아이디나 비밀번호를 확인해주세요.");
            } else if (checkPassword.equals(password.getText())) {
                System.out.println("로그인 성공");

                //로그인 쿠키  true로 설정
                member.setLoginCookie(true);
                //현재 로그인한 회원 정보 저장
                member.setLoginName(id.getText());
                member.setLoginPassword(password.getText());

                JOptionPane.showMessageDialog(frame, "로그인 성공 - 환영합니다, " + member.getLoginName() + "님.");

                //new game을 경유하여 로그인 요청이 들어온 경우
                if (member.isGameCookie()) {
                    //new game 쿠키를 초기화
                    member.setGameCookie(false);

                    dispose();

                    //게임 시작!
                    Thread thread = new Thread(() -> {
                        Game game = new Game();
                        setVisible(false);
                        game.gameLoop();
                    });
                    thread.start();

                    //메뉴로 복귀
                } else {
                    member.setGameCookie(false);
                    dispose();
                    new Menu();
                    setVisible(false);
                }
            } else {
                System.out.println("로그인 실패");
                JOptionPane.showMessageDialog(frame, "로그인 실패 - 아이디나 비밀번호를 확인해주세요.");
            }
        }
    }

    class GuestLoginListener implements ActionListener {
        JFrame frame;

        public GuestLoginListener(JFrame f) {
            frame = f;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {

            if (member.isLoginCookie()) {
                System.out.println("로그인 쿠키 : " + member.isLoginCookie());
                JOptionPane.showMessageDialog(frame, "이미 로그인 되어 있습니다.");
            } else {
                System.out.println("게스트 로그인");
                member.setLoginCookie(true);
                member.setLoginName("guest");
                member.setLoginPassword("guest");

                JOptionPane.showMessageDialog(frame, "게스트로 로그인 하셨습니다.");

                //new game을 경유하여 게스트 로그인 요청이 들어온 경우
                if (member.isGameCookie()) {
                    //new game 쿠키 제거
                    member.setGameCookie(false);

                    Thread thread = new Thread(() -> {
                        Game game = new Game();
                        setVisible(false);
                        game.gameLoop();
                    });
                    thread.start();
                } else {
                    member.setGameCookie(false);
                    dispose();
                    new Menu();
                    setVisible(false);
                }
            }
        }
    }

    class RecordListListener implements ActionListener {
        JFrame frame;

        public RecordListListener(JFrame f) {
            frame = f;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            //버튼을 누르면 이쪽으로 이동
            ConnectDB db = new ConnectDB();
            db.setConnection();
            int best = db.showBestRecord();
            ArrayList<SendGameInfo> playRecord = db.playRecordList();

            JFrame frame = new JFrame();
            frame.setBounds(50, 50, 600, 600); // 전체 창 크기
            frame.setTitle("게임기록");
            frame.setLocationRelativeTo(null);
            frame.setAlwaysOnTop(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫기 버튼 누르면 꺼지게 설정
            frame.setVisible(true);

            JLabel[] txt = new JLabel[playRecord.size()];
            JLabel initTxt = new JLabel("살아남은 시간 / 킬카운트 / 생존한 스테이지 수 / 점수 \n");
            JLabel blank = new JLabel("");
            JLabel memInfo = new JLabel(member.getLoginName() + "님의 게임 플레이 기록입니다.");
            JLabel bestInfo = new JLabel("최고기록 : " + best);

            memInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
            bestInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
            initTxt.setAlignmentX(Component.CENTER_ALIGNMENT);
            blank.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


            frame.add(panel);
            panel.add(Box.createVerticalStrut(20)); // 수직 간격 20픽셀
            panel.add(memInfo);
            if (best == 0) {
                bestInfo = new JLabel("게임 플레이 기록이 없습니다");
                bestInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
            }
            panel.add(Box.createVerticalStrut(10)); // 수직 간격 20픽셀
            panel.add(bestInfo);

            panel.add(Box.createVerticalStrut(20)); // 수직 간격 20픽셀

            panel.add(initTxt);
            panel.add(Box.createVerticalStrut(20)); // 수직 간격 20픽셀

            for (int i = 0; i < playRecord.size(); i++) {
                txt[i] = new JLabel("\n" + playRecord.get(i).getPlayTime() + "    " + playRecord.get(i).getKillCount()
                        + "    " + playRecord.get(i).getStage() + "    " + playRecord.get(i).getScore() + "\n");
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

    class ChallengeListener implements ActionListener {
        JFrame frame;
        int rate = 0;
        ConnectDB db = new ConnectDB();

        public ChallengeListener(JFrame f) {
            frame = f;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            //버튼을 누르면 이쪽으로 이동
            db.setConnection();
            CheckChallengeRepository cr = db.checkChallenge();

            JFrame frame = new JFrame();
            frame.setBounds(50, 50, 500, 330); // 전체 창 크기
            frame.setTitle("도전과제");
            frame.setLocationRelativeTo(null);
            frame.setAlwaysOnTop(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫기 버튼 누르면 꺼지게 설정
            frame.setVisible(true);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            Font myFont1 = new Font("Noto Sans KR", Font.PLAIN, 15);


            JLabel remove;
            JLabel timeAtk;
            JLabel noItem;

            //long C_remove, long C_timeAtk, long C_noItem
            switch (cr.getChk_remove()) {
                case 1: {
                    remove = new JLabel("도전과제 달성! 적 10마리 처치");
                    rate += 10;
                }
                break;
                case 2: {
                    remove = new JLabel("도전과제 달성! 적 50마리 처치");
                    rate += 20;
                }
                break;
                case 3: {
                    remove = new JLabel("도전과제 달성! 적 100마리 처치");
                    rate += 30;
                }
                break;
                case 4: {
                    remove = new JLabel("도전과제 달성! 적 500마리 처치");
                    rate += 40;
                }
                break;

                default: {
                    remove = new JLabel("적 처치 도전과제 미달성");
                }
                break;
            }


            switch (cr.getChk_timeAtk()) {
                case 1: {
                    timeAtk = new JLabel("도전과제 달성! = 70초 안에 스테이지 3 클리어");
                    rate += 10;
                }
                break;
                case 2: {
                    timeAtk = new JLabel("도전과제 달성! = 120초 안에 스테이지 5 클리어");
                    rate += 20;
                }
                break;
                case 3: {
                    timeAtk = new JLabel("도전과제 달성! = 200초 안에 스테이지 7 클리어");
                    rate += 30;
                }
                break;
                default: {
                    timeAtk = new JLabel("타임어택 도전과제 미달성");
                }
                break;
            }


            switch (cr.getChk_noItem()) {
                case 1: {
                    noItem = new JLabel("도전과제 달성! 상점 미사용하고 스테이지 4 클리어 ");
                    rate += 10;
                }
                break;
                case 2: {
                    noItem = new JLabel("도전과제 달성! 상점 미사용하고 스테이지 8 클리어 ");
                    rate += 20;
                }
                break;
                case 3: {
                    noItem = new JLabel("도전과제 달성! 상점 미사용하고 스테이지 12 클리어 ");
                    rate += 30;
                }
                break;
                default: {
                    noItem = new JLabel("상점 미사용 도전과제 미달성");
                }
                break;
            }
            remove.setFont(myFont1);
            timeAtk.setFont(myFont1);
            noItem.setFont(myFont1);


            JLabel title = new JLabel("도전과제 목록");
            JLabel memInfo = new JLabel(member.getLoginName() + "님의 도전과제 달성률 페이지 입니다.");
            JLabel rateInfo = new JLabel("도전과제 달성률 : " + rate + "%");
            title.setFont(myFont1);
            memInfo.setFont(myFont1);
            rateInfo.setFont(myFont1);

            memInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            rateInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
            remove.setAlignmentX(Component.CENTER_ALIGNMENT);
            timeAtk.setAlignmentX(Component.CENTER_ALIGNMENT);
            noItem.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel.add(Box.createVerticalStrut(50)); // 수직 간격 20픽셀

            panel.add(memInfo);
            panel.add(Box.createVerticalStrut(10)); // 수직 간격 20픽셀

            panel.add(rateInfo);
            panel.add(Box.createVerticalStrut(20)); // 수직 간격 20픽셀

            panel.add(title);
            panel.add(Box.createVerticalStrut(20)); // 수직 간격 20픽셀

            panel.add(remove);
            panel.add(Box.createVerticalStrut(15)); // 수직 간격 20픽셀
            panel.add(timeAtk);
            panel.add(Box.createVerticalStrut(15)); // 수직 간격 20픽셀
            panel.add(noItem);
            panel.add(Box.createVerticalStrut(15)); // 수직 간격 20픽셀


            // 스크롤 만드는 함수
            JScrollPane scrollPane = new JScrollPane(panel);
            frame.add(scrollPane);
            setVisible(true);

        }
    }

}

