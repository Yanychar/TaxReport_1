package com.c2point.tms.web.ui.subcontract;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.c2point.tms.entity.taxreport.Address;
import com.c2point.tms.entity.taxreport.Contact;
import com.c2point.tms.entity.taxreport.Contractor;
import com.c2point.tms.entity.taxreport.IDType;
import com.c2point.tms.util.CheckValueUtils;
import com.c2point.tms.util.UIhelper;
import com.c2point.tms.web.ui.ButtonBar;
import com.c2point.tms.web.ui.listeners.ContractChangedListener;
import com.c2point.tms.web.ui.ModType;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class ContractorEditDlg extends Window {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger( ContractorEditDlg.class.getName());
	
	private Contractor				contractor;
	private ContractChangedListener	changedListener;
	private ModType					modType;

	private TextField 	nameField;
	private ComboBox	taxCountryCode;    	// Contractor country of tax residence. Mandatory
	
	private TextField 	tunnusField;		// Finnish tunnus or finnish Sotu 
	private TextField 	foreignIDField;		// Foreign business ID
	private OptionGroup	idTypeGroup;		// Mandatory if taxCountryCode is NOT Finland 

	private TextField 	firstNameField;
	private TextField 	lastNameField;
	private TextField 	phoneNumberField;
	
	private TextField 	addressField;
	private TextField 	indexField;
	private TextField 	cityField;
	private ComboBox	countryField;
	
	
	
	public ContractorEditDlg( Contractor contractor, ContractChangedListener changedListener, ModType modType ) {

		if ( contractor == null  ) {
			throw new IllegalArgumentException( "Valid Contractor and shall be specified!" );
		}
		this.contractor = contractor;
		this.changedListener = changedListener;
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
		
	
		nameField = new TextField( "Contractor:" );
		nameField.setWidth( "40em" );
		nameField.setInputPrompt( "Enter Company Name ..." );
		nameField.setImmediate( true );
		
		taxCountryCode = new ComboBox( "Tax Residence:" );
		taxCountryCode.setInputPrompt( "Select country of tax residence ..." );
		taxCountryCode.setFilteringMode( FilteringMode.CONTAINS );
		taxCountryCode.setNullSelectionAllowed( true );
		taxCountryCode.setWidth( "20em" );
		taxCountryCode.setImmediate( true );
		taxCountryCode.addValueChangeListener( new ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				if ( logger.isDebugEnabled()) logger.debug( "TaxCountry combo fired ValueChange event. Received." );
				
				updateUI();
			}
			
		});
		
		tunnusField = new TextField( "Finnish ID:");
		tunnusField.setWidth( "10em" );
		tunnusField.setInputPrompt( "Enter Business ID ..." );
		tunnusField.setImmediate( true );
		
		foreignIDField = new TextField( "Foreign ID :");
		foreignIDField.setWidth( "10em" );
		foreignIDField.setInputPrompt( "Enter foreign ID ..." );
		foreignIDField.setImmediate( true );
		
		idTypeGroup = new OptionGroup( "ID Type: ");
		idTypeGroup.setStyleName( "horizontal" );
		idTypeGroup.setImmediate( true );

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
		
		addressField = new TextField( "Street:" );
		addressField.setWidth( "40em" );
		addressField.setInputPrompt( "Enter Contractor Street address (street name, houes#, entrance, apartment, etc.)" );
		addressField.setImmediate( true );

		indexField = new TextField( "Postal Code:" );
		indexField.setWidth( "10em" );
		indexField.setInputPrompt( "Enter Contractor postal code ..." );
		indexField.setImmediate( true );
		
		cityField = new TextField( "City:" );
		cityField.setWidth( "20em" );
		cityField.setInputPrompt( "Enter post office name ..." );
		cityField.setImmediate( true );
		
		countryField = new ComboBox( "Country:" );
		countryField.setInputPrompt( "Select Contractor country ..." );
		countryField.setFilteringMode( FilteringMode.CONTAINS );
		countryField.setNullSelectionAllowed( true );
		countryField.setWidth( "20em" );
		countryField.setImmediate( true );
		UIhelper.fillCountryCombo( countryField );

		ButtonBar btb = ButtonBar.getOkCancelBar();
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

		Label separator1 = new Label( "<hr/>", ContentMode.HTML );
		separator1.setWidth( "100%" );
		Label separator2 = new Label( "<hr/>", ContentMode.HTML );
		separator2.setWidth( "100%" );
		Label separator3 = new Label( "<hr/>", ContentMode.HTML );
		separator3.setWidth( "100%" );
		
		content.addComponent( nameField );
		content.addComponent( taxCountryCode );
		content.addComponent( separator1 );
		content.addComponent( tunnusField );
		content.addComponent( foreignIDField );
		content.addComponent( idTypeGroup );
		content.addComponent( separator2 );
		
		Label contactLabel = new Label();
		contactLabel.setContentMode( ContentMode.HTML );
		contactLabel.addStyleName( "h2" );
		contactLabel.setValue( "Contact Person" );
		content.addComponent( contactLabel );
		
		content.addComponent( firstNameField );
		content.addComponent( lastNameField );
		content.addComponent( phoneNumberField );
		content.addComponent( separator3 );
		content.addComponent( addressField );
		content.addComponent( indexField );
		content.addComponent( cityField );
		content.addComponent( countryField );

		content.addComponent( btb );
		
		setContent( content );
		
	}

	private void setCaption() {

		switch ( modType ) {
			case New: {
				setCaption( "New Contractor" );
				return;
			}
			case Edit: {
				setCaption( "Edit Contractor" );
				return;
			}
			case View: {
				setCaption( "View Contractor" );
				return;
			}
		}
	}
	
	private void dataToView() {

		nameField.setValue( CheckValueUtils.notNull( contractor.getName()));

		UIhelper.fillCountryCombo( taxCountryCode );
		if ( this.contractor.getCountryCode() != null ) {
			
			taxCountryCode.setValue( this.contractor.getCountryCode());
		} else {

			taxCountryCode.setValue( "FI" );
			
		}
		tunnusField.setValue( CheckValueUtils.notNull( contractor.getFinnishBusinessID()));
		foreignIDField.setValue( CheckValueUtils.notNull( contractor.getForeignBusinessID()));

		UIhelper.fillForeignIDTypeGroup( idTypeGroup );
		idTypeGroup.setValue( contractor.getIdType()); 

		if ( this.contractor.getContact() != null ) {
			
			firstNameField.setValue( CheckValueUtils.notNull( contractor.getContact().getFirstName()));
			lastNameField.setValue( CheckValueUtils.notNull( contractor.getContact().getLastName()));
			phoneNumberField.setValue( CheckValueUtils.notNull( contractor.getContact().getPhoneNumber()));

			if ( this.contractor.getContact().getAddress() != null ) {

				addressField.setValue( CheckValueUtils.notNull( contractor.getContact().getAddress().getStreet()));
				indexField.setValue( CheckValueUtils.notNull( contractor.getContact().getAddress().getIndex()));
				cityField.setValue( CheckValueUtils.notNull( contractor.getContact().getAddress().getCity()));

				UIhelper.fillCountryCombo( countryField );
				if ( this.contractor.getContact().getAddress().getCountryCode() != null ) {
					
					countryField.setValue( this.contractor.getContact().getAddress().getCountryCode());
				}				
			}
		}	
		
		if ( countryField.getValue() == null ) {

			countryField.setValue( "FI" );
			
		}

	}
	
	private void viewToData() {

		if ( modType != ModType.View ) {		
			
			this.contractor.setName( nameField.getValue());

			this.contractor.setCountryCode(( String )taxCountryCode.getValue());
			contractor.setFinnishBusinessID( tunnusField.getValue());
			contractor.setForeignBusinessID( foreignIDField.getValue());

			UIhelper.fillForeignIDTypeGroup( idTypeGroup );
			contractor.setIdType(( IDType )idTypeGroup.getValue()); 

			
			
			if ( this.contractor.getContact() == null 
				&& (
					firstNameField.getValue() != null 
				||	lastNameField.getValue() != null
				||	phoneNumberField.getValue() != null
				|| 	addressField.getValue() != null
				||	indexField.getValue() != null
				||	cityField.getValue() != null
				|| 	countryField.getValue() != null )) {
				
				this.contractor.setContact( new Contact( new Address()));
				
				
			}
					
			
			this.contractor.getContact().setFirstName( firstNameField.getValue());
			this.contractor.getContact().setLastName( lastNameField.getValue());
			this.contractor.getContact().setPhoneNumber( phoneNumberField.getValue());
			
			this.contractor.getContact().getAddress().setStreet( addressField.getValue());
			this.contractor.getContact().getAddress().setIndex( indexField.getValue());
			this.contractor.getContact().getAddress().setCity( cityField.getValue());
			this.contractor.getContact().getAddress().setCountryCode(( String )countryField.getValue());
			
		}
	}
	
	private void save() {
		
		if ( changedListener != null ) {
			if ( modType == ModType.New ) {
				this.changedListener.added();
			} else if ( modType == ModType.Edit ) {
				this.changedListener.edited();
			}
		}
	}

	private void updateUI() {

		boolean flag = ( taxCountryCode.getValue() != null &&
				(( String )taxCountryCode.getValue()).compareToIgnoreCase( "FI" ) == 0 );
		
		tunnusField.setEnabled( flag ); 
		foreignIDField.setEnabled( !flag );
		idTypeGroup.setEnabled( !flag );
			
	}
}
