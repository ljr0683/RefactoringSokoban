package com.zetcode;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel {
	
	private int undoCount;
	private int moveCount;
	private int limitturn;
	private File file;
	private Replay replay;
	private MyTimer time;
	private Timer timer;
	private BoardManager boardManager;
	
	private ImageIcon backSpaceIcon ;
	private JLabel backSpaceLabel ;
	private ImageIcon[] boomImage ;
	private JLabel[] boomLabel;
	
	private int levelSelected;
	private int mode;
	private String selectCharacter;

	private final int OFFSET = 30;
	private final int SPACE = 64;

	private ArrayList<Wall> walls;
	private ArrayList<Baggage> baggs;
	private ArrayList<Area> areas;

	private GamePlayer soko;
	private int w = 0;
	private int h = 0;

	private boolean isReplay = false;

	public Board(int levelSelected, String selectCharacter, int mode, BoardManager boardManager) {

		this.moveCount = 0;
		undoCount = 3;
		
		boomImage = new ImageIcon[3];
		boomLabel = new JLabel[3];
		
		for(int i = 0; i < boomImage.length; i++) {
			boomImage[i] = new ImageIcon("src/resources/Boom/boom"+i+".png");
			boomLabel[i] = new JLabel(boomImage[i]);
			boomLabel[i].setVisible(true);
			add(boomLabel[i]);
		}
		this.boardManager = boardManager;
		this.mode = mode;
		
		limitturn = 500;
	
		initBoard(levelSelected, selectCharacter);
	}

	public Board(int levelSelected, File file, Replay replay, String selectCharacter, BoardManager boardManager) { // 리플레이 일때

		this.file = file;
		this.replay = replay;
		isReplay = true;
		this.boardManager = boardManager;
		initBoard(levelSelected, selectCharacter);
		
	}

	private void initBoard(int levelSelected, String selectCharacter) {
		
		setLayout(null);

		this.levelSelected = levelSelected;
		
		this.selectCharacter = selectCharacter;
		backSpaceIcon = new ImageIcon("src/resources/BackSpace/BackSpace.png");
		backSpaceLabel = new JLabel(backSpaceIcon);
		
		add(backSpaceLabel);
		backSpaceLabel.addMouseListener(new MyMouseListener()); 
		backSpaceLabel.setBounds(25, 20, 128, 128);
		
		time = new MyTimer();
		
		timer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				repaint();
			}
			
		});
		timer.start();
		
		setFocusable(true);
		initWorld();
	}

	private void initWorld() { // 초기화

		walls = new ArrayList<>();
		baggs = new ArrayList<>();
		areas = new ArrayList<>();

		int x = OFFSET;
		int y = OFFSET;

		Wall wall; // 벽
		Baggage b; // 미는거
		Area a; // 끝나는거
		Llm llm;// 가운데 안보이는 벽
		LevelData leveldata = new LevelData(); 
		
		for (int i = 0; i < leveldata.level[levelSelected].length(); i++) {

			char item = leveldata.level[levelSelected].charAt(i);

			switch (item) {

			case '\n':
				y += SPACE;

				if (this.w < x) { // width의 크기를 x가 가장 큰걸로 함
					this.w = x;
				}

				x = OFFSET; // 한번 줄바꿈이 되면 x=OFF으로 다시 초기화
				break;

			case '#':
				wall = new Wall(x, y, mode); // x,y 지점에 Wall 객체 생성
				walls.add(wall); // Wall 객체를 Wall 어레이 리스트에 넣음
				x += SPACE; // x에 한칸(SPACE)를 더함
				break;
				
			case '!':
				llm = new Llm(x, y);
				walls.add(llm);
				x += SPACE;
				break;

			case '$':
				b = new Baggage(x, y);
				baggs.add(b); // b == Baggage 객체
				x += SPACE;
				break;

			case '.':
				a = new Area(x, y); // 끝나는 지점
				areas.add(a); // a == Area 객체
				x += SPACE;
				break;

			case '@':
				soko = new GamePlayer(x, y, selectCharacter); // 플레이어임
				x += SPACE;
				break;

			case ' ':
				x += SPACE;
				break;

			default:
				break;
			}

			h = y; // 높이를 정함.
		}
		if(replay !=null) {
			try {
				FileReader fr = new FileReader(file);
				int c;
				while ((c = fr.read()) != -1) {
					boardManager.replayDequeOffer(c - 48);
				}
				fr.close();
			} catch (IOException e) {
				
			}

			addKeyListener(boardManager.getReplayKeyAdapter()); //boardManager getReplayAdapter 만들기
			
		}else {
			addKeyListener(boardManager.getPlayKeyAdapter()); // boardManager.getPlayKeyAdpater 만들기
			
		}
		
	}

	private void buildWorld(Graphics g) {
		
		if (mode == 1) {
			g.setColor(new Color(0, 0, 0));
		} else {
			g.setColor(new Color(250, 240, 170));
		}	
		
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		ArrayList<Actor> world = new ArrayList<>();

		world.addAll(walls);
		world.addAll(areas);
		world.addAll(baggs);
		world.add(soko);

		for (int i = 0; i < world.size(); i++) {

			Actor item = world.get(i);

			if (item instanceof Area) {

				g.drawImage(item.getImage(), item.x() + 16, item.y() + 16, this);
			}
			else if(item instanceof GamePlayer) {
				if(selectCharacter.equals("Mario")) { // 마리오 일때
					g.drawImage(item.getImage(), item.x(), item.y() , this);
					
				}else { // 노란모자 일때
					g.drawImage(item.getImage(), item.x() + 13, item.y() , this);
				}
			}
			else {
				Actor.setMode(mode); // 없어지는 모드인지 그냥인지 판별하기 위해
				g.drawImage(item.getImage(), item.x(), item.y(), this);
			}
			
			if(!isReplay) {
				
				g.setColor(new Color(0, 0, 0));
				g.drawString("ExtraUndo : " + Integer.toString(undoCount), w-90, 18);
				if(mode==3) {
				g.drawString("MoveLimited : " + limitturn , w-90, 80);
				if(limitturn <= moveCount) {
					boardManager.isFailed();
		    	}
				}
			}
		}
		
		if (boardManager.getIsFailed()) {
			if(!isReplay) {
				time.setIsFinished(true);
				
			}
		}
		
		if(!isReplay) {
			if(time.notMoveTime==4) {
				boomLabel[0].setBounds(w/2-200, 50, 64, 64);
				boomLabel[0].setVisible(true);
			}
			if(time.notMoveTime==5) {
				boomLabel[0].setVisible(false);
				boomLabel[1].setBounds(w/2, 50, 64, 64);
				boomLabel[1].setVisible(true);
			}
			if(time.notMoveTime==6) {
				boomLabel[1].setVisible(false);
				boomLabel[2].setBounds(w/2+200, 50, 64, 64);
				boomLabel[2].setVisible(true);
				boardManager.isFailed();
			}
		}
		
		if(time.time >= 1) {
			
			g.setColor(Color.BLUE);	
			String nowTime = Integer.toString(time.time);
			g.drawString("Time : "+nowTime,  w-90, 40);
		}
		
		if(!(mode==4)) {
		g.setColor(Color.BLUE);
		String nowTurn = Integer.toString(moveCount);
		g.drawString("MoveCount : " + nowTurn,  w-90, 60);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) { // 컴포넌트를 그림
		super.paintComponent(g);

		buildWorld(g);
	}

	public void restartLevel() {
		moveCount = 0;
		areas.clear();
		baggs.clear();
		walls.clear();

		initWorld();

		boardManager.setIsCompleted(false);
		boardManager.setIsFailed(false);
	}
	
	 private void undo() {
		if(!boardManager.getReplayDequeEmpty()) {
			if(undoCount>0) {
				Deque<Integer> replay_Deque = boardManager.getReplayDeque(); // 이쪽 수정함 replayDeque를 없앴음 위쪽에
				int key = replay_Deque.pollLast();
				replay = new Replay(boardManager);
				replay.offerReplay_Deque(key);
				if(key == 5 || key == 6) {
					key = replay_Deque.pollLast();
					replay.offerReplay_Deque(key);
				}
				replay.goBack();
				undoCount--;
			}
		}
	}
	
	public void setZeroMoveCount() {
		this.moveCount=0;
	}
	
	public void increaseMoveCount() {
		this.moveCount++;
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	
	public void callUndo() {
		undo();
	}
	
	public ArrayList<Wall> getWalls(){
		return walls;
	}
	
	public ArrayList<Area> getAreas(){
		return areas;
	}
	
	public ArrayList<Baggage> getBaggs(){
		return baggs;
	}
	
	public int getHeight() {
		return h;
	}
	
	public int getWidth() {
		return w;
	}
	
	public GamePlayer getSoko() {
		return soko;
	}
	
	public Timer getTimer() {
		return timer;
	}
	
	public MyTimer gettime() {
		return time;
	}
	
	public JLabel[] getBoomLabel() {
		return boomLabel;
	}
	
	public int getMode() {
		return mode;
	}
	
	class MyMouseListener extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			JLabel la = (JLabel)e.getSource();
			if(la.equals(backSpaceLabel)) {
				time.setIsFinished(true);
				boardManager.changePanel();
				boardManager.setIsFailed(false);
				time.notMoveTime = 0;
				moveCount=0;
			}
		}
	}
}