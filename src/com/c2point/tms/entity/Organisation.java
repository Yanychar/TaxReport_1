package com.c2point.tms.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.c2point.tms.entity.SimplePojo;
import com.c2point.tms.entity_tax.IDType;
import com.c2point.tms.util.xml.XMLconverter;

@Entity
@Access(AccessType.FIELD)
@NamedQueries({
	@NamedQuery(name = "findOrganisationByCode", query = "SELECT org FROM Organisation org WHERE org.code = :code AND org.deleted = false"),
})

@XmlRootElement
public class Organisation extends SimplePojo {

	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger( Organisation.class.getName());

	private String code;
	private String name;

	private String address;
	private String tunnus;
	private String info;
	private String phone;
	private String email;

	private String				strValue;
	
	@Transient
	private IDType			idType;
	@Transient
	private String 			countryCode;    // Contractor country of tax residence. Mandatory if finnishBusinessID is not set
	

	@ManyToMany( fetch=FetchType.LAZY )
	@JoinTable( name="subcontractors", 
    			joinColumns= 
    				@JoinColumn(name="main_org_id", referencedColumnName="id"),
    			inverseJoinColumns=     
    				@JoinColumn(name="subcontr_id", referencedColumnName="id")
	)
	@MapKey( name = "code" )
	private Map<String, Organisation>	subcontractors;
//	private Collection<Organisation>	subcontractors;
	
	@ManyToMany(mappedBy="subcontractors",fetch=FetchType.LAZY)	
	@MapKey( name = "code" )
	private Map<String, Organisation>	mainorgs;  /// Contractors. The companies who used this company as subcontractor

	
	public Organisation() {
		this( "", "" );
	}
	
	public Organisation( String code, String name ) {
		super();
		
		setCode( code );
		setName( name );
	}
	
	
	
	
	
	@XmlTransient
	public String getCode() { return code; }
	public void setCode( String code ) { this.code = code; }

	@XmlTransient
	public String getName() { return name; }
	public void setName( String name ) { this.name = name; }

	@XmlTransient
	public String getAddress() { return address; }
	public void setAddress(String address) { this.address = address; }

	@XmlTransient
	public String getTunnus() { return tunnus; }
	public void setTunnus(String tunnus) { this.tunnus = tunnus; }

	@XmlTransient
	public String getInfo() { return info; }
	public void setInfo(String info) { this.info = info; }

	@XmlTransient
	public String getPhone() { return phone; }
	public void setPhone(String phone) { this.phone = phone; }

	@XmlTransient
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	@XmlTransient
	@Access(AccessType.PROPERTY)
	protected String getStrValue() {
		
		prepareXMLrepresentation();
		
		return this.strValue;
	}

	protected void setStrValue( String strValue ) { 
		this.strValue = strValue;

		fromXMLrepresentation();
		
	}

	private void prepareXMLrepresentation() {

		String XMLstr = null;
		
		try {
			XMLstr = XMLconverter.convertToXML( this );
			if ( logger.isDebugEnabled()) logger.debug( "Organisation to XML:\n" + XMLstr );
			
			this.strValue = XMLstr;
			
		} catch (JAXBException e) {
			logger.error( e );
			logger.error( "Cannot convert Organisation to XML" );
		}

		
	}
	
	private void fromXMLrepresentation() {

		try {
			Organisation org = null;
			org = XMLconverter.initFromXML( Organisation.class, this.strValue );
			
			this.setIdType( org.getIdType());
			this.setCountryCode( org.getCountryCode());
			
		} catch (JAXBException e) {
			logger.error( e );
			logger.error( "Cannot convert XML into Organisation" );
		}

		
	}

	
	// New fields for Contractor management
	public IDType getIdType() { return idType; }
	public void setIdType(IDType idType) { this.idType = idType; }

	public String getCountryCode() { return countryCode; }
	public void setCountryCode( String countryCode ) { this.countryCode = countryCode; }

	@XmlTransient
	public Map<String, Organisation> getSubcontractors() { return this.subcontractors; }
	public void setSubcontractors( Map<String, Organisation> subcontractors ) { this.subcontractors = subcontractors; }
/*
	public Collection<Organisation> getSubcontractors() { return this.subcontractors; }
	public void setSubcontractors( Collection<Organisation> subcontractors ) { this.subcontractors = subcontractors; }
*/	
	@XmlTransient
	public Map<String, Organisation> getMainorgs() { return mainorgs; }
	public void setMainorgs( Map<String, Organisation> mainorgs ) { this.mainorgs = mainorgs; }

	
	
	/*
	 * Business methods 	
	 */
	
	// Add Subcontractor
	public boolean addSubcontractor( Organisation subContr ) {
		
		boolean ret = false;
		
		if ( getSubcontractors() == null ) {
			setSubcontractors( new HashMap<String, Organisation>());
		}
		
		subContr.setMainorg( this );
		if ( !getSubcontractors().containsKey( subContr.getCode())) {
			
			getSubcontractors().put( subContr.getCode(), subContr );
			
			ret = true; 
			
			if ( logger.isDebugEnabled()) logger.debug( "Subcontractor '" + subContr.getName() + "' is added to this MainOrg: " + this.getName());
			
		} else {

			if ( logger.isDebugEnabled()) logger.debug( "Subcontractor '" + subContr.getName() + "' exists already for this MainOrg: " + this.getName());
			
		}
		
		return ret;
		
	}

	private boolean setMainorg( Organisation mainOrg ) {

		boolean ret = false;
		
		if ( getMainorgs() == null ) {
			setMainorgs( new HashMap<String, Organisation>());
		}
		
		if ( !getMainorgs().containsKey( mainOrg.getCode())) {
			
			getMainorgs().put( mainOrg.getCode(), mainOrg );
			
			ret = true; 
			
			if ( logger.isDebugEnabled()) logger.debug( "Main Organisation '" + mainOrg.getName() + "' is added to this Subcontractor: " + this.getName());
			
		} else {

			if ( logger.isDebugEnabled()) logger.debug( "Main Organisation '" + mainOrg.getName() + "' exists already as MainOrg for this Subcontractor: " + this.getName());
			
		}
		
		return ret;
	}
	
}
