package com.Thread;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.mypack.UI.CMDPanel;
import com.mypack.UI.Main;
import com.mypack.UI.WebmanagePanel;

public class CmdThread implements Runnable{
	private String url;
	private String password;
	private Main main;
	private String scriptType;
	private String id;
	public CmdThread(String id,String url, String password,String scriptType, Main main) {
		this.id=id;
		this.url = url;
		this.password = password;
		this.main=main;
		this.scriptType=scriptType;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		JTabbedPane Jtp = main.getTabbedPane();
		JPanel tt = new CMDPanel(id,url,password,scriptType,main);
		Jtp.addTab(url, tt);
		main.getTabbedPane().setSelectedComponent(tt);
	}

}
