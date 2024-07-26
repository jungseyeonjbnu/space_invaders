package org.newdawn.spaceinvaders.jdbcdb;

import org.newdawn.spaceinvaders.login.Member;
import org.newdawn.spaceinvaders.login.challenge.ChallengeRepository;
import org.newdawn.spaceinvaders.login.challenge.CheckChallengeRepository;
import org.newdawn.spaceinvaders.stage.SettingValue;
import org.newdawn.spaceinvaders.stage.shop.Coin;

import java.sql.*;
import java.util.ArrayList;

public class ConnectDB {

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement psmt = null;
    Statement stmt;

    Coin c = new Coin();
    Member member = new Member();
    SettingValue value = new SettingValue();

    GameInfo info = new GameInfo();

    SendGameInfo sendInfo = new SendGameInfo();

    public void setConnection() {
        String url = "jdbc:mysql://localhost:3306/invader";
        String id = "user1";
        String pw = "";

        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, id, pw);
            System.out.println("DB정상연결");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("DB연결실패");

        }
    }

    public void insertResult() {

        try {
            String sql = "insert into invader(play_time, stage, kill_count, play_score, coin, name, password) values (?,?,?, ?,?, ?, ?)";

            int play_time = info.getPlayTime();
            int stage = info.getStage();
            int kill_count = info.getKillCount();
            int play_score = info.getScore();
            int coin = c.getCoin();
            String name = member.getLoginName();
            String password = member.getLoginPassword();

            psmt = con.prepareStatement(sql);
            psmt.setInt(1, play_time);
            psmt.setInt(2, stage);
            psmt.setInt(3, kill_count);
            psmt.setInt(4, play_score);
            psmt.setInt(5, coin);
            psmt.setString(6, name);
            psmt.setString(7, password);
            psmt.executeUpdate();

            System.out.println("쿼리성공_insertResult");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("쿼리실패_insertResult");
        }
    }

    public void updateResult() {

        try {

            String sql = "update invader set play_time = ?, stage = ?, kill_count = ?, play_score = ? ,coin =? where name =? and password = ? ORDER BY ID DESC LIMIT 1";

            int play_time = info.getPlayTime();
            int stage = info.getStage();
            int kill_count = info.getKillCount();
            int play_score = info.getScore();
            int coin = c.getCoin();
            String name = member.getLoginName();
            String password = member.getLoginPassword();

            psmt = con.prepareStatement(sql);
            psmt.setInt(1, play_time);
            psmt.setInt(2, stage);
            psmt.setInt(3, kill_count);
            psmt.setInt(4, play_score);
            psmt.setInt(5, coin);
            psmt.setString(6, name);
            psmt.setString(7, password);
            psmt.executeUpdate();

            System.out.println("쿼리성공_updateResult");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("쿼리실패_updateResult");
        }
    }

    public void showResult() {
        try {

            stmt = con.createStatement();
            String sql = "select * from invader";
            rs = psmt.executeQuery(sql);
            //psmt = con.prepareStatement(sql);

            int rowNum = 1;
            while (rs.next()) {
                int playTime = rs.getInt("play_time");
                String stage = rs.getString("stage");
                String killCount = rs.getString("kill_count");
                int score = rs.getInt("play_score");

                System.out.println("플레이타임: " + playTime + " 스테이지: " + stage + " 킬카운트: " + killCount + " 점수 " + score);
            }

            System.out.println("쿼리성공_showResult");
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("쿼리실패_showResult");
            throw new RuntimeException(e);
        }
    }

    public int showBestRecord() {
        String killCount = "최고기록이 없습니다.";
        int best = 0;

        try {
            stmt = con.createStatement();
            String sql = "SELECT * FROM invader WHERE name = '" + member.getLoginName() + "' ORDER BY play_score DESC LIMIT 1";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                killCount = rs.getString("kill_count");
                best = rs.getInt("play_score");

                System.out.println("킬카운트: " + killCount);
                System.out.println("최고점수: " + best);

            }

            System.out.println("쿼리성공_showBestRecord");
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("쿼리실패_showBestRecord");
            throw new RuntimeException(e);
        }
        System.out.println("score" + best);
        return best;

    }

    // 근데 이거 필요한가?
    // db에 저장된 값 중간 체크 로직
    public void currentRecord() {

        try {

            stmt = con.createStatement();
            String sql = "select * from invader ORDER BY id desc limit 1";
            rs = psmt.executeQuery(sql);
            //psmt = con.prepareStatement(sql);

            int rowNum = 1;
            while (rs.next()) {
                int playTime = rs.getInt("play_time");
                String stage = rs.getString("stage");
                String killCount = rs.getString("kill_count");
                int score = rs.getInt("play_score");

                System.out.println("플레이타임: " + playTime + " 스테이지: " + stage + " 킬카운트: " + killCount + " 점수 " + score);


            }

            System.out.println("쿼리성공_currentRecord");
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("쿼리실패_currentRecord");
            throw new RuntimeException(e);
        }

    }

    public void saveMember(Member m) {

        try {
            String sql = "insert into memberlist(name, password) values (?,?)";

            String name = m.getName();
            String password = m.getPassword();

            System.out.println("saveMember : name = " + name + " password = " + password);

            psmt = con.prepareStatement(sql);
            psmt.setString(1, name);
            psmt.setString(2, password);

            psmt.executeUpdate();

            System.out.println("쿼리성공_saveMember");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("쿼리실패_saveMember");
        }
    }

    public String checkPassword(String name) {

        String inputPs = "";

        try {
            stmt = con.createStatement();
            String sql = "SELECT * FROM memberlist WHERE name = '" + name + "'";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                //String inputName = rs.getString("name");
                inputPs = rs.getString("password");

                System.out.println("받은 아이디: " + name);
                System.out.println("찾아낸 비밀번호: " + inputPs);

            }

            System.out.println("쿼리성공_checkPassword");
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("쿼리실패_checkPassword");
            throw new RuntimeException(e);
        }
        System.out.println("반환하는 비밀번호 = " + inputPs);
        return inputPs;
    }

    //가입자 목록 반환
    public ArrayList<Member> memberList() {

        ArrayList<Member> arr = new ArrayList<Member>();
        System.out.println(arr);
        try {
            // 쿼리문을 db에 넘김, 온전한 문자열 대입

            stmt = con.createStatement();
            String sql = "select * from memberlist";
            rs = stmt.executeQuery(sql);

            // 받은 결과값을 출력
            while (rs.next()) {
                arr.add(new Member(rs.getInt(1), rs.getString(2), rs.getString(3)));
                System.out.printf("memberlist -> id : %d, name : %s , password : %s \n",
                        rs.getInt(1), rs.getString(2), rs.getString(3));
            }
            System.out.println("쿼리성공_memberList");
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("쿼리실패_memberList");
            e.printStackTrace();
        }
        return arr;
    }

    public ArrayList<SendGameInfo> playRecordList() {

        ArrayList<SendGameInfo> record = new ArrayList<SendGameInfo>();
        System.out.println(record);
        try {
            // 쿼리문을 db에 넘김, 온전한 문자열 대입

            stmt = con.createStatement();
            String sql = "SELECT * FROM invader WHERE name = '" + member.getLoginName() + "' ORDER BY id DESC";
            rs = stmt.executeQuery(sql);


            // public GameInfo(int playTime, int killCount,int stage, int score){
            // 받은 결과값을 출력
            while (rs.next()) {
                record.add(new SendGameInfo(rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
                System.out.printf("playRecordList -> playTime : %d, killCount : %d , stage : %d, score : %d  \n",
                        rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
            }
            System.out.println("쿼리성공_playRecordList");
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("쿼리실패_playRecordList");
            e.printStackTrace();
        }
        return record;
    }

    public void insertChallenge() {

        try {
            String sql = "insert into challenge(name, remove, time_atk, no_item) values (?,?, ?, ?)";

            int remove = ChallengeRepository.getC_remove();
            int time_atk = ChallengeRepository.getC_timeAtk();
            int no_item = ChallengeRepository.getC_noItem();

            String name = member.getLoginName();


            psmt = con.prepareStatement(sql);
            psmt.setString(1, name);
            psmt.setInt(2, remove);
            psmt.setInt(3, time_atk);
            psmt.setInt(4, no_item);
            psmt.executeUpdate();
            System.out.println("remove : " + remove+  " time_atk + " + time_atk + " no_item + " + no_item);
            System.out.println("쿼리성공_insertChallenge");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("쿼리실패_insertChallenge");
        }
    }

    public CheckChallengeRepository checkChallenge() {
        int remove = 0;
        int timeAtk= 0;
        int noItem= 0;

        try {

            stmt = con.createStatement();
            String sql = "select remove from challenge WHERE name = '" + member.getLoginName() + "' ORDER BY remove desc limit 1";
            psmt = con.prepareStatement(sql);
            rs = psmt.executeQuery(sql);
            //psmt = con.prepareStatement(sql);

            while (rs.next()) {
                 remove = rs.getInt("remove");
            }

             sql = "select time_atk from challenge WHERE name = '" + member.getLoginName() + "' ORDER BY time_atk desc limit 1";
            rs = psmt.executeQuery(sql);
            while (rs.next()) {
                 timeAtk = rs.getInt("time_atk");
            }

             sql = "select no_item from challenge WHERE name = '" + member.getLoginName() + "' ORDER BY no_item desc limit 1";
            rs = psmt.executeQuery(sql);
            while (rs.next()) {
                 noItem = rs.getInt("no_item");
            }
            System.out.println("remove"+ remove +" timeAtk" + timeAtk + "noItem"+ noItem);

            System.out.println("쿼리성공_currentRecord");
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("쿼리실패_currentRecord");
            throw new RuntimeException(e);
        }
        return new CheckChallengeRepository(remove, timeAtk, noItem);
    }


}