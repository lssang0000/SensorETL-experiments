package com.ssu.ssetl.collector;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ssu.ssetl.Constants;
import com.ssu.ssetl.transform.Transformer;
import com.ssu.ssetl.transormmy.TransformerMy;

import au.com.bytecode.opencsv.CSVWriter;

public class Collector {

	private static Collector instance;

//	private Transformer t = null;
	private TransformerMy t = null;
	private CSVWriter writer = null;
	private int dataType;
	private List<String[]> transformedCsvContents = null;
	private String transformedCsvPath = null;

	private Collector() {
		// TODO Auto-generated constructor stub
//		t = new Transformer();
		t = new TransformerMy();
		
		transformedCsvContents = new ArrayList<String[]>();
		transformedCsvPath = ""; 
		dataType=0;
	}

	public static synchronized Collector getInstance() {
		if (instance == null) {
			instance = new Collector();
		}
		return instance;
	}
	
	public void setDataType(int dType){
		dataType = dType;
		switch (dataType) {
//		case Constants.ANALYZER_SSU:	transformedCsvPath = Constants.OUTPUT_FILE_1;break;
		case Constants.ANALYZER_SSU:	transformedCsvPath = Constants.OUTPUT_FILE_1_TMY;break;
//		case Constants.ANALYZER_MARKETING:	transformedCsvPath = Constants.OUTPUT_FILE_2;break;
		case Constants.ANALYZER_MARKETING:	transformedCsvPath = Constants.OUTPUT_FILE_2_TMY;break;
//		case Constants.ANALYZER_HEART:	transformedCsvPath = Constants.OUTPUT_FILE_3;break;
		case Constants.ANALYZER_HEART:	transformedCsvPath = Constants.OUTPUT_FILE_3_TMY;break;
		default:
		}
	}
	
	public void recvMsg(List<String> header, List<String> input) {
		
		List<String> res = t.doTransform(header, input, dataType);
		
		switch (dataType) {
		case Constants.ANALYZER_SSU:
			if (res.size() == 4) {
				transformedCsvContents.add(res.toArray(new String[0]));
			}
			break;
		case Constants.ANALYZER_MARKETING:
				System.out.println("[Collector] res : "+res);
				transformedCsvContents.add(res.toArray(new String[0]));
			break;
		case Constants.ANALYZER_HEART:
			if (res.size() == 8) {
//				System.out.println(res);
				transformedCsvContents.add(res.toArray(new String[0]));
			}
			break;
		default:
		}
	}

	public void stop() {

		try {
			writer = new CSVWriter(new FileWriter(transformedCsvPath));
			for(int i = 0 ; i < transformedCsvContents.size() ; i++){
				String[] content = transformedCsvContents.get(i);
//				System.out.println(content);
				writer.writeNext(content);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void setHeader(List<String> header){
		transformedCsvContents.add(header.toArray(new String[0]));
	}

}
