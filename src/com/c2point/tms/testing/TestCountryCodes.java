package com.c2point.tms.testing;

import java.util.Locale;

public class TestCountryCodes {
	 
	 
	 
	    public static void main(String[] args) {
	 
	    	TestCountryCodes obj = new TestCountryCodes();
		obj.run();
	 
	    }
	 
	    public void run() {
	 
		String[] locales = Locale.getISOCountries();
	 
		for (String countryCode : locales) {
	 
			Locale obj = new Locale("", countryCode);
	 
			System.out.println("Country Code = " + obj.getCountry() 
				+ ", Country Name = " + obj.getDisplayCountry());
	 
		}
	 
		System.out.println("Done");
	    }
	 
}
