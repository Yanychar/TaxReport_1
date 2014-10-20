package com.c2point.tms.testing;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;

import com.c2point.tms.application.Taxreport_1UI;
import com.c2point.tms.entity.Organisation;
import com.c2point.tms.entity.TmsUser;
import com.c2point.tms.entity_tax.IDType;
import com.c2point.tms.entity_tax.ReportStatusType;
import com.c2point.tms.entity_tax.ReportType;
import com.c2point.tms.entity_tax.TaxReport;
import com.c2point.tms.datalayer.DataFacade;
import com.c2point.tms.datalayer.OrganisationFacade;
import com.c2point.tms.datalayer.tax.TaxReportsFacade;
import com.vaadin.ui.UI;

public class TestData {

	private static Logger logger = LogManager.getLogger( TestData.class.getName());
	
	public static void initOrganisationAndUser() {
		
		logger.error( "ATTENTION!!! Still Test Data has been used. Fill Reports !!!" );
		
		
		Organisation org = new Organisation( "0001", "Rakennus Nordic Oy" );
		org.setAddress( "Punavuorenkatu 2545, 001233 Sejnapuna4poki" );
		org.setTunnus( "154352-7" );
		org.setPhone( "0401234567" );
		org.setEmail( "test.email@email.com" );

		org.setIdType( IDType.Vat );
		org.setCountryCode( "FI" );

		
		Organisation existingOrg = OrganisationFacade.getInstance().getOrganisation( org.getCode());
		if ( existingOrg == null ) {
			org = OrganisationFacade.getInstance().addOrganisation( org );
		} else {
			org = existingOrg;
		}
		
		TmsUser user = null;

		Collection<TmsUser> list = DataFacade.getInstance().list( TmsUser.class );
		
		if ( list != null && list.size() > 0 ) {
			
			user = list.iterator().next();
		} else {

			user = new TmsUser( "123456", "Serguei", "Sevastianov" );			

			user.setOrganisation( org );
			
			user = DataFacade.getInstance().insert( user );

		}
		
		if ( UI.getCurrent() != null && ((Taxreport_1UI) UI.getCurrent()).getSessionData() != null ) {
		
			((Taxreport_1UI) UI.getCurrent()).getSessionData().setUser( user );
		}

		
		// Add one more organisation for test purposes
		org = new Organisation( "0002", "Test Org Oy" );
		org.setAddress( "Simpukkatie 16 A, 00987 Helsinki" );
		org.setTunnus( "154352-7" );
		org.setPhone( "111 111 11 11" );
		org.setEmail( "test.email@email.com" );

		org.setIdType( IDType.Vat );
		org.setCountryCode( "FI" );
		
		org = OrganisationFacade.getInstance().getOrganisation( org.getCode());
		if ( org != null ) {
			org = OrganisationFacade.getInstance().addOrganisation( org );
		}
		
	}

	public static Organisation getTestOrganisation() {
		return OrganisationFacade.getInstance().getOrganisation( "0001" );
	}
	
	public static void initTaxReports() {
		
		String [][] reportsData = {
				{ "1", "2014", ReportType.Employees.toString(), ReportStatusType.Basic.toString() },
				{ "1", "2014", ReportType.Contracts.toString(), ReportStatusType.Basic.toString() },
				{ "2", "2014", ReportType.Employees.toString(), ReportStatusType.Basic.toString() },
				{ "2", "2014", ReportType.Employees.toString(), ReportStatusType.Correction.toString() },
				{ "2", "2014", ReportType.Contracts.toString(), ReportStatusType.Basic.toString() },
				{ "3", "2014", ReportType.Employees.toString(), ReportStatusType.Basic.toString() },
				{ "3", "2014", ReportType.Contracts.toString(), ReportStatusType.Basic.toString() },
				
		};

		
		Organisation org = OrganisationFacade.getInstance().getOrganisation( "0001" );
		
		TaxReport report;
		
		TaxReportsFacade trFacade = TaxReportsFacade.getInstance();
		
		int counter = 1;
		for ( String [] reportData : reportsData  ) {
			
			report = new TaxReport( ReportType.valueOf( reportData[2] ));
//			report.setId( counter++ );
			report.setDate( new LocalDate( Integer.parseInt( reportData[1] ), Integer.parseInt( reportData[0] ), 1 ));
			report.setStatus( ReportStatusType.valueOf( reportData[3] ));
			
			report.setOrganisation( org );
			
			trFacade.addReport( report );
		}
		
		
	}
	
	
}
