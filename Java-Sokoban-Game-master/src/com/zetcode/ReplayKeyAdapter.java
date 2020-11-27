package com.zetcode;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

public class ReplayKeyAdapter extends KeyAdapter{
	private Replay replay;
	private BoardManager boardManager;

	
	public ReplayKeyAdapter(BoardManager boardManager, Replay replay) {
		this.boardManager = boardManager;
		this.replay = replay;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if (boardManager.getIsCompleted()) { // 게임이 끝남.
			return;
		}

		if (boardManager.getIsFailed()) {
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

