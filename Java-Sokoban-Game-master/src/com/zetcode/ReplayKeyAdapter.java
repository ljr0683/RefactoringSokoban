package com.zetcode;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

public class ReplayKeyAdapter extends KeyAdapter{
	Replay replay;
	Board board;
	
	public ReplayKeyAdapter(int levelSelected, LevelSelectPanel previousPanel, UIManager frame, File file, String selectCharacter, Replay replay) {
		this.replay = replay;
		createBoard(levelSelected, previousPanel, frame, file, replay, selectCharacter);
	}
	
	private void createBoard(int levelSelected, LevelSelectPanel previousPanel, UIManager frame, File file,Replay replay, String selectCharacter) {
		board = new Board(levelSelected, previousPanel, frame, file, replay, selectCharacter, this);
		int width = board.getBoardWidth();
		int height = board.getBoardHeight();
		frame.changePanel(board, width, height);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if (board.getIsCompleted()) { // 게임이 끝남.
			return;
		}

		if (board.getIsFailed()) {
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
		board.isCompleted();
		board.repaint();
			
	}
	
	public Board getBoard() {
		return board;
	}
}

