package com.mypack.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.mypack.test.test;

public class HttpClass {
	AnalysisClass analysisClass;
	public HttpClass(AnalysisClass analysisClass) {
		// TODO Auto-generated constructor stub
		this.analysisClass = analysisClass;
	}

	public String postSend(String url,String postText){
		try {
			byte[] payload = postText.getBytes();
			System.out.println(postText);
			int port=0;
			String host ="";
			if(url!=null && url.length()>0){
				
				if(url.indexOf("http://")!=-1 ||url.indexOf("HTTP://")!=-1){
					url = url.substring(7,url.length());
				}
				if(url.indexOf(":")!=-1){
					port = Integer.parseInt(url.split(":")[1].substring(0,url.split(":")[1].indexOf("/")));
					host = url.substring(0,url.indexOf(":"));
				}else{
					port = 80;
					host = url.substring(0,url.indexOf("/"));
				}
			}
			Socket socket =new  Socket(host,port);
			String path = url.substring(url.indexOf("/"),url.length());
			OutputStream data = socket.getOutputStream();
			String text = "";
			text+="POST "+path+" HTTP/1.0\r\nProxy-Connection: keep-alive\r\n";
			text+="Content-Length: "+payload.length+"\r\n";
			text+="User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36\r\n";
			text+="Content-type: application/x-www-form-urlencoded\r\n";
			text+="\r\n";
			byte[] b3 =new byte[text.getBytes().length+payload.length];
			System.arraycopy(text.getBytes(), 0,b3, 0, text.getBytes().length);  
			System.arraycopy(payload, 0, b3, text.getBytes().length,payload.length);  
			System.out.println(new String(b3));
			data.write(b3);
			data.flush();
			String temp;
			String result="";
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while((temp=br.readLine())!=null){
				result=result+temp+"\r\n";
			}
			result = analysisClass.resultAnalysis(result, null)[0];
			return result;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
