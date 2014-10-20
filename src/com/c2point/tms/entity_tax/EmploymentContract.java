package com.c2point.tms.entity_tax;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;

import com.c2point.tms.entity.TaskReport;
import com.c2point.tms.entity.TmsUser;
import com.c2point.tms.util.DateUtil;

@XmlRootElement
public class EmploymentContract {

	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger( EmploymentContract.class.getName());

	private EmployeeCertificateType	certificate;
	private EmploymentContractType	type;
	private LocalDate				startDate;
	private LocalDate				endDate;
	private int						days;
	private float					hours;

	private Map<String, Boolean> dateSet;
	
	public EmploymentContract() {
		
		this.certificate = EmployeeCertificateType.Yes;
		this.type = EmploymentContractType.Direct;
		this.startDate = null;
		this.endDate = null;
		this.days = 0;
		this.hours = 0;
		
		dateSet = new HashMap<String, Boolean>();
		
	}

	public EmploymentContract( TmsUser user ) {
		this();
		
		// TODO. Fill TmsUser contract details when they will be handled
	}
	
	public EmployeeCertificateType getCertificate() { return certificate; }
	public void setCertificate( EmployeeCertificateType certificate ) { this.certificate = certificate; }

	public EmploymentContractType getType() { return type; }
	public void setType( EmploymentContractType type ) { this.type = type; }

	@XmlElement( name = "start" )
	@XmlJavaTypeAdapter( type=LocalDate.class, value=LocalDateXmlAdapter.class )
	public LocalDate getStartDate() { return startDate; }
	public void setStartDate( LocalDate startDate ) { this.startDate = startDate; }

	@XmlElement( name = "end" )
	@XmlJavaTypeAdapter( type=LocalDate.class, value=LocalDateXmlAdapter.class )
	public LocalDate getEndDate() { return endDate; }
	public void setEndDate( LocalDate endDate ) { this.endDate = endDate; }

	public int getDays() { return days; }
	public void setDays( int days ) { this.days = days; }

	public float getHours() { return hours; }
	public void setHours( float hours ) { this.hours = hours; }


	/*
	 * Business Methods
	 */
	
	public boolean handleTaskReport( TaskReport taskReport ) {
		
		setHours( getHours() + taskReport.getHours()); 
		
		addDayIfNecessary( taskReport );
		
		return true;
	}
	
	private void addDayIfNecessary( TaskReport taskReport ) {
		
		String dateKey = DateUtil.dateNoDelimToString( taskReport.getDate());

		if ( !dateSet.containsKey( dateKey )) {
			
			dateSet.put( dateKey,  true );
			
			setDays( getDays() + 1 ); 
			
		}
	}
	
}
