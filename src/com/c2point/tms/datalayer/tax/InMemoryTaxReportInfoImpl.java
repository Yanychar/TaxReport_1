package com.c2point.tms.datalayer.tax;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;

import com.c2point.tms.entity.taxreport.ReportType;

public class InMemoryTaxReportInfoImpl implements TaxReportInfoIF {

	private static Logger logger = LogManager.getLogger( InMemoryTaxReportInfoImpl.class.getName());
	
	private Map< ReportType, LocalDate > lastDateStorade = new HashMap< ReportType, LocalDate >();
	
	@Override
	public LocalDate getNewReportTime( ReportType type ) {
		
		logger.error( "Temporal implementation of TaxReportInfoIF!!!" );

		if ( lastDateStorade.containsKey( type )) {
			return lastDateStorade.get( type );
		}
		
		return null;
		
	}

	@Override
	public void setLatestReportTime( ReportType type, LocalDate date ) {

		logger.error( "Temporal implementation of TaxReportInfoIF!!!" );
		
		LocalDate latestDate = getNewReportTime( type );
		LocalDate newDate = date.withDayOfMonth( 1 );
		
		if ( latestDate == null || latestDate != null && latestDate.isBefore( newDate )) {
		
			lastDateStorade.put( type, newDate );
		}
		
	}

}
