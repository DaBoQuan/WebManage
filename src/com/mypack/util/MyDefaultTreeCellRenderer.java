package com.mypack.util;

import java.awt.Component;  
  
import java.awt.Image;

import javax.swing.ImageIcon;  
import javax.swing.JLabel;
import javax.swing.JTree;  
import javax.swing.tree.DefaultMutableTreeNode;  
import javax.swing.tree.DefaultTreeCellRenderer;  
  
/** 
 * 自定义树描述类,将树的每个节点设置成不同的图标 
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
     * 重写父类DefaultTreeCellRenderer的方法 
     */  
    @Override  
    public Component getTreeCellRendererComponent(JTree tree, Object value,  
            boolean sel, boolean expanded, boolean leaf, int row,  
            boolean hasFocus)  
    {  
  
        //执行父类原型操作  
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,  
                row, hasFocus);  
        img=new ImageIcon("e:/f3.png");
		img.setImage(img.getImage().getScaledInstance(15,14,Image.SCALE_DEFAULT));
		  
        this.setIcon(img);
        return this;  
    }  
  
}  