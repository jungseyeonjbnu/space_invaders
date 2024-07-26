package org.newdawn.spaceinvaders.login;

import org.newdawn.spaceinvaders.jdbcdb.ConnectDB;

import java.util.*;

public class MemberRepository {


    private static long sequence = 0L;
    Member member = new Member();
    ConnectDB db = new ConnectDB();


    public void save(String name, String password) {

        member.setName(name);
        member.setPassword(password);
        db.setConnection();
        db.saveMember(member);
    }

    public String findPassword(String name) {
        db.setConnection();
        if (db.checkPassword(name) == null) {

            System.out.println("비밀번호가 틀렸거나 존재하지 않음");
            return null;
        } else {
            System.out.println("MemberRepository - 반환하는 비밀번호 : " + db.checkPassword(name));
            return db.checkPassword(name);
        }

    }

    public void findAll() {

    }
}
