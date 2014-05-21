package com.c2point.tms.entity.taxreport;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.math.RandomUtils;

@XmlRootElement
@XmlType
public class Contractor {

	private long	id;    // To differentiate Contractors. ID can come from DB if exist. Otherwise generated
	
	private String			code;
	private String			name;
	
	private String 			finnishBusinessID;
	private String 			foreignBusinessID;
	private IDType			idType;
	private String 			countryCode;    // Contractor country of tax residence. Mandatory if finnishBusinessID is not set

	private Contact			contact;
	
	private List<Employee>	employees;
	
	
	public Contractor() {
		this.id = RandomUtils.nextLong();

		setIdType( IDType.Vat );
		
		employees = new ArrayList<Employee>();
		
	}
	
	public Contractor( String name ) {
		
		this();
		this.name = name;
		
	}

	public Contractor( String name, String finnishBusinessID ) {
		
		this( name );
		this.finnishBusinessID = finnishBusinessID;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement( name = "ytunnus" )
	public String getFinnishBusinessID() {
		return finnishBusinessID;
	}

	public void setFinnishBusinessID(String finnishBusinessID) {
		this.finnishBusinessID = finnishBusinessID;
	}

	@XmlElement( name = "foreignid" )
	public String getForeignBusinessID() {
		return foreignBusinessID;
	}

	public void setForeignBusinessID(String foreignBusinessID) {
		this.foreignBusinessID = foreignBusinessID;
	}

	@XmlElement( name = "idtype" )
	public IDType getIdType() {
		return idType;
	}

	public void setIdType(IDType idType) {
		this.idType = idType;
	}

	@XmlElement( name = "ccode" )
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public List<Employee> getEmployees() { return employees; }
	public void setEmployees( List<Employee> employees ) { this.employees = employees; }

	
	
	/*
	 * Business methods
	 */

	
}
