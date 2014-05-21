package com.c2point.tms.web.ui.taxreports;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.event.EventListenerList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;

import com.c2point.tms.entity.Organisation;
import com.c2point.tms.entity.taxreport.Address;
import com.c2point.tms.entity.taxreport.Contact;
import com.c2point.tms.entity.taxreport.Site;
import com.c2point.tms.web.ui.listeners.SitesModelListener;

public class SitesModel {

	private static Logger logger = LogManager.getLogger( SitesModel.class.getName());

	protected EventListenerList		listenerList = new EventListenerList(); 
	
	private Organisation			mainOrg;
	private Collection<Site> 		sites;
	private Site 					selectedSite;
	private ContractsModel			activeContractsModel;

	private LocalDate				date;
	
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
	
	public boolean initReports() {

		sites = new ArrayList<Site>();

//		
//		Collection<TaxReport> cl = TaxReportsFacade.getInstance().list( (( Taxreport_1UI )UI.getCurrent()).getSessionData().getUser().getOrganisation()); 
		
//		reports.addAll( cl );
		
		
		return true;
	}
	
	
	public Organisation getMainOrg() {
		return this.mainOrg;
	}
	public void setMainOrg( Organisation mainOrg ) {
		this.mainOrg = mainOrg;
	}
	
	public LocalDate getDate() { return this.date; }
	public void setDate( LocalDate date ) { this.date = date; }

	public Site getSelectedSite() { return this.selectedSite; }
	public void setSelectedSite( Site site ) {
		
		if ( site != null && this.selectedSite != null && site.getId() != this.selectedSite.getId()
				||
			 site == null && this.selectedSite != null
			 	||
			 site != null && this.selectedSite == null
				
				) {
			this.selectedSite = site;
		
			activeContractsModel = new ContractsModel( this.selectedSite );
			
			fireSiteSelected( this.selectedSite );

			if ( logger.isDebugEnabled()) logger.debug( "SiteModel changed selected site!" );
		}
	}
	
	public Collection<Site> getSites() {
		return sites;
	}
	public void setSites( Collection<Site> sites ) {
		this.sites = sites;
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

			sites.add( site );
			
			fireSiteAdded( site );
		}
		
		return site;
	}
	
	public boolean deleteSite( Site site ) {

		boolean bRes = false;
		try {
			bRes = sites.remove( site );
			
			fireSiteDeleted( site );
			
		} catch ( Exception e ) {
			logger.error( "Cannot delete Site:" + site );
		}
		
		return bRes;
	}
	
	public boolean modifySite( Site site ) {

		boolean bRes = false;
/*		
		for ( Site tmpSite : sites ) {
			
			if ( tmpSite.getId() == site.getId()) {

				fireSiteEdited( site );
				
			}
		}
*/
		if ( site != null ) {
			
			fireSiteEdited( site );
			
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
	
	

}
