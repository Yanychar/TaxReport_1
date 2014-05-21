package com.c2point.tms.web.ui.subcontract;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;

import com.c2point.tms.application.Taxreport_1UI;
import com.c2point.tms.entity.taxreport.Address;
import com.c2point.tms.entity.taxreport.Employee;
import com.c2point.tms.entity.taxreport.EmployeeCertificateType;
import com.c2point.tms.entity.taxreport.EmploymentContract;
import com.c2point.tms.entity.taxreport.EmploymentContractType;
import com.c2point.tms.util.CheckValueUtils;
import com.c2point.tms.util.UIhelper;
import com.c2point.tms.web.ui.ButtonBar;
import com.c2point.tms.web.ui.ModType;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class EmployeeEditDlg extends Window {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger( EmployeeEditDlg.class.getName());
	
	private ModType			modType;
	private EmployeesModel	model;
	private Employee		employee;

	private TextField 	firstNameField;
	private TextField 	lastNameField;
	private DateField 	birthField;

	private OptionGroup	certificateField;
	private ComboBox	typeField;
	private DateField	startField;
	private DateField	endField;
	private TextField	daysField;
	private TextField	hoursField;
	
	private TextField 	tunnusField;
	private TextField 	taxField;
	
	private ComboBox	taxCountryCode;

	private Label		taxAddressHeader;
	private TextField 	taxAddressField;
	private TextField 	taxIndexField;
	private TextField 	taxCityField;
	
	private Label		finAddressHeader;
	private TextField 	finAddressField;
	private TextField 	finIndexField;
	private TextField 	finCityField;
	
	
	public EmployeeEditDlg( Employee employee, EmployeesModel model, ModType modType ) {

		if ( employee == null ) {
			throw new IllegalArgumentException( "Valid Employee shall be specified!" );
		}
		
		this.employee = employee;
		this.model = model;
		this.modType = modType;
		
		initUI();
		
		dataToView();

	}

	private void initUI() {

		setCaption();
		setModal( true );
		
		firstNameField = new TextField( "First Name:" );
		firstNameField.setWidth( "20em" );
		firstNameField.setInputPrompt( "Enter First Name ..." );
		firstNameField.setImmediate( true );
		
		lastNameField = new TextField( "Last Name:" );
		lastNameField.setWidth( "20em" );
		lastNameField.setInputPrompt( "Enter Last Name ..." );
		lastNameField.setImmediate( true );
		
		birthField = new DateField( "Date of Birth: " );
		birthField.setLocale( (( Taxreport_1UI )UI.getCurrent()).getSessionData().getLocale());
		birthField.setDateFormat( "dd.MM.yyyy" );
		birthField.setResolution( Resolution.DAY );
		birthField.setImmediate(true);

		certificateField = new OptionGroup( "Posted Employee certificate:" );
		certificateField.setStyleName( "horizontal" );
		certificateField.setImmediate( true );

		
		typeField = new ComboBox( "Contract Type:" );
		typeField.setInputPrompt( "Select contract type ..." );
		typeField.setFilteringMode( FilteringMode.CONTAINS );
		typeField.setNullSelectionAllowed( true );
		typeField.setWidth( "20em" );
		typeField.setImmediate( true );
		typeField.addValueChangeListener( new ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				if ( logger.isDebugEnabled()) logger.debug( "Contract Type combo fired ValueChange event. Received." );
				
//				updateUI();
			}
			
		});
		
		
		startField = new DateField( "Start date on Site: " );
		startField.setLocale( (( Taxreport_1UI )UI.getCurrent()).getSessionData().getLocale());
		startField.setDateFormat( "dd.MM.yyyy" );
		startField.setResolution( Resolution.DAY );
		startField.setImmediate(true);
		
		endField = new DateField( "Estimated end date on Site: " );
		endField.setLocale( (( Taxreport_1UI )UI.getCurrent()).getSessionData().getLocale());
		endField.setDateFormat( "dd.MM.yyyy" );
		endField.setResolution( Resolution.DAY );
		endField.setImmediate(true);
		
		daysField = new TextField( "Quantity of days:" );
		daysField.setWidth( "10em" );
		daysField.setInputPrompt( "Enter number of days ..." );
		daysField.setNullSettingAllowed( true );
		daysField.setNullRepresentation( "0" );
		daysField.setConverter( Integer.class );
		daysField.addValidator( new IntegerRangeValidator( "Wrong number of days!", 0, 31 ));
		daysField.setImmediate( true );
		
		hoursField = new TextField( "Quantity of hours:" );
		hoursField.setWidth( "10em" );
		hoursField.setInputPrompt( "Enter number of hours ..." );
		hoursField.setNullSettingAllowed( true );
		hoursField.setNullRepresentation( "0" );
		hoursField.setConverter( Integer.class );
		hoursField.addValidator( new IntegerRangeValidator( "Wrong number of hours!", 0, 300 ));
		hoursField.setImmediate( true );
		
		tunnusField = new TextField( "Personal ID:");
		tunnusField.setWidth( "15em" );
		tunnusField.setInputPrompt( "Enter Finnish Tunnus ..." );
		tunnusField.setImmediate( true );

		taxField = new TextField( "Tax ID:");
		taxField.setWidth( "15em" );
		taxField.setInputPrompt( "Enter Tax Number ..." );
		taxField.setImmediate( true );
		
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

		taxAddressField = new TextField( "Street:" );
		taxAddressField.setWidth( "40em" );
		taxAddressField.setInputPrompt( "Enter street address (street name, houes#, entrance, apartment, etc.)" );
		taxAddressField.setNullRepresentation( "" );
		taxAddressField.setImmediate( true );

		taxIndexField = new TextField( "Postal Code:" );
		taxIndexField.setWidth( "10em" );
		taxIndexField.setInputPrompt( "Enter postal code ..." );
		taxIndexField.setNullRepresentation( "" );
		taxIndexField.setImmediate( true );
		
		taxCityField = new TextField( "City:" );
		taxCityField.setWidth( "20em" );
		taxCityField.setInputPrompt( "Enter post office name ..." );
		taxCityField.setNullRepresentation( "" );
		taxCityField.setImmediate( true );
		
		
		finAddressField = new TextField( "Street:" );
		finAddressField.setWidth( "40em" );
		finAddressField.setInputPrompt( "Enter street address (street name, houes#, entrance, apartment, etc.)" );
		finAddressField.setNullRepresentation( "" );
		finAddressField.setImmediate( true );

		finIndexField = new TextField( "Postal Code:" );
		finIndexField.setWidth( "10em" );
		finIndexField.setInputPrompt( "Enter postal code ..." );
		finIndexField.setNullRepresentation( "" );
		finIndexField.setImmediate( true );
		
		finCityField = new TextField( "City:" );
		finCityField.setWidth( "20em" );
		finCityField.setInputPrompt( "Enter post office name ..." );
		finCityField.setNullRepresentation( "" );
		finCityField.setImmediate( true );

		taxAddressHeader = new Label( "<b><u>Address in the tax residence country:</u></b>", ContentMode.HTML );
		finAddressHeader = new Label( "<b><u>Address in Finland:</u></b>", ContentMode.HTML );
		
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

		FormLayout leftForm = new FormLayout();
		leftForm.setMargin( new MarginInfo( true, true, false, true ));
