package com.c2point.tms.web.ui.old_tmp.wizard;

import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class WizardModel {

	private static Logger logger = LogManager.getLogger( TaxWizard.class.getName());

	
	private EventListenerList 	listenerList = new EventListenerList(); 

	private ArrayList< TaxWizardPage > 	pages;
	private int 						activePageIndex;
	
	
	public WizardModel() {
		
		pages = new ArrayList< TaxWizardPage >();
		activePageIndex = -1;
	}
	
	public void add( TaxWizardPage page ) {
		
		if ( page != null ) {
			pages.add( page );

			firePageAdded( pages.size() -1 );
		
			// if first one make it active
			if ( size() == 1 ) 
				setActiveIndex( 0 );
			
			
		} else
			logger.error( "Added page cannot be null!" );
	}
	
	public void setActiveIndex( int index ) {
		
		if ( this.activePageIndex >= 0 && this.activePageIndex != index ) {
			firePageDeactivated( this.activePageIndex );
		}
	
		if ( this.activePageIndex != index && index >=0 && index < size()) {
			
			this.activePageIndex = index; 
		
			this.firePageActivated( this.activePageIndex );
		}
	}
	public int getActiveIndex() { return this.activePageIndex; }
	
	public TaxWizardPage getActivePage() {
		
		return getPage( this.activePageIndex );
	}
	
	public TaxWizardPage getPage( int index ) {
		
		if ( index >= 0 && index < size()) {

			return pages.get( index );
			
		} else {
			logger.error( "Wrong index specified");
			
		}
		
		return null;
	}
	
	public boolean isFirstPage() { return this.activePageIndex == 0; } 
	public boolean isLastPage() { return this.activePageIndex == 0; } 
	
	public int size() { return pages.size(); }
	
	public void addWizardListener( WizardListener listener ) {
		listenerList.add( WizardListener.class, listener);
	}
	
	private void firePageAdded( int index ) {
		Object[] listeners = listenerList.getListenerList();

	    for ( int i = listeners.length-2; i >= 0; i -= 2) {
	    	if ( listeners[ i ] == WizardListener.class) {
	    		(( WizardListener )listeners[ i + 1 ] ).newPageAdded( this, index );
	         }
	     }
	 }

	private void firePageActivated( int index ) {
		Object[] listeners = listenerList.getListenerList();

	    for ( int i = listeners.length-2; i >= 0; i -= 2) {
	    	if ( listeners[ i ] == WizardListener.class) {
	    		(( WizardListener )listeners[ i + 1 ] ).pageActivated( this, index );
	         }
	     }
	 }

	private void firePageDeactivated( int index ) {
		Object[] listeners = listenerList.getListenerList();

	    for ( int i = listeners.length-2; i >= 0; i -= 2) {
	    	if ( listeners[ i ] == WizardListener.class) {
	    		(( WizardListener )listeners[ i + 1 ] ).pageDeactivated( this, index );
	         }
	     }
	 }

	public ClickListener getCancelListener() {
		
		return new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if ( logger.isDebugEnabled())  logger.debug( "CANCEL button has been pressed!" );
			}
			
		};
	}
	
	public ClickListener getFinishListener() {
		
		return new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if ( logger.isDebugEnabled())  logger.debug( "FINISH button has been pressed!" );
				
			}
			
		};
	}
	public ClickListener getNextListener() {
		
		return new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				if ( logger.isDebugEnabled())  logger.debug( "NEXT button has been pressed!" );
				
				if ( getActivePage().validateForNext()) {
					setNextPage();
				}
				
			}
			
		};
	}
	public ClickListener getBackListener() {
		
		return new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				if ( logger.isDebugEnabled())  logger.debug( "BACK button has been pressed!" );

				if ( getActivePage().validateForBack()) {
					setBackPage();
				}
				
			}
			
		};
	}

	private void setNextPage() {
		setActiveIndex( this.getActiveIndex() + 1 );		
	}
	
	private void setBackPage() {
		setActiveIndex( this.getActiveIndex() - 1 );		
	}
	
	
}
