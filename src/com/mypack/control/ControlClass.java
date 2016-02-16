package com.mypack.control;

import com.mypack.Thread.CmdThread;
import com.mypack.Thread.ReadFileThread;
import com.mypack.Thread.WebMangeThread;
import com.mypack.UI.Main;

public class ControlClass {
	private Main main;
	public ControlClass(Main main) {
		// TODO Auto-generated constructor stub
		this.main=main;
	}
	
	public void addWebMange(String url,String id,String password, String scriptType){
		new Thread(new WebMangeThread(id,url,scriptType, password,main)).start();
	}
	public void addCmd(String url,String id,String password, String scriptType){
		new Thread(new CmdThread(id,url,scriptType, password,main)).start();
	}
	public void addRead(String path,String fileText){
		new Thread(new ReadFileThread(main, path, fileText)).start();
	}
}
