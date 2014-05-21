package com.c2point.tms.entity;

import java.text.Collator;
import java.util.Comparator;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Entity
public class TmsUser extends SimplePojo {

	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger( TmsUser.class.getName());
	
	private String code;
	private String firstName;
	private String midName;
	private String lastName;
	
	@ManyToOne
	private Organisation organisation;
	
	/**
	 * 
	 */
	public TmsUser() {
		this( "", "", "", "" );
	}

	/**
	 * @param code
	 * @param firstName
	 * @param midName
	 * @param lastName
	 */
	public TmsUser( String code, String firstName, String midName, String lastName ) {
		super();
		this.code = code;
		this.firstName = firstName;
		this.midName = midName;
		this.lastName = lastName;
		this.organisation = null;

	}
	public TmsUser( String code, String firstName, String lastName ) {
		this( code, firstName, "", lastName);
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the midName
	 */
	public String getMidName() {
		return midName;
	}

	/**
	 * @param midName the midName to set
	 */
	public void setMidName(String midName) {
		this.midName = midName;
	}

	/**
	 * @return the Last Name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the Last Name to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @return First Name + Last Name
	 */
	public String getFirstAndLastNames() {
		return ( this.getFirstName() != null ? this.getFirstName() : "" )
			   + " " 
			   + ( this.getLastName() != null ? this.getLastName() : "" );
	}

	public String getLastAndFirstNames() {
		return ( this.getLastName() != null ? this.getLastName() : "" )
			   + " " 
			   + ( this.getFirstName() != null ? this.getFirstName() : "" );
	}

	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation( Organisation organisation ) {
		this.organisation = organisation;
	}

	@Override
	public String toString() {
		return "TmsUser [" + "code=" + (code != null ? code : "NULL" ) + ", "
				+ "firstName=" + (firstName != null ? firstName : "NULL" ) + ", "
				+ "midName=" + (midName != null ? midName : "NULL" ) + ", "
				+ "lastName=" + (lastName != null ? lastName : "NULL" ) + ", " 
				+ "organisation=" + ( organisation != null ? organisation.getName() : "NULL" ) + ", " 
				+ "]";
	}

	public String toStringShort() {
		return "TmsUser [ " 
				+ "code=" + (code != null ? code : "NULL" ) + ", "
				+ "Fio=" + this.getFirstAndLastNames() + " from "
				+ this.getOrganisation().getName()
				+ " ]";
	}

	public class LastNameComparator implements Comparator< TmsUser >{

		private Collator standardComparator;
		
		public LastNameComparator() {
			standardComparator = Collator.getInstance(); 
		}
		
		@Override
		public int compare( TmsUser arg1, TmsUser arg2 ) {
			return standardComparator.compare( arg1.getLastAndFirstNames(), arg2.getLastAndFirstNames());
		}

	}

	
}
