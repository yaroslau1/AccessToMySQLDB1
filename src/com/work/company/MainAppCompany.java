package com.work.company;

import com.work.DAO.CityConnectDAO;
import com.work.DAO.CountryConnectDAO;
import com.work.exeption.DAOException;

public class MainAppCompany {

	public static void main(String[] args) {
		long start;
		long end;
		start = System.currentTimeMillis();
		try (CityConnectDAO cityConnectDAO = new CityConnectDAO(); CountryConnectDAO countryConnectDAO = new CountryConnectDAO()){	
			//cityConnectDAO.math();
			//System.out.println( cityConnectDAO.getAll() );
			//System.out.println( cityConnectDAO.findByName("Minsk") );
			//System.out.println( cityConnectDAO.findByName("Brest") );
			//System.out.println( cityConnectDAO.findByName("Grodno") );
			
			//cityConnectDAO.addValues("Stry", "BLR", 1234);
			//System.out.println(cityConnectDAO.getAll());
			System.out.println( countryConnectDAO.getAll() );
			//cityConnectDAO.deleteByID(4081);
			//cityConnectDAO.deleteByID(4089);
			//cityConnectDAO.deleteByID(4090);
			//cityConnectDAO.deleteByID(4091);
			//cityConnectDAO.updateById(4080);
			//System.out.println(cityConnectDAO.findByName("Stry"));
			
		} catch (DAOException e) {			
			e.printStackTrace();
		} catch (Exception e1) {
			
			e1.printStackTrace();
		} 
		end = System.currentTimeMillis();
		System.out.println(end - start + "Congratulation!!!!");
	}
}