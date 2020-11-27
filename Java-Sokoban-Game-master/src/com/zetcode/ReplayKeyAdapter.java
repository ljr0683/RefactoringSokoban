package com.zetcode;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

public class ReplayKeyAdapter extends KeyAdapter{
	Replay replay;
	BoardManager boardManager;
	Music music;

	
	public ReplayKeyAdapter(BoardManager boardManager, Replay replay) {
		this.boardManager = boardManager;
		this.replay = replay;
		music = new Music(true);
		music.start();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if (boardManager.getIsCompleted()) { // 게임이 끝남.
			music.close();
			return;
		}

		if (boardManager.getIsFailed()) {
			music.close();
			return;
		}
		
		
		int key1 = e.getKeyCode();
		
		switch (key1) {
		case KeyEvent.VK_LEFT:
			replay.goBack();
			break;
			
		case KeyEvent.VK_RIGHT:
			replay.goAhead();
			break;
		
		default:
			break;
		}
		boardManager.callIsCompleted();
		boardManager.repaint();
			
	}
}

