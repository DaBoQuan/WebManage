package com.mypack.test;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;



import javax.swing.JButton;

import javax.swing.JFrame;

import javax.swing.JScrollPane;

import javax.swing.JTree;

import javax.swing.tree.DefaultMutableTreeNode;

import javax.swing.tree.DefaultTreeModel;



public class ��� extends JFrame {

	private static int i = 1;



	private DefaultMutableTreeNode root;

	private DefaultTreeModel treeModel;

	private JTree tree;



	public ���() {

		root = new DefaultMutableTreeNode("Ĭ�ϸ�");



		// ���ø��ڵ㴴��TreeModel

		this.treeModel = new DefaultTreeModel(root);



		tree = new JTree(treeModel);

		tree.setRootVisible(false); // ��������ϵͳ�����ɼ�



		DefaultMutableTreeNode node0 = new DefaultMutableTreeNode("�ļ�0");

		treeModel.insertNodeInto(node0, root, root.getChildCount());

		tree.expandRow(0);



		JScrollPane treeScrollPane = new JScrollPane(tree);

		this.getContentPane().add(treeScrollPane, BorderLayout.SOUTH);



		JButton btn = new JButton("������֦");

		btn.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {

				String temp = "�ļ�" + i++;

				DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(temp);



				���.this.treeModel.insertNodeInto(node1, root, root.getChildCount());



				���.this.tree.updateUI();



				System.out.println(temp);

			}

		});

		this.getContentPane().add(btn, BorderLayout.NORTH);



		this.setTitle("���β˵�");

		this.pack();

		this.setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}



	public static void main(String[] args) {

		new ���();

	}

}