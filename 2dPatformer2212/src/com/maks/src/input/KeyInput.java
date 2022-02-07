package com.maks.src.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.maks.src.Game;
import com.maks.src.objects.User;

public class KeyInput extends KeyAdapter {
	User user;
	Game game;
	public KeyInput(User user, Game game) {
		this.user = user;
		this.game = game;
	}
	public void keyPressed(KeyEvent e) {
		user.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		user.keyReleased(e);
	}
}
