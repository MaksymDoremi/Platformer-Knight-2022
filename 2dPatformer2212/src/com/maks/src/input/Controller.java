package com.maks.src.input;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;

import com.maks.src.Game;
import com.maks.src.objects.*;

public class Controller {

	// main array for storage enemies
	public List<Enemy> enemies = new LinkedList<Enemy>();

	// objects
	Random rnd;
	Enemy tempEnemy;
	User user;
	Game game;

	// boolean to prevent killing instantly
	public boolean hurted;

	public Controller() {
		rnd = new Random();

		// addEnemy(new LionEnemy(rnd.nextInt(5000) - 2500, 550, 1000));
		addEnemy(new DragonEnemy(rnd.nextInt(5000) - 2500, 402, 1000));

		// initialize boolean
		hurted = false;
	}

	public void update(User user, Game game) {
		// update all enemies(move, sprites)
		for (int i = 0; i < enemies.size(); i++) {
			tempEnemy = enemies.get(i);
			tempEnemy.update(user, game);
		}

		// create new enemies if there is not any
		if (enemies.size() == 0) {
			int a = 0;

			for (int i = 0; i < 2; i++) {
				a = rnd.nextInt(2);
				switch (a) {
				case 0:
					addEnemy(new LionEnemy(rnd.nextInt(5000) - 2500, 550, 1000));
					break;
				case 1:
					addEnemy(new DragonEnemy(rnd.nextInt(5000) - 2500, 402, 1000));
					break;

				}
			}

		}
	}

	public void hurtEnemy(User user) {
		// if user ended his attack, we can change boolean 
		//and be able to hurt enemies again
		if (!user.isAttack) {
			hurted = false;
		}
		
		//just for visual effect, when sword is down
		if (user.getSpriteNum() == 5) {
			//choose randomly userPower index
			int a = rnd.nextInt(user.powerSize());
			
			// to prevent instant kill
			if (!hurted) {
				// to check if user attacks
				if (user.isAttack) {
					for (int i = 0; i < enemies.size(); i++) {

						tempEnemy = enemies.get(i);
						// if user collides with enemy and attacks ==> hurt enemy
						if (user.rectSwordLeft.intersects(tempEnemy.mainRect)
								|| user.rectSwordRight.intersects(tempEnemy.mainRect)) {
							tempEnemy.setHealth(tempEnemy.getHealth() - user.getPower(a));

						}

					}

				}
				//prevent instant kill
				hurted = true;
			}
		}

	}
	
	//REMOVES ENEMY IF HEALTH IS <= 0
	public void killEnemy() {
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).getHealth() <= 0) {
				tempEnemy = enemies.get(i);
				removeEnemy(tempEnemy);
			}
		}
	}

	public void gameOver(User user, Graphics g, Timer timer) {

		if (user.getHealth() <= 0) {
			Game.gameOver = true;
			Graphics2D g2d = (Graphics2D) g;
			
			//game over message
			g2d.setFont(new Font("Algerian", Font.PLAIN, 60));
			g2d.setPaint(Color.white);
			g2d.drawString("You lost", 650, 600);
			
			//exit button
			Rectangle exitButton = new Rectangle(720, 300, 100, 50);
			g.setFont(new Font("Times New Roman", Font.BOLD, 36));
			g.setColor(Color.white);
			g.drawString("Exit", exitButton.x + 15, exitButton.y + 36);
			g2d.draw(exitButton);

		}
	}
	
	//mouse listener for EXIT button
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if (x >= 720 && x <= 820) {

			if (y >= 300 && y <= 350) {
				System.exit(1);
			}
		}
	}

	// DRAW ALL AENEMIES
	public void draw(Graphics g2d) {
		for (int i = 0; i < enemies.size(); i++) {
			tempEnemy = enemies.get(i);
			tempEnemy.draw(g2d);
		}
	}

	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}

	public void removeEnemy(Enemy enemy) {
		enemies.remove(enemy);
	}

}
