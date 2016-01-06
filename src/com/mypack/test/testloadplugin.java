package com.mypack.test;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class testloadplugin{

	public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
//		URL url1 = new URL("file:C:/WebAdmin_fat.jar");  
//        URLClassLoader myClassLoader1 = new URLClassLoader(new URL[] { url1 }, Thread.currentThread().getContextClassLoader());  
//        Class<?> myClass1 =myClassLoader1.loadClass("com.mypack.util.test2");  
//        PluginInterFace action1 = (PluginInterFace) myClass1.newInstance();  
//        action1.testPlugin();  
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:data.db");
		Statement stat = conn.createStatement();
		//stat.executeUpdate( "create table lists(id integer PRIMARY KEY autoincrement,url varchar(225),scriptType varchar(5),ip varchar(20),password varchar(20),config varchar(500));" );
		//stat.execute("insert into lists(url,scriptType,ip,password,config) values('2','2','3','4','5')");
		stat.executeUpdate("update lists set url='2222' where id=42");
		//stat.execute("delete from lists where scripttype='asp';");
	}


}
