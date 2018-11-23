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

import com.work.entity.City;
import com.work.exeption.DAOException;

public class CityConnectDAO implements CityDAO, AutoCloseable {

	private Connection connection;
	private PreparedStatement getAll = null;
	private PreparedStatement addValues = null;
	private PreparedStatement deleteByID = null;
	private PreparedStatement update = null;

	public CityConnectDAO() throws DAOException {
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
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(url, user, pass);
			getAll = connection.prepareStatement("SELECT ID, Name, CountryCode, Population FROM city");
			addValues = connection.prepareStatement("INSERT INTO world.city (Name, CountryCode, Population) VALUES (?, ?, ?)");
			deleteByID = connection.prepareStatement("DELETE FROM city WHERE id = ?");
			update = connection.prepareStatement("UPDATE city SET Name = ?, Population = ? WHERE Id = ?");
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

	public void close() throws DAOException {
		SQLException exception = new SQLException("Some errors with closing");
		if(getAll != null){
			try {
				getAll.close();
			} catch (SQLException e) {
				exception.addSuppressed(e);
			}
		}
		if(deleteByID != null){
			try {
				deleteByID.close();
			} catch (SQLException e) {
				exception.addSuppressed(e);
			}
		}
		if(update != null){
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
			throw new DAOException("errors with closing PrepereStatement in city DAO", exception);
		}
	}

	@Override
	public List<City> getAll() throws DAOException {
		List<City> listCity = new LinkedList<>();
		try (ResultSet resultSet = getAll.executeQuery()){
			while(resultSet.next()){
				City city = new City();
				String id = resultSet.getString("ID");
				String name = resultSet.getString("Name");
				String population = resultSet.getString("Population");
				city.setName(name);
				city.setId(Integer.parseInt(id));
				city.setPopularion(Integer.parseInt(population));
				listCity.add(city);
			}
			return listCity;
		} catch (SQLException e) {
			throw new DAOException("Error in getAll method", e);
		}
	}

	@Override
	public List<City> findByName(String name) throws DAOException {
		List<City> listCity = new LinkedList<>();
		try (ResultSet resultSet = getAll.executeQuery()){
			while(resultSet.next()){
				City city = new City();;
				String nameForSearch = resultSet.getString("Name");
				if (nameForSearch.equals(name)) {
					String id = resultSet.getString("ID");
					String countryCode = resultSet.getString("CountryCode");
					String population = resultSet.getString("Population");
					city.setName(name);
					city.setId(Integer.parseInt(id));
					city.setCountryCode(countryCode);
					city.setPopularion(Integer.parseInt(population));
					listCity.add(city);
				}
			}
			return listCity;
		} catch (SQLException e) {
			throw new DAOException("Error in findByName method", e);
		}
	}

	public void insert(City city) throws DAOException {
		try {
			addValues.setString(1, city.getName());
			addValues.setString(2, city.getCountryCode());
			addValues.setInt(3, city.getPopulation());
			addValues.execute();

		} catch (SQLException e) {
			throw new DAOException("error in add city", e);
		}
	}

	public void deleteById(int id) throws DAOException {
		try {
			deleteByID.setInt(1, id);
			deleteByID.execute();
		} catch (SQLException e) {
			throw new DAOException("error in delete city", e);
		}
	}

	public void update(City city) throws DAOException {
		try {
			update.setString(1, city.getName());
			update.setInt(2, city.getPopulation());
			update.setInt(3, city.getId());
			update.execute();
		} catch (SQLException e) {
			throw new DAOException("error in update city", e);
		}
	}
}
