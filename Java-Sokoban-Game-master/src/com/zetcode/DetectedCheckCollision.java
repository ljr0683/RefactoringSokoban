package com.zetcode;

public class DetectedCheckCollision {
	private GameManager gameManager;
	private Baggage bag;
	private Baggage item;
	
	public DetectedCheckCollision(GameManager gameManager) {
		this.gameManager = gameManager;
	}
	
	public boolean checkWallCollision(Actor actor, int type) {
		return checkLeftWallCollision(actor, type);
	}
	
	private boolean checkLeftWallCollision(Actor actor, int type) {
		if(type==gameManager.LEFT_COLLISION) {

			for (int i = 0; i < gameManager.getWallsSize(); i++) {

				Wall wall = gameManager.getWalls(i); 

				if (actor.isLeftCollision(wall)) { 

					return true;
				}
			}

			return false; 
		}
		return checkRightWallCollision(actor, type);
	}
	
	private boolean checkRightWallCollision(Actor actor, int type) {
		if(type==gameManager.RIGHT_COLLISION) {
			
			for (int i = 0; i < gameManager.getWallsSize(); i++) {

				Wall wall = gameManager.getWalls(i);

				if (actor.isRightCollision(wall)) {
					
					return true;
				}
			}

			return false;
		}
		return checkTopWallCollision(actor, type);
	}
	
	private boolean checkTopWallCollision(Actor actor, int type) {
		if(type==gameManager.TOP_COLLISION) {
			for (int i = 0; i < gameManager.getWallsSize(); i++) {

				Wall wall = gameManager.getWalls(i);

				if (actor.isTopCollision(wall)) {

					return true;
				}
			}

			return false;
		}
		return checkBottomWallCollision(actor, type);
	}
	
	private boolean checkBottomWallCollision(Actor actor, int type) {
		if(type==gameManager.BOTTOM_COLLISION) {

			for (int i = 0; i < gameManager.getWallsSize(); i++) {

				Wall wall = gameManager.getWalls(i);

				if (actor.isBottomCollision(wall)) {

					return true;
				}
			}

			return false;
		}
		return false;
		
	}

	public boolean checkBagCollision(int type) { 
		return checkLeftBagCollision(type);
	}
	
	private boolean checkLeftBagCollision(int type) {
		if(type==gameManager.LEFT_COLLISION) { 

			for (int i = 0; i < gameManager.getBaggsSize(); i++) {

				this.bag = gameManager.getBaggs(i);

				if (gameManager.getSoko().isLeftCollision(this.bag)) { 

					for (int j = 0; j < gameManager.getBaggsSize(); j++) {

						this.item = gameManager.getBaggs(j); 

						if (!this.bag.equals(this.item)) { 
							
							if (this.bag.isLeftCollision(this.item)) {
								return true;
							}
						}

						if (checkWallCollision(this.bag, gameManager.LEFT_COLLISION)) {
							return true;
						}
					}
					
					this.bag.move(-gameManager.SPACE, 0);
					gameManager.setFlag(true);
					gameManager.setBags(this.bag);

				}

			}

			return false;
		}
		return checkRightBagCollision(type);
	}
	
	private boolean checkRightBagCollision(int type) {
		if(type==gameManager.RIGHT_COLLISION) { 

			for (int i = 0; i < gameManager.getBaggsSize(); i++) {

				Baggage bag = gameManager.getBaggs(i); 

				if (gameManager.getSoko().isRightCollision(bag)) { 

					for (int j = 0; j < gameManager.getBaggsSize(); j++) {

						Baggage item = gameManager.getBaggs(j); 

						if (!bag.equals(item)) { 

							if (bag.isRightCollision(item)) { 
								return true;
							}
						}

						if (checkWallCollision(bag, gameManager.RIGHT_COLLISION)) { 
							return true;
						}
					}
					
					bag.move(gameManager.SPACE, 0);
					gameManager.setFlag(true);
					gameManager.setBags(bag);
					
				}

			}

			return false;
		}
		return checkTopBagCollision(type);
	}
	
	private boolean checkTopBagCollision(int type) {
		if(type==gameManager.TOP_COLLISION) {

			for (int i = 0; i < gameManager.getBaggsSize(); i++) {
				
				Baggage bag = gameManager.getBaggs(i);

				if (gameManager.getSoko().isTopCollision(bag)) {

					for (int j = 0; j < gameManager.getBaggsSize(); j++) {

						Baggage item = gameManager.getBaggs(j);

						if (!bag.equals(item)) {

							if (bag.isTopCollision(item)) {
								return true;
							}
						}

						if (checkWallCollision(bag, gameManager.TOP_COLLISION)) {
							return true;
						}
					}

					bag.move(0, -gameManager.SPACE);
					gameManager.setFlag(true);
					gameManager.setBags(bag);

				}

			}

			return false;
		}
		return checkBottomBagCollision(type);
	}
	
	private boolean checkBottomBagCollision(int type) {
		if(type==gameManager.BOTTOM_COLLISION) {

			for (int i = 0; i < gameManager.getBaggsSize(); i++) {

				Baggage bag = gameManager.getBaggs(i);

				if (gameManager.getSoko().isBottomCollision(bag)) {

					for (int j = 0; j < gameManager.getBaggsSize(); j++) {

						Baggage item = gameManager.getBaggs(j);

						if (!bag.equals(item)) {

							if (bag.isBottomCollision(item)) {
								return true;
							}
						}

						if (checkWallCollision(bag, gameManager.BOTTOM_COLLISION)) {

							return true;
						}
					}

					bag.move(0, gameManager.SPACE);
					gameManager.setFlag(true);
					gameManager.setBags(bag);

				}

			}
		}
		return false;
	}
}