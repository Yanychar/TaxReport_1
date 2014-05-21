package com.c2point.tms.web.ui.old_tmp.wizard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.VerticalLayout;

public class TaxProgressBar extends VerticalLayout //HorizontalLayout 
						 implements WizardListener {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger( TaxProgressBar.class.getName());

	private HorizontalLayout 	headersBar = new HorizontalLayout(); 
	private ProgressBar 		progressBar = new ProgressBar(); 
	
	public TaxProgressBar() {
		super();
		
		this.setWidth( "100%" );

		headersBar.setWidth( "100%" );
		progressBar.setWidth( "100%" );
		progressBar.setHeight( "14px" );
//		progressBar.setValue( 1.0f );
		
		this.addComponent( headersBar ); 
		this.addComponent( progressBar );
	}

	private void updateProgressBar( WizardModel model ) {
		
//        int stepCount = model.size();
//        float padding = (1.0f / stepCount) / 2;
//        float progressValue = padding + activeStepIndex / (float) stepCount;
        
        float progressValue = ( model.getActiveIndex() + 1.0f ) / model.size();
        if ( logger.isDebugEnabled()) logger.debug( "Progress value calculation: " + ( model.getActiveIndex() + 1.0f ) + "/" + model.size() + " = " +  progressValue );
        
        progressBar.setValue( progressValue );
    }
	
	
	@Override
	public void newPageAdded( WizardModel model, int index ) {

		Label header = new Label();
		header.setContentMode( ContentMode.HTML );
		
		header.setValue( Integer.toString( index + 1 ) + ". " + model.getPage( index ).getCaption());
		
		headersBar.addComponent( header, index );
		header.setEnabled( false );

		updateProgressBar( model );
		
	}

	@Override
	public void pageActivated( WizardModel model, int index ) {
		
		Label header = ( Label )headersBar.getComponent( index );
		
		if ( header != null ) {

			header.setEnabled( true );
			header.setValue( "<b>" + getHeader( model, index ) + "</b>" );
			
			updateProgressBar( model );
			
		} else {
			logger.error( "Cannot highlight header because of wrong index" );
		}
		
	}

	@Override
	public void pageDeactivated(WizardModel model, int index) {

		Label header = ( Label )headersBar.getComponent( index );
		
		if ( header != null ) {

			
			header.setValue( getHeader( model, index ));
			header.setEnabled( false );

		} else {
			logger.error( "Cannot highlight header because of wrong index" );
		}
		
	}
	
	private String getHeader( WizardModel model, int index ) {

		return Integer.toString( index + 1 ) + ". " + model.getPage( index ).getCaption();
		
	}

	
}
