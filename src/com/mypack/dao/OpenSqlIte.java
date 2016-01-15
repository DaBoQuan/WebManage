package com.mypack.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OpenSqlIte {
	Connection sqlConn = null;
	PreparedStatement stat = null;
	public OpenSqlIte(){
		try {
			Class.forName("org.sqlite.JDBC");
//	        String path = System.getProperty("user.dir");
//			sqlConn = DriverManager.getConnection("jdbc:sqlite:"+path+"/data.db");
			sqlConn = DriverManager.getConnection("jdbc:sqlite:data.db");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//查所有
	public List<String[]> getAllResultSet(){
		try {
			if(sqlConn!=null){
				List<String[]> all = new ArrayList<String[]>();
				ResultSet res = sqlConn.createStatement().executeQuery("select * from lists;");
				while(res.next()){
					String temp[] = new String[]{res.getString("id"),res.getString("url"),res.getString("scriptType"),res.getString("ip"),res.getString("password"),res.getString("config"),res.getString("coding")};
					all.add(temp);
				}
				return all;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//查一条
	public String[] getOneResultSet(String id){
		try {
			if(sqlConn!=null){
				stat = sqlConn.prepareStatement("select * from lists where id=?;");
				stat.setInt(1, Integer.parseInt(id));
				ResultSet res = stat.executeQuery();
				String temp[] = new String[]{res.getString("id"),res.getString("url"),res.getString("scriptType"),res.getString("ip"),res.getString("password"),res.getString("config"),res.getString("coding")};
				return temp;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(stat!=null){
				try {
					stat.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	//添加一条
	public int addOne(String[] values) {
		try {
			if(sqlConn!=null){
				stat = sqlConn.prepareStatement("insert into lists(url,scriptType,password,config,coding)values(?,?,?,?,?);");
				stat.setString(1,values[0]);
				stat.setString(2,values[1]);
				stat.setString(3,values[2]);
				stat.setString(4,values[3]);
				stat.setString(5,values[4]);
				int i = stat.executeUpdate();
				return i;

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(stat!=null){
				try {
					stat.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return 0;
	}
	//删除一条
	public int delOne(String url) {
		try {
			if(sqlConn!=null){
				stat = sqlConn.prepareStatement("delete from lists where id=?");
				stat.setString(1, url);
				if(stat.execute()){
					return 1;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(stat!=null){
				try {
					stat.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return 0;
	}
	//修改一条
	public int updateOne(String[] values){
		try {
			if(sqlConn!=null){
				stat = sqlConn.prepareStatement("UPDATE lists SET url=?,scriptType=?,password=?,config=?,coding=? WHERE id = ?;");
				stat.setString(1,values[0]);
				stat.setString(2,values[1]);
				stat.setString(3,values[2]);
				stat.setString(4,values[3]);
				stat.setString(5,values[4]);
				stat.setInt(6,Integer.parseInt(values[5]));
				int i = stat.executeUpdate();
				return i;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(stat!=null){
				try {
					stat.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return 0;
	}
}
