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

public class DragonEnemy extends Enemy {
	
	int percentOfHealth;
	
	private String leftRunSpriteSourceFolder = "img/enemy/dragon/left/run";
	private String leftAttackSpriteSourceFolder = "img/enemy/dragon/left/attack";

	private String rightRunSpriteSourceFolder = "img/enemy/dragon/right/run";
	private String rightAttackSpriteSourceFolder = "img/enemy/dragon/right/attack";

	List<Image> leftRun = new LinkedList<Image>();
	List<Image> leftAttack = new LinkedList<Image>();
	List<Image> rightRun = new LinkedList<Image>();
	List<Image> rightAttack = new LinkedList<Image>();

	int spriteCounter = 0;
	int spriteNum = 0;

	String direction = "left";

	ImageIcon enemyIcon = new ImageIcon(getClass().getResource("/enemy/dragon/right/run/r (1).png"));
	
	// RECTANGLES
	Rectangle rightAttackRect = new Rectangle(x + 151 + 161, y + 196, 450 - 161 - 151 - 90, 143);
	Rectangle leftAttackRect = new Rectangle(x + 90, y + 196, 450 - 161 - 90, 143);
	
	Random rnd = new Random();
	
	LinkedList<Integer> dragonPower = new LinkedList<Integer>();
	
	boolean attacked = false;

	public DragonEnemy(int x, int y, int health) {
		super(x, y, health);
		// TODO Auto-generated constructor stub
		
		dragonPower.add(600);
		dragonPower.add(150);
		dragonPower.add(250);
		dragonPower.add(100);
		dragonPower.add(400);
		//rnd = new Random();
		Main.sortAndAddSprites(leftRunSpriteSourceFolder, leftRun);
		Main.sortAndAddSprites(leftAttackSpriteSourceFolder, leftAttack);
		Main.sortAndAddSprites(rightRunSpriteSourceFolder, rightRun);
		Main.sortAndAddSprites(rightAttackSpriteSourceFolder, rightAttack);

		isMove = true;
		isAttack = false;
	}

	@Override
	public void update(User user, Game game) {
		
		int possibleAttack  = rnd.nextInt(70)+1;
		mainRect = new Rectangle(x + 151, y + 196, 161, 143);
		rightAttackRect = new Rectangle(x + 151 + 161, y + 196, 450 - 161 - 151 - 90, 143);
		leftAttackRect = new Rectangle(x + 90, y + 196, 450 - 161 - 90, 143);
		

		if (isMove && !isAttack) {
			if (Game.allowToMove) {
				x += velX;
			} else {
				x += velX - user.getVelX();
			}
		}
		
		if (x > user.getX()) {
			direction = "left";
			if(leftAttackRect.intersects(user.mainRect)) {
				velX = 0;
				if(possibleAttack == 3 && !isAttack) {
					spriteNum = 0;
					spriteCounter = 0;
					isAttack = true;
					attacked = false;
					
				}
			}else {
				velX = -3;
			}
			
		} else if (x < user.getX()) {
			direction = "right";
			if(rightAttackRect.intersects(user.mainRect)) {
				velX = 0;
				if(possibleAttack == 3 && !isAttack) {
					spriteNum = 0;
					spriteCounter = 0;
					isAttack = true;
					attacked = false;
				}
			}else {
				velX = 3;
			}
			
		} 
		
		if(isAttack && !Game.allowToMove) {
			x -= user.getVelX();
		}
		
		if(isAttack && !attacked) {
			if((leftAttackRect.intersects(user.mainRect) || rightAttackRect.intersects(user.mainRect)) && spriteNum == 11) {
				user.setHealth(user.getHealth() - dragonPower.get(rnd.nextInt(dragonPower.size())));
				attacked = true;
			}
		}
		
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
		//g2d.drawRect(x + 151, y + 196, 161, 143);
		// left attack
		//g2d.drawRect(x, y + 196, 450 - 161, 143);
		// right attack
		//g2d.drawRect(x + 151 + 161, y + 196, 450 - 161 - 151, 143);
		
		g2d.setColor(Color.red);
		g2d.fillRect(x+151, y + 196 + 161, 100, 3);
		
		g2d.setColor(Color.green);
		g2d.fillRect(x+151, y + 196 + 161, 1 * percentOfHealth, 3);
	}

	public Image getEnemyImage() {
		return enemyIcon.getImage();
	}

	public void setX(int x) {
		this.x = x;
	}

}
