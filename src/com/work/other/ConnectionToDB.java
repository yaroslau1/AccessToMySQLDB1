package com.work.other;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.cj.jdbc.Driver;

public class ConnectionToDB {
	private String url = "jdbc:mysql://localhost:3306/world"+
			"?verifyServerCertificate=false"+
			"&useSSL=false"+
			"&requireSSL=false"+
			"&serverTimezone=UTC"+
			"&useLegacyDatetimeCode=false"+
			"&serverTimezone=UTC";
	private String user = "root";
	private String pass = "1234";
	
    public Connection getConnection(){
      try {
          DriverManager.registerDriver(new Driver());
          return DriverManager.getConnection(url, user, pass);
      } catch (SQLException ex) {
          throw new RuntimeException("Error connecting to the database", ex);
      }
    }
}
