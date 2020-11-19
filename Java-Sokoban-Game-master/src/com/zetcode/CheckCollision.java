package com.zetcode;

public class CheckCollision {
	private Board board;
	
	public CheckCollision(Board board) {
		this.board = board;
	}
	
	public boolean checkWallCollision(Actor actor, int type) {

		switch (type) { // type == 왼쪽 오른쪽 위 아래인지 숫자 1,2,3,4

		case Board.LEFT_COLLISION: // 왼쪽 키가 눌렸을때

			for (int i = 0; i < board.getWallsSize(); i++) {

				Wall wall = board.getWalls(i); // walls 어레이 리스트에서 하나씩 wall에 Wall객체저장

				if (actor.isLeftCollision(wall)) { // actor 왼쪽에 벽이 있다면 true 리턴

					return true;
				}
			}

			return false; // actor 왼쪽에 벽이 없다면 false 리턴

		case Board.RIGHT_COLLISION:
			
			for (int i = 0; i < board.getWallsSize(); i++) {

				Wall wall = board.getWalls(i);

				if (actor.isRightCollision(wall)) {
					
					return true;
				}
			}

			return false;

		case Board.TOP_COLLISION:

			for (int i = 0; i < board.getWallsSize(); i++) {

				Wall wall = board.getWalls(i);

				if (actor.isTopCollision(wall)) {

					return true;
				}
			}

			return false;

		case Board.BOTTOM_COLLISION:

			for (int i = 0; i < board.getWallsSize(); i++) {

				Wall wall = board.getWalls(i);

				if (actor.isBottomCollision(wall)) {

					return true;
				}
			}

			return false;

		default:
			break;
		}

		return false;
	}

	public boolean checkBagCollision(int type) { // Bag 객체의 충돌

		switch (type) {

		case Board.LEFT_COLLISION: // 왼쪽 키가 눌렸을때,

			for (int i = 0; i < board.getBaggsSize(); i++) {

				Baggage bag = board.getBaggs(i);

				if (board.getSoko().isLeftCollision(bag)) { // soko 왼쪽에 bag이 존재하면

					for (int j = 0; j < board.getBaggsSize(); j++) {

						Baggage item = board.getBaggs(j); // bag 어레이 리스트를 하나씩 검사하여 item에 넣음

						if (!bag.equals(item)) { // item과 bag이 다른 객체라면

							if (bag.isLeftCollision(item)) {// bag 왼쪽에 item(또 다른 Baggage)이 존재하면 안움직임
								return true;
							}
						}

						if (checkWallCollision(bag, Board.LEFT_COLLISION)) {// bag객체 왼쪽에 wall 객체가 존재하면 안움직임.
							return true;
						}
					}
					// 위의 if문을 다 만족하지 않으면 그제서야 bag객체가 움직임.
					bag.move(-Board.SPACE, 0);
					board.setFlag(true);
					board.setBags(bag);
					// bag객체가 움직인 후 게임이 끝났는지 검사함.

				}

			}

			return false;

		case Board.RIGHT_COLLISION: // 오른쪽 키가 눌렸을때

			for (int i = 0; i < board.getBaggsSize(); i++) {

				Baggage bag = board.getBaggs(i); // 변수 bag에 Baggage 어레이 리스트에서 객체를 하나씩 검사함 검사를 하다가

				if (board.getSoko().isRightCollision(bag)) { // soko의 오른쪽에 bag객체가 존재하면

					for (int j = 0; j < board.getBaggsSize(); j++) {

						Baggage item = board.getBaggs(j); // Baggage 어레이 리스트에서 객체를 하나씩 검사하여 item에 넣음

						if (!bag.equals(item)) { // item(또 다른 Baggage)객체와 bag객체가 같지 않을때

							if (bag.isRightCollision(item)) { // bag 객체 오른쪽에 item 객체가 존재하면 안움직임
								return true;
							}
						}

						if (checkWallCollision(bag, Board.RIGHT_COLLISION)) { // bag객체 오른쪽에 wall 객체가 존재하면 안움직임
							return true;
						}
					}
					// 위의 if문을 다 만족하지 않으면 그제서야 bag 객체가 움직임.
					bag.move(Board.SPACE, 0);
					board.setFlag(true);
					board.setBags(bag);
					// bag객체가 움직인 후 게임이 끝났는지 검사함.
				}

			}

			return false;

		case Board.TOP_COLLISION:

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

		case Board.BOTTOM_COLLISION:

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

			break;

		default:
			break;
		}

		return false;
	}
}
