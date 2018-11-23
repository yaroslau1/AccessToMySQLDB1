package com.work.entity;

public class Country {

	private int capital;
	private int population;
	private String name;
	private String code;

	public Country () {

	}

	public Country (String code, String name, int population, int capital) {
		this.capital = capital;
		this.population = population;
		this.name = name;
		this.code = code;
	}
	
	public void setCapital(int capital) {
		this.capital = capital;
	}
	
	public void setPopularion(int population) {
		this.population = population;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public int getCapital () {
		return this.capital;
	}
	
	 public int getPopulation () {
		return this.population;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getCode() {
		return this.code;
	}
	
	@Override
	public String toString() {
		return capital + " " + name + " " + code + " " + population;
	}

}
