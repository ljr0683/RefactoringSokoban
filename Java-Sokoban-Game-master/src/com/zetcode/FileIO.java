package com.zetcode;

import java.io.*;
import java.util.*;

public class FileIO {
	private Deque<Integer> replay_Deque = new LinkedList<Integer>();
	private String filePath;
	private String folderPath;
	FileWriter fw = null;
	
	public void scoreFileInput(int levelSelected, int score) {
		folderPath = "src/score";
		filePath = "src/score/score_"+levelSelected+".txt";
		
		File file = new File(filePath);
		
		try {
			fw = new FileWriter(file,false);
			fw.write(Integer.toString(score));
			fw.flush();
			fw.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void replayFileInput(int levelSelected, String s) {
		
		folderPath = "src\\replay";
		filePath = "src\\replay\\"+s+"_replay_"+levelSelected+".txt";
		
		File folder = new File(folderPath);
		File file = new File(filePath);
		
		try {
			if(!folder.exists())
					folder.mkdir();
			fw = new FileWriter(file,false);
			int size = replay_Deque.size();
			for(int j=0; j<size; j++) {
				fw.write(Integer.toString(replay_Deque.poll()));
			}
			fw.flush();
			fw.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void enqueue(int i) {
		replay_Deque.offer(i);
	}
	
	public String getFilePath() {
		return filePath;
	}
}
