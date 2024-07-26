package org.newdawn.spaceinvaders.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import org.newdawn.spaceinvaders.SelectCharacter;
import org.newdawn.spaceinvaders.SystemTimer;
import org.newdawn.spaceinvaders.entity.AlienEntity;
import org.newdawn.spaceinvaders.entity.Entity;
import org.newdawn.spaceinvaders.entity.ShipEntity;
import org.newdawn.spaceinvaders.entity.ShotEntity;

import javax.swing.*;

/**
 * 2인용 모드 클래스입니다.
 * 사용자1은 A,D KEY로 이동하고 S KEY로 공격합니다.
 * 사용자2는 좌우 화살표 KEY로 이동하고 아래 화살표 KEY로 공격합니다.
 * <p>
 * 초기 점수는 100점, 에일리언을 죽이면 +20점, 총알 한 발에 -10점입니다.
 */
public class TwoPlayer extends Game {

    private ArrayList entities1 = new ArrayList();
    private ArrayList entities2 = new ArrayList();
    private int firecount1;
    private int firecount2;
    private Entity ship1;
    private Entity ship2;
    private int killAlien1;
    private int killAlien2;
    private boolean logicRequiredThisLoop1 = false;
    private boolean logicRequiredThisLoop2 = false;
    private long lastFire1 = 0;
    private long lastFire2 = 0;
    private int alienCount1 = 0;
    private int alienCount2 = 0;
    private boolean leftPressed = false;
    private boolean leftPressed2 = false;
    private boolean rightPressed = false;
    private boolean rightPressed2 = false;
    private boolean firePressed1 = false;
    private boolean firePressed2 = false;
    protected long firingInterval = 300;
    private long count1 = 100;
    private long count2 = 100;
    String message2  = "";


