package com.zetcode;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;

import javax.swing.Timer;

public class PlayKeyAdapter extends KeyAdapter {

	public static int SPACE = 64;
	public static final int LEFT_COLLISION = 1;
	public static final int RIGHT_COLLISION = 2;
	public static final int TOP_COLLISION = 3;
	public static final int BOTTOM_COLLISION = 4;

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

		if (checkCollision.checkWallCollision(board.getSoko(), LEFT_COLLISION)) { // soko��ü ���ʿ� ���� �ִٸ� �������� �ʰ� Ű �̺�Ʈ�� ����
			return;
		}

		if (checkCollision.checkBagCollision(LEFT_COLLISION)) {
			return;
		}

		board.getSoko().move(-SPACE, 0); // ���� �� ��Ȳ�� �������� �ʴ´ٸ� �������� ��ĭ ������.
		board.getSoko().changePlayerVector(LEFT_COLLISION);
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

		board.replayDequeOffer(LEFT_COLLISION);

		if (board.getBags() != null) {
			board.callIsEntered(board.getBags());
			if (board.getBags().getIsEntered()) {
				board.callIsCompleted();
				board.repaint();
				return;
			}
		}

		if (failed.isFailedDetected(board.getBags())) {
			board.callIsFailed();
		}
		return;
	}

	public void keyRightPressed(KeyEvent e) {
		if (checkCollision.checkWallCollision(board.getSoko(), RIGHT_COLLISION)) {
			return;
		}

		if (checkCollision.checkBagCollision(RIGHT_COLLISION)) {
			return;
		}

		board.getSoko().move(SPACE, 0);
		board.getSoko().changePlayerVector(RIGHT_COLLISION);
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

		board.replayDequeOffer(RIGHT_COLLISION);

		if (board.getBags() != null) {
			board.callIsEntered(board.getBags());
			if (board.getBags().getIsEntered()) {
				board.callIsCompleted();
				board.repaint();
				return;
			}
		}

		if (failed.isFailedDetected(board.getBags())) {
			board.callIsFailed();
		}
		return;
	}

	public void keyUpPressed(KeyEvent e) {

		if (checkCollision.checkWallCollision(board.getSoko(), TOP_COLLISION)) {
			return;
		}

		if (checkCollision.checkBagCollision(TOP_COLLISION)) {
			return;
		}

		board.getSoko().move(0, -SPACE);
		board.getSoko().changePlayerVector(TOP_COLLISION);
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

		board.replayDequeOffer(TOP_COLLISION);

		if (board.getBags() != null) {
			board.callIsEntered(board.getBags());
			if (board.getBags().getIsEntered()) {
				board.callIsCompleted();
				board.repaint();
				return;
			}
		}

		if (failed.isFailedDetected(board.getBags())) {
			board.callIsFailed();
		}
		return;

	}

	public void keyDownPressed(KeyEvent e) {

		if (checkCollision.checkWallCollision(board.getSoko(), BOTTOM_COLLISION)) {
			return;
		}

		if (checkCollision.checkBagCollision(BOTTOM_COLLISION)) {
			return;
		}

		board.getSoko().move(0, SPACE);
		board.getSoko().changePlayerVector(BOTTOM_COLLISION);
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

		board.replayDequeOffer(BOTTOM_COLLISION);

		if (board.getBags() != null) {
			board.isEntered(board.getBags());
			if (board.getBags().getIsEntered()) {
				board.callIsCompleted();
				board.repaint();
				return;
			}
		}

		if (failed.isFailedDetected(board.getBags())) {
			board.callIsFailed();
		}
		return;
	}

	public void keyReturnPressed(KeyEvent e) {

		time.time = 0;
		board.callRestartLevel();

		return;
	}

	public void keyBackPressed(KeyEvent e) {

		if (!board.getReplayDequeEmpty()) {
			board.callUndo();
		}
	}
}