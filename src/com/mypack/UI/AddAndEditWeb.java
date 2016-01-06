package com.mypack.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.mypack.dao.OpenSqlIte;
import com.mypack.test.testloadplugin;

public class AddAndEditWeb {
	JFrame frame;
	private JTextField urlField;
	private JTextField password;
	private JTextArea configArea;
	private JComboBox listComboBox ;
	private JComboBox scriptTypeComboBox;
	private JComboBox codingComboBox ;
	private JLabel idlab;
	private Main main ;
	private String res[];
	private String values[];
	/**
	 * @param main 
	 * @wbp.parser.entryPoint
	 */
	public AddAndEditWeb(){
	}
	public AddAndEditWeb(Main main){
		this.main = main;
	}
	public void run(String text,String[] res){
		//创建主窗口
		frame = new JFrame(text.equals("edit")?"修改网站":"添加网站");
		frame.setVisible(true);
		frame.getContentPane().setLayout(null);
		urlField = new JTextField();
		urlField.setBounds(14, 13, 437, 24);
		idlab = new JLabel();
		idlab.setEnabled(false);
		frame.getContentPane().add(urlField);
		urlField.setColumns(10);
		password = new JTextField();
		password.setSize(67, 24);
		password.setLocation(465, 13);
		frame.getContentPane().add(password);
		password.setColumns(10);
		configArea = new JTextArea();
		configArea.setBounds(14, 50, 518, 137);
		frame.getContentPane().add(configArea);
		
		listComboBox = new JComboBox();
		listComboBox.setBounds(14, 195, 82, 24);
		frame.getContentPane().add(listComboBox);
		scriptTypeComboBox = new JComboBox();
		scriptTypeComboBox.setModel(new DefaultComboBoxModel(new String[] {"ASP", "PHP", "ASPX", "JSP"}));
		scriptTypeComboBox.setBounds(125, 195, 82, 24);
		frame.getContentPane().add(scriptTypeComboBox);
		
		codingComboBox = new JComboBox();
		codingComboBox.setModel(new DefaultComboBoxModel(new String[] {"UTF-8", "GBK"}));
		codingComboBox.setBounds(242, 195, 82, 24);
		frame.getContentPane().add(codingComboBox);
		frame.addWindowListener(new WindowAdapter() {//监听退出事件
	       	public void windowClosing(WindowEvent e) {
	       		frame.dispose();
	       	}
		});
		this.res=res;
		if(text.equals("edit") && res!=null){
			this.edit();
		}else{
			this.Add();
		}
	}
	private void edit() {
		idlab.setText(res[0]);
		urlField.setText(res[1]);
		password.setText(res[4]);
		codingComboBox.setSelectedItem(res[6]);
		configArea.setText(res[5]);
		scriptTypeComboBox.setSelectedItem(res[2]);
		JButton addButton = new JButton("修改");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				values = new String[]{urlField.getText(),scriptTypeComboBox.getSelectedItem().toString(),password.getText(),configArea.getText(),codingComboBox.getSelectedItem().toString(),idlab.getText()};
				if(main.updateOne(values)==1){
					frame.dispose();
				}
			}
		});
		addButton.setBounds(351, 194, 113, 27);
		frame.getContentPane().add(addButton);
		frame.setBounds(100, 100, 562, 279);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	public void Add() {
		urlField.setText("http://");
		//添加
		JButton addButton = new JButton("\u6DFB\u52A0");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chenkInput()){
					String values[] =new String[]{urlField.getText(),scriptTypeComboBox.getSelectedItem().toString(),password.getText(),configArea.getText(),codingComboBox.getSelectedItem().toString()};
					if(main.addOne(values)==1){
						frame.dispose();
					}
				}
			}
		});
		addButton.setBounds(351, 194, 113, 27);
		frame.getContentPane().add(addButton);
		frame.setBounds(100, 100, 562, 279);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	
	public boolean chenkInput(){
		if(urlField.getText()!=null && urlField.getText().length()>0){
			
		}else{
			JOptionPane.showMessageDialog(null, "url不能为空", "错误", JOptionPane.ERROR_MESSAGE); 
			return false;
		}
		if(password.getText()!=null && password.getText().length()>0){
			
		}else{
			JOptionPane.showMessageDialog(null, "password不能为空", "错误", JOptionPane.ERROR_MESSAGE); 
			return false;
		}
		return true;
		
	}


}
