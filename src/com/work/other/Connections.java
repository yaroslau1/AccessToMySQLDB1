package com.work.other;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connections {

	private String url = "jdbc:mysql://localhost:3306/world"+
			"?verifyServerCertificate=false"+
			"&useSSL=false"+
			"&requireSSL=false"+
			"&serverTimezone=UTC"+
			"&useLegacyDatetimeCode=false"+
			"&serverTimezone=UTC";
	private String user = "root";
	private String pass = "1234";

	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;

	public Connections() {
	}

	public Connections(String url, String user, String pass) {
		this.url = url;
		this.user = user;
		this.pass = pass;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(String sqlQuery) {
		try {
			this.resultSet = statement.executeQuery(sqlQuery);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}

	public void openConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();  	
			connection = DriverManager.getConnection(url, user, pass);
			statement = connection.createStatement();
		} catch (SQLException e) {
			System.out.println(e);
		} catch (InstantiationException e) {			
			System.out.println(e);
		} catch (IllegalAccessException e) {			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		try {
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			statement.close();
		} catch (SQLException e) {				
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {				
			e.printStackTrace();
		}
	}

	public ResultSet getSQLQuery(String sqlQuery) {
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet; 
	}

}
