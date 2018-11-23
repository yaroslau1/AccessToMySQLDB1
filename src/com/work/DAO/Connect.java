package com.work.DAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import com.work.exeption.DAOException;

public class Connect implements AutoCloseable {
	
	private Connection connection;
	private PreparedStatement getAll = null;
	private PreparedStatement addValues = null;
	private PreparedStatement deleteByID = null;
	private PreparedStatement updateByID = null;


	public Connect() throws DAOException {
		Properties property = new Properties();
		ClassLoader classLoader = this.getClass().getClassLoader();
		InputStream reader = classLoader.getResourceAsStream("com/work/company/properties.properties");
		try {
			property.load(reader);
			String driverName = property.getProperty("driver");
			String url = property.getProperty("url");
			String user = property.getProperty("user");
			String pass = property.getProperty("pass");
			reader.close();
			Class.forName(driverName).newInstance();
			connection = DriverManager.getConnection(url, user, pass);
			getAll = connection.prepareStatement("SELECT ID, Name, CountryCode, Population FROM city");
			addValues = connection.prepareStatement("INSERT INTO world.city (Name, CountryCode, Population) VALUES (?, ?, ?)");
			deleteByID = connection.prepareStatement("DELETE FROM city WHERE id = ?");
			updateByID = connection.prepareStatement("UPDATE city SET Name = ?, Population = ? WHERE Id = ?");
		} catch (IOException e) {
			throw new DAOException("Error in constructor with file opening", e);
		} catch (InstantiationException e) {
			throw new DAOException("Error in constructor: object Class not found", e);
		} catch (IllegalAccessException e) {
			throw new DAOException("Error in constructor with access to DB", e);
		} catch (ClassNotFoundException e) {
			throw new DAOException("Error in constructor with ClassPath", e);
		} catch (SQLException e) {
			throw new DAOException("Error in constructor with SQLQuery", e);
		}
	}


	@Override
	public void close() throws Exception {
	}

}
