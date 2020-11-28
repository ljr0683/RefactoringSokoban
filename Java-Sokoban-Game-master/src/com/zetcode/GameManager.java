package com.zetcode;

import java.io.File;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.Timer;

public class GameManager {
	
	private Deque<Integer> replay_Deque ; 
	
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
	private DetectedCheckCollision detectedCheckCollision;
	private DetectedIsFailed detectedIsFailed;
	private DetectedIsCompleted detectedIsCompleted;
	private Baggage bags = null;
	private Board board;
	private int levelSelected;
	private boolean isReplay;
	private UIManager frame;
	private LevelSelectPanel previousPanel;
	private JLabel boomLabel[];
	private int mode;
	private Timer timer;
	private MyTimer time;
	private Replay replay;
	
	public final int SPACE = 64;
	public final int LEFT_COLLISION = 1;
	public final int RIGHT_COLLISION = 2;
	public final int TOP_COLLISION = 3;
	public final int BOTTOM_COLLISION = 4;
	
	public GameManager(int levelSelected, LevelSelectPanel previousPanel, UIManager frame, String selectCharacter, int mode) {
		replay_Deque = new LinkedList<>();
		board = new Board(levelSelected, selectCharacter, mode, this);
		isReplay = false;
		getData();
		this.frame = frame;
		this.previousPanel = previousPanel;
		frame.changePanel(board, width, height);
	}
	
	public GameManager(int levelSelected, LevelSelectPanel previousPanel, UIManager frame, File file, String selectCharacter) {
		replay_Deque = new LinkedList<>();
		this.replay = new Replay(levelSelected, file, selectCharacter, this);
		board = new Board(levelSelected, file, selectCharacter, this, true);
		isReplay = true;
		getData();
		this.frame = frame;
		this.previousPanel = previousPanel;
		frame.changePanel(board, width, height);
	}
	
	private void getData() {
		this.walls = board.getWalls();
		this.baggs = board.getBaggs();
		this.areas = board.getAreas();
		this.width = board.getWidth();
		this.height = board.getHeight();
		this.soko = board.getSoko();
		this.detectedCheckCollision = new DetectedCheckCollision(this);
		this.detectedIsFailed = new DetectedIsFailed(this);
		this.time = board.gettime();
		this.detectedIsCompleted = new DetectedIsCompleted(this, timer, time, isReplay);
		this.mode = board.getMode();
		this.boomLabel = board.getBoomLabel();
	}
	
	public void changePanel() {
		frame.changePanel(previousPanel);
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
		if(detectedIsFailed.isFailedDetected(bags)) {
			detectedIsFailed.isFailed();
		}
		return;
	}
	
	public void isFailed() {
		detectedIsFailed.isFailed();
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
	
	public int getWidth() {
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
		if(detectedCheckCollision.checkWallCollision(actor, type))
			return true;
		return false;
	}
	
	public boolean getCheckBagCollision(int type) {
		if(detectedCheckCollision.checkBagCollision(type))
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
		isCompleted = false;
		isFailed = false;
		board.setZeroMoveCount();
		board.restartLevel();
	}
	
	public void undo() {
		if(!replay_Deque.isEmpty()) {
			if(board.getUndoCount()>0) {
				int key = replay_Deque.pollLast();
				replay = new Replay(this);
				replay.offerReplay_Deque(key);
				if(key == 5 || key == 6) {
					key = replay_Deque.pollLast();
					replay.offerReplay_Deque(key);
				}
				replay.goBack();
				board.decreaseUndoCount();
			}
		}
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
	
	public PlayKeyAdapter getPlayKeyAdapter() {
		return new PlayKeyAdapter(time, mode, timer, this, boomLabel);
	}
	
	public ReplayKeyAdapter getReplayKeyAdapter() {
		return new ReplayKeyAdapter(this);
	}
	
	public void repaint() {
		board.repaint();
	}
	
	public void replayGoBack() {
		replay.goBack();
	}
	
	public void replayGoAhead() {
		replay.goAhead();
	}
	
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
}
