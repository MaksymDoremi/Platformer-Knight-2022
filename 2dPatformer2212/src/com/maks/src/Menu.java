package com.maks.src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class Menu {
	Rectangle playButton = new Rectangle(720, 200, 100,50);
	Rectangle exitButton = new Rectangle(720, 300, 100,50);

	
	public void draw(Graphics g) {
		
		//main text, Twilight Forest
		g.setFont(new Font("Algerian", Font.PLAIN, 50));
		g.setColor(Color.white);
		g.drawString("Twilight Forest", 550, 100);
		
		Graphics2D g2d = (Graphics2D) g;
		
		//draw play and exit buttons
		g.setFont(new Font("Times New Roman", Font.BOLD, 36));
		g.setColor(Color.white);
		g.drawString("Play", playButton.x + 15, playButton.y + 36);
		g.drawString("Exit", exitButton.x + 15, exitButton.y + 36);
		g2d.draw(playButton);
		g2d.draw(exitButton);
	}
	
	//mouse listener for play and exit buttons
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		
		if(x >= 720 && x <= 820) {
			//play button
			if(y >= 200 && y <= 250) {
				Game.state = Game.STATE.GAME;
			//exit button
			}else if(y >= 300 && y <= 350) {
				System.exit(1);
			}
		}
		
	}
}
