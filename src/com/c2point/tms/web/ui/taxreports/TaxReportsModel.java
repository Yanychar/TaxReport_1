package com.c2point.tms.web.ui.taxreports;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.event.EventListenerList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;

import com.c2point.tms.application.Taxreport_1UI;
import com.c2point.tms.datalayer.TaxReportsFacade;
import com.c2point.tms.datalayer.tax.InMemoryTaxReportInfoImpl;
import com.c2point.tms.datalayer.tax.TaxReportInfoIF;
import com.c2point.tms.entity.Organisation;
import com.c2point.tms.entity.TmsUser;
import com.c2point.tms.entity.taxreport.IDType;
import com.c2point.tms.entity.taxreport.ReportStatusType;
import com.c2point.tms.entity.taxreport.ReportType;
import com.c2point.tms.entity.taxreport.TaxReport;
import com.c2point.tms.web.ui.listeners.TaxReportModelListener;
import com.vaadin.ui.UI;

public class TaxReportsModel {

	private static Logger logger = LogManager.getLogger( TaxReportsModel.class.getName());

	protected EventListenerList		listenerList = new EventListenerList(); 
	
	private Organisation			mainOrg;
	private Collection<TaxReport> 	reports;

	private TaxReportInfoIF 		trInfo = new InMemoryTaxReportInfoImpl();
	
	public TaxReportsModel() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean initReports() {

		reports = new ArrayList<TaxReport>();

		testFillReportsList();
		
		getFilerInfo();
//		
		Collection<TaxReport> cl = TaxReportsFacade.getInstance().list( (( Taxreport_1UI )UI.getCurrent()).getSessionData().getUser().getOrganisation()); 
		
		reports.addAll( cl );
		
		
		return true;
	}
	
	
	public Organisation getMainOrg() {
		return this.mainOrg;
	}
	public void setMainOrg( Organisation mainOrg ) {
		this.mainOrg = mainOrg;
	}
	
	public Collection<TaxReport> getReports() {
		return reports;
	}
	public void setReports( Collection<TaxReport> reports) {
		this.reports = reports;
	}
	
	public TaxReport createNewReport( ReportType type, LocalDate date ) {
		
		TaxReport report = new TaxReport( type );
		report.setStatus( ReportStatusType.New );
		report.setDate( date );
		report.setOrganisation( ((Taxreport_1UI) UI.getCurrent()).getSessionData().getUser().getOrganisation());

		report = TaxReportsFacade.getInstance().addReport( report );
		if ( report != null ) {
			reports.add( report );
			
			fireReportAdded( report );
		}
		
		return report;
	}


	private void fireReportAdded( TaxReport report ) {
		Object[] listeners = listenerList.getListenerList();

	    for ( int i = listeners.length-2; i >= 0; i -= 2) {
	    	if ( listeners[ i ] == TaxReportModelListener.class ) {
	    		(( TaxReportModelListener )listeners[ i + 1 ] ).added( report );
	         }
	     }
	}

	@SuppressWarnings("unused")
	private void fireReportEdited( TaxReport report ) {
		Object[] listeners = listenerList.getListenerList();

	    for ( int i = listeners.length-2; i >= 0; i -= 2) {
	    	if ( listeners[ i ] == TaxReportModelListener.class ) {
	    		(( TaxReportModelListener )listeners[ i + 1 ] ).edited( report );
	         }
	     }
	}

	@SuppressWarnings("unused")
	private void fireReportDeleted( TaxReport report ) {
		Object[] listeners = listenerList.getListenerList();

	    for ( int i = listeners.length-2; i >= 0; i -= 2) {
	    	if ( listeners[ i ] == TaxReportModelListener.class ) {
	    		(( TaxReportModelListener )listeners[ i + 1 ] ).deleted( report );
	         }
	     }
	}

	public void addListener( TaxReportModelListener listener ) {
		listenerList.add( TaxReportModelListener.class, listener);
	}
	
	
	
	
	/*
	 * Business method  
	 */
	
	public TaxReportInfoIF getTaxReportInfo() { return trInfo; }
	
	
	
	
	
/*
 * 
 *    The rest is procedures for quick testing
 * 	
 */
	public void getFilerInfo() {
		
		// Fill Filer
		Organisation org = (( Taxreport_1UI )UI.getCurrent()).getSessionData().getUser().getOrganisation();

		this.mainOrg = org;
		
	}
	
	public void testFillReportsList() {

		Organisation org = new Organisation( "00001", "Rakennus Nordic Oy" );
		org.setAddress( "Mannerheimintie 121, 00100 Helsinki" );
		org.setTunnus( "1234656-7" );
		org.setPhone( "0401234567" );
		org.setEmail( "test.email@email.com" );

		org.setIdType( IDType.Vat );
		org.setCountryCode( "FI" );
		
		TmsUser user = new TmsUser( "0002", "Peter", "Volanchunas" );
		user.setOrganisation( org );
		
		
		(( Taxreport_1UI )UI.getCurrent()).getSessionData().setUser( user );

		String [][] reportsData = {
				{ "1", "2014", ReportType.Employees.toString(), ReportStatusType.Basic.toString() },
				{ "1", "2014", ReportType.Contracts.toString(), ReportStatusType.Basic.toString() },
				{ "2", "2014", ReportType.Employees.toString(), ReportStatusType.Basic.toString() },
				{ "2", "2014", ReportType.Employees.toString(), ReportStatusType.Correction.toString() },
				{ "2", "2014", ReportType.Contracts.toString(), ReportStatusType.Basic.toString() },
				{ "3", "2014", ReportType.Employees.toString(), ReportStatusType.Basic.toString() },
				{ "3", "2014", ReportType.Contracts.toString(), ReportStatusType.Basic.toString() },
				
		};
		
		TaxReport report;
		
		if ( reports != null ) {
			reports.clear();
		} else {
			reports = new ArrayList<TaxReport>();
		}
		
		int counter = 1;
		for ( String [] reportData : reportsData  ) {
			
			report = new TaxReport( ReportType.valueOf( reportData[2] ));
			report.setId( counter++ );
			report.setDate( new LocalDate( Integer.parseInt( reportData[1] ), Integer.parseInt( reportData[0] ), 1 ));
			report.setStatus( ReportStatusType.valueOf( reportData[3] ));
			
			reports.add( report );
		}
		
	}

	public static void main(String[] args) {

		TaxReportsModel model = new TaxReportsModel();
		
		model.testFillReportsList();
		
		for ( TaxReport report : model.getReports()) {
			logger.debug( report );
		}
	}
}
