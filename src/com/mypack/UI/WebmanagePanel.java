package com.mypack.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.mypack.util.AnalysisClass;
import com.mypack.util.Base64;
import com.mypack.util.HttpClass;
import com.mypack.util.ImageRenderer;
import com.mypack.util.MyTable;

public class WebmanagePanel extends JPanel {
	private JTextField path;
	private HttpClass http;
	private Map<String, String> payload ;
	private String resArr[];
	private String id;
	private String url;
	private String scriptType;
	private String password;
	private String postText;
	private String resultText;
	private DefaultTableModel tabModel;
	private int openTree = 0;
    JTree tree;
    DefaultTreeModel model;
    DefaultMutableTreeNode temproot;
    DefaultMutableTreeNode root;
    JScrollPane treescrollPane;
    JLabel status;
    private Base64 base64;
    private JTable table;
    private AnalysisClass analysisClass;
	/**
	 * Create the panel.
	 * @param password 
	 * @param scriptType 
	 * @param url 
	 * @param id 
	 */
	public WebmanagePanel(String id, final String url, String scriptType, final String password,final Map<String, String> payload,final Base64 base64) {
		this.payload = payload;
	    this.id=id;
	    this.url=url;
	    this.scriptType = scriptType;
	    this.password = password;
	    this.base64 = base64;
		analysisClass = new AnalysisClass(payload);
		http = new HttpClass(analysisClass);
		setLayout(null);
		path = new JTextField();
		path.setBounds(57, 5, 750, 24);
		add(path);
		path.setColumns(10);
		
		JLabel label = new JLabel("\u8DEF\u5F84\uFF1A");
		label.setBounds(14, 11, 45, 18);
		add(label);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(163, 37, 735, 467);
		add(scrollPane);
		
		
		tabModel = new DefaultTableModel(
			new Object[][] {},
			new String[] {
					""	,"\u6587\u4EF6", "�޸�ʱ��", "\u5927\u5C0F", "\u5C5E\u6027"
			}
		);
		table = new MyTable(tabModel);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(arg0.getClickCount()==2){
					int index = table.getSelectedRow();
					if(table.getValueAt(index, 0).equals("0")){
						String temp = path.getText();
						if(!(temp.substring(temp.length()-1, temp.length())).equals("/")){
							temp = temp+"/";
						}
						path.setText(temp+table.getValueAt(index, 1));
						get_ReadDict();
					}
				}
			}
		});
		//table��˫���¼�
		
		table.setRowSorter(new TableRowSorter(tabModel));
		table.setRowHeight(18);
		table.getColumnModel().getColumn(1).setPreferredWidth(500);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(120);
		table.getColumnModel().getColumn(4).setPreferredWidth(50);
		table.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
		//ͼ���У�
		TableColumn t0 = table.getTableHeader().getColumnModel().getColumn(0);
		t0.setMaxWidth(17);
		t0.setPreferredWidth(17);
		t0.setWidth(17);
		t0.setMinWidth(17);
		table.getColumnModel().getColumn(0).setMaxWidth(17);
		table.getColumnModel().getColumn(0).setMinWidth(17);
		scrollPane.setViewportView(table);
		tree = new JTree();
	
		tree.setShowsRootHandles(true);
		//��˫���¼�
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(arg0.getClickCount()==2){
					String pathtxt="";
					TreePath tp = tree.getPathForLocation(arg0.getX(), arg0.getY());
					DefaultMutableTreeNode de = (DefaultMutableTreeNode) tp.getLastPathComponent();
					//de.removeAllChildren();
					for(int i = 1;i<tp.getPath().length;i++){
						String temp =tp.getPath()[i].toString();
						if(temp.substring(temp.length()-1,temp.length()).equals("/")){
							pathtxt=pathtxt+temp;
						}else{
							pathtxt=pathtxt+temp+"/";
						}
	            	}
					System.out.println(pathtxt);
					path.setText(pathtxt);
					get_ReadDict();
					tree.updateUI();
				}
			}
		});
		treescrollPane = new JScrollPane();
		treescrollPane.setViewportView(tree);
		treescrollPane.setBounds(14, 37, 145, 467);
		add(treescrollPane);
		
		JButton readbtn = new JButton("��ȡ");
		//��ȡ��ť�¼�
		readbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				get_ReadDict();
				tree.updateUI();
				//DefaultMutableTreeNode node = path_createTreeNode(path.getText());
				//path_createTreeNode(path.getText());//Ŀ¼��ͬ��
				
			}
		});
		readbtn.setBounds(821, 4, 77, 27);
		add(readbtn);
		
		status = new JLabel("");
		status.setBounds(14, 505, 152, 24);
		add(status);
		this.open();
	}
	
	public void open(){
		status.setText("�����С�������");
		resArr = get_Index();//��ȡ��Ϣ��ϵͳ����ǰĿ¼�����̡�
		if(resArr != null && resArr.length>0){
			root = new DefaultMutableTreeNode("root");
			DefaultTreeModel treeModel = new DefaultTreeModel(root);
			tree.setModel(treeModel);
			if(resArr[0].indexOf("\\")!=-1 || resArr[2].indexOf("Win")!=-1){//�ж�Ϊwinϵͳ
				String dirves[] = null;
				resArr[1] = resArr[1].replace(":", ":\t");//c:d:e:�滻Ϊ:\t ���ڷָ�
				dirves = analysisClass.resultAnalysis(resArr[1], "\t");
				if(dirves!=null && dirves.length>0){
					for(String dirve:dirves){
						root.add(new DefaultMutableTreeNode(dirve+"/"));
					}
				}
				path.setText(resArr[0].replace("\\", "/"));
			}else{
				path.setText("/");
				root.add(new DefaultMutableTreeNode("/"));
			}

			tree.expandRow(0);
			tree.setRootVisible(false);
			tree.updateUI();
			status.setText("���");
		}
	}
	private void get_ReadDict(){
		String[] temp = null;
		postText="";
		if(scriptType.toUpperCase().equals("ASP")){
			postText = password+"="+payload.get(scriptType.toUpperCase()+"_MAKE");
			postText = postText.replace("PAYLOAD", payload.get(scriptType.toUpperCase()+"_READDICT"));
			postText = postText+"&"+payload.get("PARAM1")+"="+base64.str2HexStr(path.getText());
		}
		if(scriptType.toUpperCase().equals("PHP")){
			postText = password+"="+payload.get(scriptType.toUpperCase()+"_MAKE")+"&"+payload.get("ACTION")+"="+payload.get(scriptType.toUpperCase()+"_READDICT")+"&"+payload.get("PARAM1")+"="+base64.encodeBase64(path.getText().getBytes());
		}
		if(scriptType.toUpperCase().equals("ASPX")){
			postText = password+"="+payload.get(scriptType.toUpperCase()+"_READDICT");
			postText = postText+"&"+payload.get("PARAM1")+"="+base64.encodeBase64(path.getText().getBytes());
		}
		System.out.println("2.GET_READDICT"+postText);
		resultText = http.postSend(url, postText);
		temp = analysisClass.resultAnalysis(resultText, "\n");
		createTable(temp);//�����ұ��б�
		path_AddTree(path_createTreeNode(path.getText()), temp);;//Ŀ¼��ͬ��
	}
