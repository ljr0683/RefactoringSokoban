package com.zetcode;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

public class LevelSelectPanel extends JPanel {
	private JLabel completedReplayLabel;
	private JLabel failedReplayLabel;
	private JLabel startLabel;
	private JLabel randomStartLabel;
	private String selectCharacter;
	private JLabel backSpaceLabel;
	private ImageIcon backGroundImage;
	private JLabel scoreLabel;
	
	ImageIcon randomStartIcon ;
	ImageIcon startIcon ;
	
	private UIManager frame;
	private SelectCharacterPanel previousPanel;
	private LevelSelectPanel panel;
	
	private int levelSelected;
	
	public LevelSelectPanel(UIManager frame, SelectCharacterPanel previousPanel, int levelSelected, String selectCharacter) {
		setLayout(null);
		
		randomStartIcon = new ImageIcon("src/resources/GameStartImage/RandomStart.png");
		startIcon = new ImageIcon("src/resources/GameStartImage/Start.png");
		panel = this;
		
		this.frame=frame;
		this.previousPanel=previousPanel;
		this.levelSelected=levelSelected;
		this.selectCharacter = selectCharacter;
		
		readScoreFile();
		
		addItemToPanel();
		
		setLabelBound();
		
		addListener();
	}
	
	private void readScoreFile() {
		File scoreFile = new File("src/score/score_"+levelSelected+".txt");
		int score;
		if(!scoreFile.exists()) {
			score = 0;
			setLabel(score);
		}
		else {
			try {
				FileReader fr = new FileReader(scoreFile);
				BufferedReader bufReader = new BufferedReader(fr);
				String stringScore;
				do {
					stringScore = bufReader.readLine();
				}while((bufReader.readLine()) != null);
				
				score = Integer.parseInt(stringScore);
				setLabel(score);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setLabel(int score) {
		ImageIcon backSpaceIcon = new ImageIcon("src/resources/BackSpace/BackSpace.png");
		ImageIcon completedIcon = new ImageIcon("src/resources/GameStartImage/Completed.png");
		ImageIcon failedIcon = new ImageIcon("src/resources/GameStartImage/Failed.png");
		startLabel = new JLabel(startIcon);
		randomStartLabel = new JLabel(randomStartIcon);
		completedReplayLabel = new JLabel(completedIcon);
		failedReplayLabel = new JLabel(failedIcon);
		backSpaceLabel = new JLabel(backSpaceIcon);
		backGroundImage = new ImageIcon("src/resources/Background/DefaultBackground.png");
		Font scoreFont = new Font("배달의민족 도현", Font.BOLD, 50);
		scoreLabel = new JLabel("BestScore : "+score);
		scoreLabel.setFont(scoreFont);
	}
	
	private void setLabelBound() {
		backSpaceLabel.setBounds(25, 20, 128, 128);
		completedReplayLabel.setBounds(400, 700, 96, 96);
		failedReplayLabel.setBounds(600, 700, 96, 96);
		startLabel.setBounds(800, 700, 96, 96);
		randomStartLabel.setBounds(1000, 700, 96, 96);
		scoreLabel.setBounds(530, 150, 500, 60);
	}
	
	private void addItemToPanel() {
		add(backSpaceLabel);
		add(completedReplayLabel);
		add(failedReplayLabel);
		add(startLabel);
		add(randomStartLabel);
		add(scoreLabel);
	}
	
	private void addListener() {
		backSpaceLabel.addMouseListener(new MyMouseListener()); 
		completedReplayLabel.addMouseListener(new MyMouseListener());
		failedReplayLabel.addMouseListener(new MyMouseListener());
		startLabel.addMouseListener(new MyMouseListener());
		randomStartLabel.addMouseListener(new MyMouseListener());
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(backGroundImage.getImage(), 0, 0, this);
	}
	
	class MyMouseListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			JLabel la = (JLabel)e.getSource();
			if(la.equals(backSpaceLabel)) {
				frame.changePanel(previousPanel);
			}
			
			if(la.equals(startLabel)) {
				GameManager gameManager = new GameManager(levelSelected, panel, frame, selectCharacter, 0);
				startLabel.setIcon(startIcon);
			}
			
			if(la.equals(randomStartLabel)) {
				Random rand = new Random(System.currentTimeMillis());
				int mode = rand.nextInt(5);
				GameManager gameManager = new GameManager(levelSelected, panel, frame, selectCharacter, mode);
				randomStartLabel.setIcon(randomStartIcon);
			}
			
			if(la.equals(completedReplayLabel)) {
				replayFileReadAndStart("Completed");
			}
			
			if(la.equals(failedReplayLabel)) {
				replayFileReadAndStart("Failed");
			}
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			ImageIcon enteredStartIcon = new ImageIcon("src/resources/GameStartImage/EnteredStart.png");
			ImageIcon enteredRandomStartIcon = new ImageIcon("src/resources/GameStartImage/EnteredRandomStart.png");
			JLabel la= (JLabel)e.getSource();
			if(la.equals(startLabel)) {
				startLabel.setIcon(enteredStartIcon);
			}
			
			if(la.equals(randomStartLabel)) {
				randomStartLabel.setIcon(enteredRandomStartIcon);
			}
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			startLabel.setIcon(startIcon);
			randomStartLabel.setIcon(randomStartIcon);
		}
	}
	
	private void replayFileReadAndStart(String s) {
		String filePath = "src\\replay\\"+s+"_replay_"+levelSelected+".txt";
		File file = new File(filePath);
		
		Font replayFileNotExitsFont = new Font("배달의 민족 도현", Font.BOLD, 50);
		JLabel notExitsReplayLabel = new JLabel("Replay File Not Exits");
		notExitsReplayLabel.setFont(replayFileNotExitsFont);
		
		notExitsReplayLabel.setBounds(500, 250, 700, 200);
		notExitsReplayLabel.setForeground(new Color(255, 0, 0));
		
		add(notExitsReplayLabel);
		notExitsReplayLabel.setVisible(false);
		if(file.exists()) {
			notExitsReplayLabel.setVisible(false);
			GameManager gameManager = new GameManager(levelSelected, panel, frame, file, selectCharacter);
		}
		else {
			notExitsReplayLabel.setVisible(true);
		}
	}
}
