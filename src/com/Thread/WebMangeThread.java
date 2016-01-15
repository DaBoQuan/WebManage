package com.Thread;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.mypack.UI.Main;
import com.mypack.UI.WebmanagePanel;

public class WebMangeThread implements Runnable {
	private String url;
	private String password;
	private Main main;
	private String scriptType;
	private String id;
	public WebMangeThread(String id,String url, String password,String scriptType, Main main) {
		this.id=id;
		this.url = url;
		this.password = password;
		this.main=main;
		this.scriptType=scriptType;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		JTabbedPane jTabbed = main.getTabbedPane();
		JPanel tt = new WebmanagePanel(id,url,scriptType,password,main.getPayload(),main.getBase64(),main);
		jTabbed.addTab(url, tt);
		main.getTabbedPane().setSelectedComponent(tt);
	}
	
}
