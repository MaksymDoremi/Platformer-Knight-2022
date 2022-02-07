package com.maks.src.objects;

import java.awt.Graphics;

import java.awt.Rectangle;

import com.maks.src.Game;
import com.maks.src.GlobalPosition;

public class Enemy extends GlobalPosition {

	// ENEMY DATA
	protected int health;
	protected int velX;

	// IMPORTANT BOOLEANS
	// if enemy attacks, then it can't move, it has to finish its attack
	protected boolean isAttack;
	protected boolean isMove;

	public Rectangle mainRect;

	public Enemy(int x, int y, int health) {
		super(x, y);
		this.setHealth(health);

	}

	public void draw(Graphics g2d) {

	}

	public void update(User user, Game game) {

	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;

	}

	public int getVelX() {
		return velX;
	}

}
