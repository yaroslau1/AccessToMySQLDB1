package com.work.entity;

public class CountryLanguage {

	private boolean isOfficial;
	private float percentage;
	private String countryCode;
	private String language;

	public CountryLanguage () {

	}

	public CountryLanguage (String countryCode, String language, float percentage, boolean isOfficial) {
		this.language = language;
		this.percentage = percentage;
		this.isOfficial = isOfficial;
		this.countryCode = countryCode;
	}
	
	public void setCountryCode (String countryCode) {
		this.countryCode = countryCode;
	}
	
	public void setLanguage (String language) {
		this.language = language;
	}
	
	public void setIsOfficial (boolean isOfficial) {
		this.isOfficial = isOfficial;
	}
	
	public void setPercentage (float percentage) {
		this.percentage = percentage;
	}
	
	public String getLanguage () {
		return this.language;
	}
	
	 public String getCountryCode () {
		return this.countryCode;
	}
	
	public float getPercentage () {
		return this.percentage;
	}
	
	public boolean getIsOfficial () {
		return this.isOfficial;
	}
	
	@Override
	public String toString () {
		return countryCode + " " + language + " " + isOfficial + " " + percentage;
	}

}
