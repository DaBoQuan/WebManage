package com.mypack.util;

import java.awt.Component;  
  
import java.awt.Image;

import javax.swing.ImageIcon;  
import javax.swing.JLabel;
import javax.swing.JTree;  
import javax.swing.tree.DefaultMutableTreeNode;  
import javax.swing.tree.DefaultTreeCellRenderer;  
  
/** 
 * �Զ�����������,������ÿ���ڵ����óɲ�ͬ��ͼ�� 
 * @author RuiLin.Xie - xKF24276 
 * 
 */  
public class MyDefaultTreeCellRenderer extends DefaultTreeCellRenderer  
{  	
	private ImageIcon img;
    /** 
     * ID 
     */  
    private static final long   serialVersionUID    = 1L;  
  
    /** 
     * ��д����DefaultTreeCellRenderer�ķ��� 
     */  
    @Override  
    public Component getTreeCellRendererComponent(JTree tree, Object value,  
            boolean sel, boolean expanded, boolean leaf, int row,  
            boolean hasFocus)  
    {  
  
        //ִ�и���ԭ�Ͳ���  
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,  
                row, hasFocus);  
        img=new ImageIcon("e:/f3.png");
		img.setImage(img.getImage().getScaledInstance(15,14,Image.SCALE_DEFAULT));
		  
        this.setIcon(img);
        return this;  
    }  
  
}  