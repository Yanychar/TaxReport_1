package com.c2point.tms.web.ui.taxreports;

import java.util.Collection;

import javax.swing.event.EventListenerList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.c2point.tms.entity.taxreport.Contract;
import com.c2point.tms.entity.taxreport.Contractor;
import com.c2point.tms.entity.taxreport.Site;
import com.c2point.tms.web.ui.listeners.ContractsModelListener;

public class ContractsModel {

	private static Logger logger = LogManager.getLogger( SitesModel.class.getName());
	
	protected EventListenerList		listenerList = new EventListenerList(); 

	private Site site;
	
	private Contract	selectedContract;
	
	
	public ContractsModel( Site site ) {
		
		this.site = site;
		
	}

	public Contract getSelectedContract() { return this.selectedContract; }
	public void setSelectedContract( Contract contract ) { 
		
		this.selectedContract = contract;
		fireContractSelected( contract );
	}
	
	public Collection<Contract> getContracts() {
		return site.getContracts();
	}
	
	
	public Contract addContract( Contract.ContractType type, 
								 Contractor contractor
								  ) {

		Contract contract = new Contract( contractor, type );

		return addContract( contract );
	
	}

	public Contract addContract( Contract contract ) {
		
		if ( contract != null ) {
		
			site.getContracts().add( contract );
		
			fireContractAdded( contract );
		}
		
		return contract;
	}

	public boolean deleteContract( Contract contract ) {
		
		boolean bRes = false;

		try {
			bRes = site.getContracts().remove( contract );
		
			fireContractDeleted( contract );
		
		} catch ( Exception e ) {
			logger.error( "Cannot delete Contract:" + contract );
		}
		
		return bRes;
	}

	public boolean modifyContract( Contract contract ) {
	
		boolean bRes = false;
		
		if ( contract != null ) {
			
			fireContractEdited( contract );
			
			bRes = true;
			
		}
		
		return bRes;
	}

	
	private void fireContractAdded( Contract contract ) {
		Object[] listeners = listenerList.getListenerList();

	    for ( int i = listeners.length-2; i >= 0; i -= 2) {
	    	if ( listeners[ i ] == ContractsModelListener.class ) {
	    		(( ContractsModelListener )listeners[ i + 1 ] ).added( contract );
	         }
	     }
	}

	private void fireContractEdited( Contract contract ) {
		Object[] listeners = listenerList.getListenerList();

	    for ( int i = listeners.length-2; i >= 0; i -= 2 ) {
	    	if ( listeners[ i ] == ContractsModelListener.class ) {
	    		(( ContractsModelListener )listeners[ i + 1 ] ).edited( contract );
	         }
	     }
	}

	private void fireContractDeleted( Contract contract ) {
		Object[] listeners = listenerList.getListenerList();

	    for ( int i = listeners.length-2; i >= 0; i -= 2 ) {
	    	if ( listeners[ i ] == ContractsModelListener.class ) {
	    		(( ContractsModelListener )listeners[ i + 1 ] ).deleted( contract );
	         }
	     }
	}

	private void fireContractSelected( Contract contract ) {
		Object[] listeners = listenerList.getListenerList();

	    for ( int i = listeners.length-2; i >= 0; i -= 2 ) {
	    	if ( listeners[ i ] == ContractsModelListener.class ) {
	    		(( ContractsModelListener )listeners[ i + 1 ] ).selected( contract );
	         }
	     }
	}

	
	public void addListener( ContractsModelListener listener ) {
		listenerList.add( ContractsModelListener.class, listener );
	}
	
	
}
