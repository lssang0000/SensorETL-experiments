package com.ssu.ssetl.emsys;


import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ssu.ssetl.Constants;
import com.ssu.ssetl.collector.Collector;

import au.com.bytecode.opencsv.CSVReader;

public class Emsys {
	
	CSVReader reader = null;
	
//	private JSONArray inputStream = null;
	private int aType;	//Analyzer Type	
	private long startTime;
	private long endTime;
	
	private boolean header;
	private List<String> headerAsList;
	
	public Emsys(int fType) {
		// TODO Auto-generated constructor stub
		
		aType = 0;
		startTime = 0l;
		endTime = 0l;
		
		String fPath = "";
		switch(fType){
			case Constants.ANALYZER_SSU: fPath = Constants.INPUT_FILE_1; break;
			case Constants.ANALYZER_MARKETING: fPath = Constants.INPUT_FILE_2; break;
			case Constants.ANALYZER_HEART: fPath = Constants.INPUT_FILE_3; break;
			default : 
		}		
		aType = fType;
		
		try{
			reader = new CSVReader(new FileReader(fPath));
			header = true;
			headerAsList = new ArrayList<String>();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void sendAll(Collector col) {
		startTime = System.currentTimeMillis();
				
		try{
			String nextLine[];
			int cnt = 0;
			
			col.setDataType(aType);
			while( (nextLine = reader.readNext()) != null ){
				if(header){					
					headerAsList = new ArrayList<String>(Arrays.asList(nextLine));
					header = false;
					continue;
				}
								
				List<String> lineAsList = new ArrayList<String>(Arrays.asList(nextLine));
//				System.out.println(lineAsList);
				col.recvMsg(headerAsList, lineAsList);
				
//				if(!header){
//					break;
//				}
				cnt ++;
				if(cnt%10 == 0){
					long cTime = System.currentTimeMillis();
					System.out.println("["+cnt+"]"+ (cTime - startTime) + " ms");
				}
			}
			col.stop();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		endTime = System.currentTimeMillis();
	}
	
	public long getRunTime(){
		
		if(startTime == 0l || endTime == 0l){
			return -1;
		}		
		return endTime - startTime;
	}
}
