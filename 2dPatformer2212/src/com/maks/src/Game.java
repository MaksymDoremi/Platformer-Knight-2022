package com.maks.src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.*;

import com.maks.src.input.*;
import com.maks.src.objects.User;

public class Game extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	// STATIC IMPORTANT BOOLEANS
	// edges, to set the collisions for the end of a map
	public static boolean isTheRightEdge;
	public static boolean isTheLeftEdge;
	// permission to move user character
	public static boolean allowToMove;
	// jump through the STOP POINT
	public static boolean jump;
	// game over, forbids keyInput and mouseInput
	public static boolean gameOver = false;

	// background
	private String background = "img/bg/bg2.png";

	// global position for backgrounds
	public int positionBackground = 0;

	// COLLISION RECTANGLES

	// left rectangle - helps for collision detection
	// and stops background moving(allows user to move)
	// it's the edge of the frame,
	// cause we don't want to let user move beyond the frame
	Rectangle leftBoundaryRect = new Rectangle(0, 0, 10, getBackgroundImage().getHeight(this));

	// contains whole first background(1 piece of it), cause map has few of them,
	Rectangle helpBGleftRect = new Rectangle(0, 0, getBackgroundImage().getWidth(this),
			getBackgroundImage().getHeight(this));

	// right rectangle - same as left, but it's right side
	Rectangle rightBoundaryRect = new Rectangle(1525, 0, 10, getBackgroundImage().getHeight(this));
	Rectangle helpBGrightRect = new Rectangle();

	// OBJECTS
	Timer timer;
	User user;
	Controller c;
	Menu menu;

	//constants for game and menu
	public enum STATE {
		MENU, GAME
	};
	
	//firstly we will see the menu
	public static STATE state = STATE.MENU;

	public Game() {
		this.setFocusable(true);
		
		//set booleans to their right value
		isTheRightEdge = false;
		isTheLeftEdge = true;
		allowToMove = true;
		jump = false;

		// initialize  objects
		timer = new Timer(10, this);
		timer.start();
		
		user = new User(0, 522, 5000);
		
		menu = new Menu();

		c = new Controller();

		// add key and mouse listeners
		this.addKeyListener(new KeyInput(user, this));
		this.addMouseListener(new MouseInput(user, this, menu, c));
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		//draw background, it will be drawn 3 times to make map wide
		g2d.drawImage(getBackgroundImage(), 0 + positionBackground, 0, this);
		g2d.drawImage(getBackgroundImage(), getBackgroundImage().getWidth(null) + positionBackground, 0, this);
		g2d.drawImage(getBackgroundImage(), 2 * getBackgroundImage().getWidth(null) + positionBackground, 0, this);
		
		// if game(player clicked "play")
		// draw health points, user and enemies
		if (state == STATE.GAME) {
			//health points
			g2d.fillRoundRect(10, 10, 260, 100, 30, 30);
			g2d.drawImage((new ImageIcon(getClass().getResource("/icon.png"))).getImage(), 10, 10, this);
			
			//collision rectangles
			helpBGleftRect = new Rectangle(0 + positionBackground, 0, getBackgroundImage().getWidth(this),
					getBackgroundImage().getHeight(this));
			helpBGrightRect = new Rectangle(2 * getBackgroundImage().getWidth(null) + positionBackground, 0,
					getBackgroundImage().getWidth(this), getBackgroundImage().getHeight(this));

			// draw rectangles for visual help and understanding
			/*
			 * g2d.setPaint(Color.red); g2d.draw(leftBoundaryRect);
			 * g2d.draw(helpBGleftRect); g2d.setPaint(Color.green);
			 * g2d.draw(rightBoundaryRect); g2d.draw(helpBGrightRect);
			 */
			
			//draw characters
			user.draw(g2d);
			c.draw(g2d);
			//if user died ==> game over
			
			//EXIT BUTTON
			Rectangle exitButton = new Rectangle(1400, 20, 100, 50);
			g.setFont(new Font("Times New Roman", Font.BOLD, 36));
			g.setColor(Color.white);
			g.drawString("Exit", exitButton.x + 15, exitButton.y + 36);
			g2d.draw(exitButton);
			
			if (user.getHealth() <= 0) {
				c.gameOver(user, g2d, timer);
			}
		//if menu, so draw just menu page
		} else if (state == STATE.MENU) {
			menu.draw(g2d);
		}

	}
	
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if (x >= 1400 && x <= 1500) {

			if (y >= 20 && y <= 70) {
				System.exit(1);
			}
		}
	}
	
	// returns backround image
	public Image getBackgroundImage() {
		ImageIcon i = new ImageIcon(background);
		return i.getImage();
	}

	// MOVES BG OR USER(depends on certain conditions)
	public void moveBGorUser() {

		// check for left boundary
		// if it's boundary so allow my user to move
		// so BG won't move in method moveBG()
		if (helpBGleftRect.x <= leftBoundaryRect.x + 3 && helpBGleftRect.x >= leftBoundaryRect.x - 3) {
			isTheLeftEdge = true;

			allowToMove = true;

			isTheRightEdge = false;

			// ALSO if it's the middle(user doesn't move only BG moves)
			// and I want to get out of stop point,
			// so it has to be similar way as the edge
			// then teleport me out of stop point 1 pixel further
			if (user.getDirection().equals("left")) {
				if (!jump) {
					user.setX(Main.frameWidth / 2 - 5);
					jump = true;
				}
				allowToMove = true;

			}
			// else check for right boundary(works the same way, but another directon)
		} else if ((int) helpBGrightRect.getWidth() + helpBGrightRect.x <= (int) rightBoundaryRect.getWidth()
				+ rightBoundaryRect.x + 4
				&& (int) helpBGrightRect.getWidth() + helpBGrightRect.x >= (int) rightBoundaryRect.getWidth()
						- rightBoundaryRect.x - 4) {
			isTheRightEdge = true;

			allowToMove = true;

			isTheLeftEdge = false;

			if (user.getDirection().equals("right")) {
				if (!jump) {
					user.setX(Main.frameWidth / 2 + 5);
					jump = true;
				}
				allowToMove = true;
			}
		}

		// check for stop point(middle)
		// I did a gap for 8 pixels, to make sure user will catch that point
		// gap is <4;4> so I make teleport(jump) for +5 or -5 pixels
		if (user.getX() >= Main.frameWidth / 2 - 4 && user.getX() <= Main.frameWidth / 2 + 4) {
			allowToMove = false;
			jump = false;
		}
		
		//move background if user isn't allowed to move
		moveBG(user.getVelX());

	}

	// METHOD MOVES BG IF MY USER IS AT THE MIDDLE(stop point)
	public void moveBG(int velX) {
		// if user isn't allowed to move by itself
		if (!allowToMove) {
			positionBackground -= velX;
		}
	}

	// actions, important methods to play the game
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//if game
		if (state == STATE.GAME) {

			repaint();
			
			//draws user's sprites
			user.update();
			//draws enemies sprites
			c.update(user, this);
			//checks for hurting enemies
			c.hurtEnemy(user);
			//checks for enemy death
			c.killEnemy();
			//moves background or allows user to move
			moveBGorUser();
			
		}

		

	}

}
