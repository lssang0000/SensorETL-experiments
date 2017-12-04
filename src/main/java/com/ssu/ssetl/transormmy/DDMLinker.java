package com.ssu.ssetl.transormmy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.ssu.ssetl.Constants;

public class DDMLinker {
	
	private static DDMLinker instance;
	
	DocumentBuilderFactory dbFactory;
	DocumentBuilder dBuilder;
	
	private DDMLinker() {
		// TODO Auto-generated constructor stub
		try{
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static synchronized DDMLinker getInstance(){
		if (instance == null) {
			instance = new DDMLinker();
		}		
		return instance;
	}
	
	public List<String> getAttributes(int dType){
		
//		System.out.println("[Linker] dType : " + dType);
		
		List<String> list = new ArrayList<String>();
		String ddmPath = "";
		switch (dType) {
		case Constants.ANALYZER_SSU: ddmPath = Constants.DDM_U3; break;
		case Constants.ANALYZER_MARKETING: ddmPath = Constants.DDM_MARKETING; break;
		case Constants.ANALYZER_HEART: ddmPath = Constants.DDM_HEART; break;
		default:
		}
		
		try{
			Document doc = dBuilder.parse(new File(ddmPath));
			NodeList attList = doc.getElementsByTagName("variable");
			for(int i = 0 ; i < attList.getLength() ; i++){
				Element variable = (Element) attList.item(i);
				list.add(variable.getAttribute("name"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		
		return list;
	}
}
