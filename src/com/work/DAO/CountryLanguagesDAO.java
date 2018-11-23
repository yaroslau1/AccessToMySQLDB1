package com.work.DAO;

import java.util.List;

import com.work.entity.CountryLanguage;
import com.work.exeption.DAOException;

public interface CountryLanguagesDAO {
	public List<CountryLanguage> getAll () throws DAOException;
	public List<CountryLanguage> findByCountryCode (String countryCode) throws DAOException;
	public void insert (CountryLanguage countryLanguage) throws DAOException;
	public void deleteByCountryCode (String countryCode) throws DAOException;
	public void update (CountryLanguage countryLanguage) throws DAOException;

}
