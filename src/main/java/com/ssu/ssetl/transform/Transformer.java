package com.ssu.ssetl.transform;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;

import com.ssu.ssetl.Constants;

import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.Remove;

public class Transformer {

	CSVLoader loader = null;
	Instances train_data = null;
	Instances input_data = null;
	Instances trans_data = null;
	
	public Transformer() {
		// TODO Auto-generated constructor stub
		loader = new CSVLoader();
	}

	public void doLoad(String msg) {

	}
	
	public List<String> doTransform(List<String> header, List<String> input, int aType) {

		String fPath = "";
		switch (aType) {
		case 1:	fPath = Constants.TRAIN_FILE_1;	break;
		case 2:	fPath = Constants.TRAIN_FILE_2;	break;
		case 3: fPath = Constants.TRAIN_FILE_3; break;
		default:
		}
		
		//set Train
		try{
			loader.setSource(new File(fPath));
			train_data = loader.getDataSet();
			
//			//remove
//			String[] opetions = {"-R","1"};
//			Remove rm = new Remove();
//			rm.setOptions(opetions);
//			rm.setInputFormat(train_data);
//			train_data = Filter.useFilter(train_data, rm);
		}catch(Exception e){
			e.printStackTrace();
		}				
				
		
		//set Input
		File f = new File("temp");
		
//		System.out.println("header : "+header);
//		System.out.println("input : "+input);
		
//		StringBuilder header = new StringBuilder();
//		StringBuilder values = new StringBuilder();
//		Iterator<String> keys = json.keySet().iterator();
//		while(keys.hasNext()){
//			String key = keys.next();
//			String val = json.get(key).toString();
//			
//			header.append(key);
//			values.append(val);
//			if(keys.hasNext()){
//				header.append(", ");
//				values.append(", ");
//			}
//		}
//		System.out.println(header);
//		System.out.println(values);
		
		StringBuilder hStr = new StringBuilder();
		StringBuilder iStr = new StringBuilder();
		
		for(int i = 0 ; i < header.size() ; i++){
			hStr.append(header.get(i));
			if(i != header.size()-1)
				hStr.append(", ");
			
			iStr.append(input.get(i));
			if(i != input.size()-1)
				iStr.append(", ");
		}
		
//		System.out.println("hStr :"+hStr);
//		System.out.println("iStr :"+iStr);
		
		try{			
			BufferedWriter out = new BufferedWriter(new FileWriter(f));
			out.write(hStr.toString()+"\n");
			out.write(iStr.toString());
			out.close();			
			
			BufferedReader in = new BufferedReader(new FileReader(f));
			String line="";
			while((line = in.readLine())!=null){
//				System.out.println("[line !!]"+line);
			}
			
			loader.setSource(f);
			input_data = loader.getDataSet();
		}catch(Exception e){
			e.printStackTrace();
		}
		
//		System.out.println(input_data);
		
		//print input
//		System.out.println(input_data);
//		for (int i = 0; i < input_data.size(); i++) {
//			Instance doc = input_data.get(i);
//			System.out.println("doc[" + i + "] : " + doc);
//		}
		
		Instance data = input_data.get(0);
//		System.out.println("[1.input]"+data);
		
		
		
		//add Input to Train
		train_data.add(data);
//		System.out.println("[2.get1Last]"+train_data.get(train_data.size()-1));
				
//		System.out.println("========================");
//		System.out.println(train_data);
//		System.out.println("========================");
//		
//		System.out.println("[3.get2Last]"+train_data.get(train_data.size()-2));
		
		//Transform
		trans_data = featureSelection(train_data);
//		System.out.println("[2.transfromed]"+trans_data.get(trans_data.size()-1));
//		System.out.println("[3.transformed]"+trans_data.get(trans_data.size()-2));
		
		//extract transformed input
//		System.out.println("[4.extract 2.t] : "+trans_data.get(trans_data.size()-1));
		String resStr = trans_data.get(trans_data.size()-1).toString();
		List<String> items = Arrays.asList(resStr.split("\\s*,\\s*"));
//		System.out.println("[5.return val] "+items.toString());
		
		return items;
	}

	
	private Instances featureSelection(Instances data) {
		
		
		Instances newData = null;
		
		try{
			AttributeSelection filter = new AttributeSelection();
			CfsSubsetEval eval = new CfsSubsetEval();
			GreedyStepwise search = new GreedyStepwise();
			search.setSearchBackwards(true);
			filter.setEvaluator(eval);
			filter.setSearch(search);
			filter.setInputFormat(data);
			// generate new data
			newData = Filter.useFilter(data, filter);
//			System.out.println(newData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return newData;
	}
}
