package com.example.demo.units;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    public static Connection CreateConnection(){
		Connection conn= null;

		String url="jdbc:mysql://192.168.33.10:3306/springbootdemo";
		String username="mieruca";
		String password="123456";

		try {
			// load Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// create connection
			conn = DriverManager.getConnection(url,username,password);
			
			System.out.println("Connected successfully ...");
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(SQLException ex){
			ex.printStackTrace();
			System.out.println("Error connection " + ex);
		}

		// create connection
		return conn;
	}
    
        public static Connection CreateConnection(String hostName, String sqllnstanceName,String database, String userName, String password){
		
		Connection conn= null;

		try {
			// load Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String url="jdbc:mysql://" + sqllnstanceName +"/"+database;
			
			// create connection
			conn = DriverManager.getConnection(url,userName,password);
			
			System.out.println("Connected successfully hihi...");
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(SQLException ex){
			ex.printStackTrace();
			System.out.println("Error connection " + ex);
		}

		// create connection
		return conn;
	}
//	
//	public static Connection getConnection() throws SQLException,ClassNotFoundException{
//		String hostName = "";
//		String sqllnstanceName = "";
//		String database = "";
//		String userName="";
//		String password="";
//		
//		return CreateConnection(hostName,sqllnstanceName,
//				database,userName,password);
//	}
}
