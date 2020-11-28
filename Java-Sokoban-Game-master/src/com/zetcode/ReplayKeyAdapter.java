package com.zetcode;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

public class ReplayKeyAdapter extends KeyAdapter{
	private GameManager gameManager;

	
	public ReplayKeyAdapter(GameManager gameManager) {
		this.gameManager = gameManager;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if (gameManager.getIsCompleted()) { // 게임이 끝남.
			return;
		}

		if (gameManager.getIsFailed()) {
			return;
		}
		
		int key1 = e.getKeyCode();
		
		switch (key1) {
		case KeyEvent.VK_LEFT:
			gameManager.replayGoBack();
			break;
			
		case KeyEvent.VK_RIGHT:
			gameManager.replayGoAhead();
			break;
		
		default:
			break;
		}
		gameManager.callIsCompleted();
		gameManager.repaint();
	}
}

