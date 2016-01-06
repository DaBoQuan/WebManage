package com.mypack.util;

import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ImageRenderer implements TableCellRenderer {
	private ImageIcon img;
	 private JLabel showImg;
	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object arg1,
			boolean arg2, boolean arg3, int arg4, int arg5) {
		if(arg1!=null){
			if(arg1.equals("0")){//ÎÄ¼þ¼Ð
				img=new ImageIcon(this.getClass().getResource("/res/f3.png"));
				img.setImage(img.getImage().getScaledInstance(15,14,Image.SCALE_DEFAULT));
				  
				showImg=new JLabel();
				showImg.setIcon(img);
		        return showImg;
			}else{
				img=new ImageIcon(this.getClass().getResource("/res/f4.png"));
				img.setImage(img.getImage().getScaledInstance(15,14,Image.SCALE_DEFAULT));
				  
				showImg=new JLabel();
				showImg.setIcon(img);
		        return showImg;
			}
		}
		return null;
	}

}
