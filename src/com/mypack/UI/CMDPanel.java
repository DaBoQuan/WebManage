package com.mypack.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import com.mypack.util.AnalysisClass;
import com.mypack.util.Base64;
import com.mypack.util.HttpClass;

public class CMDPanel extends JPanel {

	/**
	 * Create the panel.
	 * @param main 
	 * @param scriptType 
	 * @param password 
	 * @param url 
	 * @param id 
	 */
	private JTextArea textArea;
	private String id;
	private String url;
	private String password;
	private String scriptType;
	private Main main;
	private HttpClass http;
	private Map<String, String> payload;
	private String	postText ;
	private String temp;
	private AnalysisClass analysisClass;
	private int len ;
	private String path;
	private String execTool;
	public CMDPanel(String id, String url, String password, String scriptType, Main main) {
		this.id = id;
		this.url = url;
		this.password = password;
		this.scriptType = scriptType;
		this.main = main;
		this.payload = main.getPayload();
		http = new HttpClass();
		analysisClass = new AnalysisClass(payload);
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setBounds(14, 13, 887, 493);
		add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				try {
					Rectangle rec = textArea.modelToView(textArea.getCaretPosition());
					int row =  rec.y / rec.height + 1;
					if(textArea.getLineCount()<=row){
						textArea.setEditable(true);
					}else{
						textArea.setEditable(false);
					}
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		textArea.setFont(new Font("ËÎÌו", Font.BOLD, 20));
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==8){
					try {
						String tt  = textArea.getText(textArea.getLineStartOffset(textArea.getLineCount()-1), textArea.getLineEndOffset(textArea.getLineCount()-1) - textArea.getLineStartOffset(textArea.getLineCount()-1) - 1); 
						if(tt.length()<len){
							textArea.setEditable(false);
							textArea.setCaretPosition( textArea.getDocument().getLength());
						}else{
							textArea.setEditable(true);
						}
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(arg0.getKeyChar()==KeyEvent.VK_ENTER ){
					//cmd = textArea.getText(textArea.getLineStartOffset(textArea.getLineCount()-1), textArea.getLineEndOffset(textArea.getLineCount()-1) - textArea.getLineStartOffset(textArea.getLineCount()-1) - 1);
					String[] i = textArea.getText().split("\n");
					String cmd = i[i.length-1];
		        	if(cmd!=null && cmd.length()>0){
		        		textArea.append("\r\n");
						execCmd(cmd.substring(path.length()-1, cmd.length()));
						
						arg0.consume();
		        	}
			       
				}
			}
		});
		textArea.setForeground(Color.GREEN);
		textArea.setBackground(Color.BLACK);
		scrollPane.setViewportView(textArea);
		int i =get_Index();
		System.out.println(path);
		if(i==1){
			execTool="cmd";
			execCmd("ver");
		}else if(i==2){
			execTool="/bin/sh";
			execCmd("uname -a");
		}
	}
	public void execCmd(String cmd){
		if(execTool.equals("cmd")){
		temp = "cd/d\""+path.trim()+"\"&"+cmd+"&echo [S]&cd&echo [E]";
		}else{
		temp = "cd \""+path.trim()+"\";"+cmd+";echo [S];pwd;echo [E]";
		}
		postText= password+"="+payload.get(scriptType.toUpperCase()+"_MAKE")+"&"+payload.get("ACTION")+"="+payload.get(scriptType.toUpperCase()+"_SHELL")+"&"+payload.get("PARAM1")+"="+Base64.encodeBase64(execTool.getBytes())+"&"+payload.get("PARAM2")+"="+Base64.encodeBase64(temp.getBytes());
		System.out.println(postText);
		String   respond = http.postSend(url, postText);
		String resule = respond.substring(respond.indexOf(payload.get("SPL"))+payload.get("SPL").length(), respond.indexOf("[S]")); 
		textArea.append(resule);
		path =respond.substring(respond.indexOf("[S]")+3,respond.indexOf("[E]"));
		textArea.append(execTool.equals("cmd")?path.trim()+"\\":path.trim()+"/");
		len = path.length();
	}
	private int get_Index() {
		// TODO Auto-generated method stub
		String[] temp = null;
		postText = password+"="+payload.get(scriptType.toUpperCase()+"_MAKE")+"&"+payload.get("ACTION")+"="+payload.get(scriptType.toUpperCase()+"_INDEX");
		System.out.println("1.GET_INDEX="+postText);
		String resultText = http.postSend(url, postText);
		
		temp = analysisClass.resultAnalysis(resultText,"\t");
		if(temp!=null && temp.length>0){
			if(temp[2].indexOf("Win")!=-1){
				path = temp[0].replace("/", "\\")+"\\";
				return 1;
			}else{
				path = temp[0];
				return 2;
			}
		}
		return 0;
	}
}
