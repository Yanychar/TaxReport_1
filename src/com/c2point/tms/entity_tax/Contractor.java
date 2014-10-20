package com.c2point.tms.entity_tax;

import com.c2point.tms.entity.Organisation;
import com.c2point.tms.entity.TaskReport;
import com.c2point.tms.entity.TmsUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
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
	private Contract		contract;
	
	private Map<Long, Employee>	employees;

	private Organisation	organisation;
	
	public Contractor() {
		
		setId( RandomUtils.nextLong());

		setIdType( IDType.Vat );
		
		setOrganisation( null );
		
		employees = new HashMap<Long,Employee>();
		
	}
	
	public Contractor( String name ) {
		
		this();
		this.name = name;
		
	}

	public Contractor( String name, String finnishBusinessID ) {
		
		this( name );
		this.finnishBusinessID = finnishBusinessID;
		
	}

	public Contractor( Organisation org, Contract.ContractType contractType ) {

		this();
		
		setId( org.getId());
		setCode( org.getCode());
		setName( org.getName());
		setOrganisation( org );

		setFinnishBusinessID( org.getTunnus());
		setIdType( IDType.Vat );

		
		// Contact
		setContact( new Contact( org.getServiceOwner()));
		setContract( new Contract( contractType ));
		
	}

	public long getId() { return id; }
	public void setId( long id ) { this.id = id; }

	public String getCode() { return code; }
	public void setCode( String code ) { this.code = code; }

	public String getName() { return name; }
	public void setName( String name ) { this.name = name; }

	@XmlTransient
	public Organisation getOrganisation() { return organisation; }
	public void setOrganisation( Organisation organisation ) { this.organisation = organisation; }

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

	public Contact getContact() { return contact; }
	public void setContact(Contact contact) { this.contact = contact; }

	public Contract getContract() { return contract; }
	public void setContract( Contract contract ) { this.contract = contract; }

	public Map<Long, Employee> getEmployees() { return employees; }
	public void setEmployees( Map<Long, Employee> employees ) { this.employees = employees; }

	
	
	/*
	 * Business methods
	 */
	private boolean employeeExist( Employee employee ) {
		
		return getEmployees().containsKey( employee.getId());
		
	}
	
	private boolean addEmployee( Employee employee ) {
		
		if ( !employeeExist( employee )) {
			
			getEmployees().put( employee.getId(), employee );
			
			return true;
		}
		
		return false;
	}

	private Employee getEmployee( Employee employee ) {
		return getEmployee( employee.getId());
	}
	private Employee getEmployee( TmsUser user ) {
		return getEmployee( user.getId());
	}
	private Employee getEmployee( long id ) {
		
		return getEmployees().get( id );
		
	}
	
	
	public boolean handleTaskReport( TaskReport taskReport ) {
		
		Employee reportedEmp;
		boolean bRes = false;
		
		reportedEmp = getEmployee( taskReport.getUser());

		if ( reportedEmp == null ) {
			// User must be added to handle this report
			
			reportedEmp = new Employee( taskReport.getUser());
			
			if ( !addEmployee( reportedEmp )) {
				// Cannot add Employee
				reportedEmp = null;
				
			}
		}
			
		// Handle report if Employee exists or was added successfully
		if ( reportedEmp != null ) {
			
			bRes = reportedEmp.handleTaskReport( taskReport );
			
		}
			
		return bRes;
	}
}
