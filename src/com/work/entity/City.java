package com.work.entity;

public class City {
	private int id;
	private int population;
	private String name;
	private String countryCode;
	
	public City() {
	}
	
	public City(int id, String name, int population, String countryCode) {
		this.id = id;
		this.population = population;
		this.name = name;
		this.countryCode = countryCode;
	}
	
	public City(String name, int population, String countryCode) {
		this.population = population;
		this.name = name;
		this.countryCode = countryCode;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setPopularion(int population) {
		this.population = population;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public int getId () {
		return this.id;
	}
	
	 public int getPopulation () {
		return this.population;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getCountryCode() {
		return this.countryCode;
	}
	
	public void showCity() {
		System.out.println(id + " " + name + " " + population);
	}
	
	@Override
	public String toString() {
		return id + " " + name + " " + countryCode + " " + population;
	}

}
