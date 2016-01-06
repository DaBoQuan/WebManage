package com.mypack.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TestTree extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestTree frame = new TestTree();
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
	@SuppressWarnings("serial")
	public TestTree() {
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 574, 398);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JTree tree = new JTree();
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(arg0.getClickCount()==2){
					if(((DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent()).isLeaf()){
						System.out.println(1);
					}else{
						System.out.println(2);
					}
				}
			}
		});
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("1") {
				{
					DefaultMutableTreeNode node_1;
					node_1 = new DefaultMutableTreeNode("2");
						node_1.add(new DefaultMutableTreeNode("3"));
					add(node_1);
					add(new DefaultMutableTreeNode("4"));
					add(new DefaultMutableTreeNode("5"));
				}
			}
		));
		tree.setBounds(14, 13, 140, 325);
		contentPane.add(tree);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(323, 204, 113, 27);
		contentPane.add(btnNewButton);
	}
}
