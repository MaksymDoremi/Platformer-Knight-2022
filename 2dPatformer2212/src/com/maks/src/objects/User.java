package com.maks.src.objects;

import com.maks.src.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;

import com.maks.src.GlobalPosition;
import com.maks.src.input.Controller;

public class User extends GlobalPosition {

	// USER DATA
	private int health;
	private LinkedList<Integer> power = new LinkedList<Integer>();
	private int percentOfHealth;

	// VELOCITY IS ZERO
	private int velX = 0;

	// IMPORTANT BOOLEANS
	// if user attacks, then user can't move, it has to finish its attack
	public boolean isAttack = false;
	// boolean for checking if user moves, for changing sprites(run, stand sprites)
	public boolean isMove = false;

	// DIRECTION STRING
	String direction = "left";

	// SOURCE FOLDERS FOR SPRITES
	// zmenil jsem url do vsech sourceFolders, aby to fungovalo
	// ma to vyhled takto: materska/a td, teda img/a td
	private String leftStandSpriteSourceFolder = "img/user/left/stand";
	private String leftRunSpriteSourceFolder = "img/user/left/run";
	private String leftAttackSpriteSourceFolder = "img/user/left/attack";

	private String rightStandSpriteSourceFolder = "img/user/right/stand";
	private String rightRunSpriteSourceFolder = "img/user/right/run";
	private String rightAttackSpriteSourceFolder = "img/user/right/attack";

	// USER ARRAYS WITH SPRITES
	private List<Image> leftStand = new LinkedList<Image>();
	private List<Image> leftRun = new LinkedList<Image>();
	private List<Image> leftAttack = new LinkedList<Image>();

	private List<Image> rightStand = new LinkedList<Image>();
	private List<Image> rightRun = new LinkedList<Image>();
	private List<Image> rightAttack = new LinkedList<Image>();

	// integers for sprite changing
	int spriteCounter = 0;
	int spriteNum = 0;

	// USER ICON
	// getResource vyzaduje /enemy/a td, nesmim uvadet materskou slozku img/
	private ImageIcon userIcon = new ImageIcon(getClass().getResource("/user/left/stand/s (1).png"));

	// RECTANGLES FOR COLLISIONS
	public Rectangle rectSwordLeft = new Rectangle(x, y + 100, 87, 125);
	public Rectangle rectSwordRight = new Rectangle(x + 167, y + 100, 87, 125);
	public Rectangle mainRect = new Rectangle(x + 87, y + 100, 80, 125);

	Controller c;

	public User(int x, int y, int health) {
		super(x, y);
		this.setHealth(health);
		// add power, user will harm enemies with random power
		power.add(100);
		power.add(200);
		power.add(300);
		power.add(400);

		percentOfHealth = 100;

		// sort sprite lists
		Main.sortAndAddSprites(leftStandSpriteSourceFolder, leftStand);
		Main.sortAndAddSprites(leftRunSpriteSourceFolder, leftRun);
		Main.sortAndAddSprites(leftAttackSpriteSourceFolder, leftAttack);

		Main.sortAndAddSprites(rightStandSpriteSourceFolder, rightStand);
		Main.sortAndAddSprites(rightRunSpriteSourceFolder, rightRun);
		Main.sortAndAddSprites(rightAttackSpriteSourceFolder, rightAttack);
	}

	public void draw(Graphics g2d) {

		userIcon = new ImageIcon(getUserImage());
		// draw user
		g2d.drawImage(getUserImage(), super.x, super.y, null);
		// main body
		// g2d.drawRect(x + 87, y + 100, 80, 125);
		// left sword
		// g2d.drawRect(x, y + 100, 87, 125);
		// right sword
		// g2d.drawRect(x + 167, y + 100, 87, 125);

		// DRAW HP LINE
		// red line
		g2d.setColor(Color.red);
		g2d.fillRect(50, 70, 200, 20);
		// green line
		g2d.setColor(Color.green);
		g2d.fillRect(50, 70, 2 * percentOfHealth, 20);

		// draw percents of health
		g2d.setColor(Color.black);
		g2d.setFont(new Font("Times New Roman", Font.BOLD, 12));
		if (percentOfHealth >= 0) {
			g2d.drawString(percentOfHealth + "%", 52, 85);
		} else {
			g2d.drawString("0%", 52, 85);
		}

	}

	public Image getUserImage() {
		return userIcon.getImage();
	}

