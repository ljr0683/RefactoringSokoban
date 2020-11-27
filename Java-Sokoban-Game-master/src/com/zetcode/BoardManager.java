package com.zetcode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

import javax.swing.JLabel;

public class BoardManager {
	
	private Deque<Integer> replay_Deque = new LinkedList<>(); 
	
	private boolean isCompleted;
	private boolean isFailed;
	private boolean isCollision;
	private boolean flag;
	private ArrayList<Wall> walls;
	private ArrayList<Baggage> baggs;
	private ArrayList<Area> areas;
	private Player soko;
	private int width;
	private int height;
	private CheckCollision checkCollision;
	private FailedDetected failedDetected;
	private Baggage bags = null;
	private Board board;
	private int levelSelected;
	
	public BoardManager(ArrayList<Wall> walls, ArrayList<Baggage> baggs, ArrayList<Area> areas, int width, int height, Player soko, Board board, int levelSelected) {
		this.walls = walls;
		this.baggs = baggs;
		this.areas = areas;
		this.width = width;
		this.height = height;
		this.soko = soko;
		this.checkCollision = new CheckCollision(this);
		failedDetected = new FailedDetected(this);
		this.board = board;
		this.levelSelected = levelSelected;
	}
	
	public void isEntered(Baggage bag) {
		for (int i = 0; i < areas.size(); i++) {
			Area area = areas.get(i);
			if (bag.x() == area.x() && bag.y() == area.y()) {
				bag.setIsEntered();
			}
		}
	}
	
	public void callIsFailedDetected(Baggage bags) { //Replay에서 사용
		if(failedDetected.isFailedDetected(bags)) {
			failedDetected.isFailed();
		}
		return;
	}
	
	public void isFailed() {
		failedDetected.isFailed();
	}
	
	public void setIsCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted; 
	}
	
	public boolean getIsCompleted() {
		return isCompleted;
	}
	
	public void setIsFailed(boolean isFailed) {
		this.isFailed = isFailed;
	}
	
	public boolean getIsCollision() {
		return isCollision;
	}
	
	public void setIsCollision(boolean isCollision) {
		this.isCollision = isCollision;
	}
	
	public boolean getIsFailed() {
		return isFailed;
	}
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public boolean getFlag() {
		return flag;
	}
	
	public int getwidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWallsSize() {
		return walls.size();
	}
	
	public Wall getWalls(int i) {
		return walls.get(i);
	}
	
	public Baggage getBaggs(int i) {
		return baggs.get(i);
	}
	
	public int getBaggsSize() {
		return baggs.size();
	}

	
	public Baggage getBags() {
		return bags;
	}
	
	public void setBags(Baggage collisionBag) {
		if(collisionBag!=null) {
			bags = collisionBag;
		}
	}
	
	public Player getSoko() {
		return soko;
	}
	
	public boolean getCheckWallCollision(Actor actor, int type) {
		if(checkCollision.checkWallCollision(actor, type))
			return true;
		return false;
	}
	
	public boolean getCheckBagCollision(int type) {
		if(checkCollision.checkBagCollision(type))
			return true;
		return false;
	}
	
	public Deque<Integer> getReplayDeque(){
		if(!replay_Deque.isEmpty()) {
			return replay_Deque;
		}
		return null;
	}
	
	public int getReplayDequeSize() {
		return replay_Deque.size();
	}
	
	public void replayDequeOffer(int keyDirection) {
		replay_Deque.offer(keyDirection);
	}
	
	public void boardSetZeroMoveCount() {
		board.setZeroMoveCount();;
	}
	
	public void boardIncreaseMoveCount() {
		board.increaseMoveCount();
	}
	
	public void attachLabel(JLabel label) {
		board.add(label);
	}
	
	public int getLevelSelected() {
		return levelSelected;
	}
	
	public void restartLevel() {
		board.setZeroMoveCount();
		board.restartLevel();
	}
	
	public void undo() {
		board.callUndo();
	}
	
	public boolean getReplayDequeEmpty() {
		if(replay_Deque.isEmpty()) 
			return true;
		return false;
	}
}
