package com.zetcode;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.Timer;

public class PlayKeyAdapter extends KeyAdapter {

	private Board board;
	private CheckCollision checkCollision;
	private MyTimer time;
	private Timer timer;
	private BoardManager boardManager;
	private JLabel[] boomLabel;

	private int mode;

	public PlayKeyAdapter(Board board, MyTimer time, int mode, Timer timer, BoardManager boardManager, JLabel[] boomLabel) {
		this.board = board;
		this.time = time;
		this.mode = mode;
		this.timer = timer;
		this.boardManager = boardManager;
		this.checkCollision = new CheckCollision(boardManager);
		this.boomLabel = boomLabel;
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (boardManager.getIsCompleted()) { // 게임이 끝남.
			return;
		}

		if (boardManager.getIsFailed()) {
			timer.stop();
			return;
		}
		for(int i=0; i<boomLabel.length; i++) {
			boomLabel[i].setVisible(false);
		}

		int key = e.getKeyCode();
		boardManager.setFlag(false);

		if (key == KeyEvent.VK_LEFT) {
			keyLeftPressed(e);
		} else if (key == KeyEvent.VK_RIGHT) {
			keyRightPressed(e);
		} else if (key == KeyEvent.VK_UP) {
			keyUpPressed(e);
		} else if (key == KeyEvent.VK_DOWN) {
			keyDownPressed(e);
		} else if (key == KeyEvent.VK_R) {
			keyReturnPressed(e);
		} else if (key == KeyEvent.VK_BACK_SPACE) {
			keyBackPressed(e);
		}
		
		if (mode == 2) {

			for (int i = 0; i < boardManager.getWallsSize(); i++) {

				Wall next = boardManager.getWalls(i);

				if (next instanceof Llm && board.getMoveCount() >= 1) {

					((Llm) next).rellm();

				}
			}
		} // 이부분까지 설정

		board.repaint();
		return;
	}

	public void keyLeftPressed(KeyEvent e) {

		if (checkCollision.checkWallCollision(boardManager.getSoko(), boardManager.LEFT_COLLISION)) { // soko객체 왼쪽에 벽이 있다면 움직이지 않고 키 이벤트를 끝냄
			return;
		}

		if (checkCollision.checkBagCollision(boardManager.LEFT_COLLISION)) {
			return;
		}

		boardManager.getSoko().move(-boardManager.SPACE, 0); // 만약 위 상황을 만족하지 않는다면 왼쪽으로 한칸 움직임.
		boardManager.getSoko().changePlayerVector(boardManager.LEFT_COLLISION);
		boardManager.boardIncreaseMoveCount();

		if (boardManager.getFlag()) {
			if (!boardManager.getIsCollision()) {
				boardManager.replayDequeOffer(5);
			}
			boardManager.setIsCollision(true);
		} else {
			if (boardManager.getIsCollision()) {
				boardManager.replayDequeOffer(6);
			}

			boardManager.setIsCollision(false);
		}

		boardManager.replayDequeOffer(boardManager.LEFT_COLLISION);

		if (boardManager.getBags() != null) {
			boardManager.isEntered(boardManager.getBags());
			if (boardManager.getBags().getIsEntered()) {
				boardManager.callIsCompleted();
				boardManager.repaint();
				return;
			}
		}


		
		boardManager.callIsFailedDetected(boardManager.getBags());
		return;
	}

	public void keyRightPressed(KeyEvent e) {
		if (checkCollision.checkWallCollision(boardManager.getSoko(), boardManager.RIGHT_COLLISION)) {
			return;
		}

		if (checkCollision.checkBagCollision(boardManager.RIGHT_COLLISION)) {
			return;
		}

		boardManager.getSoko().move(boardManager.SPACE, 0);
		boardManager.getSoko().changePlayerVector(boardManager.RIGHT_COLLISION);
		boardManager.boardIncreaseMoveCount();

		if (boardManager.getFlag()) {
			if (!boardManager.getIsCollision()) {
				boardManager.replayDequeOffer(5);
			}
			boardManager.setIsCollision(true);
		} else {
			if (boardManager.getIsCollision()) {
				boardManager.replayDequeOffer(6);
			}

			boardManager.setIsCollision(false);
		}

		boardManager.replayDequeOffer(boardManager.RIGHT_COLLISION);

		if (boardManager.getBags() != null) {
			boardManager.isEntered(boardManager.getBags());
			if (boardManager.getBags().getIsEntered()) {
				boardManager.callIsCompleted();
				boardManager.repaint();
				return;
			}
		}
		
		boardManager.callIsFailedDetected(boardManager.getBags());
		
		return;
	}

	public void keyUpPressed(KeyEvent e) {

		if (checkCollision.checkWallCollision(boardManager.getSoko(), boardManager.TOP_COLLISION)) {
			return;
		}

		if (checkCollision.checkBagCollision(boardManager.TOP_COLLISION)) {
			return;
		}

		boardManager.getSoko().move(0, -boardManager.SPACE);
		boardManager.getSoko().changePlayerVector(boardManager.TOP_COLLISION);
		boardManager.boardIncreaseMoveCount();

		if (boardManager.getFlag()) {
			if (!boardManager.getIsCollision()) {
				boardManager.replayDequeOffer(5);
			}
			boardManager.setIsCollision(true);
		} else {
			if (boardManager.getIsCollision()) {
				boardManager.replayDequeOffer(6);
			}

			boardManager.setIsCollision(false);
		}

		boardManager.replayDequeOffer(boardManager.TOP_COLLISION);

		if (boardManager.getBags() != null) {
			boardManager.isEntered(boardManager.getBags());
			if (boardManager.getBags().getIsEntered()) {
				boardManager.callIsCompleted();
				boardManager.repaint();
				return;
			}
		}
		
		boardManager.callIsFailedDetected(boardManager.getBags());
		
		return;

	}

	public void keyDownPressed(KeyEvent e) {

		if (checkCollision.checkWallCollision(boardManager.getSoko(), boardManager.BOTTOM_COLLISION)) {
			return;
		}

		if (checkCollision.checkBagCollision(boardManager.BOTTOM_COLLISION)) {
			return;
		}

		boardManager.getSoko().move(0, boardManager.SPACE);
		boardManager.getSoko().changePlayerVector(boardManager.BOTTOM_COLLISION);
		boardManager.boardIncreaseMoveCount();

		if (boardManager.getFlag()) {
			if (!boardManager.getIsCollision()) {
				boardManager.replayDequeOffer(5);
			}
			boardManager.setIsCollision(true);
		} else {
			if (boardManager.getIsCollision()) {
				boardManager.replayDequeOffer(6);
			}

			boardManager.setIsCollision(false);
		}

		boardManager.replayDequeOffer(boardManager.BOTTOM_COLLISION);

		if (boardManager.getBags() != null) {
			boardManager.isEntered(boardManager.getBags());
			if (boardManager.getBags().getIsEntered()) {
				boardManager.callIsCompleted();
				boardManager.repaint();
				return;
			}
		}
		
		boardManager.callIsFailedDetected(boardManager.getBags());
		
		return;
	}

	public void keyReturnPressed(KeyEvent e) {

		time.time = 0;
		board.restartLevel();
		
		return;
	}

	public void keyBackPressed(KeyEvent e) {

		if (!boardManager.getReplayDequeEmpty()) {
			boardManager.undo();
		}
	}
}