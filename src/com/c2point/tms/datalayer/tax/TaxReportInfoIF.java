package com.c2point.tms.datalayer.tax;

import org.joda.time.LocalDate;

import com.c2point.tms.entity_tax.ReportType;

public interface TaxReportInfoIF {

	public LocalDate	getNewReportTime( ReportType type );
	public void 		setLatestReportTime( ReportType type, LocalDate date );
}
