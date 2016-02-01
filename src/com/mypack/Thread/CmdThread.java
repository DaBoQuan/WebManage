package com.mypack.Thread;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.mypack.UI.CMDPanel;
import com.mypack.UI.Main;

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
		JPanel tt;
		try {
			tt = new CMDPanel(id,url,scriptType,password,main);
			Jtp.addTab(url, tt);
			main.getTabbedPane().setSelectedComponent(tt);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
