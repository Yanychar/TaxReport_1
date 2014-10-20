package com.c2point.tms.entity_tax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.math.RandomUtils;

import com.c2point.tms.entity.Project;
import com.c2point.tms.util_tax.ValidationHelper;

public class Site {

	private long 		id;
	
	private String		siteVeroID;
	private String		siteNumber;

	private String		name;
	
	private Address		address;
	private Contact		contact;
	
	private Map<Long, Contractor>	contractors = new HashMap<Long, Contractor>();
	
	public Site() {
		
		setId( RandomUtils.nextLong());
		
	}
	
	public Site( Project project ) {
		
		setId( project.getId());
		setSiteNumber( project.getCode());
		setName( project.getName());
		
		// Adress is Project address
		setAddress( new Address( project ));
		
		// Contact is Project Manager
		setContact( new Contact( project ));
		
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

	@XmlElement( name = "contractor" )
	@XmlElementWrapper( name="all_contractors" )
	public Map<Long, Contractor> getContractors() { return contractors; }
	public void setContracts( Map<Long, Contractor> contractors ) { this.contractors = contractors; }
	
	/*
	 * Business Methods
	 */
	
	public boolean addContractor( Contractor contractor ) {
		
		if ( contractor != null ) {
			
			getContractors().put( contractor.getId(), contractor );
			
			return true;
			
		}
		
		return false;
	}

	public boolean deleteContractor( Contractor contractor ) {
		
		if ( contractor != null ) {
			
			getContractors().remove( contractor.getId());
			
			return true;
			
		}
		
		return false;
	}

	public Contractor getContractor( long id ) {
		
		Contractor retContractor = null;
		
		if ( getContractors() != null ) {
			
			if ( getContractors().containsKey( id )) {

				retContractor = getContractors().get( id );
				
			}
			
		}
		
		return retContractor;
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