	public void update() {
		c = new Controller();
		percentOfHealth = 100 * health / 5000;

		rectSwordLeft = new Rectangle(x, y + 100, 87, 125);
		rectSwordRight = new Rectangle(x + 167, y + 100, 87, 125);
		mainRect = new Rectangle(x + 87, y + 100, 80, 125);

		// border collision
		if (getX() < 0) {
			setX(0);
		} else if (getX() > Main.frameWidth - userIcon.getIconWidth()) {
			setX(Main.frameWidth - userIcon.getIconWidth());
		}

		// MOVE LOGIC
		// if it's left/right edge == user can move by itself
		// if there's no left/right edges == user can't move, moves only background
		if ((Game.allowToMove && Game.isTheLeftEdge) || (Game.allowToMove && Game.isTheRightEdge)) {
			x += velX;
		}

		// CHANGING SPRITES

		// SPRITES FOR LEFT STAND AND RUN
		if (direction.equals("left") && !isAttack) {
			if (isMove) {
				spriteCounter++;

				if (spriteCounter > 5) {
					userIcon = new ImageIcon(leftRun.get(spriteNum));
					spriteNum++;

					if (spriteNum > leftRun.size() - 1) {
						spriteNum = 0;
					}
					spriteCounter = 0;
				}
			} else {
				spriteCounter++;

				if (spriteCounter > 5) {
					userIcon = new ImageIcon(leftStand.get(spriteNum));
					spriteNum++;

					if (spriteNum > leftStand.size() - 1) {
						spriteNum = 0;
					}
					spriteCounter = 0;
				}
			}
		}

		// SPRITES FOR RIGHT STAND AND RUN
		if (direction.equals("right") && !isAttack) {
			if (isMove) {
				spriteCounter++;

				if (spriteCounter > 5) {
					userIcon = new ImageIcon(rightRun.get(spriteNum));
					spriteNum++;

					if (spriteNum > rightRun.size() - 1) {
						spriteNum = 0;
					}
					spriteCounter = 0;
				}
			} else {
				spriteCounter++;

				if (spriteCounter > 5) {
					userIcon = new ImageIcon(rightStand.get(spriteNum));
					spriteNum++;

					if (spriteNum > rightStand.size() - 1) {
						spriteNum = 0;
					}
					spriteCounter = 0;
				}
			}
		}

		// SPRITES FOR LEFT AND RIGHT ATTACK
		if (isAttack && direction.equals("left")) {
			spriteCounter++;
			isMove = false;

			if (spriteCounter > 5) {
				userIcon = new ImageIcon(leftAttack.get(spriteNum));
				spriteNum++;
				isAttack = true;
				if (spriteNum > leftAttack.size() - 1) {
					isAttack = false;
					// c.setHurted(0);
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
				userIcon = new ImageIcon(rightAttack.get(spriteNum));
				spriteNum++;
				isAttack = true;
				if (spriteNum > leftAttack.size() - 1) {
					isAttack = false;
					// c.setHurted(0);
					// isMove = false;
					spriteNum = 0;
				}
				spriteCounter = 0;
			}
		}

	}

	// MOVE
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (!Game.gameOver) {
			// USER CAN'T MOVE IF ATTACKS
			if (!isAttack) {
				if (key == KeyEvent.VK_A) {
					direction = "left";
					isMove = true;

					velX = -6;
				} else if (key == KeyEvent.VK_D) {
					direction = "right";
					isMove = true;

					velX = 6;
				}
			}
		}

	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (!Game.gameOver) {
			// CONDITION TO PREVENT ATTACKING MORE TIMES IN A ROW,
			// User has to finish its attack to start another one
			// (it doesn't make sense start attack
			// if your sword still didn't touch the ground)
			// condition will pas if userIcon is the last attack sprite
			// (sword touches the ground), it's done in update() method
			if (!isAttack) {
				spriteNum = 0;

				if (key == KeyEvent.VK_A) {
					isMove = false;
					velX = 0;
				} else if (key == KeyEvent.VK_D) {
					isMove = false;
					velX = 0;
				}
			}
		}

	}

	// ATTACK
	public void mouseClicked(MouseEvent e) {
		if (!Game.gameOver) {
			// stop user move
			if (isMove) {
				velX = 0;
			}

			// START ATTACK
			if (!isAttack) {

				spriteCounter = 0;
				spriteNum = 0;

				isAttack = true;

			}
		}

	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getPower(int index) {
		return power.get(index);
	}

	public int powerSize() {
		return power.size();
	}

	public int getSpriteNum() {
		return spriteNum;
	}

	public int getVelX() {
		return velX;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public String getDirection() {
		return direction;
	}

}
