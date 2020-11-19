package com.zetcode;

public class CheckCollision {
	private Board board;
	
	public CheckCollision(Board board) {
		this.board = board;
	}
	
	public boolean checkWallCollision(Actor actor, int type) {

		switch (type) { // type == ���� ������ �� �Ʒ����� ���� 1,2,3,4

		case Board.LEFT_COLLISION: // ���� Ű�� ��������

			for (int i = 0; i < board.getWallsSize(); i++) {

				Wall wall = board.getWalls(i); // walls ��� ����Ʈ���� �ϳ��� wall�� Wall��ü����

				if (actor.isLeftCollision(wall)) { // actor ���ʿ� ���� �ִٸ� true ����

					return true;
				}
			}

			return false; // actor ���ʿ� ���� ���ٸ� false ����

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

	public boolean checkBagCollision(int type) { // Bag ��ü�� �浹

		switch (type) {

		case Board.LEFT_COLLISION: // ���� Ű�� ��������,

			for (int i = 0; i < board.getBaggsSize(); i++) {

				Baggage bag = board.getBaggs(i);

				if (board.getSoko().isLeftCollision(bag)) { // soko ���ʿ� bag�� �����ϸ�

					for (int j = 0; j < board.getBaggsSize(); j++) {

						Baggage item = board.getBaggs(j); // bag ��� ����Ʈ�� �ϳ��� �˻��Ͽ� item�� ����

						if (!bag.equals(item)) { // item�� bag�� �ٸ� ��ü���

							if (bag.isLeftCollision(item)) {// bag ���ʿ� item(�� �ٸ� Baggage)�� �����ϸ� �ȿ�����
								return true;
							}
						}

						if (checkWallCollision(bag, Board.LEFT_COLLISION)) {// bag��ü ���ʿ� wall ��ü�� �����ϸ� �ȿ�����.
							return true;
						}
					}
					// ���� if���� �� �������� ������ �������� bag��ü�� ������.
					bag.move(-Board.SPACE, 0);
					board.setFlag(true);
					board.setBags(bag);
					// bag��ü�� ������ �� ������ �������� �˻���.

				}

			}

			return false;

		case Board.RIGHT_COLLISION: // ������ Ű�� ��������

			for (int i = 0; i < board.getBaggsSize(); i++) {

				Baggage bag = board.getBaggs(i); // ���� bag�� Baggage ��� ����Ʈ���� ��ü�� �ϳ��� �˻��� �˻縦 �ϴٰ�

				if (board.getSoko().isRightCollision(bag)) { // soko�� �����ʿ� bag��ü�� �����ϸ�

					for (int j = 0; j < board.getBaggsSize(); j++) {

						Baggage item = board.getBaggs(j); // Baggage ��� ����Ʈ���� ��ü�� �ϳ��� �˻��Ͽ� item�� ����

						if (!bag.equals(item)) { // item(�� �ٸ� Baggage)��ü�� bag��ü�� ���� ������

							if (bag.isRightCollision(item)) { // bag ��ü �����ʿ� item ��ü�� �����ϸ� �ȿ�����
								return true;
							}
						}

						if (checkWallCollision(bag, Board.RIGHT_COLLISION)) { // bag��ü �����ʿ� wall ��ü�� �����ϸ� �ȿ�����
							return true;
						}
					}
					// ���� if���� �� �������� ������ �������� bag ��ü�� ������.
					bag.move(Board.SPACE, 0);
					board.setFlag(true);
					board.setBags(bag);
					// bag��ü�� ������ �� ������ �������� �˻���.
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