    public TwoPlayer() {

        setIgnoreRepaint(true);

        container.pack();
        container.setResizable(false);
        container.setVisible(true);

        container.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
//                System.exit(0);
                //container.dispose();
                container.setVisible(false);
            }
        });

        addKeyListener(new KeyInputHandlerTwo());

        requestFocus();

        createBufferStrategy(2);
        strategy = getBufferStrategy();

        initEntities();

    }


    private void startGame() {
        // clear out any existing entities and intialise a new set
        entities1.clear();
        entities2.clear();
        initEntities();

        // blank out any keyboard settings we might currently have
        leftPressed = false;
        leftPressed2 = false;
        rightPressed = false;
        rightPressed2 = false;
        firePressed1 = false;
        firePressed2 = false;
    }

    private void initEntities() {

        entities1.remove(ship1);
        entities2.remove(ship2);

        switch (value.getChangeShip()) {
            case 2: {
                ship1 = new ShipEntity(this, "sprites/character1.jpg", 188, 550);
                entities1.add(ship1);

            }
            break;
            case 3: {
                ship1 = new ShipEntity(this, "sprites/character2.png", 188, 550);
                entities1.add(ship1);
            }
            break;
            case 4: {
                ship1 = new ShipEntity(this, "sprites/character3.png", 188, 550);
                entities1.add(ship1);

            }
            break;

            default: {
                ship1 = new ShipEntity(this, "sprites/ship.gif", 188, 550);
                entities1.add(ship1);
            }
            break;
        }
        switch (value.getChangeShip2()) {

            case 2: {
                ship2 = new ShipEntity(this, "sprites/character1.jpg", 563, 550);
                entities2.add(ship2);

            }
            break;
            case 3: {
                ship2 = new ShipEntity(this, "sprites/character2.png", 563, 550);
                entities2.add(ship2);
            }
            break;
            case 4: {
                ship2 = new ShipEntity(this, "sprites/character3.png", 563, 550);
                entities2.add(ship2);

            }
            break;

            default: {
                ship2 = new ShipEntity(this, "sprites/ship.gif", 563, 550);
                entities2.add(ship2);
            }
            break;
        }

        alienCount1 = 0;
        alienCount2 = 0;
        for (int row = 0; row < 5; row++) {
            for (int x = 0; x < 6; x++) {
                Entity alien = new AlienEntity(this, 50 + (x * 50), (50) + row * 30);
                entities1.add(alien);
                alienCount1++;

                Entity alien2 = new AlienEntity(this, 450 + (x * 50), (50) + row * 30);
                entities2.add(alien2);
                alienCount2++;
            }
        }
    }

    public void updateLogic(int number) {
        if (number == 1) {
            logicRequiredThisLoop1 = true;
        } else if (number == 2) {
            logicRequiredThisLoop2 = true;
        }
    }

    public void removeEntity(Entity entity) {
        super.removeEntity(entity);
        removeList.add(entity);
    }

    public void notifyEnd() {
        waitingForKeyPress = true;
        message1 = "player 1 score: " + count1 + "    player 2 score: " + count2;

        if (count1 > count2) {
            message = "   player 1 win";
        } else if (count1 < count2) {
            message = "   player 2 win";
        } else {
            message = "   동점입니다";
        }

        message2  = "스페이스 바를 누르세요";

        killAlien1 = 0;
        firecount1 = 0;
        killAlien2 = 0;
        firecount2 = 0;
        count1 = 100;
        count2 = 100;

    }

    public void notifyAlienKilled(int number) {
        // reduce the alient count, if there are none left, the player has won!

        if (number == 1) {
            alienCount1--;
            killAlien1++;
            count1 += 20;
            gameSound.soundPlay("bgm/monster.wav", false);

            if (alienCount1 == 0) {
                notifyEnd();
            }

            for (int i = 0; i < entities1.size(); i++) {
                Entity entity1 = (Entity) entities1.get(i);
                if (entity1 instanceof AlienEntity) {
                    // speed up by 2%
                    entity1.setHorizontalMovement(entity1.getHorizontalMovement() * 1.02);
                }
            }
        } else if (number == 2) {
            alienCount2--;
            killAlien2++;
            count2 += 20;
            gameSound.soundPlay("bgm/monster.wav", false);

            if (alienCount2 == 0) {
                notifyEnd();
            }

            for (int j = 0; j < entities2.size(); j++) {
                Entity entity2 = (Entity) entities2.get(j);

                if (entity2 instanceof AlienEntity) {

                    entity2.setHorizontalMovement(entity2.getHorizontalMovement() * 1.02);
                }
            }
        }

    }

    public void tryToFire(int number) {

        if (number == 1) {

            if (System.currentTimeMillis() - lastFire1 < firingInterval) {
                return;
            }

            lastFire1 = System.currentTimeMillis();
            ShotEntity shot1 = new ShotEntity(this, "sprites/shot.gif", ship1.getX() + 10, ship1.getY() - 30);
            entities1.add(shot1);
            firecount1++;
            count1 -= 10;
        } else if (number == 2) {

            if (System.currentTimeMillis() - lastFire2 < firingInterval) {
                return;
            }

            lastFire2 = System.currentTimeMillis();

            ShotEntity shot2 = new ShotEntity(this, "sprites/shot.gif", ship2.getX() + 10, ship2.getY() - 30);
            entities2.add(shot2);
            firecount2++;
            count2 -= 10;

        }
        gameSound.soundPlay("bgm/fire.wav", false);
    }

    public void gameLoop() {
        message = "2인용 모드 START";
        message1 = "스페이스 바를 누르세요 ";
        long lastLoopTime = SystemTimer.getTime();

        gameSound.soundPlay("bgm/bg.wav", true);

        while (gameRunning) {

            long delta = SystemTimer.getTime() - lastLoopTime;
            lastLoopTime = SystemTimer.getTime();

            lastFpsTime += delta;
            fps++;
            sum = sum + delta;

            if (lastFpsTime >= 1000) {
                container.setTitle(windowTitle + " (FPS: " + fps + ")");
                lastFpsTime = 0;
                fps = 0;
            }

            //진행 상황 표시
            Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
            g.setFont(myFont1);
            g.setColor(Color.black);
            g.fillRect(0, 0, 800, 600);

            if (!waitingForKeyPress) {

                message = "킬카운트: " + killAlien1 + "        공격횟수" + firecount1 + "        점수" + count1;
                message1 = "킬카운트: " + killAlien2 + "        공격횟수" + firecount2 + "        점수" + count2;

                g.setColor(Color.white);
                g.drawString(message, (800 - g.getFontMetrics().stringWidth(message)) / 16, 40);
                g.drawString(message1, (1250 - g.getFontMetrics().stringWidth(message1)) / 2, 40);

                for (int i = 0; i < entities1.size(); i++) {
                    Entity entity1 = (Entity) entities1.get(i);

                    entity1.move(delta, 1);
                }

                if (!waitingForKeyPress) {
                    for (int i = 0; i < entities2.size(); i++) {
                        Entity entity2 = (Entity) entities2.get(i);

                        entity2.move(delta, 2);
                    }
                }
            }

            for (int i = 0; i < entities1.size(); i++) {
                Entity entity1 = (Entity) entities1.get(i);

                entity1.draw(g);
            }

            for (int i = 0; i < entities2.size(); i++) {
                Entity entity2 = (Entity) entities2.get(i);

                entity2.draw(g);
            }

            for (int p = 0; p < entities1.size(); p++) {
                for (int s = p + 1; s < entities1.size(); s++) {
                    Entity me = (Entity) entities1.get(p);
                    Entity him = (Entity) entities1.get(s);

                    if (me.collidesWith(him, 1)) {
                        me.collidedWith(him, 1);
                        him.collidedWith(me, 1);
                    }
                }
            }

            for (int p = 0; p < entities2.size(); p++) {
                for (int s = p + 1; s < entities2.size(); s++) {
                    Entity me = (Entity) entities2.get(p);
                    Entity him = (Entity) entities2.get(s);

                    if (me.collidesWith(him, 2)) {
                        me.collidedWith(him, 2);
                        him.collidedWith(me, 2);
                    }
                }
            }

            entities1.removeAll(removeList);
            entities2.removeAll(removeList);
            removeList.clear();

            if (logicRequiredThisLoop1) {
                for (int i = 0; i < entities1.size(); i++) {
                    Entity entity = (Entity) entities1.get(i);
                    entity.doLogic();
                }

                logicRequiredThisLoop1 = false;
            }

            if (logicRequiredThisLoop2) {
                for (int i = 0; i < entities2.size(); i++) {
                    Entity entity2 = (Entity) entities2.get(i);
                    entity2.doLogic();
                }

                logicRequiredThisLoop2 = false;
            }

            if (waitingForKeyPress) {
                g.setColor(Color.white);
                g.drawString(message, (800 - g.getFontMetrics().stringWidth(message)) / 2, 250);
                g.drawString(message1, (800 - g.getFontMetrics().stringWidth(message1)) / 2, 300);
                g.drawString(message2, (800 - g.getFontMetrics().stringWidth(message2)) / 2, 400);

            }

            g.dispose();
            strategy.show();

            ship1.setHorizontalMovement(0);
            ship2.setHorizontalMovement(0);

            if ((leftPressed) && (!rightPressed)) {
                ship1.setHorizontalMovement(-moveSpeed);
            } else if ((rightPressed) && (!leftPressed)) {
                ship1.setHorizontalMovement(moveSpeed);
            }

            if ((leftPressed2) && (!rightPressed2)) {
                ship2.setHorizontalMovement(-moveSpeed);
            } else if ((rightPressed2) && (!leftPressed2)) {
                ship2.setHorizontalMovement(moveSpeed);
            }

            if (firePressed1) {
                tryToFire(1);
            }

            if (firePressed2) {
                tryToFire(2);
            }

            SystemTimer.sleep(lastLoopTime + 10 - SystemTimer.getTime());
        }
    }

    private class KeyInputHandlerTwo extends KeyAdapter {
        /**
         * The number of key presses we've had while waiting for an "any key" press
         */
        private int pressCount = 1;

        public void keyPressed(KeyEvent e) {

            if (waitingForKeyPress) {
                return;
            }

            //사용자1은 a,d로 움직이고 s키로 공격
            //사용자2는 방향키로 움직이고 아래키로 공격

            if (e.getKeyCode() == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                firePressed1 = true;
            }

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed2 = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed2 = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                firePressed2 = true;
            }

        }


        public void keyReleased(KeyEvent e) {

            if (waitingForKeyPress) {
                return;
            }

            if (e.getKeyCode() == KeyEvent.VK_A) {
                leftPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                rightPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                firePressed1 = false;
            }

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed2 = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed2 = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                firePressed2 = false;
            }
        }

        public void keyTyped(KeyEvent e) {

            if (waitingForKeyPress) {
                if (pressCount == 1) {
                    waitingForKeyPress = false;
                    startGame();
                    pressCount = 0;
                } else {
                    pressCount++;
                }
            }

            // if we hit escape, then quit the game
            if (e.getKeyChar() == 27) {
                System.exit(0);
            }
        }
    }

    public static void main(String argv[]) {
        TwoPlayer t = new TwoPlayer();

        t.gameLoop();
    }
}
