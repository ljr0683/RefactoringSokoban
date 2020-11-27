package com.zetcode;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class DetectedIsCompleted {
	
	private BoardManager boardManager;
	private Timer timer;
	private MyTimer time;
	private int timerCount;
	
	public DetectedIsCompleted(BoardManager boardManager, Timer timer, MyTimer time) {
		this.boardManager = boardManager;
		this.timer = timer;
		this.time = time;
	}
	
	public void isCompleted() { // 다 최종지점에 넣었을경우 isCompleted=true Replay에서 사용중
		
		int nOfBags = boardManager.getBaggsSize(); // Bag 객체의 숫자
		
		int finishedBags = 0; // Bag객체의 숫자와 finishedBags가 isCompleted=ture == 게임 종료
		
		for (int i = 0; i < nOfBags; i++) {

			Baggage bag = boardManager.getBaggs(i);

			for (int j = 0; j < nOfBags; j++) {

				Area area = boardManager.getAreas(j); // 끝나는 지점

				if (bag.x() == area.x() && bag.y() == area.y()) { // bag x,y와 area x,y가 같으면 finishedBags +1증가
					
					finishedBags += 1;
				}
			}
		}
		
		if (finishedBags == nOfBags) { // finishedBag과 nOfbags가 같으면 모두 최종지점에 넣었다는 뜻
			boardManager.boardSetZeroMoveCount();
			timer.stop();
			String s = "Completed";
			System.out.println("1234");
			FileIO replayFileIo = new FileIO();
			int size = boardManager.getReplayDequeSize();
			
			for (int i = 0; i < size; i++) {
				replayFileIo.enqueue(boardManager.getReplayDeque().poll());
			}
			
			replayFileIo.replayFileInput(boardManager.getLevelSelected(), s);
			
			if(!boardManager.getIsReplay()) { // replay가 아닐만 스코어 계산
				this.timerCount = time.getTime();
				File scoreFileFolder = new File("src/score");
				if(!scoreFileFolder.exists())
					scoreFileFolder.mkdir();
				File scoreFile = new File("src/score/score_"+boardManager.getLevelSelected()+".txt");
				Score score = new Score(boardManager.getLevelSelected(), boardManager.getMoveCount(), timerCount, scoreFile);
			}
			
			boardManager.setIsCompleted(true); // 따라서 끝남
			
			ImageIcon completeImage = new ImageIcon("src/resources/Complete & Failed/Complete.png");
			JLabel completeLabel = new JLabel(completeImage);
			
			boardManager.attachLabel(completeLabel);
			completeLabel.setBounds(0, 0, boardManager.getwidth(), boardManager.getHeight());
			
		}
	}

}
