package com.c2point.tms.entity.taxreport;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;

@XmlRootElement
public class EmploymentContract {

	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger( EmploymentContract.class.getName());

	private EmployeeCertificateType	certificate;
	private EmploymentContractType	type;
	private LocalDate				startDate;
	private LocalDate				endDate;
	private Integer					days;
	private Integer					hours;
	
	public EmploymentContract() {
		
		this.certificate = EmployeeCertificateType.Yes;
		this.type = EmploymentContractType.Direct;
		this.startDate = null;
		this.endDate = null;
		this.days = 0;
		this.hours = 0;
		
		
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

	public Integer getDays() { return days; }
	public void setDays( Integer days ) { this.days = days; }

	public Integer getHours() { return hours; }
	public void setHours( Integer hours ) { this.hours = hours; }

	
}
