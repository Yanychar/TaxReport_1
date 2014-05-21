package com.c2point.tms.entity.taxreport;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.LocalDate;

public class LocalDateXmlAdapter extends XmlAdapter<String, LocalDate> {

	@Override
	public String marshal( LocalDate date ) throws Exception {
		
//		return DateTimeFormat.forPattern("ddMMyyyy").print( date );
		
	return date.toString();	
	}

	@Override
	public LocalDate unmarshal( String dateStr ) throws Exception {

//		return DateTimeFormat.forPattern("ddMMyyyy").parseLocalDate( dateStr );
		
		return new LocalDate( dateStr );
	}	
}
