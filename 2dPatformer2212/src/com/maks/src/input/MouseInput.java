package com.maks.src.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.maks.src.Game;
import com.maks.src.Menu;
import com.maks.src.objects.User;

public class MouseInput implements MouseListener {

	User user;
	Game game;
	Menu menu;
	Controller c;

	public MouseInput(User user, Game game, Menu menu, Controller c) {
		this.user = user;
		this.game = game;
		this.menu = menu;
		this.c = c;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		user.mouseClicked(e);
		menu.mouseClicked(e);
		game.mouseClicked(e);

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
