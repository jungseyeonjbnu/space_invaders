package org.newdawn.spaceinvaders.main;

import org.newdawn.spaceinvaders.Sound;
import org.newdawn.spaceinvaders.SystemTimer;
import org.newdawn.spaceinvaders.entity.AlienEntity;
import org.newdawn.spaceinvaders.entity.MeteoEntity;
import org.newdawn.spaceinvaders.entity.Entity;
import org.newdawn.spaceinvaders.entity.ShipEntity;
import org.newdawn.spaceinvaders.entity.ShotEntity;
import org.newdawn.spaceinvaders.jdbcdb.ConnectDB;
import org.newdawn.spaceinvaders.jdbcdb.GameInfo;
import org.newdawn.spaceinvaders.login.challenge.Challenge;
import org.newdawn.spaceinvaders.login.Member;
import org.newdawn.spaceinvaders.stage.SettingValue;
import org.newdawn.spaceinvaders.stage.shop.Coin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The main hook of our game. This class with both act as a manager
 * for the display and central mediator for the game logic.
 * <p>
 * Display management will consist of a loop that cycles round all
 * entities in the game asking them to move and then drawing them
 * in the appropriate place. With the help of an inner class it
 * will also allow the player to control the main ship.
 * <p>
 * As a mediator it will be informed when entities within our game
 * detect events (e.g. alient killed, played died) and will take
 * appropriate game actions.
 *
 * @author Kevin Glass
 */
public class Game extends Canvas {
    SettingValue value = new SettingValue();
    /**
     * The stragey that allows us to use accelerate page flipping
     */
    protected BufferStrategy strategy;
    /**
     * True if the game is currently "running", i.e. the game loop is looping
     */
    protected boolean gameRunning = true;
    /**
     * The list of all the entities that exist in our game
     */
    private ArrayList entities = new ArrayList();
    /**
     * The list of entities that need to be removed from the game this loop
     */
    protected ArrayList removeList = new ArrayList();
    /**
     * The entity representing the player
     */
    private Entity ship;
    /**
     * The speed at which the player's ship should move (pixels/sec)
     */
    private Entity ship1;
    protected double moveSpeed = 300;
    /**
     * The time at which last fired a shot
     */
    protected long lastFire = 0;
    /** The interval between our players shot (ms) */

    /**
     * The number of aliens left on the screen
     */
    private int alienCount;

    /**
     * The message to display which waiting for a key press
     */
    protected String message = "";
    protected String message1 = "";
    /**
     * True if we're holding up game play until a key has been pressed
     */
    protected boolean waitingForKeyPress = true;
    /**
     * True if the left cursor key is currently pressed
     */
    private boolean leftPressed = false;
    /**
     * True if the right cursor key is currently pressed
     */
    private boolean rightPressed = false;
    /**
     * True if we are firing
     */
    private boolean firePressed = false;

    /**
     * True if game logic needs to be applied this loop, normally as a result of a game event
     */
    private boolean logicRequiredThisLoop = false;
    /**
     * The last time at which we recorded the frame rate
     */
    protected long lastFpsTime;
    /**
     * The current number of frames recorded
     */
    protected int fps;
    /**
     * The normal title of the game window
     */
    protected String windowTitle = "Space Invaders 102";
    /**
     * The game window that we'll update with the frame count
     */
    protected JFrame container;
    private int shotInterval;

    //ConnectDB 인스턴스
    ConnectDB db = new ConnectDB();

    GameInfo info = new GameInfo();
    private int currentLevel = info.getStage();
    protected Sound gameSound = new Sound();
    Coin coin = new Coin();
    Member member = new Member();

    Challenge c = new Challenge();

    Random random = new Random();

    long currentTime = System.currentTimeMillis();

    private Timer meteoTimer;
    private MeteoTask meteoTask;
    private int meteoInitialDelay = 1000;
    Font myFont1 = new Font("둥근모꼴", Font.PLAIN, 13);


