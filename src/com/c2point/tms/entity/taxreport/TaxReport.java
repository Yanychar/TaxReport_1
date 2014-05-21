package com.c2point.tms.entity.taxreport;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.joda.time.LocalDate;

import com.c2point.tms.entity.Organisation;
import com.c2point.tms.entity.SimplePojo;
import com.c2point.tms.entity.taxreport.Site;


@Entity
@NamedQueries({
	 @NamedQuery( name = "listAll", 
	 query = "SELECT report FROM TaxReport report " +
				"WHERE report.organisation = :org AND " +
					  "report.deleted = false"),
})

@XmlRootElement
public class TaxReport extends SimplePojo {

	
	// Report Type (Employees, Contracts, etc.) 
	private ReportType			type;
	private String				code;
	private ReportStatusType	status;
	
	private LocalDate			date;
	
	private LocalDate			lastModiDate;
	
	private String				strValue;
	
	private List<Site>			sites = new ArrayList<Site>();

	@ManyToOne
	private Organisation 		organisation;
	
	
	
	protected TaxReport() {
		super();
		
	}
	
	public TaxReport( ReportType type ) {
		this();
		
		this.type = type;
	}

	@XmlTransient
	public ReportType getType() { return type; }
	public void setType(ReportType type) { this.type = type; }

	@XmlTransient
	public String getCode() { return code; }
	public void setCode( String code ) { this.code = code; }

	@XmlTransient
	public ReportStatusType getStatus() { return status; }
	public void setStatus(ReportStatusType status) { this.status = status; }

	@XmlTransient
	public LocalDate getDate() { return this.date; }
	public void setDate( LocalDate date ) { this.date = date; }

	@XmlTransient
	public LocalDate getLastModiDate() { return lastModiDate; }
	public void setLastModiDate( LocalDate lastModiDate ) { this.lastModiDate = lastModiDate; }

	@XmlTransient
	public Organisation getOrganisation() { return organisation; }
	public void setOrganisation( Organisation organisation ) { this.organisation = organisation; }
	
	@Transient
	@XmlElement( name = "site" )
	@XmlElementWrapper( name="construction_sites" )
	public List<Site> getSites() { return sites; }
	public void setSites(List<Site> sites) { this.sites = sites; }

	@XmlTransient
	protected String getStrValue() {
		
		prepareXMLrepresentation();
		
		return this.strValue;
	}

	protected void setStrValue( String strValue ) { 
		this.strValue = strValue;

		fromXMLrepresentation();
		
	}

	private void prepareXMLrepresentation() {
		
	}
	
	private void fromXMLrepresentation() {
		
	}

	/*
	 * Business methods
	 * 
	 */
	
	public boolean addSite( Site site ) {
		
		if ( site != null ) {
			
			return getSites().add( site );
			
		}
		
		return false;
	}
	
	
	
	@Override
	public String toString() {
		return "TaxReport [type=" + type + ", code=" + code + ", status=" + status 
				+ ", date=" + date.monthOfYear().getAsText() + ", " + date.getYear() 
				+ "]";
	}
	
	
	
}
