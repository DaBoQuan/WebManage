package com.mypack.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileIO {
	public String read(String path){
		String temp ;
		StringBuffer buff = null;
		try {
			buff = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			while((temp=br.readLine())!=null){
				buff.append(temp);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buff.toString();
	}

}
