package com.c2point.tms.entity_tax;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.persistence.annotations.Converter;
import org.joda.time.LocalDate;

import com.c2point.tms.entity.Organisation;
import com.c2point.tms.entity.SimplePojo;
import com.c2point.tms.util.xml.XMLconverter;


@Entity
@Access(AccessType.FIELD)
@Converter(name = "localDateConverter", converterClass = LocalDateConverterForJPA.class)  
@NamedQueries({
	 @NamedQuery( name = "listAll", 
	 query = "SELECT report FROM TaxReport report " +
				"WHERE report.organisation = :org AND " +
					  "report.deleted = false"),
})

@XmlRootElement
public class TaxReport extends SimplePojo {

	private static Logger logger = LogManager.getLogger( TaxReport.class.getName()); 
	
	// Report Type (Employees, Contracts, etc.) 
	private ReportType			type;
	private String				code;
	private ReportStatusType	status;
	
	@Temporal(TemporalType.DATE)
//	@Convert("localDateConverter")	
	private Date			date;
	
	@Temporal(TemporalType.DATE)
//	@Convert("localDateConverter")	
	private Date			lastModiDate;
	
	private String				strValue;
	
	@Transient
	private Map<Long, Site>			sites = new HashMap<Long, Site>();

	@ManyToOne
	private Organisation 		organisation;

/*	
	@Temporal(TemporalType.DATE)
	private java.util.Date			date1;

	private java.sql.Date			date2;

	@Column(columnDefinition = "DATE")
	@Temporal(TemporalType.DATE)
	@Convert("localDateConverter")	
	private LocalDate			date3;
	
	@Column(columnDefinition = "DATE")
//	@Temporal(TemporalType.DATE)
	@Convert("localDateConverter")	
	private LocalDate			date4;
*/	
	
	protected TaxReport() {
		super();
		
		setStatus( ReportStatusType.New );
		
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
	public LocalDate getDate() { return new LocalDate( this.date ); }
	public void setDate( LocalDate date ) { 
		this.date = ( date != null ? date.toDateTimeAtStartOfDay().toDate() : null ); 
	}

	@XmlTransient
	public LocalDate getLastModiDate() { return new LocalDate( this.lastModiDate ); }
	public void setLastModiDate( LocalDate lastModiDate ) { 
		this.lastModiDate = ( lastModiDate != null ? lastModiDate.toDateTimeAtStartOfDay().toDate() : null ); 
	}

	@XmlTransient
	public Organisation getOrganisation() { return organisation; }
	public void setOrganisation( Organisation organisation ) { this.organisation = organisation; }
	
	@XmlElement( name = "site" )
	@XmlElementWrapper( name="construction_sites" )
	public Map<Long, Site> getSites() { return sites; }
	public void setSites( Map<Long, Site> sites ) { this.sites = sites; }

	@XmlTransient
	@Access(AccessType.PROPERTY)
	@Column( name="xml_data", nullable=true, length=1048576)
	public String getStrValue() {
		
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
			XMLstr = XMLconverter.convertToXML( this, false );
			if ( logger.isDebugEnabled()) logger.debug( "XML:\n" + XMLconverter.convertToXML( this, true ));

			this.strValue = XMLstr;
			
		} catch (JAXBException e) {
			logger.error( "Cannot convert TaxReport to XML\n" + e );
		}

		
	}
	
	private void fromXMLrepresentation() {
		
		if ( this.strValue != null && this.strValue.length() > 0 ) {
			
			try {
				TaxReport tmpReport = null;
				tmpReport = XMLconverter.initFromXML( TaxReport.class, this.strValue );
				
				this.setSites( tmpReport.getSites());
				
				if ( logger.isDebugEnabled()) logger.debug( "XML was successfully converted to TaxReport (sites only)" );
				
			} catch (JAXBException e) {
				logger.error( "Cannot convert XML into TaxReport\n" + e  );
			}
			
			
		}
	}

	
	public void update( TaxReport report ) {

		this.setType( report.getType());
		this.setCode( report.getCode());
		this.setStatus( report.getStatus());
		this.setDate( report.getDate());
		this.setLastModiDate( report.getLastModiDate());
		this.setSites( report.getSites());
		this.setOrganisation( report.getOrganisation());
		
	}
	
	/*
	 * Business methods
	 * 
	 */
	
	public boolean addSite( Site site ) {
		
		if ( site != null ) {
			
			getSites().put( site.getId(), site );
			
			return true;
			
		}
		
		return false;
	}
	
	public Site getSite( long id ) {
		
		Site retSite = null;
		
		if ( getSites() != null ) {
			
			if ( getSites().containsKey( id )) {

				retSite = getSites().get( id );
				
			}
			
		}
		
		return retSite;
	}
	
	
	@Override
	public String toString() {
		
		
		return "TaxReport [type=" + type + ", code=" + code + ", status=" + status 
				+ ", date=" + ( date != null ? new SimpleDateFormat( "MMM, yyyy" ).format( date ) : "" )
				+ "]";
	}
	
	
	
}