//��ȡĿ����Ϣ
	private String[] get_Index() {

		// TODO Auto-generated method stub
		String[] temp = null;
		if(payload.get(scriptType.toUpperCase()+"_MAKE").indexOf("PAYLOAD")!=-1){
			postText = password+"="+payload.get(scriptType.toUpperCase()+"_MAKE");
			postText = postText.replace("PAYLOAD", payload.get(scriptType.toUpperCase()+"_INDEX"));
		}else{
			postText = password+"="+payload.get(scriptType.toUpperCase()+"_MAKE")+"&"+payload.get("ACTION")+"="+payload.get(scriptType.toUpperCase()+"_INDEX");
		}
		System.out.println("1.GET_INDEX="+postText);
		resultText = http.postSend(url, postText);
		System.out.println(resultText);
		temp = analysisClass.resultAnalysis(resultText,"\t");
		if(temp!=null && temp.length>0){
			return temp;
		}
		return null;
	}
	//�ұ�table���
	public void createTable(String[] values){
		tabModel.setRowCount(0);
		String[] addTemp = null;
		if(values!=null & values.length!=-1){
			for(int i =0 ;i<values.length;i++){
				if(values[i].indexOf("../")==-1 && values[i].indexOf("./")==-1){
					addTemp = analysisClass.resultAnalysis(values[i],"\t");
					tabModel.addRow(getFileOrPath(addTemp));
				}
			}
		}
	}
	//����Ŀ¼�����ļ�
	public String[] getFileOrPath(String[] values){//�ж����ļ�����·��
		String[] temp =null;
		if(null!=values&&values.length>3){
			if(values[0].indexOf("/")!=-1){
				temp = new String[]{"0",values[0],values[1],values[2],values[3]};
			}else{
				temp = new String[]{"1",values[0],values[1],values[2],values[3]};
			}
		}
		return temp;
	}
	//Ŀ¼��
	public void path_AddTree(DefaultMutableTreeNode path,String[] values){
		String[] addTemp = null;
		for(int i =0 ;i<values.length;i++){
			if(values[i].indexOf("../")==-1 && values[i].indexOf("./")==-1){
				addTemp = analysisClass.resultAnalysis(values[i],"\t");
				addTemp = getFileOrPath(addTemp);
				if(addTemp!=null && addTemp.length>0){
					if(addTemp[0].equals("0")){
						path.add(new DefaultMutableTreeNode(addTemp[1]));
					}
				}else{
					status.setText("�ļ��в�����");
				}
			}
		}
	}
	public DefaultMutableTreeNode path_createTreeNode(String path){
		String[] temp = null;
		path = path.replace("/", "/\t");
		temp = analysisClass.resultAnalysis(path, "\t");
		DefaultMutableTreeNode test = root;
		for(int i=0;i<temp.length;i++){
			if(temp[i]!=null && temp[i].length()>0){
				DefaultMutableTreeNode tt = null;
				Enumeration<DefaultMutableTreeNode> b1 = test.breadthFirstEnumeration();
				while(b1.hasMoreElements()){
					DefaultMutableTreeNode b2 = b1.nextElement();
					if(b2.getUserObject().equals(temp[i])){
						tt = b2;//��������ֱ���˳�while
						break;
					}
				}
				if(tt==null){//tt=null˵��û���ѵ���ֱ����Ӽ���
					test.add(new DefaultMutableTreeNode(temp[i]));
					i = i-1;
				}else{
					test = tt;
				}
			}
		}
		return test;
	}
}


