package com.zetcode;

import java.util.Deque;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class DetectedIsFailed {
	private GameManager gameManager;
	private Deque<Integer> replay_Deque; 

	public DetectedIsFailed(GameManager gameManager) {
		this.gameManager = gameManager;
	}
	
	public void isFailed() {
		
		gameManager.setIsFailed(true);
		
		if(gameManager.getIsFailed()) {
			this.replay_Deque = gameManager.getReplayDeque();
			gameManager.boardSetZeroMoveCount();
			String s = "Failed";
			FileIO fileio = new FileIO();
			if(replay_Deque!=null) {
				int size = replay_Deque.size();
			
				for (int i = 0; i < size; i++) {
					fileio.enqueue(replay_Deque.poll());
				}
	
				fileio.replayFileInput(gameManager.getLevelSelected(), s);
			}
			
			ImageIcon failedImage = new ImageIcon("src/resources/Complete & Failed/Failed.png");
			JLabel failedLabel = new JLabel(failedImage);
			
			gameManager.attachLabel(failedLabel);
			failedLabel.setBounds(0, 0, gameManager.getWidth(), gameManager.getHeight());
		}
	}
	
	public boolean isFailedDetected(Baggage bag) { // failed ���� detected �޼ҵ�

		if (bag != null) {			
			if(wallTopBottomCollision(bag)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean wallTopBottomCollision(Baggage bag) {
		if (gameManager.getCheckWallCollision(bag, gameManager.TOP_COLLISION) || gameManager.getCheckWallCollision(bag, gameManager.BOTTOM_COLLISION)) { // ��, �Ʒ� �� �ΰ� // �����丵 �Ϸ�

			for (int i = 0; i < gameManager.getWallsSize(); i++) {
				Wall item1 = gameManager.getWalls(i); //item1 = ���� �ִ� ��
				if (bag.isTopCollision(item1) || bag.isBottomCollision(item1)) {

					for (int j = 0; j < gameManager.getWallsSize(); j++) {
						Wall item2 = gameManager.getWalls(j);
						if (!item2.equals(item1) && item1.isLeftCollision(item2) || item1.isRightCollision(item2)) { // item2�� ���� �� ���� ��

							for (int k = 0; k < gameManager.getBaggsSize(); k++) {
								Baggage item3 = gameManager.getBaggs(k);
								if (!item3.equals(bag) && item2.isBottomCollision(item3) || item2.isTopCollision(item3)) { // item3�� item2 �Ʒ��� bag
									return true;
								}
							}
						}
					}
				}
			}
			if (gameManager.getCheckWallCollision(bag, gameManager.LEFT_COLLISION) || gameManager.getCheckWallCollision(bag, gameManager.RIGHT_COLLISION)) {
				return true;
			}
		}
		
		return wallLeftRightCollison(bag);
	}
	
	private boolean wallLeftRightCollison(Baggage bag) {
		if (gameManager.getCheckWallCollision(bag, gameManager.LEFT_COLLISION) || gameManager.getCheckWallCollision(bag, gameManager.RIGHT_COLLISION)) { // ��, ������ �� 2�� �����丵 ��
			for (int i = 0; i < gameManager.getWallsSize(); i++) {

				Wall item1 = gameManager.getWalls(i);

				if (bag.isLeftCollision(item1) || bag.isRightCollision(item1)) {

					for (int j = 0; j < gameManager.getWallsSize(); j++) {

						Wall item2 = gameManager.getWalls(j);

						if (!item2.equals(item1) && item1.isTopCollision(item2) || item1.isBottomCollision(item2)) {
							for (int k = 0; k < gameManager.getBaggsSize(); k++) {
								Baggage item3 = gameManager.getBaggs(k);
								if (!item3.equals(bag) && item2.isRightCollision(item3)
										|| item2.isLeftCollision(item3)) {
									return true;
								}
							}
						}
					}
				}
			}
		}
		
		return oneWallTopOrBottom(bag);
	}
	
	private boolean oneWallTopOrBottom(Baggage bag) {
		if (gameManager.getCheckWallCollision(bag, gameManager.TOP_COLLISION) || gameManager.getCheckWallCollision(bag, gameManager.BOTTOM_COLLISION)) { // ���� �� 1�� , �Ʒ� �� 1�� �����Ѱ��� �����丵
			for (int i = 0; i < gameManager.getBaggsSize(); i++) {
				Baggage item1 = gameManager.getBaggs(i);
				LeftRightBagCollision(bag, item1);
			}
		} //
		
		return oneWallLeftOrRight(bag);
	}
	
	private boolean LeftRightBagCollision(Baggage bag, Baggage item) {
		if(!item.equals(bag) && bag.isLeftCollision(item) || bag.isRightCollision(item)) {
			for (int j = 0; j < gameManager.getWallsSize(); j++) {
				Wall item2 = gameManager.getWalls(j);
				wallBagTopBottomCollision(bag, item2);
				
			}
		}
		return false;
	}
	
	private boolean wallBagTopBottomCollision(Baggage bag, Wall item) {
		if (bag.isBottomCollision(item) || bag.isTopCollision(item)) { // item2�� bag��ü ���� �Ʒ��� ��
			for (int k = 0; k < gameManager.getBaggsSize(); k++) {
				Baggage item3 = gameManager.getBaggs(k);
				if (!item3.equals(item) && !item3.equals(bag)) { // item3�� item2(��) �� ���� ��ü
					if (item.isLeftCollision(item3) || item.isRightCollision(item3)) {
						return true;
					}
					if (bag.isTopCollision(item) && item.isBottomCollision(item3)
							|| bag.isBottomCollision(item) && item.isTopCollision(item3)) {
						for (int h = 0; h < gameManager.getWallsSize(); h++) {
							Wall item4 = gameManager.getWalls(h);
							if (!item4.equals(item) && item3.isRightCollision(item4)
									|| item3.isLeftCollision(item4)) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean oneWallLeftOrRight(Baggage bag) {
		if (gameManager.getCheckWallCollision(bag, gameManager.LEFT_COLLISION) || gameManager.getCheckWallCollision(bag, gameManager.RIGHT_COLLISION)) { 
			for (int i = 0; i < gameManager.getBaggsSize(); i++) {
				Baggage item1 = gameManager.getBaggs(i);
				topBottomBagCollosion(bag, item1);
			}
		}
		
		return topBag(bag);
	}
	
	private boolean topBottomBagCollosion(Baggage bag, Baggage item) {
		if (!item.equals(bag) && bag.isTopCollision(item) || bag.isBottomCollision(item)) { //item1�� bag ���� ��

			for (int j = 0; j < gameManager.getWallsSize(); j++) {
				Wall item2 =  gameManager.getWalls(j);
				wallBagLeftRightCollison(bag, item2);
			}
		}
		return false;
	}
	
	private boolean wallBagLeftRightCollison(Baggage bag, Wall item) {
		if (bag.isRightCollision(item) || bag.isBottomCollision(item)) { 
			for (int k = 0; k < gameManager.getBaggsSize(); k++) {
				Baggage item3 = gameManager.getBaggs(k);

				if (!item3.equals(item) && !item3.equals(bag)) {
					if (item.isTopCollision(item3) || item.isRightCollision(item3)) {
						return true;
					}

					if (bag.isRightCollision(item) && item.isLeftCollision(item3)
							|| bag.isLeftCollision(item) && item.isRightCollision(item3)) {
						for (int h = 0; h <  gameManager.getWallsSize(); h++) {
							Wall item4 = gameManager.getWalls(h);
							if (!item4.equals(item) && item3.isTopCollision(item4)
									|| item3.isBottomCollision(item4)) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
		
	}
	
	private boolean topBag(Baggage bag) {
		for (int i = 0; i < gameManager.getBaggsSize(); i++) { // 4���� bag�϶�
			Baggage item1 = gameManager.getBaggs(i);
			if (!item1.equals(bag) && bag.isTopCollision(item1)) {

				for (int j = 0; j < gameManager.getBaggsSize(); j++) {

					Baggage item2 =gameManager.getBaggs(j);

					if (!item2.equals(item1) && !item2.equals(bag)) {

						if (item1.isLeftCollision(item2) || item1.isRightCollision(item2)) {

							for (int k = 0; k < gameManager.getBaggsSize(); k++) {

								Baggage item3 = gameManager.getBaggs(k);

								if (item2.isBottomCollision(item3)) {
									return true;
								}
							}
						}
					}
				}
			}
		}
		
		return bottomBag(bag);
	}
	
	private boolean bottomBag(Baggage bag) {
		for (int i = 0; i < gameManager.getBaggsSize(); i++) { // 4���� bag�϶�
			Baggage item1 = gameManager.getBaggs(i);
			if (!item1.equals(bag) && bag.isBottomCollision(item1)) {

				for (int j = 0; j < gameManager.getBaggsSize(); j++) {

					Baggage item2 = gameManager.getBaggs(j);

					if (!item2.equals(item1) && !item2.equals(bag)) {

						if (item1.isLeftCollision(item2) || item1.isRightCollision(item2)) {

							for (int k = 0; k < gameManager.getBaggsSize(); k++) {

								Baggage item3 = gameManager.getBaggs(k);

								if (item2.isTopCollision(item3)) {
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
}