    /**
     * Construct our game and set it running.
     */
    public Game() {
        // create a frame to contain our game
        container = new JFrame("Space Invaders 102");

        // get hold the content of the frame and set up the resolution of the game
        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(800, 600));
        panel.setLayout(null);

        // setup our canvas size and put it into the content of the frame
        setBounds(0, 0, 800, 600);

        panel.add(this);

        // Tell AWT not to bother repainting our canvas since we're
        // going to do that our self in accelerated mode
        setIgnoreRepaint(true);

        // finally make the window visible
        container.pack();
        container.setResizable(false);
        container.setVisible(true);
        container.setLocationRelativeTo(null);

        // add a listener to respond to the user closing the window. If they
        // do we'd like to exit the game
        container.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // add a key input system (defined below) to our canvas
        // so we can respond to key pressed
        addKeyListener(new KeyInputHandler());

        // request the focus so key events come to us
        requestFocus();

        // create the buffering strategy which will allow AWT
        // to manage our accelerated graphics
        createBufferStrategy(2);
        strategy = getBufferStrategy();

        // initialise the entities in our game so there's something
        // to see at startup
        initEntities();
    }

    /**
     * Start a fresh game, this should clear out any old data and
     * create a new set.
     */
    private void startGame() {
        // clear out any existing entities and intialise a new set
        entities.clear();
        initEntities();

        // blank out any keyboard settings we might currently have
        leftPressed = false;
        rightPressed = false;
        firePressed = false;
    }

    /**
     * Initialise the starting state of the entities (ship and aliens). Each
     * entitiy will be added to the overall list of entities in the game.
     */
    private void initEntities() {
        // create the player ship and place it roughly in the center of the screen
        entities.remove(ship);

        switch (value.getChangeShip()) {

            case 2: {
                ship = new ShipEntity(this, "sprites/character1.jpg", 370, 550);
                shotInterval = 800;
                entities.add(ship);
            }
            break;
            case 3: {
                ship = new ShipEntity(this, "sprites/character2.png", 370, 550);
                shotInterval = 450;
                entities.add(ship);
            }
            break;
            case 4: {
                ship = new ShipEntity(this, "sprites/character3.png", 370, 550);
                shotInterval = 900;
                entities.add(ship);
            }
            break;

            default: {
                ship = new ShipEntity(this, "sprites/ship.gif", 370, 550);
                shotInterval = 500;
                entities.add(ship);
            }
            break;
        }

        // create a block of aliens (5 rows, by 12 aliens, spaced evenly)
        alienCount = 0;
        //매 스테이지마다 적의 수 변경
        for (int row = 0; row < (currentLevel + 1); row++) {
            for (int x = 0; x < 12; x++) {
                Entity alien = new AlienEntity(this, 100 + (x * 50), (50) + row * 30);
                entities.add(alien);
                alienCount++;
            }
        }

        // Add the initial MeteoEntity object
        if (meteoTimer == null) {
            meteoTimer = new Timer();
            meteoTask = new MeteoTask();
            meteoTimer.schedule(meteoTask, 2000, meteoInitialDelay);
        }
    }

    private void addMeteoEntity() {
        Entity meteo = new MeteoEntity(this, random.nextInt(601) + 100, 0);
        entities.add(meteo);
    }

    private class MeteoTask extends TimerTask {
        @Override
        public void run() {
            addMeteoEntity();
        }
    }

    /**
     * Notification from a game entity that the logic of the game
     * should be run at the next opportunity (normally as a result of some
     * game event)
     */
    public void updateLogic() {
        logicRequiredThisLoop = true;
    }

    /**
     * Remove an entity from the game. The entity removed will
     * no longer move or be drawn.
     *
     * @param entity The entity that should be removed
     */
    public void removeEntity(Entity entity) {
        removeList.add(entity);
    }

    /**
     * Notification that the player has died.
     */
    public void notifyDeath() {
        while (!waitingForKeyPress) {
            save();
        }
        message = "Oh no! They got you, try again?";
        waitingForKeyPress = true;

        //구매한 능력치 초기화
        SettingValue.setChangeInterval(1);
        SettingValue.setSlowInvaderSpeed(1);

        System.out.printf("시간: %d 스테이지 : %d 킬카운트 : %d 코인 : %d 이름 : %s 비밀번호 : %s \n",
                info.getPlayTime(), info.getStage(),
                info.getKillCount(), coin.getCoin(),
                member.getLoginName(), member.getLoginPassword());


        message = "게임이 끝났습니다! 당신의 기록을 확인하세요!";
        gameRunning = false;
        int best = db.showBestRecord();

        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame,
                "게임이 끝났습니다! " + member.getLoginName() + "님의 기록을 확인하세요! \n" +
                        "\n" +
                        "총 플레이 시간: " + info.getPlayTime() + " 스테이지 : " + info.getStage()
                        + " 킬카운트 : " + info.getKillCount() +
                        "\n  점수 : " + info.getScore() + " 최고점수 : " + best);

        container.dispose();
        new Menu();
        setVisible(false);
    }

    /**
     * Notification that the player has won since all the aliens
     * are dead.
     */
    /**
     * Notification that an alien has been killed
     */
	 /*
    하는 거 많은 코드
    현재 스테이지 레벨이 5 이상이 되면 게임루프를 벗어난 후, db에 플레이기록을 저장
    -> 그후 현재까지 플레이한 기록을 새창으로 제공
    스테이지 레벨이 5 이하일 경우, 총 플레이시간을 더한 후 다음 스테이지 정보를 보여줌

     */
    public void notifyWin() {
        message = "Well done! You Win!";
        save();
    }

    public void save() {
        sumTime = (sum / 1000) + sumTime;
        info.setPlayTime((int) sumTime);
        info.setKillCount(killAlien);

        int score = (info.getKillCount() * currentLevel) * 1000 / info.getPlayTime();
        info.setScore(score + coin.getCoin());
        System.out.println("score = " + score);

        if (currentLevel == 1) {
            db.setConnection();
            db.insertResult();
            db.currentRecord();
        } else {
            db.setConnection();
            db.updateResult();
            db.currentRecord();
        }
        c.isSatisfy((currentLevel + 1), info.getPlayTime());
        db.insertChallenge();   //도전과제 관련 로직

        System.out.printf("시간: %d 스테이지 : %d 킬카운트 : %d 코인 : %d 이름 : %s 비밀번호 : %s \n",
                info.getPlayTime(), info.getStage(),
                info.getKillCount(), coin.getCoin(),
                member.getLoginName(), member.getLoginPassword());
        message = "next stage = " + (currentLevel + 1) + " 플레이시간: " + sumTime;
        waitingForKeyPress = true;
    }

    /**
     * Notification that an alien has been killed
     */

    int killAlien = info.getKillCount();

    public void notifyAlienKilled() {
        // reduce the alient count, if there are none left, the player has won!
        alienCount--;
        killAlien++;
        //gameSound.soundPlay("bgm/monster.wav",false);

        if (random.nextInt(6) == 1) {
            coin.setCoin(Coin.getCoin() + 1);
        }

        //스테이지가 끝나면 다음 스테이지를 보여줌
        //notifyWin() 함수로 현재까지 죽인 적의 수와 플레이 시간을 넘김
        if (alienCount == 0) {
            waitingForKeyPress = true;

            //스테이지 레벨 증가로직
            info.setStage(currentLevel + 1);
            currentLevel = info.getStage();

            //적 하강 속도 변환 로직
            // 스테이지가 증가할때마다 속도 5씩 증가, 적 속도 감소 아이템을 구매하는 만큼 속도 감소
            float i = ((5 * info.getStage()) + 10) * value.getSlowInvaderSpeed();
            value.setAlienY(i);

            System.out.println("스테이지 레벨 증가. 현재 레벨: " + (currentLevel + 1) + " 적 속도 증가 : " + value.getAlienY());
            notifyWin();

        }

        // if there are still some aliens left then they all need to get faster, so
        // speed up all the existing aliens
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = (Entity) entities.get(i);

            if (entity instanceof AlienEntity) {
                // speed up by 2%
                entity.setHorizontalMovement(entity.getHorizontalMovement() * 1.02);
            }
        }
    }


    /**
     * Attempt to fire a shot from the player. Its called "try"
     * since we must first check that the player can fire at this
     * point, i.e. has he/she waited long enough between shots
     */
    public void tryToFire() {

        //사격 발사속도 관련
        if (System.currentTimeMillis() - lastFire < (int) (shotInterval * value.getChangeInterval())) {
            return;
        }
        lastFire = System.currentTimeMillis();
        // if we waited long enough, create the shot entity, and record the time.
        //fireShape(value.getChangeShip());
        fireShape();
    }

    /*
    n=1 : 기본
    n=2 : 산탄, 3발
    n=3 : 양 옆에서 번갈아가면서 총알
    n=4 : 2점사
     */
    private int i;  //번갈아가면서 총쏠때 쓰는거

    public void fireShape() {

        switch (value.getChangeShip()) {

            case 2: {
                ShotEntity shot2 = new ShotEntity(this, "sprites/shot.gif", ship.getX() - 20, ship.getY() - 30);
                entities.add(shot2);
                ShotEntity shot = new ShotEntity(this, "sprites/shot.gif", ship.getX() + 10, ship.getY() - 40);
                entities.add(shot);
                ShotEntity shot1 = new ShotEntity(this, "sprites/shot.gif", ship.getX() + 40, ship.getY() - 30);
                entities.add(shot1);
            }
            break;
            case 3: {
                if (i == 1) {
                    ShotEntity shot = new ShotEntity(this, "sprites/shot.gif", ship.getX() + 35, ship.getY() - 30);
                    entities.add(shot);
                    i = 0;
                } else {
                    ShotEntity shot = new ShotEntity(this, "sprites/shot.gif", ship.getX() - 15, ship.getY() - 30);
                    entities.add(shot);
                    i = 1;
                }
            }
            break;
            case 4: {
                ShotEntity shot = new ShotEntity(this, "sprites/shot.gif", ship.getX() + 10, ship.getY() - 30);
                entities.add(shot);
                ShotEntity shot1 = new ShotEntity(this, "sprites/shot.gif", ship.getX() + 10, ship.getY() - 50);
                entities.add(shot1);
            }
            break;
            default: {
                ShotEntity shot = new ShotEntity(this, "sprites/shot.gif", ship.getX() + 10, ship.getY() - 30);
                entities.add(shot);
            }
            break;
        }
    }


    /**
     * The main game loop. This loop is running during all game
     * play as is responsible for the following activities:
     * <p>
     * - Working out the speed of the game loop to update moves
     * - Moving the game entities
     * - Drawing the screen contents (entities, text)
     * - Updating game events
     * - Checking Input
     * <p>
     */
    long sum = 0;
    long sumTime = info.getPlayTime();
    int a = 0;

    public void gameLoop() {
        long lastLoopTime = SystemTimer.getTime();
        //gameSound.soundPlay("bgm/bg.wav", true);

        // keep looping round til the game ends

        while (gameRunning) {
            // work out how long its been since the last update, this
            // will be used to calculate how far the entities should
            // move this loop
            long delta = SystemTimer.getTime() - lastLoopTime;
            lastLoopTime = SystemTimer.getTime();

            // update the frame counter
            lastFpsTime += delta;
            fps++;

            sum = sum + delta;
            // update our FPS counter if a second has passed since
            // we last recorded
            if (lastFpsTime >= 1000) {
                container.setTitle(windowTitle + " (FPS: " + fps + ")");
                lastFpsTime = 0;
                fps = 0;
            }

            // Get hold of a graphics context for the accelerated
            // surface and blank it out
            Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
            g.setFont(myFont1);
            g.setColor(Color.black);
            g.fillRect(0, 0, 800, 600);

            // cycle round asking each entity to move itself
            if (!waitingForKeyPress) {

                //현재 정보 표시 코드
                message = "킬카운트: " + killAlien + "    스테이지: " + (currentLevel + 1)
                        + "    현재 시간: " + ((int) sum / 1000) + "초" + "    coin: " + coin.getCoin();
                String tip = "팁 : " + member.getLoginName() + "님, 게임 중 언제든 'm'키를 눌러 메뉴로 나갈 수 있습니다.";

                /*
                도전과제 출력 로직
                조건 만족시 오른쪽 상단에 3초간 출력 -> 안되는 중
                 */
                String messageName = "";
                if (c.isSatisfy(killAlien) != null) {
                    a = 0;

                }
                a += delta;
                if ((a / 1000) < 3) {
                    messageName = c.isSatisfy(killAlien);
                    g.setColor(Color.white);
                    g.drawString(messageName, 500, 60);
                }

                g.setColor(Color.white);
                g.drawString(message, (800 - g.getFontMetrics().stringWidth(message)) / 16, 40);    //아무 버튼 누르기
                g.drawString(tip, (800 - g.getFontMetrics().stringWidth(tip)) / 14, 60);    // m 누르는 그거
                //g.drawString(messageName, 500, 60);     //도전과제 출력 로직
                for (int i = 0; i < entities.size(); i++) {
                    Entity entity = (Entity) entities.get(i);

                    entity.move(delta);
                }
            }

            // cycle round drawing all the entities we have in the game
            for (int i = 0; i < entities.size(); i++) {
                Entity entity = (Entity) entities.get(i);

                entity.draw(g);
            }

            // brute force collisions, compare every entity against
            // every other entity. If any of them collide notify
            // both entities that the collision has occured
            for (int p = 0; p < entities.size(); p++) {
                for (int s = p + 1; s < entities.size(); s++) {
                    Entity me = (Entity) entities.get(p);
                    Entity him = (Entity) entities.get(s);

                    if (me.collidesWith(him)) {
                        me.collidedWith(him);
                        him.collidedWith(me);
                    }
                }
            }

            // remove any entity that has been marked for clear up
            entities.removeAll(removeList);
            removeList.clear();

            // if a game event has indicated that game logic should
            // be resolved, cycle round every entity requesting that
            // their personal logic should be considered.
            if (logicRequiredThisLoop) {
                for (int i = 0; i < entities.size(); i++) {
                    Entity entity = (Entity) entities.get(i);
                    entity.doLogic(currentLevel);
                }

                logicRequiredThisLoop = false;
            }

            // if we're waiting for an "any key" press then draw the
            // current message
            if (waitingForKeyPress) {
                g.setColor(Color.white);
                g.drawString(message, (800 - g.getFontMetrics().stringWidth(message)) / 2, 250);

                //메시지 깜박임
                a += delta;
                if ((a / 1000) % 2 == 0) {
                    g.drawString("Press any key", (800 - g.getFontMetrics().stringWidth("Press any key")) / 2, 300);
                } else {
                    g.drawString("", (800 - g.getFontMetrics().stringWidth("")) / 2, 300);
                }


                //두번째 메시지 사용할때 쓰는거
                message1 = "팁 : 게임이 너무 어려울 땐 상점에서 아이템을 구매하거나, 캐릭터를 변경해 보세요.";
                g.setColor(Color.white);
                g.drawString(message1, (800 - g.getFontMetrics().stringWidth(message1)) / 2, 350);


                //도전과제 표시 메시지
                String message2 = c.isSatisfy((currentLevel + 1), info.getPlayTime());

                g.setColor(Color.white);
                g.drawString(message2, (800 - g.getFontMetrics().stringWidth(message2)) / 2, 450);

                //게임 시작시 카운트 0으로 세팅
                sum = sumTime;

            }

            // finally, we've completed drawing so clear up the graphics
            // and flip the buffer over
            g.dispose();
            strategy.show();

            // resolve the movement of the ship. First assume the ship
            // isn't moving. If either cursor key is pressed then
            // update the movement appropraitely
            ship.setHorizontalMovement(0);

            if ((leftPressed) && (!rightPressed)) {
                ship.setHorizontalMovement(-moveSpeed);
            } else if ((rightPressed) && (!leftPressed)) {
                ship.setHorizontalMovement(moveSpeed);
            }

            // if we're pressing fire, attempt to fire
            if (firePressed) {
                tryToFire();
            }

            // we want each frame to take 10 milliseconds, to do this
            // we've recorded when we started the frame. We add 10 milliseconds
            // to this and then factor in the current time to give
            // us our final value to wait for
            SystemTimer.sleep(lastLoopTime + 10 - SystemTimer.getTime());
        }
    }

    /**
     * A class to handle keyboard input from the user. The class
     * handles both dynamic input during game play, i.e. left/right
     * and shoot, and more static type input (i.e. press any key to
     * continue)
     * <p>
     * This has been implemented as an inner class more through
     * habbit then anything else. Its perfectly normal to implement
     * this as seperate class if slight less convienient.
     *
     * @author Kevin Glass
     */
    protected class KeyInputHandler extends KeyAdapter {
        /**
         * The number of key presses we've had while waiting for an "any key" press
         */
        private int pressCount = 1;

        /**
         * Notification from AWT that a key has been pressed. Note that
         * a key being pressed is equal to being pushed down but *NOT*
         * released. Thats where keyTyped() comes in.
         *
         * @param e The details of the key that was pressed
         */
        public void keyPressed(KeyEvent e) {
            // if we're waiting for an "any key" typed then we don't
            // want to do anything with just a "press"
            if (waitingForKeyPress) {
                return;
            }


            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                firePressed = true;
            }

            if (e.getKeyCode() == KeyEvent.VK_M) {
                System.out.println("\n m 눌림");
                waitingForKeyPress = true;
                container.dispose();
                new Menu();
                setVisible(false);
            }

        }

        /**
         * Notification from AWT that a key has been released.
         *
         * @param e The details of the key that was released
         */
        public void keyReleased(KeyEvent e) {
            // if we're waiting for an "any key" typed then we don't
            // want to do anything with just a "released"
            if (waitingForKeyPress) {
                return;
            }

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                firePressed = false;
            }

        }

        /**
         * Notification from AWT that a key has been typed. Note that
         * typing a key means to both press and then release it.
         *
         * @param e The details of the key that was typed.
         */
        public void keyTyped(KeyEvent e) {
            // if we're waiting for a "any key" type then
            // check if we've recieved any recently. We may
            // have had a keyType() event from the user releasing
            // the shoot or move keys, hence the use of the "pressCount"
            // counter.
            if (waitingForKeyPress) {
                if (pressCount == 1) {
                    // since we've now recieved our key typed
                    // event we can mark it as such and start
                    // our new game
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

    /**
     * The entry point into the game. We'll simply create an
     * instance of class which will start the display and game
     * loop.
     *
     * @param argv The arguments that are passed into our game
     */

    public static void main(String argv[]) {
        Game g = new Game();

        //db 연결 코드
        ConnectDB db = new ConnectDB();
        db.setConnection();

        // Start the main game loop, note: this method will not
        // return until the game has finished running. Hence we are
        // using the actual main thread to run the game.
        g.gameLoop();
    }


}
