package com.mypack.test;

import java.io.*;  
/** 
 * @author E-mail:dbdxj@yahoo.com.cn 
 * @version create time��2010-4-15 ����10:03:03 
 *  
 */  
public class testcmd {  
      
    class ReaderConsole implements Runnable{  
        private InputStream is;  
        public ReaderConsole(InputStream is){  
            this.is = is;  
        }  
        public void run(){  
            InputStreamReader isr = null;  
            try {  
                isr = new InputStreamReader(is, "gbk");  
            } catch (UnsupportedEncodingException e1) {  
                e1.printStackTrace();  
            }  
            BufferedReader br = new BufferedReader(isr);  
              
            int c = -1;  
            try{  
                while((c = br.read()) != -1){  
                    System.out.print((char)c);  
                }  
            }catch(Exception e){  
                e.printStackTrace();  
            }  
        }  
    }  
      
    class WrittenConsole implements Runnable{  
        private OutputStream os;  
        public WrittenConsole(OutputStream os){  
            this.os = os;  
        }  
        public void run(){  
            try{  
                while(true){  
                    String line = this.getConsoleLine();  
                    line += "/n";  
                    os.write(line.getBytes());  
                    os.flush();  
                }  
            }catch(Exception e){  
                e.printStackTrace();  
            }  
        }  
        private String getConsoleLine() throws IOException{  
            String line = null;  
            InputStreamReader input = new InputStreamReader(System.in);  
            BufferedReader br = new BufferedReader(input);  
            line = br.readLine();  
            return line;  
        }  
    }  
      
    public void execute() throws Exception{  
    	System.out.println(1);
        String[] cmds = {"cmd"};  
        Process process = Runtime.getRuntime().exec(cmds);  
        InputStream is = process.getInputStream();  
        OutputStream os = process.getOutputStream();  
          
        Thread t1 = new Thread(new ReaderConsole(is));  
        Thread t2 = new Thread(new WrittenConsole(os));  
        t1.start();  
        t2.start();  
    }  
      
    public static void main(String[] args) {  
    	testcmd t = new testcmd();  
        try{  
            t.execute();  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
    }  
}  