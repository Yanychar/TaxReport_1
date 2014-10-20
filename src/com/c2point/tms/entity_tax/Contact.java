package com.c2point.tms.entity_tax;

import com.c2point.tms.entity.Project;
import com.c2point.tms.entity.TmsUser;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Contact extends Person {

	private Address 			address;
	
	public Contact() {

		super();
		
	}
	
	public Contact( String firstName, String lastName, String phoneNumber, String email ) {

		super( firstName, lastName, phoneNumber, email );
		
	}
	
	public Contact( Address address ) {

		super( "", "" );
		
		setAddress( address );
		
	}

	public Contact( String firstName, String lastName, Address address ) {

		super( firstName, lastName );
		
		setAddress( address );
		
	}
	
	public Contact( String firstName, String lastName, String phoneNumber, String email, Address address ) {
		
		this( firstName, lastName, phoneNumber, email );
		
		setAddress( address );

	}
	
	public Contact( TmsUser user ) {

		super( user );
		
		Address addr = new Address();
		addr.setDescription( user.getAddress());
		setAddress( addr );
		
	}

	public Contact( Project project ) {
		
		this( project != null ? project.getProjectManager() : ( TmsUser )null );
		
	}
	
	
	
	public Address getAddress() { return address; }
	public void setAddress( Address address ) { this.address = address; }

	
}
