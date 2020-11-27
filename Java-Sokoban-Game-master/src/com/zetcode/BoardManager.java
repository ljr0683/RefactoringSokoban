package com.zetcode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.Timer;

public class BoardManager {
	
	private Deque<Integer> replay_Deque = new LinkedList<>(); 
	
	private boolean isCompleted;
	private boolean isFailed;
	private boolean isCollision;
	private boolean flag;
	private ArrayList<Wall> walls;
	private ArrayList<Baggage> baggs;
	private ArrayList<Area> areas;
	private GamePlayer soko;
	private int width;
	private int height;
	private CheckCollision checkCollision;
	private FailedDetected failedDetected;
	private DetectedIsCompleted detectedIsCompleted;
	private Baggage bags = null;
	private Board board;
	private int levelSelected;
	private boolean isReplay;
	
	public final int SPACE = 64;
	public final int LEFT_COLLISION = 1;
	public final int RIGHT_COLLISION = 2;
	public final int TOP_COLLISION = 3;
	public final int BOTTOM_COLLISION = 4;
	
	public BoardManager(ArrayList<Wall> walls, ArrayList<Baggage> baggs, ArrayList<Area> areas, int width, int height, GamePlayer soko, Board board, int levelSelected, boolean isReplay, Timer timer, MyTimer time) {
		this.walls = walls;
		this.baggs = baggs;
		this.areas = areas;
		this.width = width;
		this.height = height;
		this.soko = soko;
		this.checkCollision = new CheckCollision(this);
		this.failedDetected = new FailedDetected(this);
		this.detectedIsCompleted = new DetectedIsCompleted(this, timer, time, isReplay);
		this.board = board;
		this.levelSelected = levelSelected;
		this.isReplay = isReplay;
	}
	
	public void isEntered(Baggage bag) {
		for (int i = 0; i < areas.size(); i++) {
			Area area = areas.get(i);
			if (bag.x() == area.x() && bag.y() == area.y()) {
				bag.setIsEntered();
			}
		}
	}
	
	public void callIsFailedDetected(Baggage bags) {
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
	
	public Area getAreas(int i) {
		return areas.get(i);
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
	
	public GamePlayer getSoko() {
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
	
	public int getMoveCount() {
		return board.getMoveCount();
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
	
	public boolean getIsReplay() {
		return isReplay;
	}
	
	public void callIsCompleted() {
		detectedIsCompleted.isCompleted();
	}
	
	public void repaint() {
		board.repaint();
	}
}
