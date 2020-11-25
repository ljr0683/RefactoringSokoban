package com.zetcode;

public class CheckCollision {
	private Board board;
	private Baggage bag;
	private Baggage item;
	
	public CheckCollision(Board board) {
		this.board = board;
	}
	
	public boolean checkWallCollision(Actor actor, int type) {

		if(type==1) {

			for (int i = 0; i < board.getWallsSize(); i++) {

				Wall wall = board.getWalls(i); 

				if (actor.isLeftCollision(wall)) { 

					return true;
				}
			}

			return false; 
		}
			else if(type==2) {
			
			for (int i = 0; i < board.getWallsSize(); i++) {

				Wall wall = board.getWalls(i);

				if (actor.isRightCollision(wall)) {
					
					return true;
				}
			}

			return false;
		}
		return checkTopOrBottomWallCollision(actor, type);
		}
		
		public boolean checkTopOrBottomWallCollision(Actor actor, int type) {
		if(type==3) {

			for (int i = 0; i < board.getWallsSize(); i++) {

				Wall wall = board.getWalls(i);

				if (actor.isTopCollision(wall)) {

					return true;
				}
			}

			return false;
		}
		else if(type==4) {

			for (int i = 0; i < board.getWallsSize(); i++) {

				Wall wall = board.getWalls(i);

				if (actor.isBottomCollision(wall)) {

					return true;
				}
			}

			return false;
		}
			

		return false;
	
}

	public boolean checkBagCollision(int type) { 

		if(type==1) { 

			for (int i = 0; i < board.getBaggsSize(); i++) {

				this.bag = board.getBaggs(i);

				if (board.getSoko().isLeftCollision(this.bag)) { 

					for (int j = 0; j < board.getBaggsSize(); j++) {

						this.item = board.getBaggs(j); 

						if (!this.bag.equals(this.item)) { 
							
							if (this.bag.isLeftCollision(this.item)) {
								return true;
							}
						}

						if (checkWallCollision(this.bag, Board.LEFT_COLLISION)) {
							return true;
						}
					}
					
					this.bag.move(-Board.SPACE, 0);
					board.setFlag(true);
					board.setBags(this.bag);

				}

			}

			return false;
		}
		if(type==2) { 

			for (int i = 0; i < board.getBaggsSize(); i++) {

				Baggage bag = board.getBaggs(i); 

				if (board.getSoko().isRightCollision(bag)) { 

					for (int j = 0; j < board.getBaggsSize(); j++) {

						Baggage item = board.getBaggs(j); 

						if (!bag.equals(item)) { 

							if (bag.isRightCollision(item)) { 
								return true;
							}
						}

						if (checkWallCollision(bag, Board.RIGHT_COLLISION)) { 
							return true;
						}
					}
					
					bag.move(Board.SPACE, 0);
					board.setFlag(true);
					board.setBags(bag);
					
				}

			}

			return false;
		}
		return checkTopOrBottomBagCollision(type);
	}
	public boolean checkTopOrBottomBagCollision(int type) {
		if(type==3) {

			for (int i = 0; i < board.getBaggsSize(); i++) {
				
				Baggage bag = board.getBaggs(i);

				if (board.getSoko().isTopCollision(bag)) {

					for (int j = 0; j < board.getBaggsSize(); j++) {

						Baggage item = board.getBaggs(j);

						if (!bag.equals(item)) {

							if (bag.isTopCollision(item)) {
								return true;
							}
						}

						if (checkWallCollision(bag, Board.TOP_COLLISION)) {
							return true;
						}
					}

					bag.move(0, -Board.SPACE);
					board.setFlag(true);
					board.setBags(bag);

				}

			}

			return false;
		}
		if(type==4) {

			for (int i = 0; i < board.getBaggsSize(); i++) {

				Baggage bag = board.getBaggs(i);

				if (board.getSoko().isBottomCollision(bag)) {

					for (int j = 0; j < board.getBaggsSize(); j++) {

						Baggage item = board.getBaggs(j);

						if (!bag.equals(item)) {

							if (bag.isBottomCollision(item)) {
								return true;
							}
						}

						if (checkWallCollision(bag, Board.BOTTOM_COLLISION)) {

							return true;
						}
					}

					bag.move(0, Board.SPACE);
					board.setFlag(true);
					board.setBags(bag);

				}

			}
		}
		return false;
	}
}