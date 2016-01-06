package com.mypack.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClass {
	public String postSend(String url,String postText){
		try {
			URL openurl = new URL(url);
			StringBuffer resulet = new StringBuffer();
			HttpURLConnection conn = (HttpURLConnection) openurl.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setInstanceFollowRedirects(true);  
			conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			OutputStream op = conn.getOutputStream();
			op.write(postText.getBytes());
			op.flush();
			op.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String tt=null;
			while((tt=br.readLine())!=null){
				resulet.append(tt+"\n");
			}
			br.close();
			op.close();
			conn.disconnect();
			return resulet.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
