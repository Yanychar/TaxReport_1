package com.c2point.tms.web.ui.subcontract;

import java.util.Collection;
import java.util.Map;

import javax.swing.event.EventListenerList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.c2point.tms.entity.Organisation;
import com.c2point.tms.web.ui.listeners.OrgModelListener;

public class SubContrModel {

	private static Logger logger = LogManager.getLogger( SubContrModel.class.getName());

	protected EventListenerList			listenerList = new EventListenerList(); 

	private Organisation 				mainOrg;

	private Organisation 				selectedSubContr;

	
	
	public SubContrModel( Organisation mainOrg ) {
	
		this.mainOrg = mainOrg;
		
	}

	public Collection<Organisation> getSubContractors() {

		if ( mainOrg.getSubcontractors() != null ) {
			
			return mainOrg.getSubcontractors().values();

		}
			
		return null;
		
	}

	public Organisation getSelectedSubContr() { return this.selectedSubContr; }
	public void setSelectedSubContr( Organisation subContr ) {
		
		if ( subContr != null && this.selectedSubContr != null && subContr.getId() != this.selectedSubContr.getId()
				||
				subContr == null && this.selectedSubContr != null
			 	||
			 	subContr != null && this.selectedSubContr == null
				
				) {
			this.selectedSubContr = subContr;
		
			fireOrgSelected( this.selectedSubContr );

			if ( logger.isDebugEnabled()) logger.debug( "SubContrModel select new SubContr!" );
		}
	}
	
	public Organisation add( Organisation subContr ) {

		if ( subContr != null ) {

			subContr = mainOrg.addSubcontractor( subContr );
			
			// TODO
			// Shall be saved in DB firstly: org = OrgFacade.
			if ( true ) {
			
				fireOrgAdded( subContr );
			}
		}
		
		return subContr;
	}
	
	public boolean delete( Organisation subContr ) {

		boolean bRes = false;
		
		try {
			Map<String, Organisation> map = mainOrg.getSubcontractors();
			
			if ( map != null ) {
			
				map.remove( subContr.getCode());
				
				if ( map.remove( subContr.getCode()) != null ) {

					// TODO
					// Shall be saved in DB firstly: org = OrgFacade.
					if ( true ) {
					
						fireOrgDeleted( subContr );
						bRes = true;
					}
				}
			}
			
		} catch ( Exception e ) {
			logger.error( "Cannot delete SubContractor" );
		}
		
		return bRes;
	}
	
	public boolean modify( Organisation subContr ) {

		boolean bRes = false;
/*		
		for ( Site tmpSite : sites ) {
			
			if ( tmpSite.getId() == site.getId()) {

				fireSiteEdited( site );
				
			}
		}
*/
		// TODO
		// Shall be saved in DB firstly: org = OrgFacade.
		if ( true ) {
		
			fireOrgEdited( subContr );
			
			bRes = true;
			
		}
		
		
		return bRes;
	}
	
	private void fireOrgAdded( Organisation subContr ) {
		Object[] listeners = listenerList.getListenerList();

	    for ( int i = listeners.length-2; i >= 0; i -= 2) {
	    	if ( listeners[ i ] == OrgModelListener.class ) {
	    		(( OrgModelListener )listeners[ i + 1 ] ).added( subContr );
	         }
	     }
	}

	private void fireOrgEdited( Organisation subContr ) {
		Object[] listeners = listenerList.getListenerList();

	    for ( int i = listeners.length-2; i >= 0; i -= 2) {
	    	if ( listeners[ i ] == OrgModelListener.class ) {
	    		(( OrgModelListener )listeners[ i + 1 ] ).edited( subContr );
	         }
	     }
	}

	private void fireOrgDeleted( Organisation subContr ) {
		Object[] listeners = listenerList.getListenerList();

	    for ( int i = listeners.length-2; i >= 0; i -= 2) {
	    	if ( listeners[ i ] == OrgModelListener.class ) {
	    		(( OrgModelListener )listeners[ i + 1 ] ).deleted( subContr );
	         }
	     }
	}

	private void fireOrgSelected( Organisation subContr ) {
		Object[] listeners = listenerList.getListenerList();

	    for ( int i = listeners.length-2; i >= 0; i -= 2) {
	    	if ( listeners[ i ] == OrgModelListener.class ) {
	    		(( OrgModelListener )listeners[ i + 1 ] ).selected( subContr );
	         }
	     }
	}

	
	public void addListener( OrgModelListener listener ) {
		listenerList.add( OrgModelListener.class, listener );
	}
	
	
	
	
	/*
	 * Business method  
	 */
	
	
	
	
	
}
