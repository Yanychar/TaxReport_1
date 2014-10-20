package com.c2point.tms.web.ui.taxreports;

import java.util.Collection;

import javax.swing.event.EventListenerList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.c2point.tms.datalayer.tax.TaxReportsFacade;
import com.c2point.tms.entity.Organisation;
import com.c2point.tms.entity_tax.Address;
import com.c2point.tms.entity_tax.Contact;
import com.c2point.tms.entity_tax.Site;
import com.c2point.tms.entity_tax.TaxReport;
import com.c2point.tms.web.ui.listeners.SitesModelListener;

public class SitesModel {

	private static Logger logger = LogManager.getLogger( SitesModel.class.getName());

	protected EventListenerList		listenerList = new EventListenerList(); 
	
	private Organisation			mainOrg;
	private	TaxReport				report;

//	private Collection<Site> 		sites;
	
	private Site 					selectedSite;
	private ContractsModel			activeContractsModel;

/*	
	public SitesModel() {
		this( null );
	}
	
	public SitesModel( LocalDate date ) {
	
		if ( date != null ) {
			setDate( date );
		} else {
			setDate( LocalDate.now());
		}
	}
*/
	
	public SitesModel( TaxReport report ) {

		this.report = report;
//		this.sites = new ArrayList<Site>( report.getSites());

		
	}
	
	
/*	
	public boolean initReports() {

		sites = new ArrayList<Site>();

//		
//		Collection<TaxReport> cl = TaxReportsFacade.getInstance().list( (( Taxreport_1UI )UI.getCurrent()).getSessionData().getUser().getOrganisation()); 
		
//		reports.addAll( cl );
		
		
		return true;
	}
*/	
	
	public Organisation getMainOrg() {
		return this.mainOrg;
	}
	public void setMainOrg( Organisation mainOrg ) {
		this.mainOrg = mainOrg;
	}
	
	public TaxReport getReport() { return this.report; }

	public Site getSelectedSite() { return this.selectedSite; }
	public void setSelectedSite( Site site ) {
		
		if ( site != null && this.selectedSite != null && site.getId() != this.selectedSite.getId()
				||
			 site == null && this.selectedSite != null
			 	||
			 site != null && this.selectedSite == null
				
				) {
			this.selectedSite = site;
		
			activeContractsModel = ( this.selectedSite != null
										? new ContractsModel( this.selectedSite )
										: null 
									);
			
			fireSiteSelected( this.selectedSite );

			if ( logger.isDebugEnabled()) logger.debug( "SiteModel changed selected site!" );
		}
	}
	
	public Collection<Site> getSites() {
		return this.report.getSites().values();
	}

	public Site addSite( String siteVeroID, String siteNumber,
								Address address, Contact contact ) {
		
		Site site = new Site();
		site.setSiteVeroID( siteVeroID );
		site.setSiteNumber( siteNumber );
		site.setAddress( address );
		site.setContact( contact );

		return addSite( site );
	}

	public Site addSite( Site site ) {

		if ( site != null ) {

			this.report.addSite( site );
			
			if ( TaxReportsFacade.getInstance().updateReport( this.report )) {
				
				fireSiteAdded( site );
			}
		}
		
		return site;
	}
	
	public boolean deleteSite( Site site ) {

		boolean bRes = false;
		try {
			bRes = getSites().remove( site );

			if ( TaxReportsFacade.getInstance().updateReport( this.report )) {
			
				fireSiteDeleted( site );
			
			}
			
		} catch ( Exception e ) {
			logger.error( "Cannot delete Site:" + site.getName() + "\n" + e );
		}
		
		return bRes;
	}
	
	public boolean modifySite( Site site ) {

		boolean bRes = false;

		if ( site != null ) {
			
			if ( TaxReportsFacade.getInstance().updateReport( this.report )) {
			
				fireSiteEdited( site );
			
			}
			
			bRes = true;
			
		}
		
		
		return bRes;
	}
	
	private void fireSiteAdded( Site site ) {
		Object[] listeners = listenerList.getListenerList();

	    for ( int i = listeners.length-2; i >= 0; i -= 2) {
	    	if ( listeners[ i ] == SitesModelListener.class ) {
	    		(( SitesModelListener )listeners[ i + 1 ] ).added( site );
	         }
	     }
	}

	private void fireSiteEdited( Site site ) {
		Object[] listeners = listenerList.getListenerList();

	    for ( int i = listeners.length-2; i >= 0; i -= 2) {
	    	if ( listeners[ i ] == SitesModelListener.class ) {
	    		(( SitesModelListener )listeners[ i + 1 ] ).edited( site );
	         }
	     }
	}

	private void fireSiteDeleted( Site site ) {
		Object[] listeners = listenerList.getListenerList();

	    for ( int i = listeners.length-2; i >= 0; i -= 2) {
	    	if ( listeners[ i ] == SitesModelListener.class ) {
	    		(( SitesModelListener )listeners[ i + 1 ] ).deleted( site );
	         }
	     }
	}

	private void fireSiteSelected( Site site ) {
		Object[] listeners = listenerList.getListenerList();

	    for ( int i = listeners.length-2; i >= 0; i -= 2) {
	    	if ( listeners[ i ] == SitesModelListener.class ) {
	    		(( SitesModelListener )listeners[ i + 1 ] ).selected( site );
	         }
	     }
	}

	
	public void addListener( SitesModelListener listener ) {
		listenerList.add( SitesModelListener.class, listener );
	}
	
	
	
	
	/*
	 * Business method  
	 */
	
	public ContractsModel getContractorsModel() {
		
		return activeContractsModel;
	}
	
/*
	public TaxReport updateReport( TaxReport report ) {
		
		if ( TaxReportsFacade.getInstance().updateReport( report )) {
			
			fireReportEdited( report );
		}
		
		return report;
	}
*/


}
