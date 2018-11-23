package com.work.other;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBEntity {
	
	private String url;
	private String user;
	private String password;
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;	
	
	public DBEntity(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
		
	}
	
	public void openConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");  	//динамическая загрузка
			connection = DriverManager.getConnection(url, user, password);
			statement = connection.createStatement();
		} catch (SQLException | ClassNotFoundException e) {	
			System.out.println("open");
			System.out.println(e);
		}
		
	}
	
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("close");
			System.out.println(e);
		}
	}
	
	public void getAllInformationFromBD() {
		try {
			resultSet = statement.executeQuery("SELECT * FROM worker");
			while(resultSet.next()){
				String id = resultSet.getString("marka");
				String name = resultSet.getString("speed");
				String surname = resultSet.getString("price");
				System.out.println(id + " "+ name + " " + surname);
			}
		} catch (SQLException e) {
			System.out.println("get");
			System.out.println(e);
		}
	}

}
