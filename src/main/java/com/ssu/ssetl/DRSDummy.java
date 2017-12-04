package com.ssu.ssetl;

import java.io.File;

import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.converters.CSVSaver;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;

public class DRSDummy {
	public static void main(String ar[]) {

		CSVLoader loader = new CSVLoader();
		Instances train_data = null;

		try {
			//loader.setSource(new File(Constants.TRAIN_FILE_3));
//			loader.setSource(new File(Constants.TRAIN_FILE_2));
			loader.setSource(new File(Constants.TRAIN_FILE_1));
			train_data = loader.getDataSet();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		train_data = featureSelection(train_data);
		System.out.println(train_data);
		
		//write csv
		CSVSaver saver = new CSVSaver();
		
		try{
			saver.setInstances(train_data);
//			saver.setFile(new File(Constants.TRAIN_TRANS_3));
			saver.setFile(new File(Constants.TRAIN_TRANS_1));
			saver.writeBatch();
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	private static Instances featureSelection(Instances data) {

		Instances newData = null;

		try {
			AttributeSelection filter = new AttributeSelection();
			CfsSubsetEval eval = new CfsSubsetEval();
			GreedyStepwise search = new GreedyStepwise();
			search.setSearchBackwards(true);
			filter.setEvaluator(eval);
			filter.setSearch(search);
			filter.setInputFormat(data);
			// generate new data
			newData = Filter.useFilter(data, filter);
			// System.out.println(newData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newData;
	}
}
