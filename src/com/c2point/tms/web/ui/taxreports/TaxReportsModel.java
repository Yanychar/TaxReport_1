package com.c2point.tms.web.ui.taxreports;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.event.EventListenerList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;

import com.c2point.tms.application.Taxreport_1UI;
import com.c2point.tms.datalayer.ShowType;
import com.c2point.tms.datalayer.OrganisationFacade;
import com.c2point.tms.datalayer.tax.InMemoryTaxReportInfoImpl;
import com.c2point.tms.datalayer.tax.TaxReportInfoIF;
import com.c2point.tms.datalayer.tax.TaxReportsFacade;
import com.c2point.tms.entity.Organisation;
import com.c2point.tms.entity.TmsUser;
import com.c2point.tms.web.ui.listeners.OrgModelListener;
import com.c2point.tms.entity_tax.IDType;
import com.c2point.tms.entity_tax.ReportStatusType;
import com.c2point.tms.entity_tax.ReportType;
import com.c2point.tms.entity_tax.TaxReport;
import com.c2point.tms.web.ui.listeners.TaxReportModelListener;
import com.vaadin.ui.UI;

public class TaxReportsModel {

	private static Logger logger = LogManager.getLogger( TaxReportsModel.class.getName());

	protected EventListenerList		listenerList = new EventListenerList(); 
	
	private Organisation				fillerOrg;
	private Organisation				selectedOrg;
	private Collection<Organisation>	orgs;
	
	private Collection<TaxReport> 	reports;

	private TaxReportInfoIF 		trInfo = new InMemoryTaxReportInfoImpl();
	
	public TaxReportsModel( Organisation filler ) {
		
		this( filler, filler );
	}
	
	public TaxReportsModel( Organisation filler, Organisation org ) {

		this( filler, org, false );
	}
	
	public TaxReportsModel( Organisation filler, Organisation org, boolean showAll ) {

		this.fillerOrg = filler;
		
		if ( showAll ) {
			
			initOrgs();
			
		} else {
			this.orgs = null;
		}
		selectOrganisation( org );

		
	}

	public boolean selectOrganisation( Organisation org ) {
		
		boolean bRes = false;
		
		if ( orgs != null && orgs.contains( org )
			||	orgs == null
		) {
			
			this.selectedOrg = org;
			
			initReports();
			
			fireOrgSelected( this.selectedOrg );
			
			bRes = true;
			
		}
		
		return bRes;
	}
	
	public Organisation getSelectedOrganisation() {
		
		return this.selectedOrg;
	}
	
	public boolean initReports() {

		reports = new ArrayList<TaxReport>();

//		testFillReportsList();
		
//		getFilerInfo();
//		
		Collection<TaxReport> cl = TaxReportsFacade.getInstance().list( (( Taxreport_1UI )UI.getCurrent()).getSessionData().getUser().getOrganisation()); 
		
		reports.addAll( cl );
		
		
		return true;
	}
	
	private void initOrgs() {

		if ( this.orgs != null && this.orgs.size() > 0 ) {
			this.orgs.clear();
		}

		this.orgs = OrganisationFacade.getInstance().getOrganisations( ShowType.CURRENT );

	}
	
	public Organisation getFiller() {
		return this.fillerOrg;
	}
	public void setFiller( Organisation filler ) {
		this.fillerOrg = filler;
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
	
	
	private void fireOrgSelected( Organisation org ) {
		
		Object[] listeners = listenerList.getListenerList();

	    for ( int i = listeners.length-2; i >= 0; i -= 2) {
	    	if ( listeners[ i ] == OrgModelListener.class ) {
	    		(( OrgModelListener )listeners[ i + 1 ] ).selected( org );
	         }
	     }

	}
	
	public void addListener( OrgModelListener listener ) {
		listenerList.add( OrgModelListener.class, listener );
	}
	
	
	
	/*
	 * Business method  
	 */
	
	public TaxReportInfoIF getTaxReportInfo() { return trInfo; }
	
	
}
