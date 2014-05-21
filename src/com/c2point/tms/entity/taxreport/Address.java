package com.c2point.tms.entity.taxreport;

import javax.xml.bind.annotation.XmlElement;

import com.c2point.tms.util.CheckValueUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

public class Address {

	private String street;
	private String poBox;
	private String index;
	private String city;
	private String countryCode;
	
	private String description;
	
	public Address() {
		
		setCountryCode( "FI" );
		
	}

	public Address( String street, String postalCode, String city, String countryCode ) {
		
		this( street, null, postalCode, city, countryCode );

	}

	public Address( String street, String poBox, String index, String city,
			String countryCode ) {
		super();
		setStreet( street );
		setPoBox( poBox );
		setIndex( index );
		setCity( city );
		setCountryCode( countryCode );
	}


	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@XmlElement( name = "pobox" )
	public String getPoBox() {
		return poBox;
	}

	public void setPoBox(String poBox) {
		this.poBox = poBox;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex( String index ) {
		this.index = index;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@XmlElement( name = "ccode" )
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@XmlElement( name = "descr" )
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }


	/*
	 *  Business Methods 
	 * 
	 */
	public String oneLineAddress() {
		
		String resStr = "";
				
		if ( !CheckValueUtils.isEmpty( getStreet())) {
			resStr = resStr.concat( WordUtils.capitalizeFully( StringUtils.trim( getStreet()))) + ", ";
		}
		if ( !CheckValueUtils.isEmpty( getIndex())) {
			resStr = resStr.concat( WordUtils.capitalizeFully( StringUtils.trim( getIndex()))) + " ";
		}
		if ( !CheckValueUtils.isEmpty( getCity())) {
			resStr = resStr.concat( WordUtils.capitalizeFully( StringUtils.trim( getCity()))) + ", ";
		}
		if ( !CheckValueUtils.isEmpty( getCountryCode())) {
			resStr = resStr.concat( StringUtils.trim( getCountryCode()));
		}
				
		return resStr;
		
	}
	
	
}
