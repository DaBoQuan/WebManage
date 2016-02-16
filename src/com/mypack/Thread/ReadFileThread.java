package com.mypack.Thread;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.mypack.UI.Main;
import com.mypack.UI.ReadFileUi;

public class ReadFileThread implements Runnable {
	private Main main;
	private String path;
	private String fileText;
	public ReadFileThread(Main main,String path,String fileText) {
		this.main=main;
		this.path = path;
		this.fileText = fileText;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		JTabbedPane jTabbed = main.getTabbedPane();
		JPanel jj = new ReadFileUi(path,fileText);
		jTabbed.add(path,jj);
		main.getTabbedPane().setSelectedComponent(jj);
	}

}
