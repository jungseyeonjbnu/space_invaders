package org.newdawn.spaceinvaders.entity;

import org.newdawn.spaceinvaders.main.Game;
import org.newdawn.spaceinvaders.main.TwoPlayer;

/**
 * !!!!!!!!move, collidesWith 함수 재정의!!!!!!!!!!!
 */

/**
 * An entity representing a shot fired by the player's ship
 * 
 * @author Kevin Glass
 */
public class ShotEntity extends Entity {
	/** The vertical speed at which the players shot moves */
	private double moveSpeed = -300;
	/** The game in which this entity exists */
	private Game game;
	/** True if this shot has been "used", i.e. its hit something */

	private TwoPlayer twoPlayer;
	private boolean used = false;
	
	/**
	 * Create a new shot from the player
	 * 
	 * @param game The game in which the shot has been created
	 * @param sprite The sprite representing this shot
	 * @param x The initial x location of the shot
	 * @param y The initial y location of the shot
	 */
	public ShotEntity(Game game,String sprite,int x,int y) {
		super(sprite,x,y);
		
		this.game = game;
		
		dy = moveSpeed;
	}

	public ShotEntity(TwoPlayer twoPlayer,String sprite,int x,int y) {
		super(sprite,x,y);

		this.twoPlayer = twoPlayer;

		dy = moveSpeed;
	}

	/**
	 * Request that this shot moved based on time elapsed
	 * 
	 * @param delta The time that has elapsed since last move
	 */
	public void move(long delta) {
		// proceed with normal move
		super.move(delta);
		
		// if we shot off the screen, remove ourselfs
		if (y < -100) {
			game.removeEntity(this);
		}
	}

	public void move(long delta,int player) {
		// proceed with normal move
		super.move(delta,player);

		// if we shot off the screen, remove ourselfs
		if (y < -100) {
			twoPlayer.removeEntity(this);
		}
	}

	/**
	 * Notification that this shot has collided with another
	 * entity
	 * 
	 * @parma other The other entity with which we've collided
	 */
	public void collidedWith(Entity other) {
		// prevents double kills, if we've already hit something,
		// don't collide
		if (used) {
			return;
		}
		
		// if we've hit an alien, kill it!
		if (other instanceof AlienEntity) {
			// remove the affected entities
			game.removeEntity(this);
			game.removeEntity(other);
			
			// notify the game that the alien has been killed
			game.notifyAlienKilled();
			used = true;
		}
	}
	public void collidedWith(Entity other, int player) {
		// prevents double kills, if we've already hit something,
		// don't collide
		if (used) {
			return;
		}

		// if we've hit an alien, kill it!
		if (other instanceof AlienEntity) {
			// remove the affected entities
			twoPlayer.removeEntity(this);
			twoPlayer.removeEntity(other);

			// notify the game that the alien has been killed
			twoPlayer.notifyAlienKilled(player);
			used = true;
		}
	}

}