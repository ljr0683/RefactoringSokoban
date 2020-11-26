package com.zetcode;

import java.io.*;
import java.util.*;

public class Replay {
	private Deque<Integer> replay_Deque = new LinkedList<>();
	private Stack<Integer> replay_Stack = new Stack<>();
	private File file;

	private boolean undo = false;
	private BoardManager boardManager;

	private int backCounter = 0;
	
	Replay(int levelSelected, LevelSelectPanel previousPanel, UIManager frame, File file, String selectCharacter){
		replay_Deque = new LinkedList<>();
		
		this.file = file;
		
		try {
			FileReader fr = new FileReader(file);
			int c;
			while((c = fr.read()) != -1) {
				replay_Deque.offer(c-48);
			}
			fr.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		ReplayKeyAdapter replayKeyAdapter = new ReplayKeyAdapter(levelSelected, previousPanel, frame, file, selectCharacter, this);
	}
	
	Replay(BoardManager boardManager){
		this.boardManager=boardManager;
		undo = true;
	}
	
	public void goBack() {
		int key3 = 0 ;
		 
		if(!undo) {
			if (!replay_Stack.isEmpty()) {
				key3 = replay_Stack.pop();
				backCounter++;
			}
		}
		else {
			if(!replay_Deque.isEmpty()) {
				key3 = replay_Deque.poll();
			}
		}
		if(key3 == 5 || key3 == 6) {
			switch (key3) {
			
			case 5:
				boardManager.setIsCollision(false);
				if(!undo) {
					replay_Deque.offerFirst(key3);
					key3 = replay_Stack.pop();
				}
				else {
					key3 = replay_Deque.poll();
				}
				
				break;
	
			case 6:
				boardManager.setIsCollision(true);
				if(!undo) {
					replay_Deque.offerFirst(key3);
					key3 = replay_Stack.pop();
				}
				else {
					key3 = replay_Deque.poll();
				}
				
				break;
	
			}
			
			DoingGoBack(key3);
		}
		else {
			DoingGoBack(key3);
		}
	}
	
	private void DoingGoBack(int key3) {
		if(key3==1) {
		
			if (boardManager.getIsCollision()) {
				for(int i=0; i<boardManager.getBaggsSize(); i++) {
					Baggage collisionBag = boardManager.getBaggs(i);
					if(boardManager.getSoko().isLeftCollision(collisionBag))
						boardManager.setBags(collisionBag);
				}
				boardManager.getBags().move(Board.SPACE, 0);
			}
			boardManager.getSoko().move(Board.SPACE, 0);
			boardManager.getSoko().changePlayerVector(Board.LEFT_COLLISION);
			
			if(!undo)
				replay_Deque.offerFirst(key3);
			
		}
			
		if(key3==2) {
			if (boardManager.getIsCollision()) {
				for(int i=0; i<boardManager.getBaggsSize(); i++) {
					Baggage collisionBag = boardManager.getBaggs(i);
					if(boardManager.getSoko().isRightCollision(collisionBag))
						boardManager.setBags(collisionBag);
				}
				boardManager.getBags().move(-Board.SPACE, 0);
			}

			boardManager.getSoko().move(-Board.SPACE, 0);
			boardManager.getSoko().changePlayerVector(Board.RIGHT_COLLISION);
			
			if(!undo)
				replay_Deque.offerFirst(key3);
		}
		DoingTopOrBottomGoBack(key3);
	}
		private void DoingTopOrBottomGoBack(int key3) {
		if(key3==3) {
			if (boardManager.getIsCollision()) {
				for(int i=0; i<boardManager.getBaggsSize(); i++) {
					Baggage collisionBag = boardManager.getBaggs(i);
					if(boardManager.getSoko().isTopCollision(collisionBag))
						boardManager.setBags(collisionBag);
				}
				boardManager.getBags().move(0, Board.SPACE);
			}

			boardManager.getSoko().move(0, Board.SPACE);
			boardManager.getSoko().changePlayerVector(Board.TOP_COLLISION);
			
			if(!undo)
				replay_Deque.offerFirst(key3);
			
		}
		if(key3==4) {
			if (boardManager.getIsCollision()) {
				for(int i=0; i<boardManager.getBaggsSize(); i++) {
					Baggage collisionBag = boardManager.getBaggs(i);
					if(boardManager.getSoko().isBottomCollision(collisionBag))
						boardManager.setBags(collisionBag);
				}
				boardManager.getBags().move(0, -Board.SPACE);
			}

			boardManager.getSoko().move(0, -Board.SPACE);
			boardManager.getSoko().changePlayerVector(Board.BOTTOM_COLLISION);
			
			if(!undo)
				replay_Deque.offerFirst(key3);
		}
	}
	
	
	public void goAhead() {
		int key2 = replay_Deque.poll();

		replay_Stack.push(key2);
		if (backCounter > 0) {
			backCounter--;
		} else {
			replay_Deque.offer(key2);
		}
		
		if(key2 == 5 || key2 == 6) {
		
			switch (key2) {
				
			case 5:
				boardManager.setIsCollision(true);
				key2 = replay_Deque.poll();
				replay_Stack.push(key2);
				replay_Deque.offer(key2);
				break;
				
			case 6:
				boardManager.setIsCollision(false);
				key2 = replay_Deque.poll();
				replay_Stack.push(key2);
				replay_Deque.offer(key2);
			break;
				
			default:
				break;
			}
			
			DoingGoAhead(key2);
		}
		else {
			DoingGoAhead(key2);
		}
	}
	
	public void DoingGoAhead(int key2) {
		if(key2==1){

			if (boardManager.getCheckWallCollision(boardManager.getSoko(), Board.LEFT_COLLISION)) { // soko객체 왼쪽에 벽이 있다면 움직이지 않고 키 이벤트를 끝냄
				return;
			}

			if (boardManager.getCheckBagCollision(Board.LEFT_COLLISION)) {
				return;
			}

			boardManager.getSoko().move(-Board.SPACE, 0); 
			boardManager.getSoko().changePlayerVector(Board.LEFT_COLLISION);

			if (boardManager.getBags()!= null) {
				boardManager.isEntered(boardManager.getBags());
				if (boardManager.getBags().getIsEntered()) {
					return;
				}
			}
			
			boardManager.callIsFailedDetected(boardManager.getBags());
			

		
		}
		if(key2==2) {
			if (boardManager.getCheckWallCollision(boardManager.getSoko(),Board.RIGHT_COLLISION)) {
				return;
			}

			if (boardManager.getCheckBagCollision(Board.RIGHT_COLLISION)) {
				return;
			}

			boardManager.getSoko().move(Board.SPACE, 0);
			boardManager.getSoko().changePlayerVector(Board.RIGHT_COLLISION);

			if (boardManager.getBags() != null) {
				boardManager.isEntered(boardManager.getBags());
				if (boardManager.getBags().getIsEntered()) {
					return;
				}
			}

			boardManager.callIsFailedDetected(boardManager.getBags());
			
		}
		DoingTopOrBottomGoAhead(key2);
	}
		public void DoingTopOrBottomGoAhead(int key2) {
		if(key2==3) {

			if (boardManager.getCheckWallCollision(boardManager.getSoko(), Board.TOP_COLLISION)) {
				return;
			}

			if (boardManager.getCheckBagCollision(Board.TOP_COLLISION)) {
				return;
			}

			boardManager.getSoko().move(0, -Board.SPACE);
			boardManager.getSoko().changePlayerVector(Board.TOP_COLLISION);

			if (boardManager.getBags() != null) {
				boardManager.isEntered(boardManager.getBags());
				if (boardManager.getBags().getIsEntered()) {
					return;
				}
			}

			boardManager.callIsFailedDetected(boardManager.getBags());
			
			
		}
		if(key2==4) {
			if (boardManager.getCheckWallCollision(boardManager.getSoko(), Board.BOTTOM_COLLISION)) {
				return;
			}

			if (boardManager.getCheckBagCollision(Board.BOTTOM_COLLISION)) {
				return;
			}

			boardManager.getSoko().move(0, Board.SPACE);
			boardManager.getSoko().changePlayerVector(Board.BOTTOM_COLLISION);

			if (boardManager.getBags() != null) {
				boardManager.isEntered(boardManager.getBags());
				if (boardManager.getBags().getIsEntered()) {
					return;
				}
			}

			boardManager.callIsFailedDetected(boardManager.getBags());
			
		}
	}
	
	public void offerReplay_Deque(int key) {
		replay_Deque.offer(key);
	}
	
	public void setBoardManager(BoardManager boardManager) {
		this.boardManager = boardManager;
	}
}