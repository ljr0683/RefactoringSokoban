package com.zetcode;

public class CheckCollision {
	private Board board;
	private BoardManager boardManager;
	private Baggage bag;
	private Baggage item;
	
	public CheckCollision(Board board) {
		this.board = board;
	}
	
	public CheckCollision(BoardManager boardManager) {
		this.boardManager = boardManager;
	}
	
	public boolean checkWallCollision(Actor actor, int type) {

		if(type==boardManager.LEFT_COLLISION) {

			for (int i = 0; i < boardManager.getWallsSize(); i++) {

				Wall wall = boardManager.getWalls(i); 

				if (actor.isLeftCollision(wall)) { 

					return true;
				}
			}

			return false; 
		}
			else if(type==boardManager.RIGHT_COLLISION) {
			
			for (int i = 0; i < boardManager.getWallsSize(); i++) {

				Wall wall = boardManager.getWalls(i);

				if (actor.isRightCollision(wall)) {
					
					return true;
				}
			}

			return false;
		}
		return checkTopOrBottomWallCollision(actor, type);
		}
		
		public boolean checkTopOrBottomWallCollision(Actor actor, int type) {
		if(type==boardManager.TOP_COLLISION) {
			for (int i = 0; i < boardManager.getWallsSize(); i++) {

				Wall wall = boardManager.getWalls(i);

				if (actor.isTopCollision(wall)) {

					return true;
				}
			}

			return false;
		}
		else if(type==boardManager.BOTTOM_COLLISION) {

			for (int i = 0; i < boardManager.getWallsSize(); i++) {

				Wall wall = boardManager.getWalls(i);

				if (actor.isBottomCollision(wall)) {

					return true;
				}
			}

			return false;
		}
			

		return false;
	
}

	public boolean checkBagCollision(int type) { 

		if(type==boardManager.LEFT_COLLISION) { 

			for (int i = 0; i < boardManager.getBaggsSize(); i++) {

				this.bag = boardManager.getBaggs(i);

				if (boardManager.getSoko().isLeftCollision(this.bag)) { 

					for (int j = 0; j < boardManager.getBaggsSize(); j++) {

						this.item = boardManager.getBaggs(j); 

						if (!this.bag.equals(this.item)) { 
							
							if (this.bag.isLeftCollision(this.item)) {
								return true;
							}
						}

						if (checkWallCollision(this.bag, boardManager.LEFT_COLLISION)) {
							return true;
						}
					}
					
					this.bag.move(-boardManager.SPACE, 0);
					boardManager.setFlag(true);
					boardManager.setBags(this.bag);

				}

			}

			return false;
		}
		if(type==boardManager.RIGHT_COLLISION) { 

			for (int i = 0; i < boardManager.getBaggsSize(); i++) {

				Baggage bag = boardManager.getBaggs(i); 

				if (boardManager.getSoko().isRightCollision(bag)) { 

					for (int j = 0; j < boardManager.getBaggsSize(); j++) {

						Baggage item = boardManager.getBaggs(j); 

						if (!bag.equals(item)) { 

							if (bag.isRightCollision(item)) { 
								return true;
							}
						}

						if (checkWallCollision(bag, boardManager.RIGHT_COLLISION)) { 
							return true;
						}
					}
					
					bag.move(boardManager.SPACE, 0);
					boardManager.setFlag(true);
					boardManager.setBags(bag);
					
				}

			}

			return false;
		}
		return checkTopOrBottomBagCollision(type);
	}
	public boolean checkTopOrBottomBagCollision(int type) {
		if(type==boardManager.TOP_COLLISION) {

			for (int i = 0; i < boardManager.getBaggsSize(); i++) {
				
				Baggage bag = boardManager.getBaggs(i);

				if (boardManager.getSoko().isTopCollision(bag)) {

					for (int j = 0; j < boardManager.getBaggsSize(); j++) {

						Baggage item = boardManager.getBaggs(j);

						if (!bag.equals(item)) {

							if (bag.isTopCollision(item)) {
								return true;
							}
						}

						if (checkWallCollision(bag, boardManager.TOP_COLLISION)) {
							return true;
						}
					}

					bag.move(0, -boardManager.SPACE);
					boardManager.setFlag(true);
					boardManager.setBags(bag);

				}

			}

			return false;
		}
		if(type==boardManager.BOTTOM_COLLISION) {

			for (int i = 0; i < boardManager.getBaggsSize(); i++) {

				Baggage bag = boardManager.getBaggs(i);

				if (boardManager.getSoko().isBottomCollision(bag)) {

					for (int j = 0; j < boardManager.getBaggsSize(); j++) {

						Baggage item = boardManager.getBaggs(j);

						if (!bag.equals(item)) {

							if (bag.isBottomCollision(item)) {
								return true;
							}
						}

						if (checkWallCollision(bag, boardManager.BOTTOM_COLLISION)) {

							return true;
						}
					}

					bag.move(0, boardManager.SPACE);
					boardManager.setFlag(true);
					boardManager.setBags(bag);

				}

			}
		}
		return false;
	}
}