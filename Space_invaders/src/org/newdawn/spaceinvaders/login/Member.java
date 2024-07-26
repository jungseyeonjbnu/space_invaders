package org.newdawn.spaceinvaders.login;

public class Member {

    private int id;
    private String name;

    private String password;
    //로그인 여부를 판단하는 변수
    private static boolean loginCookie = false;

    //새 게임이 시작되는지 판단하는 변수
    private static boolean gameCookie = false;

    private static String loginName;
    private static String loginPassword;

    /*
        getter, setter
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public static boolean isGameCookie() {
        return gameCookie;
    }

    public static void setGameCookie(boolean gameCookie) {
        Member.gameCookie = gameCookie;
    }


    public static boolean isLoginCookie() {
        return loginCookie;
    }


    public static void setLoginCookie(boolean logonCookie) {
        Member.loginCookie = logonCookie;
    }

    public static String getLoginName() {
        return loginName;
    }

    public static void setLoginName(String loginName) {
        Member.loginName = loginName;
    }

    public static String getLoginPassword() {
        return loginPassword;
    }

    public static void setLoginPassword(String loginPassword) {
        Member.loginPassword = loginPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Member() {
    }

    public Member(int id,String name, String password) {

        this.id = id;
        this.name = name;
        this.password = password;
    }
}
