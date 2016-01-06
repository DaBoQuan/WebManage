package com.mypack.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class tableselect extends JFrame implements ActionListener {

private JTable table;
private DefaultTableModel model = null;
private String[] columnnames = { "no", "name" };
private String[][] data = { { "1", "name1" }, { "2", "name2" },
{ "3", "name3" } };

public tableselect() {
getContentPane().setLayout(null);

JButton btnNewButton = new JButton("change");
btnNewButton.setBounds(12, 10, 93, 23);
btnNewButton.addActionListener(this);
getContentPane().add(btnNewButton);

JScrollPane scrollPane = new JScrollPane();
scrollPane.setBounds(12, 43, 428, 270);
getContentPane().add(scrollPane);

model = new DefaultTableModel(data, columnnames);
table = new JTable(model);
scrollPane.setViewportView(table);

setSize(460, 350);
setDefaultCloseOperation(EXIT_ON_CLOSE);
setVisible(true);
}

public static void main(String[] args) {
new tableselect();
}

public void actionPerformed(ActionEvent e) {
int row = table.getSelectedRow();
System.out.println(table.getValueAt(row, 0));
System.out.println(table.getValueAt(row, 1));
}
}