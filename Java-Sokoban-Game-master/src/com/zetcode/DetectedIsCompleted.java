package com.zetcode;

import java.awt.Font;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class DetectedIsCompleted {
	
	private GameManager gameManager;
	private Timer timer;
	private MyTimer time;
	private int timerCount;
	private int score;
	private boolean isReplay;
	
	public DetectedIsCompleted(GameManager gameManager, Timer timer, MyTimer time, boolean isReplay) {
		this.isReplay = false;
		this.gameManager = gameManager;
		this.timer = timer;
		this.time = time;
		this.isReplay = isReplay;
	}
	
	public void isCompleted() { // 다 최종지점에 넣었을경우 isCompleted=true Replay에서 사용중
		
		int nOfBags = gameManager.getBaggsSize(); // Bag 객체의 숫자
		
		int finishedBags = 0; // Bag객체의 숫자와 finishedBags가 isCompleted=ture == 게임 종료
		
		for (int i = 0; i < nOfBags; i++) {

			Baggage bag = gameManager.getBaggs(i);

			for (int j = 0; j < nOfBags; j++) {

				Area area = gameManager.getAreas(j); // 끝나는 지점

				if (bag.x() == area.x() && bag.y() == area.y()) { // bag x,y와 area x,y가 같으면 finishedBags +1증가
					
					finishedBags += 1;
				}
			}
		}
		
		if (finishedBags == nOfBags) { // finishedBag과 nOfbags가 같으면 모두 최종지점에 넣었다는 뜻
			gameManager.boardSetZeroMoveCount();
			timer.stop();
			String s = "Completed";
			FileIO replayFileIo = new FileIO();
			int size = gameManager.getReplayDequeSize();
			
			for (int i = 0; i < size; i++) {
				replayFileIo.enqueue(gameManager.getReplayDeque().poll());
			}
			
			replayFileIo.replayFileInput(gameManager.getLevelSelected(), s);
			
			if(!gameManager.getIsReplay()) { // replay가 아닐만 스코어 계산
				this.timerCount = time.getTime();
				File scoreFileFolder = new File("src/score");
				if(!scoreFileFolder.exists())
					scoreFileFolder.mkdir();
				File scoreFile = new File("src/score/score_"+gameManager.getLevelSelected()+".txt");
				Score computeScore = new Score(gameManager.getLevelSelected(), gameManager.getMoveCount(), timerCount, scoreFile);
				score = computeScore.computeScore();
			}
			
			gameManager.setIsCompleted(true); // 따라서 끝남
			
			ImageIcon completeImage = new ImageIcon("src/resources/Complete & Failed/Complete.png");
			JLabel completeLabel = new JLabel(completeImage);
			
			Font scoreFont = new Font("배달의민족 도현", Font.BOLD, 30);
			JLabel scoreLabel = new JLabel("YourScore :" + score);
			scoreLabel.setFont(scoreFont);
			
			gameManager.attachLabel(completeLabel);
			completeLabel.setBounds(0, 0, gameManager.getwidth(), gameManager.getHeight());
			
			attachLabel(scoreLabel);
			
		}
	}
	
	private void attachLabel(JLabel scoreLabel) {
		if(!isReplay) {
			gameManager.attachLabel(scoreLabel);
			scoreLabel.setBounds(530, 150, 500, 60);
		}
	}

}
