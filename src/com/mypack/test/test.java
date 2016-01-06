package com.mypack.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class test extends JFrame {
	private ImageIcon img;
	 private JLabel showImg;
	 
	 private final static int WIDTH=50;
	 private final static int HEIGHT=42;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test frame = new test();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 236, 182);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		img=new ImageIcon("e:/f3.png");
		  img.setImage(img.getImage().getScaledInstance(test.WIDTH,test.HEIGHT,Image.SCALE_DEFAULT));
		  
		  showImg=new JLabel();
		  showImg.setIcon(img);
		  
		  getContentPane().add(showImg,BorderLayout.CENTER);
		  //this.setBounds(300,200,400,300);
		  //this.pack();
		 // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  this.setVisible(true);
		JLabel lblNewLabel = new JLabel();
		contentPane.add(lblNewLabel, BorderLayout.CENTER);
	}

}
