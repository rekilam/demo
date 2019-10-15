package com.example.demo.units;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {

    public static Connection getMyConnection() throws SQLException, ClassNotFoundException {

        return CreateConnection();
    }

    //
    // Test Connection ...
    //

    public static Connection getConnection() throws SQLException{
        return CreateConnection();
    }
    
    public static Connection CreateConnection(){
		Connection conn= null;

		String url="jdbc:mysql://192.168.33.10:3306/springbootdemo?useUnicode=true&characterEncoding=UTF-8";
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
			
			String url="jdbc:mysql://" + sqllnstanceName +"/"+database+"?useUnicode=true&characterEncoding=UTF-8";
			
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
}
