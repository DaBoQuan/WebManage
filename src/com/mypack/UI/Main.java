package com.mypack.UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.mypack.Listener.MouseListener;
import com.mypack.control.ControlClass;
import com.mypack.dao.OpenSqlIte;
import com.mypack.util.Base64;
import com.mypack.util.FileIO;
import com.mypack.util.MyTable;

public class Main{

	private JFrame frame;
	private JPopupMenu webListMenu;
	private JScrollPane scrollPane;
	private JTable table;
	private  AddAndEditWeb addWeb;
	private OpenSqlIte openSqlIte= new OpenSqlIte();
	private FileIO fileIO = new FileIO();
	private DefaultTableModel model;
	private String[] res;
	private List<String[]> allResult ;
	private JTabbedPane tabbedPane;
	private ControlClass controlClass;
	private Map<String, String> payload = new HashMap<String, String>();
	private Base64 base64;
	/**
	 * Launch the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args){

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws MalformedURLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public Main() throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws MalformedURLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private void initialize() throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
//		URLClassLoader classLoader = new URLClassLoader(new URL[]{new URL("file:c:/12.jar")}, Thread.currentThread().getContextClassLoader());
//		Class<?> classload = classLoader.loadClass("com.mypack.util.panel");
//		final test test1 = (test) classload.newInstance();
//		test1.testPlugin();
		base64 = new Base64();
		addWeb = new AddAndEditWeb(this);
		controlClass = new ControlClass(this);
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 918, 589);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		loadPayload();
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2){
					tabbedPane.remove(tabbedPane.getSelectedIndex());
				}
			}
		});
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel webList = new JPanel();
		
		webList.setLayout(null);
		tabbedPane.addTab("��վ�б�", null, webList, null);
		
		
		scrollPane = new JScrollPane();
		//��ӵ��б��Ҽ��˵�
		webListMenu = new JPopupMenu();
		JMenuItem menuItem1 = new JMenuItem();
		menuItem1.setText("���");
		//����¼�
		MouseListener mouse = new MouseListener(this);
		menuItem1.addActionListener(mouse);
		
		JMenuItem delMenu = new JMenuItem();
		delMenu.setText("ɾ��");
		//ɾ���¼�
		delMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					getSelect();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		JMenuItem editMenu = new JMenuItem();
		editMenu.setText("�޸�");
		//�޸��¼�
		editMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					addWeb.run("edit", getOneResult());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		JMenuItem cmdMenu = new JMenuItem("cmd");
		cmdMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int i = table.getSelectedRow();
				cmd(table.getValueAt(i, 0).toString(),table.getValueAt(i, 1).toString(),table.getValueAt(i, 2).toString(),table.getValueAt(i, 4).toString());
			}
		});
		webListMenu.add(editMenu);
		webListMenu.add(menuItem1);
		webListMenu.add(delMenu);
		webListMenu.add(cmdMenu);
		//scrollPane�հ��б�����Ҽ��¼�
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton()==MouseEvent.BUTTON3)//ֻ��Ӧ����Ҽ������¼�
				{
					webListMenu.show(scrollPane, e.getX(), e.getY());
				}
				
			}
		});
		
		scrollPane.setBounds(0, 0, 909, 522);
		webList.add(scrollPane);
		
		model = new DefaultTableModel(new Object[][]{},new String[] {"id","\u7F51\u7AD9\u5730\u5740", "\u811A\u672C\u7C7B\u578B", "ip\u5730\u5740", "\u5BC6\u7801"});
		getAllresult();
		table = new MyTable(model);
		//����id��
		TableColumn t0 = table.getTableHeader().getColumnModel().getColumn(0);
		t0.setMaxWidth(0);
		t0.setPreferredWidth(0);
		t0.setWidth(0);
		t0.setMinWidth(0);
		table.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
		table.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
		//url��
		table.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(600);
		
		//table �б��¼�

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(e.getClickCount()==2){
					int i = table.getSelectedRow();
					webManage(table.getValueAt(i, 0).toString(),table.getValueAt(i, 1).toString(),table.getValueAt(i, 2).toString(),table.getValueAt(i, 4).toString());
				}
				if(e.getButton()==MouseEvent.BUTTON3)//ֻ��Ӧ����Ҽ������¼�
				{
					//�Ҽ��Զ�ѡ��
					 int row = table.rowAtPoint(e.getPoint());
					 table.setRowSelectionInterval(row,row);
					webListMenu.show(scrollPane, e.getX(), e.getY());
				}
			}
		});
		scrollPane.setViewportView(table);


		
	}
	public void webManage(String id,String url,String scriptType,String password){
		controlClass.addWebMange(url, id, scriptType,password);
	}
	public void cmd(String id,String url,String scriptType,String password){
		controlClass.addCmd(url, id, password, scriptType);
	}
	//��ȡ���н��
	public  void getAllresult(){
		//���table��������
		model.setRowCount(0);
		Object obj[] ;
		allResult = openSqlIte.getAllResultSet();
		for(String[] resArray : allResult){
			model.addRow(resArray);
		}
	}
	//table ѡ��ɾ��
	public void getSelect() throws SQLException{
		int[] i = table.getSelectedRows();
		for(int index : i){
			openSqlIte.delOne((String) table.getValueAt(index, 0));
		}
		getAllresult();
	}
	//��ȡһ��
	public String[] getOneResult() throws SQLException{
		int i = table.getSelectedRow();
		res =  openSqlIte.getOneResultSet((String) table.getValueAt(i, 0));
		return res;
	}
	//�޸�һ��
	public int updateOne(String[] values){
		int i =openSqlIte.updateOne(values);
		this.getAllresult();
		return i;
	}
	//���һ��
	public int addOne(String[] values){
		int i = openSqlIte.addOne(values);
		this.getAllresult();
		return i;
	}
	//����payload
	public void loadPayload(){
		Properties prop = new Properties();
		InputStream in = getClass().getResourceAsStream("/config.ini");
		try {
			prop.load(in);
			 Iterator<Entry<Object, Object>> it = prop.entrySet().iterator();
			 while(it.hasNext()){
				 Entry<Object,Object > en = it.next();
				 payload.put(String.valueOf(en.getKey()),String.valueOf(en.getValue()));
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
//get=====set
	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	public Map<String, String> getPayload() {
		return payload;
	}

	public void setPayload(Map<String, String> payload) {
		this.payload = payload;
	}

	public Base64 getBase64() {
		return base64;
	}

	public void setBase64(Base64 base64) {
		this.base64 = base64;
	}
	
	public FileIO getFileIO() {
		return fileIO;
	}

	public void setFileIO(FileIO fileIO) {
		this.fileIO = fileIO;
	}

}
