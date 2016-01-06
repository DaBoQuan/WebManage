package com.mypack.test;


import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class JSONViewer extends JPanel {
    private JTree tree;//创建默认的树
    private TreePath treePath;//路径
    int t=0;
    public JSONViewer() {
    	
        this.setLayout(new BorderLayout());
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("我的计算机");//设置跟节点
        tree = new JTree(root);
        File[] roots = File.listRoots();
        for (int i = 0; i < roots.length; i++) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(roots[i].getPath());
            root.add(node);//增加到根节点
        }
        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                try {
                    TreePath tp = tree.getPathForLocation(e.getX(), e.getY());
                    String ttt=tp.getLastPathComponent().toString();
                    System.out.println(ttt+"#############");
                    System.out.println("----------->"+tp);
                    if (tp != null) {
                        treePath = tp;
                        int size = treePath.getPath().length;
                        System.out.println("------>"+size);
                        StringBuffer pathname = new StringBuffer();
                        if (size > 0) {
                            File file = new File(ttt+"\\");
                            File[] list = file.listFiles();
                            for (int i = 0; i < list.length; i++) {
                                String[] strings = list[i].getPath().split("////");
                                for(int j=0;j<strings.length;j++){
                                	System.out.println(strings[j]+"%%%%%%%%%%%%%%%%%%%%");
                                }
                                DefaultMutableTreeNode node = new DefaultMutableTreeNode(strings[strings.length-1]);
                                ((DefaultMutableTreeNode) tp.getLastPathComponent()).add(node);
                            }
                        }
                    }
                } catch (Exception e1) {
                    System.out.println("can not open");
                }
            }
        };
        tree.addMouseListener(ml);
        tree.setShowsRootHandles(true);
        tree.setRootVisible(true);
        JScrollPane scrPane = new JScrollPane(tree);
        add(scrPane);
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Explorer");
        jFrame.setLayout(new BorderLayout());
        jFrame.add(new JSONViewer());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(300, 700);
        jFrame.setVisible(true);
    }
}
