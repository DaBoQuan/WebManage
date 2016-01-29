package com.mypack.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;

import com.mypack.UI.Main;

public class HttpClass {
	private Map<String, String> payload;
	AnalysisClass analysisClass;
	public HttpClass(AnalysisClass analysisClass,Map<String, String> payload) {
		// TODO Auto-generated constructor stub
		this.analysisClass = analysisClass;
		this.payload = payload;
	}
	public static String getip(String url){
		try {
			if(url!=null && url.length()>0){
				
				if(url.indexOf("http://")!=-1 ||url.indexOf("HTTP://")!=-1){
					url = url.substring(7,url.length());
				}
				if(url.indexOf(":")!=-1){
					url = url.substring(0,url.indexOf(":"));
				}else{
					url = url.substring(0,url.indexOf("/"));
				}
			}
			return InetAddress.getByName(url).getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String postSend(String url,String postText){
		try {
			byte[] send = postText.getBytes();
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
			text+=payload.get("Request-type")+" "+path+" HTTP/1.1\r\nConnection:close\r\n";
			text+="Host: "+host+"\r\n";
			text+="Content-Length: "+send.length+"\r\n";
			text+="User-Agent: "+payload.get("User-Agent")+"\r\n";
			text+="Content-type: application/x-www-form-urlencoded\r\n";
			text+="\r\n";
			byte[] b3 =new byte[text.getBytes().length+send.length];
			System.arraycopy(text.getBytes(), 0,b3, 0, text.getBytes().length);  
			System.arraycopy(send, 0, b3, text.getBytes().length,send.length);  
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
