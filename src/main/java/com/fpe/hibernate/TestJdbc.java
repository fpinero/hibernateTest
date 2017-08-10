package com.fpe.hibernate;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJdbc {

	public static void main(String[] args) {
		
		try{
			String jdbcUrl = "jdbc:mysql://128.199.62.107:3306/test_hibernate?useSSL=false";
			String user = "remoUser";
			String pwd = "1q2w3e4r-";
			
			System.out.println("Connecting to database: " + jdbcUrl);
			
			Connection myConn = DriverManager.getConnection(jdbcUrl, user, pwd);
			
			System.out.println("Connection successful!!!");
			
			myConn.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}
