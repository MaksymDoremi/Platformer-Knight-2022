package com.maks.src.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import com.maks.src.Game;
import com.maks.src.Main;

public class LionEnemy extends Enemy {
	
	// WORKS THE SAME WAY WITH DRAGON(but dragon has bigger power)

	int percentOfHealth;

	// source folders
	private String leftRunSpriteSourceFolder = "img/enemy/lion/left/run";
	private String leftAttackSpriteSourceFolder = "img/enemy/lion/left/attack";

	private String rightRunSpriteSourceFolder = "img/enemy/lion/right/run";
	private String rightAttackSpriteSourceFolder = "img/enemy/lion/right/attack";

	// list with sprites
	List<Image> leftRun = new LinkedList<Image>();
	List<Image> leftAttack = new LinkedList<Image>();
	List<Image> rightRun = new LinkedList<Image>();
	List<Image> rightAttack = new LinkedList<Image>();

	int spriteCounter = 0;
	int spriteNum = 0;

	// default direction
	String direction = "left";

	// getResource vyzaduje /enemy/a td, nesmim uvadet materskou slozku img/
	ImageIcon enemyIcon = new ImageIcon(getClass().getResource("/enemy/lion/left/stand/s (1).png"));
	// collision rectangles
	Rectangle leftAttackRect = new Rectangle(x, y + 45, 64, 145);
	Rectangle rightAttackRect = new Rectangle(x + 54 + 126, y + 45, 240 - 126 - 50, 145);

	Random rnd = new Random();

	LinkedList<Integer> lionPower = new LinkedList<Integer>();

	boolean attacked = false;

	public LionEnemy(int x, int y, int health) {
		super(x, y, health);

		// add power, it will harm randomly
		lionPower.add(100);
		lionPower.add(70);
		lionPower.add(200);
		lionPower.add(250);
		lionPower.add(50);
		// sort png files
		Main.sortAndAddSprites(leftRunSpriteSourceFolder, leftRun);
		Main.sortAndAddSprites(leftAttackSpriteSourceFolder, leftAttack);
		Main.sortAndAddSprites(rightRunSpriteSourceFolder, rightRun);
		Main.sortAndAddSprites(rightAttackSpriteSourceFolder, rightAttack);

		// set booleans to right value
		isMove = true;
		isAttack = false;
	}

	@Override
	public void update(User user, Game game) {

		// chance to attack
		int possibleAttack = rnd.nextInt(70) + 1;

		// collision rectangles
		mainRect = new Rectangle(x + 54, y + 45, 126, 145);
		leftAttackRect = new Rectangle(x + 30, y + 45, 64 - 30, 145);
		rightAttackRect = new Rectangle(x + 54 + 126 + 10, y + 45, 240 - 126 - 50 - 30, 145);

		// move logic
		// can't move if attacks
		if (isMove && !isAttack) {
			if (Game.allowToMove) {
				x += velX;
			} else {
				// if moves background so slow down enemies
				x += velX - user.getVelX();
			}
		}

		// POSSIBLE ATTACK
		if (x > user.getX()) {
			direction = "left";
			if (leftAttackRect.intersects(user.mainRect)) {
				velX = 0;
				if (possibleAttack == 3 && !isAttack) {
					spriteNum = 0;
					spriteCounter = 0;
					isAttack = true;
					attacked = false;
				}
			} else {
				velX = -2;
			}

		} else if (x < user.getX()) {
			direction = "right";
			if (rightAttackRect.intersects(user.mainRect)) {
				velX = 0;
				if (possibleAttack == 3 && !isAttack) {
					spriteNum = 0;
					spriteCounter = 0;
					isAttack = true;
					attacked = false;
				}
			} else {
				velX = 2;
			}

		}

		if (isAttack && !Game.allowToMove) {
			x -= user.getVelX();
		}

		// harm user
		if (isAttack && !attacked) {
			if ((leftAttackRect.intersects(user.mainRect) || rightAttackRect.intersects(user.mainRect))
					&& spriteNum == 9) {
				user.setHealth(user.getHealth() - lionPower.get(rnd.nextInt(lionPower.size())));
				attacked = true;
			}
		}

		// RUN AND STAND SPRITES
		if (direction.equals("left") && !isAttack) {
			if (isMove) {
				spriteCounter++;

				if (spriteCounter > 5) {
					enemyIcon = new ImageIcon(leftRun.get(spriteNum));
					spriteNum++;

					if (spriteNum > leftRun.size() - 1) {
						spriteNum = 0;
					}
					spriteCounter = 0;
				}
			}

		}

		if (direction.equals("right") && !isAttack) {
			if (isMove) {
				spriteCounter++;

				if (spriteCounter > 5) {
					enemyIcon = new ImageIcon(rightRun.get(spriteNum));
					spriteNum++;

					if (spriteNum > rightRun.size() - 1) {
						spriteNum = 0;
					}
					spriteCounter = 0;
				}
			}

		}

		if (isAttack && direction.equals("left")) {
			spriteCounter++;
			isMove = false;

			if (spriteCounter > 5) {
				enemyIcon = new ImageIcon(leftAttack.get(spriteNum));
				spriteNum++;
				isAttack = true;
				if (spriteNum > leftAttack.size() - 1) {
					isAttack = false;
					// isMove = false;
					spriteNum = 0;
				}
				spriteCounter = 0;
			}
		}

		if (isAttack && direction.equals("right")) {
			spriteCounter++;
			isMove = false;
			if (spriteCounter > 5) {
				enemyIcon = new ImageIcon(rightAttack.get(spriteNum));
				spriteNum++;
				isAttack = true;
				if (spriteNum > leftAttack.size() - 1) {
					isAttack = false;
					// isMove = false;
					spriteNum = 0;
				}
				spriteCounter = 0;
			}
		}

		isMove = true;

	}

	@Override
	public void draw(Graphics g2d) {
		percentOfHealth = 100 * getHealth() / 1000;
		enemyIcon = new ImageIcon(getEnemyImage());

		g2d.drawImage(getEnemyImage(), super.x, super.y, null);
		// main body
		// g2d.drawRect(x + 54, y + 45, 126, 145);
		// right attack
		// g2d.drawRect(x + 54 + 126 + 10, y + 45, 240 - 126 - 50 - 30, 145);
		// left attack
		// g2d.drawRect(x + 30, y + 45, 64 - 30, 145);

		// red health point line
		g2d.setColor(Color.red);
		g2d.fillRect(x + 54, y + 60 + 126, 100, 3);
		// green health point line
		g2d.setColor(Color.green);
		g2d.fillRect(x + 54, y + 60 + 126, 1 * percentOfHealth, 3);
	}

	public Image getEnemyImage() {
		return enemyIcon.getImage();
	}

	public void setX(int x) {
		this.x = x;
	}

}
