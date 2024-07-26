package org.newdawn.spaceinvaders.entity;

import org.newdawn.spaceinvaders.main.Game;
import org.newdawn.spaceinvaders.Sprite;
import org.newdawn.spaceinvaders.SpriteStore;
import org.newdawn.spaceinvaders.main.TwoPlayer;
import org.newdawn.spaceinvaders.stage.SettingValue;

/**
 * !!!!!!!!move,doLogic,collidesWith 함수 재정의!!!!!!!!!!!
 */

/**
 * An entity which represents one of our space invader aliens.
 * 
 * @author Kevin Glass
 */
public class AlienEntity extends Entity {
	/** The speed at which the alient moves horizontally */
	private double moveSpeed = 75;
	/** The game in which the entity exists */
	private Game game;
	/** The animation frames */
	private Sprite[] frames = new Sprite[4];
	/** The time since the last frame change took place */
	private long lastFrameChange;
	/** The frame duration in milliseconds, i.e. how long any given frame of animation lasts */
	private long frameDuration = 250;
	/** The current frame of animation being displayed */
	private int frameNumber;

	private TwoPlayer twoPlayer;
	/**
	 * Create a new alien entity
	 * 
	 * @param game The game in which this entity is being created
	 * @param x The intial x location of this alien
	 * @param y The intial y location of this alient
	 */
	public AlienEntity(Game game,int x,int y) {
		super("sprites/alien.gif",x,y);
		
		// setup the animatin frames
		frames[0] = sprite;
		frames[1] = SpriteStore.get().getSprite("sprites/alien2.gif");
		frames[2] = sprite;
		frames[3] = SpriteStore.get().getSprite("sprites/alien3.gif");
		
		this.game = game;
		dx = -moveSpeed;
	}
	public AlienEntity(TwoPlayer twoPlayer, int x, int y) {
		super("sprites/alien.gif",x,y);

		// setup the animatin frames
		frames[0] = sprite;
		frames[1] = SpriteStore.get().getSprite("sprites/alien2.gif");
		frames[2] = sprite;
		frames[3] = SpriteStore.get().getSprite("sprites/alien3.gif");

		this.twoPlayer=twoPlayer;
		dx = -moveSpeed;
	}

	/**
	 * Request that this alien moved based on time elapsed
	 * 
	 * @param delta The time that has elapsed since last move
	 */
	public void move(long delta) {
		// since the move tells us how much time has passed
		// by we can use it to drive the animation, however
		// its the not the prettiest solution
		lastFrameChange += delta;
		
		// if we need to change the frame, update the frame number
		// and flip over the sprite in use
		if (lastFrameChange > frameDuration) {
			// reset our frame change time counter
			lastFrameChange = 0;
			
			// update the frame
			frameNumber++;
			if (frameNumber >= frames.length) {
				frameNumber = 0;
			}
			
			sprite = frames[frameNumber];
		}
		
		// if we have reached the left hand side of the screen and
		// are moving left then request a logic update 
		if ((dx < 0) && (x < 10)) {
			game.updateLogic();
		}
		// and vice vesa, if we have reached the right hand side of 
		// the screen and are moving right, request a logic update
		if ((dx > 0) && (x > 750)) {
			game.updateLogic();
		}
		
		// proceed with normal move
		super.move(delta);
	}
	public void move(long delta, int player) {
		// since the move tells us how much time has passed
		// by we can use it to drive the animation, however
		// its the not the prettiest solution
		lastFrameChange += delta;

		// if we need to change the frame, update the frame number
		// and flip over the sprite in use
		if (lastFrameChange > frameDuration) {
			// reset our frame change time counter
			lastFrameChange = 0;

			// update the frame
			frameNumber++;
			if (frameNumber >= frames.length) {
				frameNumber = 0;
			}

			sprite = frames[frameNumber];
		}
		if(player==1)
		{
			if ((dx < 0) && (x < 10)) {
				twoPlayer.updateLogic(player);
			}
			// and vice vesa, if we have reached the right hand side of
			// the screen and are moving right, request a logic update
			if ((dx > 0) && (x > 350)) {
				twoPlayer.updateLogic(player);
			}
		}
		else if(player==2)
		{
			if ((dx < 0) && (x < 410)) {
				twoPlayer.updateLogic(player);
			}
			// and vice vesa, if we have reached the right hand side of
			// the screen and are moving right, request a logic update
			if ((dx > 0) && (x > 750)) {
				twoPlayer.updateLogic(player);
			}
		}
		// if we have reached the left hand side of the screen and
		// are moving left then request a logic update


		// proceed with normal move
		super.move(delta,player);
	}

	/**
	 * Update the game logic related to aliens
	 */
	public void doLogic(int currentLevel) {
		SettingValue level = new SettingValue();
		// swap over horizontal movement and move down the
		// screen a bit
		dx = -dx;
		//y += 10;
		y += level.getAlienY();

		// if we've reached the bottom of the screen then the player
		// dies
		if (y > 570) {
			game.notifyDeath();
		}
	}
	public void doLogic() {
		// swap over horizontal movement and move down the
		// screen a bit
		dx = -dx;
		y += 10;

		// if we've reached the bottom of the screen then the player
		// dies
		if (y > 570) {
			twoPlayer.notifyEnd();
		}
	}

	/**
	 * Notification that this alien has collided with another entity
	 * 
	 * @param other The other entity
	 */
	public void collidedWith(Entity other) {
		// collisions with aliens are handled elsewhere
	}
	public void collidedWith(Entity other,int player) {
		// collisions with aliens are handled elsewhere
	}

}
