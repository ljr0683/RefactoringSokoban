package com.zetcode;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;

import javax.swing.Timer;

public class PlayKeyAdapter extends KeyAdapter{
	
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
	
	public PlayKeyAdapter(Board board, MyTimer time, int mode, Timer timer ) {
		this.board = board;
		checkCollision = new CheckCollision(board);
		failed = new FailedDetected(board);
		this.time = time;
		this.mode = mode;
		this.timer = timer;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if (board.getIsCompleted()) { // 게임이 끝남.
			
			return;
		}

		if (board.getIsFailed()) {
			timer.stop();
			return;
		}
		
		int key = e.getKeyCode();
		board.setFlag(false);

		switch (key) {

		case KeyEvent.VK_LEFT:
			
			if (checkCollision.checkWallCollision(board.getSoko(), LEFT_COLLISION)) { // soko객체 왼쪽에 벽이 있다면 움직이지 않고 키 이벤트를 끝냄
				return;
			}

			if (checkCollision.checkBagCollision(LEFT_COLLISION)) {
				return;
			}

			board.getSoko().move(-SPACE, 0); // 만약 위 상황을 만족하지 않는다면 왼쪽으로 한칸 움직임.
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
					break;
				}
			}

			if (failed.isFailedDetected(board.getBags())) {
				board.callIsFailed();
			}

			break;

		case KeyEvent.VK_RIGHT:
			
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
					break;
				}
			}

			if (failed.isFailedDetected(board.getBags())) {
				board.callIsFailed();
			}

			break;

		case KeyEvent.VK_UP:
			
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
					break;
				}
			}

			if (failed.isFailedDetected(board.getBags())) {
				board.callIsFailed();
			}

			break;

		case KeyEvent.VK_DOWN:

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
					break;
				}
			}

			if (failed.isFailedDetected(board.getBags())) {
				board.callIsFailed();
			}

			break;

		case KeyEvent.VK_R:
			time.time = 0;
			board.setMoveCount(0);
			board.callRestartLevel();

			break;
			
		case KeyEvent.VK_BACK_SPACE :
			if(!board.getReplayDequeEmpty() ) {
				if(board.getUndoCount()>=1) {
					board.callUndo();
				}
				
			}
			
			break;

		default:
			break;
			}
			Iterator<Wall> iter = board.getWallsArrayList().iterator();// 이 부분부터
			
			if (mode == 2) {
				
				for (int i = 0; i < board.getWallsSize(); i++) {
					
					Wall next = iter.next();
					
					if (next instanceof Llm && board.getMoveCount() >= 1) {
						
						((Llm) next).rellm();
						
					}
				}
			} // 이부분까지 설정
		
		board.callIsCompleted();
		board.repaint();
		}
	}