//		setMargin( true );
//		content.setSpacing( true );
		leftForm.setSizeUndefined();
		
		leftForm.addComponent( firstNameField );
		leftForm.addComponent( lastNameField );
		leftForm.addComponent( birthField );

		leftForm.addComponent( new Label( "<b><u>Contract Details:</u></b>", ContentMode.HTML ));

		leftForm.addComponent( certificateField );
		leftForm.addComponent( typeField );
		leftForm.addComponent( startField );
		leftForm.addComponent( endField );
		leftForm.addComponent( daysField );
		leftForm.addComponent( hoursField );
		
		
		FormLayout rightForm = new FormLayout();
		rightForm.setMargin( new MarginInfo( true, true, false, true ));
		rightForm.setSizeUndefined();
		
		rightForm.addComponent( tunnusField );
		rightForm.addComponent( taxField );
		rightForm.addComponent( new Label( "<hr/>", ContentMode.HTML ));
		rightForm.addComponent( taxCountryCode );
		rightForm.addComponent( taxAddressHeader );
		rightForm.addComponent( taxAddressField );
		rightForm.addComponent( taxIndexField );
		rightForm.addComponent( taxCityField );
		rightForm.addComponent( finAddressHeader );
		rightForm.addComponent( finAddressField );
		rightForm.addComponent( finIndexField );
		rightForm.addComponent( finCityField );

		HorizontalLayout hzLayout = new HorizontalLayout();
		hzLayout.setSizeFull();
		
		hzLayout.addComponent( leftForm );
		hzLayout.addComponent( rightForm );
		
		
		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		
		content.addComponent( hzLayout );
		content.addComponent( btb );
		
		content.setMargin( new MarginInfo( true, true, false, true ));
