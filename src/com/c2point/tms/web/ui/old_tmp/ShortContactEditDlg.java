package com.c2point.tms.web.ui.old_tmp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.c2point.tms.entity.taxreport.Contact;
import com.c2point.tms.util.CheckValueUtils;
import com.c2point.tms.web.ui.ButtonBar;
import com.c2point.tms.web.ui.ModType;
import com.c2point.tms.web.ui.taxreports.SitesModel;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class ShortContactEditDlg extends Window {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger( ShortContactEditDlg.class.getName());
	
	private Contact		contact;
	private SitesModel	model;
	private ModType		modType;

	private TextField 	firstNameField;
	private TextField 	lastNameField;
	private TextField 	phoneNumberField;
	
	
	
	public ShortContactEditDlg( Contact	contact, SitesModel model, ModType modType ) {

		if ( contact == null || model == null ) {
			throw new IllegalArgumentException( "Valid Contact and SitesModel shall be specified!" );
		}
		this.contact = contact;
		this.model = model;
		this.modType = modType;
		
		initUI();
		
		dataToView();

	}

	private void initUI() {

		setCaption();
		setModal( true );
		
//		VerticalLayout content = new VerticalLayout();
		FormLayout content = new FormLayout();
		content.setMargin( new MarginInfo( true, true, false, true ));
//		setMargin( true );
		content.setSpacing( true );
		content.setSizeUndefined();
		
		firstNameField = new TextField( "First Name:" );
		firstNameField.setWidth( "30em" );
		firstNameField.setInputPrompt( "Enter First Name ..." );
		firstNameField.setImmediate( true );
		
		lastNameField = new TextField( "Last Name:" );
		lastNameField.setWidth( "30em" );
		lastNameField.setInputPrompt( "Enter Last Name ..." );
		lastNameField.setImmediate( true );
		
		phoneNumberField = new TextField( "Phone number:" );
		phoneNumberField.setWidth( "30em" );
		phoneNumberField.setInputPrompt( "Enter the phone number ..." );
		phoneNumberField.setImmediate( true );

		ButtonBar btb = new ButtonBar();
		btb.setEnabled( ButtonBar.ButtonType.Ok, true );
		btb.addClickListener( ButtonBar.ButtonType.Ok, new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick( ClickEvent event ) {

				viewToData();
				save();
				close();
				
			}
			
		});
		
		btb.addClickListener( ButtonBar.ButtonType.Cancel, new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				close();
				
			}
			
		});
		
		content.addComponent( firstNameField );
		content.addComponent( lastNameField );
		content.addComponent( phoneNumberField );

		content.addComponent( btb );
		
		setContent( content );
		
	}

	private void setCaption() {

		switch ( modType ) {
			case New: {
				setCaption( "New Contact" );
				return;
			}
			case Edit: {
				setCaption( "Edit Contact" );
				return;
			}
			case View: {
				setCaption( "View Contact" );
				return;
			}
		}
	}
	
	private void dataToView() {

		firstNameField.setValue( CheckValueUtils.notNull( contact.getFirstName()));
		lastNameField.setValue( CheckValueUtils.notNull( contact.getLastName()));
		phoneNumberField.setValue( CheckValueUtils.notNull( contact.getPhoneNumber()));

	}
	
	private void viewToData() {

		if ( modType != ModType.View ) {		

			contact.setFirstName( firstNameField.getValue());
			contact.setLastName( lastNameField.getValue());
			contact.setPhoneNumber( phoneNumberField.getValue());
			
		}

	}
	
	private void save() {
/*		
		if ( modType == ModType.New ) {
			this.model.addSite( this.site );
		} else if ( modType == ModType.Edit ) {
			this.model.modifySite( this.site );
		}
*/		 
	}

	
}
