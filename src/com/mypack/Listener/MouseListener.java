package com.mypack.Listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.mypack.UI.AddAndEditWeb;
import com.mypack.UI.Main;

public class MouseListener implements ActionListener {
	Main main;
	public MouseListener(Main main) {
		// TODO Auto-generated constructor stub
		this.main = main;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		new AddAndEditWeb(main).run("add",null);
	}

}
