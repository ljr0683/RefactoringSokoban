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

	private int mode;

	public PlayKeyAdapter(Board board, MyTimer time, int mode, Timer timer) {
		this.board = board;
		checkCollision = new CheckCollision(board);
		failed = new FailedDetected(board);
		this.time = time;
		this.mode = mode;
		this.timer = timer;
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (board.getIsCompleted()) { // ������ ����.

			return;
		}

		if (board.getIsFailed()) {
			timer.stop();
			return;
		}

		int key = e.getKeyCode();
		board.setFlag(false);

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

			for (int i = 0; i < board.getWallsSize(); i++) {

				Wall next = board.getWalls(i);

				if (next instanceof Llm && board.getMoveCount() >= 1) {

					((Llm) next).rellm();

				}
			}
		} // �̺κб��� ����

		board.repaint();
		return;
	}

	public void keyLeftPressed(KeyEvent e) {

		if (checkCollision.checkWallCollision(board.getSoko(), Board.LEFT_COLLISION)) { // soko��ü ���ʿ� ���� �ִٸ� �������� �ʰ� Ű �̺�Ʈ�� ����
			return;
		}

		if (checkCollision.checkBagCollision(Board.LEFT_COLLISION)) {
			return;
		}

		board.getSoko().move(-SPACE, 0); // ���� �� ��Ȳ�� �������� �ʴ´ٸ� �������� ��ĭ ������.
		board.getSoko().changePlayerVector(Board.LEFT_COLLISION);
		board.increaseMoveCount();

		if (board.getFlag()) {
			if (!board.getIsCollision()) {
				board.replayDequeOffer(5);
			}
			board.setIsCollision(true);
		} else {
			if (board.getIsCollision()) {
				board.replayDequeOffer(6);
			}

			board.setIsCollision(false);
		}

		board.replayDequeOffer(Board.LEFT_COLLISION);

		if (board.getBags() != null) {
			board.isEntered(board.getBags());
			if (board.getBags().getIsEntered()) {
				board.isCompleted();
				board.repaint();
				return;
			}
		}

		if (failed.isFailedDetected(board.getBags())) {
			board.isFailed();
		}
		return;
	}

	public void keyRightPressed(KeyEvent e) {
		if (checkCollision.checkWallCollision(board.getSoko(), Board.RIGHT_COLLISION)) {
			return;
		}

		if (checkCollision.checkBagCollision(Board.RIGHT_COLLISION)) {
			return;
		}

		board.getSoko().move(SPACE, 0);
		board.getSoko().changePlayerVector(Board.RIGHT_COLLISION);
		board.increaseMoveCount();

		if (board.getFlag()) {
			if (!board.getIsCollision()) {
				board.replayDequeOffer(5);
			}
			board.setIsCollision(true);
		} else {
			if (board.getIsCollision()) {
				board.replayDequeOffer(6);
			}

			board.setIsCollision(false);
		}

		board.replayDequeOffer(Board.RIGHT_COLLISION);

		if (board.getBags() != null) {
			board.isEntered(board.getBags());
			if (board.getBags().getIsEntered()) {
				board.isCompleted();
				board.repaint();
				return;
			}
		}

		if (failed.isFailedDetected(board.getBags())) {
			board.isFailed();
		}
		return;
	}

	public void keyUpPressed(KeyEvent e) {

		if (checkCollision.checkWallCollision(board.getSoko(), Board.TOP_COLLISION)) {
			return;
		}

		if (checkCollision.checkBagCollision(Board.TOP_COLLISION)) {
			return;
		}

		board.getSoko().move(0, -SPACE);
		board.getSoko().changePlayerVector(Board.TOP_COLLISION);
		board.increaseMoveCount();

		if (board.getFlag()) {
			if (!board.getIsCollision()) {
				board.replayDequeOffer(5);
			}
			board.setIsCollision(true);
		} else {
			if (board.getIsCollision()) {
				board.replayDequeOffer(6);
			}

			board.setIsCollision(false);
		}

		board.replayDequeOffer(Board.TOP_COLLISION);

		if (board.getBags() != null) {
			board.isEntered(board.getBags());
			if (board.getBags().getIsEntered()) {
				board.isCompleted();
				board.repaint();
				return;
			}
		}

		if (failed.isFailedDetected(board.getBags())) {
			board.isFailed();
		}
		return;

	}

	public void keyDownPressed(KeyEvent e) {

		if (checkCollision.checkWallCollision(board.getSoko(), Board.BOTTOM_COLLISION)) {
			return;
		}

		if (checkCollision.checkBagCollision(Board.BOTTOM_COLLISION)) {
			return;
		}

		board.getSoko().move(0, SPACE);
		board.getSoko().changePlayerVector(Board.BOTTOM_COLLISION);
		board.increaseMoveCount();

		if (board.getFlag()) {
			if (!board.getIsCollision()) {
				board.replayDequeOffer(5);
			}
			board.setIsCollision(true);
		} else {
			if (board.getIsCollision()) {
				board.replayDequeOffer(6);
			}

			board.setIsCollision(false);
		}

		board.replayDequeOffer(Board.BOTTOM_COLLISION);

		if (board.getBags() != null) {
			board.isEntered(board.getBags());
			if (board.getBags().getIsEntered()) {
				board.isCompleted();
				board.repaint();
				return;
			}
		}

		if (failed.isFailedDetected(board.getBags())) {
			board.isFailed();
		}
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