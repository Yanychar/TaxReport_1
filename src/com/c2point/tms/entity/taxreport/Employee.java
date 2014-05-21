package com.c2point.tms.entity.taxreport;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.math.RandomUtils;

@XmlRootElement
public class Employee extends Person {
	
	private Address 			taxAddress;
	private Address 			finAddress;
	
	private EmploymentContract	contract;	
	
	public Employee() {
		
		super();
		
	}
	
	public Employee( String firstName, String lastName, String phoneNumber, String email ) {
		
		super( firstName, lastName, phoneNumber, email );
		
	}

	public Employee( String firstName, String lastName ) {

		super( firstName, lastName );
		
	}
	
	public Employee( String firstName, String lastName, Address finAaddress ) {
		
		this( firstName, lastName );
		
		setFinAddress( finAddress );
		
	}

	public Employee( String firstName, String lastName, String phoneNumber, String email, Address finAddress ) {
		
		this( firstName, lastName, phoneNumber, email );
		
		setFinAddress( finAddress );
		
	}

	public Employee( Address finAddress ) {
		
		this( "", "" );
		
		setFinAddress( finAddress );
		
	}

	public Address getFinAddress() { return finAddress; }
	public void setFinAddress( Address finAddress ) { this.finAddress = finAddress; }
	
	public Address getTaxAddress() { return taxAddress; }
	public void setTaxAddress( Address taxAddress ) { this.taxAddress = taxAddress; }
	
	public EmploymentContract getContract() { return contract; }
	public void setContract( EmploymentContract contract ) { this.contract = contract; }


	/*
	 * Business Methods
	 */
	
	public boolean isValid() {
		
		return true;
	}
	
	
	
}
