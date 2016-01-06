package com.mypack.UI;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


import com.mypack.util.HttpClass;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Font;

public class CMDPanel extends JPanel {

	/**
	 * Create the panel.
	 * @param main 
	 * @param scriptType 
	 * @param password 
	 * @param url 
	 * @param id 
	 */
	private JTextArea textArea;
	private String id;
	private String url;
	private String password;
	private String scriptType;
	private Main main;
	private HttpClass http;
	private Map<String, String> payload;
	public CMDPanel(String id, String url, String password, String scriptType, Main main) {
		this.id = id;
		this.url = url;
		this.password = password;
		this.scriptType = scriptType;
		this.main = main;
		this.payload = main.getPayload();
		http = new HttpClass();
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setBounds(14, 13, 887, 493);
		add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==10){
					System.out.println(1);
					String[] ml = textArea.getText().split("\r\n");
					System.out.println(ml[ml.length-1]);
					textArea.append("\r\nc:/windows/system32/");
				}
			}
		});
		
		textArea.setForeground(Color.GREEN);
		textArea.setBackground(Color.BLACK);
		textArea.setText("c:/windows/system32/");
		scrollPane.setViewportView(textArea);
		textArea.requestFocus();

	}
}
