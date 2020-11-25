package com.zetcode;

public class CheckCollision {
	private Board board;
	private Baggage bag;
	private Baggage item;
	
	public CheckCollision(Board board) {
		this.board = board;
	}
	
	public boolean checkWallCollision(Actor actor, int type) {

		if(type==1) { // type == ���� ������ �� �Ʒ����� ���� 1,2,3,4

		 // ���� Ű�� ��������

			for (int i = 0; i < board.getWallsSize(); i++) {

				Wall wall = board.getWalls(i); // walls ��� ����Ʈ���� �ϳ��� wall�� Wall��ü����

				if (actor.isLeftCollision(wall)) { // actor ���ʿ� ���� �ִٸ� true ����

					return true;
				}
			}

			return false; // actor ���ʿ� ���� ���ٸ� false ����
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

	public boolean checkBagCollision(int type) { // Bag ��ü�� �浹

		if(type==1) {

		 // ���� Ű�� ��������,

			for (int i = 0; i < board.getBaggsSize(); i++) {

				this.bag = board.getBaggs(i);

				if (board.getSoko().isLeftCollision(this.bag)) { // soko ���ʿ� bag�� �����ϸ�

					for (int j = 0; j < board.getBaggsSize(); j++) {

						this.item = board.getBaggs(j); // bag ��� ����Ʈ�� �ϳ��� �˻��Ͽ� item�� ����

						if (!this.bag.equals(this.item)) { // item�� bag�� �ٸ� ��ü���

							if (this.bag.isLeftCollision(this.item)) {// bag ���ʿ� item(�� �ٸ� Baggage)�� �����ϸ� �ȿ�����
								return true;
							}
						}

						if (checkWallCollision(this.bag, Board.LEFT_COLLISION)) {// bag��ü ���ʿ� wall ��ü�� �����ϸ� �ȿ�����.
							return true;
						}
					}
					// ���� if���� �� �������� ������ �������� bag��ü�� ������.
					this.bag.move(-Board.SPACE, 0);
					board.setFlag(true);
					board.setBags(this.bag);
					// bag��ü�� ������ �� ������ �������� �˻���.

				}

			}

			return false;
		}
		if(type==2) { // ������ Ű�� ��������

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