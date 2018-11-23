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

import com.work.entity.CountryLanguage;
import com.work.exeption.DAOException;

public class CountryLanguageConnectDAO implements CountryLanguagesDAO, AutoCloseable{

	private Connection connection;
	private PreparedStatement getAll = null;
	private PreparedStatement addValues = null;
	private PreparedStatement deleteByCountryCode = null;
	private PreparedStatement update = null;

	public CountryLanguageConnectDAO () throws DAOException {
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
			getAll = connection.prepareStatement("SELECT CountryCode, Language, IsOfficial, Percentage FROM countrylanguage");
			addValues = connection.prepareStatement("INSERT INTO world.countrylanguage (Language, CountryCode, Percentation, IsOfficial) VALUES (?, ?, ?, ?)");
			deleteByCountryCode = connection.prepareStatement("DELETE FROM countrylanguage WHERE CountryCode = ?");
			update = connection.prepareStatement("UPDATE countrylanguage SET Language = ?, Percentage = ?, IsOfficial = ? WHERE CountryCode = ?");
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
		SQLException exception = new SQLException("Some errors with closing country languages");
		if(getAll != null){
			try {
				getAll.close();
			} catch (SQLException e) {
				exception.addSuppressed(e);
			}
		}
		if(deleteByCountryCode != null){
			try {
				deleteByCountryCode.close();
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
			throw new DAOException("errors with closing PrepereStatement in country languages DAO", exception);
		}
	}

	@Override
	public List<CountryLanguage> getAll() throws DAOException {
		List<CountryLanguage> listCountry = new LinkedList<>();
		try (ResultSet resultSet = getAll.executeQuery()){
			while (resultSet.next()){
				CountryLanguage countryCode = new CountryLanguage();
				String code = resultSet.getString("CountryCode");
				if (!resultSet.wasNull()) {
					countryCode.setCountryCode(code);
				}
				String language = resultSet.getString("Language");
				if (!resultSet.wasNull()) {
					countryCode.setLanguage(language);
				}
				String isOfficial = resultSet.getString("IsOfficial");
				if (!resultSet.wasNull()) {
					countryCode.setIsOfficial( Boolean.parseBoolean(isOfficial));
				}
				String percentage = resultSet.getString("Percentage");				 
				countryCode.setPercentage( Float.parseFloat(percentage) );
				listCountry.add(countryCode);
			}
			return listCountry;
		} catch (SQLException e) {
			throw new DAOException("Error in getAll method", e);
		}
	}

	@Override
	public List<CountryLanguage> findByCountryCode(String code) throws DAOException {
		List<CountryLanguage> listCountry = new LinkedList<>();
		try (ResultSet resultSet = getAll.executeQuery()){
			while (resultSet.next()){
				CountryLanguage countryCode = new CountryLanguage();
				String codeForSearch = resultSet.getString("CountryCode");
				if (codeForSearch.equals(code)) {
					if (!resultSet.wasNull()) {
						countryCode.setCountryCode(code);
					}
					String language = resultSet.getString("Language");
					if (!resultSet.wasNull()) {
						countryCode.setLanguage(language);
					}
					String isOfficial = resultSet.getString("IsOfficial");
					if (!resultSet.wasNull()) {
						countryCode.setIsOfficial( Boolean.parseBoolean(isOfficial));
					}
					String percentage = resultSet.getString("Percentage");				 
					countryCode.setPercentage( Float.parseFloat(percentage) );
					listCountry.add(countryCode);
				}
			}
			return listCountry;
		} catch (SQLException e) {
			throw new DAOException("Error in getAll method", e);
		}
	}

	@Override
	public void insert(CountryLanguage countryLanguage) throws DAOException {
		try {
			addValues.setString(1, countryLanguage.getLanguage());
			addValues.setString(2, countryLanguage.getCountryCode());
			addValues.setFloat(3, countryLanguage.getPercentage());
			addValues.setBoolean(3, countryLanguage.getIsOfficial());
			addValues.execute();
		} catch (SQLException e) {
			throw new DAOException("error in add country language", e);
		}
	}

	@Override
	public void deleteByCountryCode(String countryCode) throws DAOException {
		try {
			deleteByCountryCode.setString(1, countryCode);
			deleteByCountryCode.execute();
		} catch (SQLException e) {
			throw new DAOException("error in delete country language", e);
		}
	}

	@Override
	public void update(CountryLanguage countryLanguage) throws DAOException {
		try {
			update.setString ( 1, countryLanguage.getLanguage() );
			update.setFloat ( 2, countryLanguage.getPercentage() );
			update.setBoolean ( 3, countryLanguage.getIsOfficial() );
			update.setString ( 4, countryLanguage.getCountryCode() );
			update.execute();
		} catch (SQLException e) {
			throw new DAOException("error in update country language", e);
		}
	}

}
