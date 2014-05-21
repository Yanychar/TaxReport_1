package com.c2point.tms.web.ui.listeners;

import java.util.EventListener;

import com.c2point.tms.entity.taxreport.TaxReport;

public interface TaxReportModelListener extends EventListener {

	public void added( TaxReport report );
	public void edited( TaxReport report );
	public void deleted( TaxReport report );

}