//		setMargin( true );
//		content.setSpacing( true );
		content.setSizeUndefined();

		
		setContent( content );
		
	}

	private void setCaption() {

		switch ( modType ) {
			case New: {
				setCaption( "New Employee" );
				return;
			}
			case Edit: {
				setCaption( "Edit Employee" );
				return;
			}
			case View: {
				setCaption( "View Employee" );
				return;
			}
		}
	}
	
	private void dataToView() {

		firstNameField.setValue( CheckValueUtils.notNull( this.employee.getFirstName()));
		lastNameField.setValue( CheckValueUtils.notNull( this.employee.getLastName()));
		birthField.setValue( this.employee.getBirthDate() != null ? this.employee.getBirthDate().toDate() : null );

		certificateField.addItem( EmployeeCertificateType.Yes );
		certificateField.addItem( EmployeeCertificateType.No );
		certificateField.setItemCaption( EmployeeCertificateType.Yes, "Yes" );
		certificateField.setItemCaption( EmployeeCertificateType.No, "No" );

		UIhelper.fillEmploymentContractType( typeField );
		
		if ( this.employee.getContract() != null ) {

			certificateField.setValue( this.employee.getContract().getCertificate());
			
			typeField.setValue( this.employee.getContract().getType());
			
			startField.setValue( 
					this.employee.getContract().getStartDate() != null 
						? this.employee.getContract().getStartDate().toDate() 
						: null 
			);
			endField.setValue( 
					this.employee.getContract().getEndDate() != null 
						? this.employee.getContract().getEndDate().toDate() 
						: null 
			);
			
			daysField.setValue( 
					this.employee.getContract().getDays() != null 
						? Integer.toString( this.employee.getContract().getDays())
						: null
			);
			hoursField.setValue( this.employee.getContract().getHours() != null
					? Integer.toString( this.employee.getContract().getHours())
					: null
		);
		} else {

			certificateField.setValue( EmployeeCertificateType.Yes );
			typeField.setValue( EmploymentContractType.Direct );
			startField.setValue( null );
			endField.setValue( null );
			daysField.setValue( null );
			hoursField.setValue( null );

			
		}
		
		tunnusField.setValue( CheckValueUtils.notNull( this.employee.getFinnishTunnus()));
		taxField.setValue( CheckValueUtils.notNull( this.employee.getTaxNumber()));

		UIhelper.fillCountryCombo( taxCountryCode );
		if ( this.employee.getTaxAddress() != null ) {
			
			taxCountryCode.setValue( this.employee.getTaxAddress().getCountryCode());

			taxAddressField.setValue( this.employee.getTaxAddress().getStreet());
			taxIndexField.setValue( this.employee.getTaxAddress().getIndex());
			taxCityField.setValue( this.employee.getTaxAddress().getCity());
			
		} else {

			taxCountryCode.setValue( "FI" );

			taxAddressField.setValue( null );
			taxIndexField.setValue( null );
			taxCityField.setValue( null );
			
		}

		if ( this.employee.getFinAddress() != null ) {

			finAddressField.setValue( this.employee.getFinAddress().getStreet());
			finIndexField.setValue( this.employee.getFinAddress().getIndex());
			finCityField.setValue( this.employee.getFinAddress().getCity());
			
		} else {

			finAddressField.setValue( null );
			finIndexField.setValue( null );
			finCityField.setValue( null );
			
		}

	}
	
	private void viewToData() {

		if ( modType != ModType.View ) {		

			this.employee.setFirstName( firstNameField.getValue());
			this.employee.setLastName( lastNameField.getValue());
			this.employee.setBirthDate( birthField.getValue());

			this.employee.setFinnishTunnus( tunnusField.getValue());
			this.employee.setTaxNumber( taxField.getValue());
			
			if ( this.employee.getContract() == null ) {

				this.employee.setContract( new EmploymentContract());
				
			}

				
			this.employee.getContract().setCertificate(( EmployeeCertificateType )certificateField.getValue());
			
			this.employee.getContract().setType(( EmploymentContractType )typeField.getValue());
			
			this.employee.getContract().setStartDate( 
					startField.getValue() != null ? LocalDate.fromDateFields( startField.getValue()) : null );
			this.employee.getContract().setEndDate( 
					endField.getValue() != null ? LocalDate.fromDateFields( endField.getValue()) : null );
			
			try {
				this.employee.getContract().setDays( Integer.valueOf( daysField.getValue()));
			} catch ( Exception e ) {
				logger.error( "Wrong value '" + daysField.getValue() + "' to set as number of Days" );
				this.employee.getContract().setDays( null );
			}
			try {
				this.employee.getContract().setHours( Integer.valueOf( hoursField.getValue()));
			} catch ( Exception e ) {
				logger.error( "Wrong value '" + daysField.getValue() + "' to set as number of Hours" );
				this.employee.getContract().setHours( null );
			}

			if ( this.employee.getTaxAddress() == null ) {

				this.employee.setTaxAddress( new Address());
				
			}
			
			if ( this.employee.getFinAddress() == null ) {

				this.employee.setFinAddress( new Address());
				
			}
			
			this.employee.getTaxAddress().setStreet( taxAddressField.getValue());
			this.employee.getTaxAddress().setIndex( taxIndexField.getValue());
			this.employee.getTaxAddress().setCity( taxCityField.getValue());
			this.employee.getTaxAddress().setCountryCode(( String )taxCountryCode.getValue());
			
			this.employee.getFinAddress().setStreet( finAddressField.getValue());
			this.employee.getFinAddress().setIndex( finIndexField.getValue());
			this.employee.getFinAddress().setCity( finCityField.getValue());

			
			
		}
	}
	
	private void save() {
		
		if ( modType == ModType.New ) {
			this.model.addEmployee( this.employee );
		} else if ( modType == ModType.Edit ) {
			this.model.modifyEmployee( this.employee );
		}
		 
	}

	private void updateUI() {

		boolean flag = ( taxCountryCode.getValue() != null &&
				(( String )taxCountryCode.getValue()).compareToIgnoreCase( "FI" ) == 0 );
		
		taxAddressField.setEnabled( ! flag );
		taxIndexField.setEnabled( ! flag );
		taxCityField.setEnabled( ! flag );

		finAddressField.setEnabled( ! flag );
		finIndexField.setEnabled( ! flag );
		finCityField.setEnabled( ! flag );

		taxAddressHeader.setEnabled( ! flag );
		finAddressHeader.setEnabled( ! flag );
	}
	
}
