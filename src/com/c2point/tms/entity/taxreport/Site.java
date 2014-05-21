package com.c2point.tms.entity.taxreport;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.math.RandomUtils;

import com.c2point.tms.util.ValidationHelper;

public class Site {

	private long 		id;
	
	private String		siteVeroID;
	private String		siteNumber;

	private String		name;
	
	private Address		address;
	private Contact		contact;
	
	private List<Contract>	contracts = new ArrayList<Contract>();
	
	public Site() {
		
		setId( RandomUtils.nextLong());
		
	}
	
	@XmlTransient
	public long getId() { return id; }
	public void setId( long id ) { this.id = id; }
	
	public String getName() { return this.name; }
	public void setName( String name ) { this.name = name; }

	public String getSiteVeroID() { return siteVeroID; }
	public void setSiteVeroID( String siteVeroID ) { this.siteVeroID = siteVeroID; }

	public String getSiteNumber() { return siteNumber; }
	public void setSiteNumber( String siteNumber ) { this.siteNumber = siteNumber; }

	public Address getAddress() { return address; }
	public void setAddress( Address address ) { this.address = address; }

	public Contact getContact() { return contact; }
	public void setContact( Contact contact ) { this.contact = contact; }

	@XmlElement( name = "contract" )
	@XmlElementWrapper( name="all_contracts" )
	public List<Contract> getContracts() { return contracts; }
	public void setContracts( List<Contract> contracts ) { this.contracts = contracts; }
	
	/*
	 * Business Methods
	 */
	
	public boolean addContract( Contract contract ) {
		
		if ( contract != null ) {
			
			return getContracts().add( contract );
			
		}
		
		return false;
	}

	public boolean isValid() {
		
		boolean validity = 
				ValidationHelper.validateID( getSiteVeroID(), 35 )
			||	ValidationHelper.validateID( getSiteNumber(), 50 )
			||	ValidationHelper.validateSiteAddress( address );
		;
		
		validity = validity && contact.isValid();
		
		return validity;
	}

	@Override
	public String toString() {
		return "Site [id=" + id + ", siteNumber=" + siteNumber + ", name="
				+ name + "]";
	}

	
	
}
