package com.zetcode;

public class FailedDetected {
	private Board board;
	
	public FailedDetected(Board board) {
		this.board=board;	
	}
	
	public boolean isFailedDetected(Baggage bag) { // failed 조건 detected 메소드

		if (bag != null) {			
			if(wallTopBottomCollision(bag)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean wallTopBottomCollision(Baggage bag) {
		if (board.getCheckWallCollision(bag, Board.TOP_COLLISION) || board.getCheckWallCollision(bag, Board.BOTTOM_COLLISION)) { // 위, 아래 벽 두개 // 리팩토링 완료

			for (int i = 0; i < board.getWallsSize(); i++) {
				Wall item1 = board.getWalls(i); //item1 = 위에 있는 벽
				if (bag.isTopCollision(item1) || bag.isBottomCollision(item1)) {

					for (int j = 0; j < board.getWallsSize(); j++) {
						Wall item2 = board.getWalls(j);
						if (!item2.equals(item1) && item1.isLeftCollision(item2) || item1.isRightCollision(item2)) { // item2는 위의 양 옆의 벽

							for (int k = 0; k < board.getBaggsSize(); k++) {
								Baggage item3 = board.getBaggs(k);
								if (!item3.equals(bag) && item2.isBottomCollision(item3) || item2.isTopCollision(item3)) { // item3는 item2 아래의 bag
									return true;
								}
							}
						}
					}
				}
			}
			if (board.getCheckWallCollision(bag, Board.LEFT_COLLISION) || board.getCheckWallCollision(bag, Board.RIGHT_COLLISION)) {
				return true;
			}
		}
		wallLeftRightCollison(bag);
		return false;
	}
	
	private boolean wallLeftRightCollison(Baggage bag) {
		if (board.getCheckWallCollision(bag, Board.LEFT_COLLISION) || board.getCheckWallCollision(bag, Board.RIGHT_COLLISION)) { // 왼, 오른쪽 벽 2개 리팩토링 끝
			for (int i = 0; i < board.getWallsSize(); i++) {

				Wall item1 = board.getWalls(i);

				if (bag.isLeftCollision(item1) || bag.isRightCollision(item1)) {

					for (int j = 0; j < board.getWallsSize(); j++) {

						Wall item2 = board.getWalls(j);

						if (!item2.equals(item1) && item1.isTopCollision(item2) || item1.isBottomCollision(item2)) {
							for (int k = 0; k < board.getBaggsSize(); k++) {
								Baggage item3 = board.getBaggs(k);
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
		oneWallTopOrBottom(bag);
		return false;
	}
	
	private boolean oneWallTopOrBottom(Baggage bag) {
		if (board.getCheckWallCollision(bag, Board.TOP_COLLISION) || board.getCheckWallCollision(bag, Board.BOTTOM_COLLISION)) { // 위에 벽 1개 , 아래 벽 1개 수정한거임 리팩토링
			for (int i = 0; i < board.getBaggsSize(); i++) {
				Baggage item1 = board.getBaggs(i);
				LeftRightBagCollision(bag, item1);
			}
		} //
		oneWallLeftOrRight(bag);
		return false;
	}
	
	private boolean LeftRightBagCollision(Baggage bag, Baggage item) {
		if(!item.equals(bag) && bag.isLeftCollision(item) || bag.isRightCollision(item)) {
			for (int j = 0; j < board.getWallsSize(); j++) {
				Wall item2 = board.getWalls(j);
				wallBagTopBottomCollision(bag, item2);
				
			}
		}
		return false;
	}
	
	private boolean wallBagTopBottomCollision(Baggage bag, Wall item) {
		if (bag.isBottomCollision(item) || bag.isTopCollision(item)) { // item2는 bag객체 위나 아래의 벽
			for (int k = 0; k < board.getBaggsSize(); k++) {
				Baggage item3 = board.getBaggs(k);
				if (!item3.equals(item) && !item3.equals(bag)) { // item3는 item2(벽) 양 옆의 객체
					if (item.isLeftCollision(item3) || item.isRightCollision(item3)) {
						return true;
					}
					if (bag.isTopCollision(item) && item.isBottomCollision(item3)
							|| bag.isBottomCollision(item) && item.isTopCollision(item3)) {
						for (int h = 0; h < board.getWallsSize(); h++) {
							Wall item4 = board.getWalls(h);
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
		if (board.getCheckWallCollision(bag, Board.LEFT_COLLISION) || board.getCheckWallCollision(bag, Board.RIGHT_COLLISION)) { 
			for (int i = 0; i < board.getBaggsSize(); i++) {
				Baggage item1 = board.getBaggs(i);
				topBottomBagCollosion(bag, item1);
				
			}
		}
		topBag(bag);
		return false;
	}
	
	private boolean topBottomBagCollosion(Baggage bag, Baggage item) {
		if (!item.equals(bag) && bag.isTopCollision(item) || bag.isBottomCollision(item)) { //item1은 bag 왼쪽 벽

			for (int j = 0; j < board.getWallsSize(); j++) {
				Wall item2 =  board.getWalls(j);
				wallBagLeftRightCollison(bag, item2);
			}
		}
		return false;
	}
	
	private boolean wallBagLeftRightCollison(Baggage bag, Wall item) {
		if (bag.isRightCollision(item) || bag.isBottomCollision(item)) { //item2는 
			for (int k = 0; k < board.getBaggsSize(); k++) {
				Baggage item3 = board.getBaggs(k);

				if (!item3.equals(item) && !item3.equals(bag)) {
					if (item.isTopCollision(item3) || item.isRightCollision(item3)) {
						return true;
					}

					if (bag.isRightCollision(item) && item.isLeftCollision(item3)
							|| bag.isLeftCollision(item) && item.isRightCollision(item3)) {
						for (int h = 0; h <  board.getWallsSize(); h++) {
							Wall item4 = board.getWalls(h);
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
		for (int i = 0; i < board.getBaggsSize(); i++) { // 4개다 bag일때
			Baggage item1 = board.getBaggs(i);
			if (!item1.equals(bag) && bag.isTopCollision(item1)) {

				for (int j = 0; j < board.getBaggsSize(); j++) {

					Baggage item2 =board.getBaggs(j);

					if (!item2.equals(item1) && !item2.equals(bag)) {

						if (item1.isLeftCollision(item2) || item1.isRightCollision(item2)) {

							for (int k = 0; k < board.getBaggsSize(); k++) {

								Baggage item3 = board.getBaggs(k);

								if (item2.isBottomCollision(item3)) {
									return true;
								}
							}
						}
					}
				}
			}
		}
		bottomBag(bag);
		return false;
	}
	
	private boolean bottomBag(Baggage bag) {
		for (int i = 0; i < board.getBaggsSize(); i++) { // 4개다 bag일때
			Baggage item1 = board.getBaggs(i);
			if (!item1.equals(bag) && bag.isBottomCollision(item1)) {

				for (int j = 0; j < board.getBaggsSize(); j++) {

					Baggage item2 = board.getBaggs(j);

					if (!item2.equals(item1) && !item2.equals(bag)) {

						if (item1.isLeftCollision(item2) || item1.isRightCollision(item2)) {

							for (int k = 0; k < board.getBaggsSize(); k++) {

								Baggage item3 = board.getBaggs(k);

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