package com.c2point.tms.web.ui.taxreports;

import java.util.Locale;

import com.c2point.tms.application.Taxreport_1UI;
import com.c2point.tms.entity.taxreport.Address;
import com.c2point.tms.entity.taxreport.Contact;
import com.c2point.tms.entity.taxreport.Site;
import com.c2point.tms.util.CheckValueUtils;
import com.c2point.tms.util.UIhelper;
import com.c2point.tms.web.ui.ButtonBar;
import com.c2point.tms.web.ui.ModType;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class SiteEditDlg extends Window {

	private static final long serialVersionUID = 1L;

	private Site		site;
	private SitesModel	model;
	private ModType		modType;

	private TextField 	nameField;
	private TextField 	veroIdField;
	private TextField 	siteNumField;
	private TextField 	siteAddressField;
	private TextField 	siteIndexField;
	private TextField 	siteCityField;
	private TextArea 	siteDescrField;

	private TextField 	contactFirstNameField;
	private TextField 	contactLastNameField;
	private TextField 	contactPhoneNumberField;
	private TextField 	contactEmailField;
	private TextField 	contactAddressField;
	private TextField 	contactIndexField;
	private TextField 	contactCityField;
	private ComboBox 	contactCountryField;
	
	
	
	public SiteEditDlg( Site site, SitesModel model, ModType modType ) {

		if ( site == null || model == null ) {
			throw new IllegalArgumentException( "Valid Site and SiteModel shall be specified!" );
		}
		this.site = site;
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
		
	
		nameField = new TextField( "Site Name:" );
		nameField.setWidth( "40em" );
		nameField.setInputPrompt( "Enter Site Name ..." );
		nameField.setImmediate( true );
		
		veroIdField = new TextField( "Site ID:");
		veroIdField.setWidth( "10em" );
		veroIdField.setInputPrompt( "Enter ID code ..." );
		veroIdField.setImmediate( true );
		
		siteNumField = new TextField( "Site Number:" );
		siteNumField.setWidth( "10em" );
		siteNumField.setInputPrompt( "Enter Site Code ..." );
		siteNumField.setImmediate( true );
		
		siteAddressField = new TextField( "Street:" );
		siteAddressField.setWidth( "40em" );
		siteAddressField.setInputPrompt( "Enter Site Street address (street name, houes#, entrance, apartment, etc.)" );
		siteAddressField.setImmediate( true );

		siteIndexField = new TextField( "Postal Code:" );
		siteIndexField.setWidth( "10em" );
		siteIndexField.setInputPrompt( "Enter Site postal code ..." );
		siteIndexField.setImmediate( true );
		
		siteCityField = new TextField( "City:" );
		siteCityField.setWidth( "20em" );
		siteCityField.setInputPrompt( "Enter post office name ..." );
		siteCityField.setImmediate( true );
		
		siteDescrField = new TextArea( "Description:" );
		siteDescrField.setWidth( "40em" );
		siteDescrField.setInputPrompt( "Enter Site free text description ..." );
		siteDescrField.setRows( 2 );
		siteDescrField.setImmediate( true );
		
		contactFirstNameField = new TextField( "First Name:" );
		contactFirstNameField.setWidth( "30em" );
		contactFirstNameField.setInputPrompt( "Enter First Name ..." );
		contactFirstNameField.setImmediate( true );
		
		contactLastNameField = new TextField( "Last Name:" );
		contactLastNameField.setWidth( "30em" );
		contactLastNameField.setInputPrompt( "Enter Last Name ..." );
		contactLastNameField.setImmediate( true );
		
		contactPhoneNumberField = new TextField( "Phone number:" );
		contactPhoneNumberField.setWidth( "30em" );
		contactPhoneNumberField.setInputPrompt( "Enter the phone number ..." );
		contactPhoneNumberField.setImmediate( true );

		contactEmailField = new TextField( "Email:" );
		contactEmailField.setWidth( "40em" );
		contactEmailField.setInputPrompt( "Enter the email ..." );
		contactEmailField.setImmediate( true );
		
		contactAddressField = new TextField( "Street:" );
		contactAddressField.setWidth( "40em" );
		contactAddressField.setInputPrompt( "Enter street address (street name, houes#, entrance, apartment, etc)." );
		contactAddressField.setImmediate( true );

		contactIndexField = new TextField( "Postal Code:" );
		contactIndexField.setWidth( "10em" );
		contactIndexField.setInputPrompt( "Enter postal code ..." );
		contactIndexField.setImmediate( true );
		
		contactCityField = new TextField( "City:" );
		contactCityField.setWidth( "20em" );
		contactCityField.setInputPrompt( "Enter post office name ..." );
		contactCityField.setImmediate( true );

		contactCountryField = new ComboBox( "Country:" );
		contactCountryField.setInputPrompt( "Select country ..." );
		contactCountryField.setFilteringMode( FilteringMode.CONTAINS );
		contactCountryField.setNullSelectionAllowed( true );
		contactCountryField.setWidth( "20em" );
		contactCountryField.setImmediate( true );
		UIhelper.fillCountryCombo( contactCountryField );
		
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
		
		content.addComponent( nameField );
		content.addComponent( veroIdField );
		content.addComponent( siteNumField );
		
		content.addComponent( new Label( "" ));
		
		content.addComponent( siteAddressField );
		content.addComponent( siteIndexField );
		content.addComponent( siteCityField );
		content.addComponent( siteDescrField );

		content.addComponent( new Label( "<hr/>", ContentMode.HTML ));
		content.addComponent( new Label( "<b><u>Site Contact Person</u></b>", ContentMode.HTML ));
		
		content.addComponent( contactFirstNameField );
		content.addComponent( contactLastNameField );
		
		content.addComponent( contactPhoneNumberField );
		content.addComponent( contactEmailField );
		
		content.addComponent( contactAddressField );
		content.addComponent( contactIndexField );
		content.addComponent( contactCityField );
		content.addComponent( contactCountryField );

		
		content.addComponent( btb );
		
		setContent( content );
		
	}

	private void setCaption() {

		switch ( modType ) {
			case New: {
				setCaption( "New Site" );
				return;
			}
			case Edit: {
				setCaption( "Edit Site" );
				return;
			}
			case View: {
				setCaption( "View Site" );
				return;
			}
		}
	}
	
	private void dataToView() {

		nameField.setValue( CheckValueUtils.notNull( site.getName()));
		veroIdField.setValue( CheckValueUtils.notNull( site.getSiteVeroID()));
		siteNumField.setValue( CheckValueUtils.notNull( site.getSiteNumber()));
		
		if ( site.getAddress() != null ) {

			siteAddressField.setValue( CheckValueUtils.notNull( site.getAddress().getStreet()));
			siteIndexField.setValue( CheckValueUtils.notNull( site.getAddress().getIndex()));
			siteCityField.setValue( CheckValueUtils.notNull( site.getAddress().getCity()));
			siteDescrField.setValue( CheckValueUtils.notNull( site.getAddress().getDescription()));
			
		}

		if ( site.getContact() != null ) {

			contactFirstNameField.setValue( CheckValueUtils.notNull( site.getContact().getFirstName()));
			contactLastNameField.setValue( CheckValueUtils.notNull( site.getContact().getLastName()));
			contactPhoneNumberField.setValue( CheckValueUtils.notNull( site.getContact().getPhoneNumber()));
			contactEmailField.setValue( CheckValueUtils.notNull( site.getContact().getEmail()));

			String countryId = null;
			if ( site.getContact().getAddress() != null ) {
				contactAddressField.setValue( CheckValueUtils.notNull( site.getContact().getAddress().getStreet()));
				contactIndexField.setValue( CheckValueUtils.notNull( site.getContact().getAddress().getIndex()));
				contactCityField.setValue( CheckValueUtils.notNull( site.getContact().getAddress().getCity()));

				// Select default Country: from User Locale or Finland
				countryId = site.getContact().getAddress().getCountryCode();
				
			}
			
			if ( countryId == null ) {
				Locale obj = (( Taxreport_1UI )UI.getCurrent()).getSessionData().getLocale();
				if ( obj != null && obj.getCountry() != null ) {
					countryId = obj.getCountry();
				} else {
					countryId = "FI";
				}
				
			}
			
//			contactCountryField.select( countryId );
			contactCountryField.setValue( countryId );
			
		}
		
	}
	
	private void viewToData() {

		if ( modType != ModType.View ) {		

			site.setName( nameField.getValue());
			site.setSiteVeroID( veroIdField.getValue());
			site.setSiteNumber( siteNumField.getValue());
		
			if ( site.getAddress() == null ) {
				site.setAddress( new Address());
			}
		
			site.getAddress().setStreet( siteAddressField.getValue());
			site.getAddress().setIndex( siteIndexField.getValue());
			site.getAddress().setCity( siteCityField.getValue());
			site.getAddress().setDescription( siteDescrField.getValue());

			
			if ( site.getContact() == null ) {

				site.setContact( new Contact( new Address()));
				
			}
				
			if ( site.getContact().getAddress() != null ) {
				
				site.getContact().setAddress( new Address());
				
			}
			
			site.getContact().setFirstName( contactFirstNameField.getValue());
			site.getContact().setLastName( contactLastNameField.getValue());
			site.getContact().setPhoneNumber( contactPhoneNumberField.getValue());
			site.getContact().setEmail( contactEmailField.getValue());

			site.getContact().getAddress().setStreet( contactAddressField.getValue());
			site.getContact().getAddress().setIndex( contactIndexField.getValue());
			site.getContact().getAddress().setCity( contactCityField.getValue());
			site.getContact().getAddress().setCountryCode(( String )contactCountryField.getValue());
			
			
			
			
		}
	}
	
	private void save() {
		
		if ( modType == ModType.New ) {
			this.model.addSite( this.site );
		} else if ( modType == ModType.Edit ) {
			this.model.modifySite( this.site );
		}
		 
	}

	
}
