package com.mypack.util;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class AnalysisClass {
	private Map<String, String> payload;
	public AnalysisClass(Map<String, String> payload){
		this.payload = payload;
	}
	public String[] resultAnalysis(String result,String split){
		String array[]=null;
		if(result!=null && result.length()>0){
			if(result.indexOf(payload.get("SPL"))!=-1){
				int i = result.indexOf(payload.get("SPL"))+payload.get("SPL").length();
				int j = result.indexOf(payload.get("SPR"));
				if(i>0 && j>0){
				result = result.substring(i, j);
				}
			}
			if(split!=null){
				array = result.split(split);
			}else{
				return new String[]{result};
			}
		}
		return array;
	}
	public String coding(String text,String coding) throws UnsupportedEncodingException{
		if(text!=null && coding!=null){
			return new String(text.getBytes(), coding);
		}
		return "";
	}
}
