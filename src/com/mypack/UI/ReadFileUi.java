package com.mypack.UI;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ReadFileUi extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public ReadFileUi(String path,String fileText) {
		setLayout(null);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(10, 10, 805, 28);
		add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 43, 894, 478);
		add(scrollPane);
		
		JTextArea fileshow = new JTextArea();
		fileshow.setEditable(false);
		scrollPane.setViewportView(fileshow);
		fileshow.append(fileText);
		textField.setText(path);
	}
}
