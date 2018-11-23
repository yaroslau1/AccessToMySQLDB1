package com.work.DAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.work.entity.Country;
import com.work.exeption.DAOException;

public class CountryConnectDAO implements CountryDAO, AutoCloseable{

	private Connection connection;
	private PreparedStatement getAll = null;
	private PreparedStatement addValues = null;
	private PreparedStatement deleteByCode = null;
	private PreparedStatement update = null;

	public CountryConnectDAO() throws DAOException {
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
			getAll = connection.prepareStatement("SELECT Capital, Name, Code, Population FROM country");
			addValues = connection.prepareStatement("INSERT INTO world.country (Name, Code, Population, Capital) VALUES (?, ?, ?, ?)");
			deleteByCode = connection.prepareStatement("DELETE FROM country WHERE Code = ?");
			update = connection.prepareStatement("UPDATE country SET Name = ?, Population = ?, Capital = ? WHERE Code = ?");
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
		SQLException exception = new SQLException("Some errors with closing countries");
		if(getAll != null){
			try {
				getAll.close();
			} catch (SQLException e) {
				exception.addSuppressed(e);
			}
		}
		if(deleteByCode != null){
			try {
				deleteByCode.close();
			} catch (SQLException e) {
				exception.addSuppressed(e);
			}
		}
		if (update != null){
			try {
				update.close();
			} catch (SQLException e) {
				exception.addSuppressed(e);
			}
		}
		if(addValues != null){
			try {
				addValues.close();
			} catch (SQLException e) {
				exception.addSuppressed(e);
			}
		}
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				exception.addSuppressed(e);
			}
		}
		if (exception.getSuppressed().length > 0) {
			throw new DAOException("errors with closing PrepereStatement in countries DAO", exception);
		}
	}

	@Override
	public List<Country> getAll() throws DAOException {
		List<Country> listCountry = new LinkedList<>();
		try (ResultSet resultSet = getAll.executeQuery()){
			while (resultSet.next()){
				Country country = new Country();
				String code = resultSet.getString("Code");
				if (!resultSet.wasNull()) {
					country.setCode(code);
				}
				String name = resultSet.getString("Name");
				if (!resultSet.wasNull()) {
					country.setName(name);
				}
				String capital = resultSet.getString("Capital");
				if (!resultSet.wasNull()) {
					country.setCapital( Integer.parseInt(capital) );
				}
				String population = resultSet.getString("Population");				 
				country.setPopularion( Integer.parseInt(population) );
				listCountry.add(country);
			}
			return listCountry;
		} catch (SQLException e) {
			throw new DAOException("Error in getAll method", e);
		}
	}

	@Override
	public List<Country> findByName(String name) throws DAOException {
		List<Country> listCountry = new LinkedList<>();
		try (ResultSet resultSet = getAll.executeQuery()){
			while (resultSet.next()){
				Country country = new Country();
				String nameForSearch = resultSet.getString("Name");
				if (nameForSearch.equals(name)) {
					String code = resultSet.getString("Code");
					if (!resultSet.wasNull()) {
						country.setCode(code);
					}
					String capital = resultSet.getString("Capital");
					if (!resultSet.wasNull()) {
						country.setPopularion( Integer.parseInt(capital) );
					}
					String population = resultSet.getString("Population");
					if (!resultSet.wasNull()) {
						country.setPopularion( Integer.parseInt(population) );
					}
					country.setName(name);
					listCountry.add(country);
				}
			}
			return listCountry;
		} catch (SQLException e) {
			throw new DAOException("Error in getAll method", e);
		}
	}

	@Override
	public void insert(Country country) throws DAOException {
		try {
			addValues.setString(1, country.getName());
			addValues.setString(2, country.getCode());
			addValues.setInt(3, country.getPopulation());
			addValues.setInt(3, country.getCapital());
			addValues.execute();
		} catch (SQLException e) {
			throw new DAOException("error in add country", e);
		}
	}

	@Override
	public void deleteByCode(String code) throws DAOException {
		try {
			deleteByCode.setString(1, code);
			deleteByCode.execute();
		} catch (SQLException e) {
			throw new DAOException("error in delete country", e);
		}

	}

	@Override
	public void update(Country country) throws DAOException {
		try {
			update.setString(1, country.getName());
			update.setInt(2, country.getPopulation());
			update.setInt(3, country.getCapital());
			update.setString(4, country.getCode());
			update.execute();
		} catch (SQLException e) {
			throw new DAOException("error in update country", e);
		}
	}

}
