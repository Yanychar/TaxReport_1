package com.c2point.tms.entity.taxreport;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.joda.time.LocalDate;

import com.c2point.tms.util.ValidationHelper;

@XmlRootElement
@XmlType(propOrder = { "firstName", "lastName", "phoneNumber", "email", "finnishTunnus", "taxNumber", "birthDate", "taxCountryCode" })
public class Person {

	private long 		id;

	
	private String		firstName;
	private String		lastName;
	
	private String		phoneNumber;
	private String		email;
	
	private String		finnishTunnus;
	private String		taxNumber;
	
	private LocalDate	birthDate;
	
	private String		taxCountryCode;
	
	public Person() { 

		setId( RandomUtils.nextLong());
		
	}
	
	public Person( String firstName, String lastName, String phoneNumber, String email ) {
		this();
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		
	}

	public Person( String firstName, String lastName ) {
		this( firstName, lastName, null, null ); 

	}	
	
	@XmlTransient
	public long getId() { return id; }
	public void setId( long id ) { this.id = id; }
	
	@XmlElement( name = "fname" )
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@XmlElement( name = "lname" )
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@XmlElement( name = "phone" )
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@XmlElement( name = "tunnus" )
	public String getFinnishTunnus() { return finnishTunnus; }
	public void setFinnishTunnus(String finnishTunnus) { this.finnishTunnus = finnishTunnus; }

	@XmlElement( name = "taxnum" )
	public String getTaxNumber() { return taxNumber; }
	public void setTaxNumber(String taxNumber) { this.taxNumber = taxNumber; }

	@XmlElement( name = "date" )
	public LocalDate getBirthDate() { return birthDate; }
	public void setBirthDate( LocalDate birthDate ) { this.birthDate = birthDate; }
	public void setBirthDate( Date birthDate ) { setBirthDate( new LocalDate( birthDate )); }
	
	@XmlElement( name = "taxccode" )
	public String getTaxCountryCode() {
		return taxCountryCode;
	}
	public void setTaxCountryCode(String taxCountryCode) {
		this.taxCountryCode = taxCountryCode;
	}

	/*
	 * Business Methods
	 */

	public String getFirstLastName() {
		
		return WordUtils.capitalizeFully( StringUtils.trim( getFirstName() + " " + getLastName() ));
	}
	
	public String getLastFirstName() {
		
		return WordUtils.capitalizeFully( StringUtils.trim( getLastName() + " " + getFirstName() ));
		
	}
	
	public boolean isValid() {
		
		boolean validity = 
				ValidationHelper.validateString( getFirstName()) 
			&&	ValidationHelper.validateString( getLastName())
			&& ( ValidationHelper.validateString( getPhoneNumber())
					|| 
				 ValidationHelper.validateString( getEmail())
			   )
		;
		
		return validity;
	}
	
}
