package org.newdawn.spaceinvaders.entity;

import org.newdawn.spaceinvaders.Sprite;
import org.newdawn.spaceinvaders.jdbcdb.GameInfo;
import org.newdawn.spaceinvaders.main.Game;
import org.newdawn.spaceinvaders.main.TwoPlayer;
import org.newdawn.spaceinvaders.stage.SettingValue;

import java.util.ArrayList;

public class MeteoEntity extends Entity {
    /**
     * The speed at which the alient moves horizontally
     */
    private double moveSpeed = 150;
    /**
     * The game in which the entity exists
     */
    private Game game;
    /**
     * The animation frames
     */
    private Sprite frames;
    /**
     * The time since the last frame change took place
     */
    private long lastFrameChange;
    /**
     * The frame duration in milliseconds, i.e. how long any given frame of animation lasts
     */
    private long frameDuration = 250;
    /**
     * The current frame of animation being displayed
     */
    private int frameNumber;

    private ArrayList entities = new ArrayList();


    private TwoPlayer twoPlayer;

    /**
     * Create a new alien entity
     *
     * @param game The game in which this entity is being created
     * @param x    The intial x location of this alien
     * @param y    The intial y location of this alient
     */
    public MeteoEntity(Game game, int x, int y) {
        super("sprites/meteo.png", x, y);

        // setup the animation frames
        frames = sprite;

        this.game = game;
        dx = 0; // 운석은 수평으로 이동하지 않으므로 dx 값을 0으로 설정
        dy = moveSpeed; // 운석이 아래로 떨어지도록 dy 값을 moveSpeed로 설정
    }

    /**
     * Request that this alien moved based on time elapsed
     *
     * @param delta The time that has elapsed since last move
     */

    public void move(long delta) {

        // 이전 운석 생성 시간으로부터 일정 시간이 경과하면 운석을 생성

        lastFrameChange += delta;

        y += dy * (delta / 1000.0) + (2 * GameInfo.getStage()); // 시간에 기반하여 운석의 y 위치 업데이트

        super.move(delta);
    }

    /**
     * Update the game logic related to aliens
     */
    /**
     * Notification that this alien has collided with another entity
     *
     * @param other The other entity
     */
    public void collidedWith(Entity other) {
        // collisions with aliens are handled elsewhere
        if (other instanceof ShipEntity) {
            game.notifyDeath();
        }
    }

    @Override
    public void collidedWith(Entity other, int player) {

    }

}