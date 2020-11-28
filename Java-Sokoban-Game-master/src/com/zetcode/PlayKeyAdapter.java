package com.zetcode;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.Timer;

public class PlayKeyAdapter extends KeyAdapter {
	private MyTimer time;
	private Timer timer;
	private GameManager gameManager;
	private JLabel[] boomLabel;

	private int mode;

	public PlayKeyAdapter(MyTimer time, int mode, Timer timer, GameManager gameManager, JLabel[] boomLabel) {
		this.time = time;
		this.mode = mode;
		this.timer = timer;
		this.gameManager = gameManager;
		this.boomLabel = boomLabel;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (gameManager.getIsCompleted()) { // 게임이 끝남.
			return;
		}

		if (gameManager.getIsFailed()) {
			timer.stop();
			return;
		}
		
		if(boomLabel!=null) {
			for(int i=0; i<boomLabel.length; i++) {
				boomLabel[i].setVisible(false);
			}
		}

		int key = e.getKeyCode();
		gameManager.setFlag(false);
		
		switch(key) {
		case KeyEvent.VK_LEFT :
			keyLeftPressed(e);
			break;
		case KeyEvent.VK_RIGHT :
			keyRightPressed(e);
			break;
		case KeyEvent.VK_UP :
			keyUpPressed(e);
			break;
		case KeyEvent.VK_DOWN :
			keyDownPressed(e);
			break;
		case KeyEvent.VK_R :
			keyReturnPressed(e);
			break;
		case KeyEvent.VK_BACK_SPACE :
			keyBackPressed(e);
			break;
		default :
			break;
		}
		
		
		modeTwo();

		gameManager.repaint();
		return;
	}
	
	private void modeTwo() {
		if (mode == 2) {

			for (int i = 0; i < gameManager.getWallsSize(); i++) {

				Wall next = gameManager.getWalls(i);

				if (next instanceof Llm && gameManager.getMoveCount() >= 1) {

					((Llm) next).rellm();

				}
			}
		}
	}

	public void keyLeftPressed(KeyEvent e) {

		if (gameManager.getCheckWallCollision(gameManager.getSoko(), gameManager.LEFT_COLLISION)) { // soko객체 왼쪽에 벽이 있다면 움직이지 않고 키 이벤트를 끝냄
			return;
		}

		if (gameManager.getCheckBagCollision(gameManager.LEFT_COLLISION)) {
			
			return;
		}

		gameManager.getSoko().move(-gameManager.SPACE, 0); // 만약 위 상황을 만족하지 않는다면 왼쪽으로 한칸 움직임.
		gameManager.getSoko().changePlayerVector(gameManager.LEFT_COLLISION);
		gameManager.boardIncreaseMoveCount();

		if (gameManager.getFlag()) {
			if (!gameManager.getIsCollision()) {
				gameManager.replayDequeOffer(5);
			}
			gameManager.setIsCollision(true);
		} else {
			if (gameManager.getIsCollision()) {
				gameManager.replayDequeOffer(6);
			}

			gameManager.setIsCollision(false);
		}

		gameManager.replayDequeOffer(gameManager.LEFT_COLLISION);

		if (gameManager.getBags() != null) {
			gameManager.isEntered(gameManager.getBags());
			if (gameManager.getBags().getIsEntered()) {
				gameManager.callIsCompleted();
				gameManager.repaint();
				return;
			}
		}


		
		gameManager.callIsFailedDetected(gameManager.getBags());
		return;
	}

	public void keyRightPressed(KeyEvent e) {
		if (gameManager.getCheckWallCollision(gameManager.getSoko(), gameManager.RIGHT_COLLISION)) {
			return;
		}

		if (gameManager.getCheckBagCollision(gameManager.RIGHT_COLLISION)) {
			return;
		}

		gameManager.getSoko().move(gameManager.SPACE, 0);
		gameManager.getSoko().changePlayerVector(gameManager.RIGHT_COLLISION);
		gameManager.boardIncreaseMoveCount();

		if (gameManager.getFlag()) {
			if (!gameManager.getIsCollision()) {
				gameManager.replayDequeOffer(5);
			}
			gameManager.setIsCollision(true);
		} else {
			if (gameManager.getIsCollision()) {
				gameManager.replayDequeOffer(6);
			}

			gameManager.setIsCollision(false);
		}

		gameManager.replayDequeOffer(gameManager.RIGHT_COLLISION);

		if (gameManager.getBags() != null) {
			gameManager.isEntered(gameManager.getBags());
			if (gameManager.getBags().getIsEntered()) {
				gameManager.callIsCompleted();
				gameManager.repaint();
				return;
			}
		}
		
		gameManager.callIsFailedDetected(gameManager.getBags());
		
		return;
	}

	public void keyUpPressed(KeyEvent e) {

		if (gameManager.getCheckWallCollision(gameManager.getSoko(), gameManager.TOP_COLLISION)) {
			return;
		}

		if (gameManager.getCheckBagCollision(gameManager.TOP_COLLISION)) {
			return;
		}

		gameManager.getSoko().move(0, -gameManager.SPACE);
		gameManager.getSoko().changePlayerVector(gameManager.TOP_COLLISION);
		gameManager.boardIncreaseMoveCount();

		if (gameManager.getFlag()) {
			if (!gameManager.getIsCollision()) {
				gameManager.replayDequeOffer(5);
			}
			gameManager.setIsCollision(true);
		} else {
			if (gameManager.getIsCollision()) {
				gameManager.replayDequeOffer(6);
			}

			gameManager.setIsCollision(false);
		}

		gameManager.replayDequeOffer(gameManager.TOP_COLLISION);

		if (gameManager.getBags() != null) {
			gameManager.isEntered(gameManager.getBags());
			if (gameManager.getBags().getIsEntered()) {
				gameManager.callIsCompleted();
				gameManager.repaint();
				return;
			}
		}
		
		gameManager.callIsFailedDetected(gameManager.getBags());
		
		return;

	}

	public void keyDownPressed(KeyEvent e) {

		if (gameManager.getCheckWallCollision(gameManager.getSoko(), gameManager.BOTTOM_COLLISION)) {
			return;
		}

		if (gameManager.getCheckBagCollision(gameManager.BOTTOM_COLLISION)) {
			return;
		}

		gameManager.getSoko().move(0, gameManager.SPACE);
		gameManager.getSoko().changePlayerVector(gameManager.BOTTOM_COLLISION);
		gameManager.boardIncreaseMoveCount();

		if (gameManager.getFlag()) {
			if (!gameManager.getIsCollision()) {
				gameManager.replayDequeOffer(5);
			}
			gameManager.setIsCollision(true);
		} else {
			if (gameManager.getIsCollision()) {
				gameManager.replayDequeOffer(6);
			}

			gameManager.setIsCollision(false);
		}

		gameManager.replayDequeOffer(gameManager.BOTTOM_COLLISION);

		if (gameManager.getBags() != null) {
			gameManager.isEntered(gameManager.getBags());
			if (gameManager.getBags().getIsEntered()) {
				gameManager.callIsCompleted();
				gameManager.repaint();
				return;
			}
		}
		
		gameManager.callIsFailedDetected(gameManager.getBags());
		
		return;
	}

	public void keyReturnPressed(KeyEvent e) {

		time.time = 0;
		gameManager.restartLevel();
		
		return;
	}

	public void keyBackPressed(KeyEvent e) {

		if (!gameManager.getReplayDequeEmpty()) {
			gameManager.undo();
		}
	}
}