package com.mypack.util;

import java.util.Map;

public class AnalysisClass {
	private Map<String, String> payload;
	public AnalysisClass(Map<String, String> payload){
		this.payload = payload;
	}
	public String[] resultAnalysis(String result,String split){
		String array[] = new String[]{};
		if(result!=null && result.length()>0){
			if(result.indexOf(payload.get("SPL"))!=-1){
				int i = result.indexOf(payload.get("SPL"))+payload.get("SPL").length();
				int j = result.indexOf(payload.get("SPR"));
				result = result.substring(i, j);
			}
			array = result.split(split);
		}
		return array;
	}
}
