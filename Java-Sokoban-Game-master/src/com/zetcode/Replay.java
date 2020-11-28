package com.zetcode;

import java.io.*;
import java.util.*;

public class Replay {
	private Deque<Integer> replay_Deque ;
	private Stack<Integer> replay_Stack ;

	private boolean undo = false;
	private GameManager gameManager;

	private int backCounter = 0;
	
	Replay(int levelSelected, File file, String selectCharacter, GameManager gameManager){
		replay_Deque = new LinkedList<>();
		replay_Stack = new Stack<>();
		this.gameManager = gameManager;
		
		try {
			FileReader fr = new FileReader(file);
			int c;
			while((c = fr.read()) != -1) {
				replay_Deque.offer(c-48);
			}
			System.out.println(replay_Deque);
			fr.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	Replay(GameManager gameManager){
		replay_Deque = new LinkedList<>();
		replay_Stack = new Stack<>();
		this.gameManager=gameManager;
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
				gameManager.setIsCollision(false);
				if(!undo) {
					replay_Deque.offerFirst(key3);
					key3 = replay_Stack.pop();
				}
				else {
					key3 = replay_Deque.poll();
				}
				
				break;
	
			case 6:
				gameManager.setIsCollision(true);
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
		if(key3==gameManager.LEFT_COLLISION) {
		
			if (gameManager.getIsCollision()) {
				for(int i=0; i<gameManager.getBaggsSize(); i++) {
					Baggage collisionBag = gameManager.getBaggs(i);
					if(gameManager.getSoko().isLeftCollision(collisionBag))
						gameManager.setBags(collisionBag);
				}
				gameManager.getBags().move(gameManager.SPACE, 0);
			}
			gameManager.getSoko().move(gameManager.SPACE, 0);
			gameManager.getSoko().changePlayerVector(gameManager.LEFT_COLLISION);
			
			if(!undo)
				replay_Deque.offerFirst(key3);
			
		}
			
		if(key3==gameManager.RIGHT_COLLISION) {
			if (gameManager.getIsCollision()) {
				for(int i=0; i<gameManager.getBaggsSize(); i++) {
					Baggage collisionBag = gameManager.getBaggs(i);
					if(gameManager.getSoko().isRightCollision(collisionBag))
						gameManager.setBags(collisionBag);
				}
				gameManager.getBags().move(-gameManager.SPACE, 0);
			}

			gameManager.getSoko().move(-gameManager.SPACE, 0);
			gameManager.getSoko().changePlayerVector(gameManager.RIGHT_COLLISION);
			
			if(!undo)
				replay_Deque.offerFirst(key3);
		}
		DoingTopOrBottomGoBack(key3);
	}
		private void DoingTopOrBottomGoBack(int key3) {
		if(key3==gameManager.TOP_COLLISION) {
			if (gameManager.getIsCollision()) {
				for(int i=0; i<gameManager.getBaggsSize(); i++) {
					Baggage collisionBag = gameManager.getBaggs(i);
					if(gameManager.getSoko().isTopCollision(collisionBag))
						gameManager.setBags(collisionBag);
				}
				gameManager.getBags().move(0, gameManager.SPACE);
			}

			gameManager.getSoko().move(0, gameManager.SPACE);
			gameManager.getSoko().changePlayerVector(gameManager.TOP_COLLISION);
			
			if(!undo)
				replay_Deque.offerFirst(key3);
			
		}
		if(key3==gameManager.BOTTOM_COLLISION) {
			if (gameManager.getIsCollision()) {
				for(int i=0; i<gameManager.getBaggsSize(); i++) {
					Baggage collisionBag = gameManager.getBaggs(i);
					if(gameManager.getSoko().isBottomCollision(collisionBag))
						gameManager.setBags(collisionBag);
				}
				gameManager.getBags().move(0, -gameManager.SPACE);
			}

			gameManager.getSoko().move(0, -gameManager.SPACE);
			gameManager.getSoko().changePlayerVector(gameManager.BOTTOM_COLLISION);
			
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
				gameManager.setIsCollision(true);
				key2 = replay_Deque.poll();
				replay_Stack.push(key2);
				replay_Deque.offer(key2);
				break;
				
			case 6:
				gameManager.setIsCollision(false);
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
		if(key2==gameManager.LEFT_COLLISION){

			if (gameManager.getCheckWallCollision(gameManager.getSoko(), gameManager.LEFT_COLLISION)) { // soko객체 왼쪽에 벽이 있다면 움직이지 않고 키 이벤트를 끝냄
				return;
			}

			if (gameManager.getCheckBagCollision(gameManager.LEFT_COLLISION)) {
				return;
			}

			gameManager.getSoko().move(-gameManager.SPACE, 0); 
			gameManager.getSoko().changePlayerVector(gameManager.LEFT_COLLISION);

			if (gameManager.getBags()!= null) {
				gameManager.isEntered(gameManager.getBags());
				if (gameManager.getBags().getIsEntered()) {
					return;
				}
			}
			
			gameManager.callIsFailedDetected(gameManager.getBags());
			

		
		}
		if(key2==gameManager.RIGHT_COLLISION) {
			if (gameManager.getCheckWallCollision(gameManager.getSoko(),gameManager.RIGHT_COLLISION)) {
				return;
			}

			if (gameManager.getCheckBagCollision(gameManager.RIGHT_COLLISION)) {
				return;
			}

			gameManager.getSoko().move(gameManager.SPACE, 0);
			gameManager.getSoko().changePlayerVector(gameManager.RIGHT_COLLISION);

			if (gameManager.getBags() != null) {
				gameManager.isEntered(gameManager.getBags());
				if (gameManager.getBags().getIsEntered()) {
					return;
				}
			}

			gameManager.callIsFailedDetected(gameManager.getBags());
			
		}
		DoingTopOrBottomGoAhead(key2);
	}
		public void DoingTopOrBottomGoAhead(int key2) {
		if(key2==gameManager.TOP_COLLISION) {

			if (gameManager.getCheckWallCollision(gameManager.getSoko(), gameManager.TOP_COLLISION)) {
				return;
			}

			if (gameManager.getCheckBagCollision(gameManager.TOP_COLLISION)) {
				return;
			}

			gameManager.getSoko().move(0, -gameManager.SPACE);
			gameManager.getSoko().changePlayerVector(gameManager.TOP_COLLISION);

			if (gameManager.getBags() != null) {
				gameManager.isEntered(gameManager.getBags());
				if (gameManager.getBags().getIsEntered()) {
					return;
				}
			}

			gameManager.callIsFailedDetected(gameManager.getBags());
			
			
		}
		if(key2==gameManager.BOTTOM_COLLISION) {
			if (gameManager.getCheckWallCollision(gameManager.getSoko(), gameManager.BOTTOM_COLLISION)) {
				return;
			}

			if (gameManager.getCheckBagCollision(gameManager.BOTTOM_COLLISION)) {
				return;
			}

			gameManager.getSoko().move(0, gameManager.SPACE);
			gameManager.getSoko().changePlayerVector(gameManager.BOTTOM_COLLISION);

			if (gameManager.getBags() != null) {
				gameManager.isEntered(gameManager.getBags());
				if (gameManager.getBags().getIsEntered()) {
					return;
				}
			}

			gameManager.callIsFailedDetected(gameManager.getBags());
			
		}
	}
	
	public void offerReplay_Deque(int key) {
		replay_Deque.offer(key);
	}
}