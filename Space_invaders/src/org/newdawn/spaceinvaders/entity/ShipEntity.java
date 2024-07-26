package org.newdawn.spaceinvaders.entity;

import org.newdawn.spaceinvaders.main.Game;
import org.newdawn.spaceinvaders.main.TwoPlayer;

/**
 * !!!!!!!!move,collidedWith 함수 재정의!!!!!!!!!!!
 */

/**
 * The entity that represents the players ship
 * 
 * @author Kevin Glass
 */
public class ShipEntity extends Entity {
	/** The game in which the ship exists */
	private Game game;
	private TwoPlayer twoPlayer;

	/**
	 * Create a new entity to represent the players ship
	 *
	 * @param game The game in which the ship is being created
	 * @param ref The reference to the sprite to show for the ship
	 * @param x The initial x location of the player's ship
	 * @param y The initial y location of the player's ship
	 */
	public ShipEntity(Game game,String ref,int x,int y) {
		super(ref,x,y);

		this.game = game;
	}

	public ShipEntity(TwoPlayer twoPlayer,String ref,int x,int y) {
		super(ref,x,y);

		this.twoPlayer = twoPlayer;
	}

	/**
	 * Request that the ship move itself based on an elapsed ammount of
	 * time
	 *
	 * @param delta The time that has elapsed since last move (ms)
	 */
	public void move(long delta) {
		// if we're moving left and have reached the left hand side
		// of the screen, don't move
		if ((dx < 0) && (x < 10)) {
			return;
		}
		// if we're moving right and have reached the right hand side
		// of the screen, don't move
		if ((dx > 0) && (x > 750)) {
			return;
		}

		super.move(delta);
	}

	public void move(long delta,int player) {
		// if we're moving left and have reached the left hand side
		// of the screen, don't move

		if(player==1) {
			if ((dx < 0) && (x < 10)) {
				return;
			}
			// if we're moving right and have reached the right hand side
			// of the screen, don't move
			if ((dx > 0) && (x > 375)) {
				return;
			}
		}
		else if(player==2){
			if ((dx <0) && (x <376)) {
				return;
			}
			// if we're moving right and have reached the right hand side
			// of the screen, don't move
			if ((dx >0) && (x > 750)) {
				return;
			}
		}

		super.move(delta,player);
	}

	/**
	 * Notification that the player's ship has collided with something
	 *
	 * @param other The entity with which the ship has collided
	 */
	public void collidedWith(Entity other) {
		// if its an alien, notify the game that the player
		// is dead
		if (other instanceof AlienEntity) {
			game.notifyDeath();
		}
	}
	public void collidedWith(Entity other, int player) {
		// if its an alien, notify the game that the player
		// is dead
		if (other instanceof AlienEntity) {
			twoPlayer.notifyEnd();
		}
	}

}