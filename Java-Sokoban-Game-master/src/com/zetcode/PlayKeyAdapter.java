package com.zetcode;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;

import javax.swing.Timer;

public class PlayKeyAdapter extends KeyAdapter {

	public static int SPACE = 64;

	private Board board;
	private CheckCollision checkCollision;
	private FailedDetected failed;
	private MyTimer time;
	private Timer timer;
	private BoardManager boardManager;

	private int mode;

	public PlayKeyAdapter(Board board, MyTimer time, int mode, Timer timer, BoardManager boardManager) {
		this.board = board;
		this.time = time;
		this.mode = mode;
		this.timer = timer;
		this.boardManager = boardManager;
		checkCollision = new CheckCollision(boardManager);
		failed = new FailedDetected(boardManager);
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
		System.out.println(mode);
		
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

		if (checkCollision.checkWallCollision(boardManager.getSoko(), Board.LEFT_COLLISION)) { // soko객체 왼쪽에 벽이 있다면 움직이지 않고 키 이벤트를 끝냄
			return;
		}

		if (checkCollision.checkBagCollision(Board.LEFT_COLLISION)) {
			return;
		}

		boardManager.getSoko().move(-SPACE, 0); // 만약 위 상황을 만족하지 않는다면 왼쪽으로 한칸 움직임.
		boardManager.getSoko().changePlayerVector(Board.LEFT_COLLISION);
		board.increaseMoveCount();

		if (boardManager.getFlag()) {
			if (!boardManager.getIsCollision()) {
				board.replayDequeOffer(5);
			}
			boardManager.setIsCollision(true);
		} else {
			if (boardManager.getIsCollision()) {
				board.replayDequeOffer(6);
			}

			boardManager.setIsCollision(false);
		}

		board.replayDequeOffer(Board.LEFT_COLLISION);

		if (boardManager.getBags() != null) {
			boardManager.isEntered(boardManager.getBags());
			if (boardManager.getBags().getIsEntered()) {
				board.isCompleted();
				board.repaint();
				return;
			}
		}

//		if (failed.isFailedDetected(boardManager.getBags())) {
//			board.isFailed();
//		}
		
		boardManager.callIsFailedDetected(boardManager.getBags());
		return;
	}

	public void keyRightPressed(KeyEvent e) {
		if (checkCollision.checkWallCollision(boardManager.getSoko(), Board.RIGHT_COLLISION)) {
			return;
		}

		if (checkCollision.checkBagCollision(Board.RIGHT_COLLISION)) {
			return;
		}

		boardManager.getSoko().move(SPACE, 0);
		boardManager.getSoko().changePlayerVector(Board.RIGHT_COLLISION);
		board.increaseMoveCount();

		if (boardManager.getFlag()) {
			if (!boardManager.getIsCollision()) {
				board.replayDequeOffer(5);
			}
			boardManager.setIsCollision(true);
		} else {
			if (boardManager.getIsCollision()) {
				board.replayDequeOffer(6);
			}

			boardManager.setIsCollision(false);
		}

		board.replayDequeOffer(Board.RIGHT_COLLISION);

		if (boardManager.getBags() != null) {
			boardManager.isEntered(boardManager.getBags());
			if (boardManager.getBags().getIsEntered()) {
				board.isCompleted();
				board.repaint();
				return;
			}
		}

//		if (failed.isFailedDetected(boardManager.getBags())) {
//			board.isFailed();
//		}
		
		boardManager.callIsFailedDetected(boardManager.getBags());
		
		return;
	}

	public void keyUpPressed(KeyEvent e) {

		if (checkCollision.checkWallCollision(boardManager.getSoko(), Board.TOP_COLLISION)) {
			return;
		}

		if (checkCollision.checkBagCollision(Board.TOP_COLLISION)) {
			return;
		}

		boardManager.getSoko().move(0, -SPACE);
		boardManager.getSoko().changePlayerVector(Board.TOP_COLLISION);
		board.increaseMoveCount();

		if (boardManager.getFlag()) {
			if (!boardManager.getIsCollision()) {
				board.replayDequeOffer(5);
			}
			boardManager.setIsCollision(true);
		} else {
			if (boardManager.getIsCollision()) {
				board.replayDequeOffer(6);
			}

			boardManager.setIsCollision(false);
		}

		board.replayDequeOffer(Board.TOP_COLLISION);

		if (boardManager.getBags() != null) {
			boardManager.isEntered(boardManager.getBags());
			if (boardManager.getBags().getIsEntered()) {
				board.isCompleted();
				board.repaint();
				return;
			}
		}

//		if (failed.isFailedDetected(boardManager.getBags())) {
//			board.isFailed();
//		}
		
		boardManager.callIsFailedDetected(boardManager.getBags());
		
		return;

	}

	public void keyDownPressed(KeyEvent e) {

		if (checkCollision.checkWallCollision(boardManager.getSoko(), Board.BOTTOM_COLLISION)) {
			return;
		}

		if (checkCollision.checkBagCollision(Board.BOTTOM_COLLISION)) {
			return;
		}

		boardManager.getSoko().move(0, SPACE);
		boardManager.getSoko().changePlayerVector(Board.BOTTOM_COLLISION);
		board.increaseMoveCount();

		if (boardManager.getFlag()) {
			if (!boardManager.getIsCollision()) {
				board.replayDequeOffer(5);
			}
			boardManager.setIsCollision(true);
		} else {
			if (boardManager.getIsCollision()) {
				board.replayDequeOffer(6);
			}

			boardManager.setIsCollision(false);
		}

		board.replayDequeOffer(Board.BOTTOM_COLLISION);

		if (boardManager.getBags() != null) {
			boardManager.isEntered(boardManager.getBags());
			if (boardManager.getBags().getIsEntered()) {
				board.isCompleted();
				board.repaint();
				return;
			}
		}

//		if (failed.isFailedDetected(boardManager.getBags())) {
//			failed.isFailed();
//		}
		
		boardManager.callIsFailedDetected(boardManager.getBags());
		
		return;
	}

	public void keyReturnPressed(KeyEvent e) {

		time.time = 0;
		board.restartLevel();
		
		return;
	}

	public void keyBackPressed(KeyEvent e) {

		if (!board.getReplayDequeEmpty()) {
			board.undo();
		}
	}
}