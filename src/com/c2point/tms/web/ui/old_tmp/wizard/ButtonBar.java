package com.c2point.tms.web.ui.old_tmp.wizard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class ButtonBar extends HorizontalLayout implements WizardListener {

	private static final long serialVersionUID = 1L;

	public enum ButtonType { Cancel, Finish, Next, Back };
	
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger( ButtonBar.class.getName());
	
	private WizardModel 	model;
	
	private Button cancelButton = new Button(); 
	private Button finishButton = new Button();
	
	private Button nextButton = new Button(); 
	private Button backButton = new Button(); 
	
	private ButtonBar( boolean bCancel, boolean bBack, boolean bNext, boolean bFinish ) {
		super();
		
		initUI();
		
		setEnabled( bCancel, bBack, bNext, bFinish );
	}

	public ButtonBar() {
		this( true, false, true, false );
	}
	
	protected void initUI() {
		
		this.setWidth( "100%" );
		
		this.setMargin( true );
		this.setSpacing( true );
		
		initResources();
		
		Label glue1 = new Label( "" );
		Label glue2 = new Label( "" );
		addComponent( cancelButton );
		addComponent( glue1 );
		addComponent( backButton );
		addComponent( nextButton ); 
		addComponent( glue2 );
		addComponent( finishButton );
		
		this.setExpandRatio( glue1,  1f );
		glue2.setWidth( "2ex" );
	}
	
	private void initResources() {

		cancelButton.setCaption( "Cancel" );
		backButton.setCaption( "Prev" );
		nextButton.setCaption( "Next" ); 
		finishButton.setCaption( "Finish" );
		
	}

	private void setEnabled( boolean bCancel, boolean bBack, boolean bNext, boolean bFinish ) {

		cancelButton.setEnabled( bCancel );
		backButton.setEnabled( bBack );
		nextButton.setEnabled( bNext ); 
		finishButton.setEnabled( bFinish );
		
	}

	public void setEnabled( ButtonType type, boolean value ) {
		
		Button button = getButton( type );
		
		if ( button != null ) 
			button.setEnabled( value );
		else {
			cancelButton.setEnabled( false );
			backButton.setEnabled( false );
			nextButton.setEnabled( false ); 
			finishButton.setEnabled( false );
		}
	}

	public Button getButton( ButtonType type ) {
		
		switch ( type ) {
			case Cancel: {
				return cancelButton;
			}
			case Finish: {
				return finishButton;
			}
			case Next: {
				return nextButton;
			}
			case Back: {
				return backButton;
			}
		}
		
		logger.error( "WRONG Button Type!!!" );
		return null;
	}

	public void addClickListener( ButtonType type, ClickListener listener ) {
		
		if  ( listener != null )
			getButton( type ).addClickListener( listener );
		
	}

	@Override
	public void newPageAdded(WizardModel model, int index) {
		
	}

	@Override
	public void pageActivated(WizardModel model, int index) {

		logger.debug( "ButtonBar received PageActivated event" );
		if ( index == 0 ) {
			logger.debug( "  First page identified. Index/Size: " + index + "/" + model.size());
			setEnabled( true, false, true, false );
			
		} else if ( index == model.size() - 1 ) {
			logger.debug( "  Last page identified. Index/Size: " + index + "/" + model.size());
			// Last Wizard page
			setEnabled( true, true, false, true );
			
		} else {
			logger.debug( "  Middle page identified. Index/Size: " + index + "/" + model.size());

			// Other pages
			setEnabled( true, true, true, false );
			
		}
		
	}

	@Override
	public void pageDeactivated(WizardModel model, int index) {

		setEnabled( false, false, false, false );
		
	}

}
