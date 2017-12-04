package com.ssu.ssetl.transormmy;

import java.util.ArrayList;
import java.util.List;

public class TransformerMy {
	
	public TransformerMy() {
		// TODO Auto-generated constructor stub
		
	}
	
	public List<String> doTransform(List<String> header, List<String> input, int dType){
				
		if(header.size() != input.size()){
			System.err.println("[TMY.err] size error");
			return new ArrayList<String>();
		}
		
		List<String> atts = DDMLinker.getInstance().getAttributes(dType);
		List<String> items = new ArrayList<String>();
				
//		System.out.println("[TMY] header : "+header);
//		System.out.println("[TMY] input : "+input);
//		System.out.println("[TMY] atts : "+atts);
		
		for(int i = 0 ; i < header.size() ; i++){
			String head = header.get(i);
			String item = input.get(i);
			if(atts.contains(head)){
				items.add(item);
			}
		}
		
//		System.out.println("[TMY] output : "+items);
		
//		System.out.println(items);
		
		return items;
	}
}
