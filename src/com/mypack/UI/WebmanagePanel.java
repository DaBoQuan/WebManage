package com.mypack.UI;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
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
import com.mypack.util.MyDefaultTreeCellRenderer;
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
    private JPopupMenu tableMenu  ;
    private JScrollPane scrollPane;
    private Main main;
	/**
	 * Create the panel.
	 * @param password 
	 * @param scriptType 
	 * @param url 
	 * @param id 
	 */
	public WebmanagePanel(String id, final String url, String scriptType, final String password,final Map<String, String> payload,final Base64 base64,Main main) {
		this.main = main;
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
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(163, 37, 735, 467);
		add(scrollPane);
		
		
		tabModel = new DefaultTableModel(
			new Object[][] {},
			new String[] {
					""	,"\u6587\u4EF6", "修改时间", "\u5927\u5C0F", "\u5C5E\u6027"
			}
		);
		table = new MyTable(tabModel);
		
		tableMenu  = new JPopupMenu();
		JMenuItem uploadMenu = new JMenuItem("上传");
		uploadMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
				jfc.showDialog(table, "open");
				System.out.println(11);
				String filePath = jfc.getSelectedFile().toPath().toString();
				String name = jfc.getSelectedFile().getName();
				String fileval = WebmanagePanel.this.main.getFileIO().read(filePath);
				try {
					upload(WebmanagePanel.this.path.getText()+"/"+name, fileval);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		tableMenu.add(uploadMenu);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(arg0.getClickCount()==2 && arg0.getButton()==MouseEvent.BUTTON1){
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
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton()==MouseEvent.BUTTON3){
					int row = table.rowAtPoint(e.getPoint());
					table.setRowSelectionInterval(row, row);
					tableMenu.show(scrollPane, e.getX(),e.getY());
				}
			}
		});
			

		table.setRowSorter(new TableRowSorter(tabModel));
		table.setRowHeight(18);
		table.getColumnModel().getColumn(1).setPreferredWidth(500);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(120);
		table.getColumnModel().getColumn(4).setPreferredWidth(50);
		table.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
		//图标列，
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
		tree.setCellRenderer(new MyDefaultTreeCellRenderer());
		//树双击事件
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
		
		JButton readbtn = new JButton("读取");
		//读取按钮事件
		readbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				get_ReadDict();
				tree.updateUI();
				//DefaultMutableTreeNode node = path_createTreeNode(path.getText());
				//path_createTreeNode(path.getText());//目录树同步
				
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
		status.setText("载入中。。。。");
		resArr = get_Index();//获取信息。系统，当前目录，磁盘。
		if(resArr != null && resArr.length>0){
			root = new DefaultMutableTreeNode("root");
			DefaultTreeModel treeModel = new DefaultTreeModel(root);
			tree.setModel(treeModel);
			if(resArr[0].indexOf("\\")!=-1 || resArr[2].indexOf("Win")!=-1){//判断为win系统
				String dirves[] = null;
				resArr[1] = resArr[1].replace(":", ":\t");//c:d:e:替换为:\t 便于分割
				dirves = analysisClass.resultAnalysis(resArr[1], "\t");
				if(dirves!=null && dirves.length>0){
					for(String dirve:dirves){
						root.add(new DefaultMutableTreeNode(dirve+"/"));
					}
				}
				path.setText(resArr[0].replace("\\", "/"));
			}else{
				path.setText(resArr[0]);
				root.add(new DefaultMutableTreeNode("/"));
			}

			tree.expandRow(0);
			tree.setRootVisible(false);
			tree.updateUI();
			status.setText("完成");
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
		createTable(temp);//穿件右边列表
		path_AddTree(path_createTreeNode(path.getText()), temp);;//目录树同步
	}
	private void upload(String remotePath,String values) throws UnsupportedEncodingException{
		if(scriptType.toUpperCase().equals("PHP")){
			String path = base64.encodeBase64(remotePath.getBytes());
			path = URLEncoder.encode(path, "UTF-8");
			postText =  password+"=";
			postText = postText+payload.get(scriptType.toUpperCase()+"_MAKE")+"&"+payload.get("ACTION")+"="+payload.get(scriptType.toUpperCase()+"_UPLOAD")+"&"+payload.get("PARAM1")+"="+path+"&"+payload.get("PARAM2")+"="+base64.str2HexStr(values);
		}else if(scriptType.toUpperCase().equals("ASP")){
			postText = password+"="+payload.get(scriptType.toUpperCase()+"_MAKE");
			postText = postText.replace("PAYLOAD",payload.get(scriptType.toUpperCase()+"_UPLOAD"));
			postText = postText+"&"+payload.get("PARAM1")+"="+base64.str2HexStr(remotePath)+"&"+payload.get("PARAM2")+"="+base64.str2HexStr(values);
		}else if(scriptType.toUpperCase().equals("ASPX")){
			System.out.println(remotePath);
			//byte[] temp = remotePath.getBytes("GBK");
			//remotePath = new String(temp, "UTF-8");
			String path = base64.encodeBase64(remotePath.getBytes());
			path = URLEncoder.encode(path, "UTF-8");
			System.out.println(path);
			postText = password+"="+payload.get(scriptType.toUpperCase()+"_UPLOAD");
			postText = postText+"&"+payload.get("PARAM1")+"="+path+"&"+payload.get("PARAM2")+"="+base64.str2HexStr(values);
		}
		String resValue = http.postSend(url, postText);
		String temp[] = analysisClass.resultAnalysis(resValue, null);
		if(temp!=null && temp.length>0 &&temp[0].equals("1")){
			status.setText("上传成功");
		}else{
			status.setText("上传失败");
		}
	}
//获取目标信息
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
	//右边table添加
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
	//区分目录还是文件
	public String[] getFileOrPath(String[] values){//判断是文件还是路径
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
	//目录树
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
					status.setText("文件夹不存在");
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
						tt = b2;//搜索到了直接退出while
						break;
					}
				}
				if(tt==null){//tt=null说明没有搜到，直接添加即可
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


