package com.c2point.tms.web.ui.subcontract;

import java.util.Collection;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.format.DateTimeFormat;

import com.c2point.tms.entity.taxreport.Contact;
import com.c2point.tms.entity.taxreport.Contract;
import com.c2point.tms.entity.taxreport.Contractor;
import com.c2point.tms.entity.taxreport.Employee;
import com.c2point.tms.web.ui.ButtonBar;
import com.c2point.tms.web.ui.listeners.ContractChangedListener;
import com.c2point.tms.web.ui.ModType;
import com.c2point.tms.web.ui.taxreports.SitesModel;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ContractViewDlg extends Window implements ContractChangedListener {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger( ContractViewDlg.class.getName());
	
	private Contract	contract;
	private SitesModel	model;
	private ModType		modType;
	
	private Label 		companyLabel;
	private Button 		companyEditButton;
	private Label 		companyInfoLabel;

	private Label 		contactLabel;
	private Label 		contactInfoLabel;

	private Label 		contractLabel;
	private Button 		contractEditButton;
	private Label 		contractInfoLabel;

	private Label 		employeesLabel;
	private Button 		employeesEditButton;
	private Label 		employeesInfoLabel;
	private Table 		employeesInfoTable;

	private boolean 	modifiedEntityFlag = false;
	
	
	public ContractViewDlg( Contract contract, SitesModel model, ModType modType ) {

		if ( contract == null || model == null ) {
			throw new IllegalArgumentException( "Valid Contract and SiteModel shall be specified!" );
		}
		
		this.contract = contract;
		this.model = model;
		this.modType = modType;
		
		initUI();
		
		dataToView();

		
	}

	public ContractViewDlg( SitesModel model, ModType modType ) {
		this( null, model, modType );
		
		this.contract = this.model.getContractorsModel().getSelectedContract();
		
	}
	
/*
 * 

	companyLabel;
	companyEditButton;
	companyInfoLabel;

	contactLabel;
	contactEditButton;
	contactInfoLabel;

	contractLabel;
	contractEditButton;
	contractInfoLabel;

	employeesLabel;
	employeesEditButton;
	employeesInfoLabel;
	employeesInfoTable;

 
 * 	
 */
	
	
	private void initUI() {

		setCaption();
//		setModal( true );
	
		
		VerticalLayout content = new VerticalLayout();
		content.setMargin( new MarginInfo( true, true, false, true ));
//		content.setSpacing( true );
		content.setSizeUndefined();
		
		
		Label separator1 = new Label( "<hr/>", ContentMode.HTML );
		separator1.setWidth( "100%" );
		Label separator2 = new Label( "<hr/>", ContentMode.HTML );
		separator2.setWidth( "100%" );

		
		ButtonBar btb = ButtonBar.getCloseBar();
		btb.addClickListener( ButtonBar.ButtonType.Close, new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick( ClickEvent event ) {

				viewToData();
				save();
				close();
				
			}
			
		});
		
		
		content.addComponent( initContractorPart());
		content.addComponent( initContactPart());
		content.addComponent( separator1 );
		content.addComponent( initContractPart());
		content.addComponent( separator2 );
		content.addComponent( initPeoplePart());
		content.addComponent( btb );
		
		this.setContent( content );
	}

	private Component initContractorPart() {

		HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin( true );
		layout.setSpacing( true );
		
		companyLabel = new Label();
		companyLabel.setContentMode( ContentMode.HTML );
		companyLabel.addStyleName( "h1" );
		
		companyEditButton = new Button();
		companyEditButton.addClickListener( new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick( ClickEvent event ) {

				ModType modType = ModType.Edit; 
				if ( ContractViewDlg.this.contract.getContractor() == null ) {
					
					ContractViewDlg.this.contract.setContractor( new Contractor());
					modType = ModType.New; 
					
				}
				
				
				ContractorEditDlg dlg = 
						new ContractorEditDlg( ContractViewDlg.this.contract.getContractor(), ContractViewDlg.this, modType );
        		
        		UI.getCurrent().addWindow( dlg );
        		dlg.center();
				
			}
			
		});
		
		companyInfoLabel = new Label();
		companyInfoLabel.setContentMode( ContentMode.HTML );
		
		VerticalLayout companyVtLayout = new VerticalLayout(); 
		
		companyVtLayout.addComponent( companyLabel );
		companyVtLayout.addComponent( companyInfoLabel );
		
		layout.addComponent( companyVtLayout );
		layout.addComponent( companyEditButton );
		
		return layout;
	}
	
	private Component initContactPart() {

		HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin( true );
		layout.setSpacing( true );
		
		contactLabel = new Label();
		contactLabel.setContentMode( ContentMode.HTML );
		contactLabel.addStyleName( "h2" );
		
		contactInfoLabel = new Label();
		contactInfoLabel.setContentMode( ContentMode.HTML );
		
		VerticalLayout vtLayout = new VerticalLayout(); 
		
		vtLayout.addComponent( contactLabel );
		vtLayout.addComponent( contactInfoLabel );
		
		layout.addComponent( vtLayout );
		
		return layout;
	}
	
	private Component initContractPart() {

		HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin( true );
		layout.setSpacing( true );
		
		contractLabel = new Label();
		contractLabel.setContentMode( ContentMode.HTML );
		contractLabel.addStyleName( "h2" );
		
		contractEditButton = new Button();
		contractEditButton.addClickListener( new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick( ClickEvent event ) {

				ContractDetailsEditDlg dlg = new ContractDetailsEditDlg( ContractViewDlg.this.contract, ContractViewDlg.this, modType );
        		
        		UI.getCurrent().addWindow( dlg );
        		dlg.center();
				
			}
			
		});
		
		contractInfoLabel = new Label();
		contractInfoLabel.setContentMode( ContentMode.HTML );
		
		VerticalLayout vtLayout = new VerticalLayout(); 
		
		vtLayout.addComponent( contractLabel );
		vtLayout.addComponent( contractInfoLabel );
		
		layout.addComponent( vtLayout );
		layout.addComponent( contractEditButton );
		
		return layout;
	}
	
	private Component initPeoplePart() {

		HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin( true );
		layout.setSpacing( true );
		
		employeesLabel = new Label();
		employeesLabel.setContentMode( ContentMode.HTML );
		employeesLabel.addStyleName( "h2" );
		
		employeesEditButton = new Button();
		employeesEditButton.addClickListener( new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick( ClickEvent event ) {
				
				if ( ContractViewDlg.this.contract.getContractor() == null ) {
					
					ContractViewDlg.this.contract.setContractor( new Contractor());
					modType = ModType.New; 
					
				}
				
				EmployeesViewDlg dlg = new EmployeesViewDlg( contract.getContractor().getEmployees(), ContractViewDlg.this, modType );
        		
        		UI.getCurrent().addWindow( dlg );
        		dlg.center();
				
			}
			
		});
		
		employeesInfoLabel = new Label();
		employeesInfoLabel.setContentMode( ContentMode.HTML );
		
		employeesInfoTable = new Table();
		employeesInfoTable.setHeight( "4em" );
		employeesInfoTable.setSelectable( false );
		
		VerticalLayout vtLayout = new VerticalLayout(); 
		
		vtLayout.addComponent( employeesLabel );
		vtLayout.addComponent( employeesInfoLabel );
		vtLayout.addComponent( employeesInfoTable );
		
		layout.addComponent( vtLayout );
		layout.addComponent( employeesEditButton );
		
		return layout;
	}
	
	private void setCaption() {

		switch ( modType ) {
			case New: {
				setCaption( "New Contract" );
				return;
			}
			case Edit: {
				setCaption( "Edit Contract" );
				return;
			}
			case View: {
				setCaption( "View Contract" );
				return;
			}
		}
	}
	
	private void dataToView() {

		showContractor();
		
		showContact();

		showContractDetails();

		showEmployeesDetails();
		
	}

	private void showContractor() {
		
		companyEditButton.setCaption( "Edit" );

		Contractor contractor = this.contract.getContractor();
		
		try {
//		if ( contractor != null ) {
			companyLabel.setValue( contractor.getName());
			
			Locale obj = new Locale( "", contractor.getCountryCode());
			String countryStr = ( obj != null ? obj.getDisplayCountry() : "" );

			String idStr = "";
			
			if ( contractor.getCountryCode() != null && contractor.getCountryCode().compareToIgnoreCase( "FI" ) == 0 ) {
				idStr = ( contractor.getFinnishBusinessID() != null 
						?	"Finnish Business ID: " + contractor.getFinnishBusinessID()
						:	"" );
			} else {
				idStr = ( contractor.getForeignBusinessID() != null 
						?	"Foreign-issued ID: " + contractor.getForeignBusinessID()
						:	"" );
				
			}
			
			
			companyInfoLabel.setValue( 
						"<i><b>" + countryStr + "</i></b>" + "<br>"
					+	"<i><b>" + idStr + "</i></b>"
			);
		} catch ( Exception e ) {
//		} else {
			if ( logger.isDebugEnabled()) logger.debug( "No Contractor data to show" );

			companyLabel.setValue( "" );
			companyInfoLabel.setValue( "<i><b>No Contractor has been specified yet ... </i></b>" );
		}

	}

	private void showContact() {

		Contact contact = null;
		
		try {
			
			contact = this.contract.getContractor().getContact();
			
			String nameStr = contact.getFirstLastName();
			String addressStr = contact.getAddress().oneLineAddress(); 
			String phoneStr = contact.getPhoneNumber(); 
			
			contactLabel.setValue( "Contact Person" );

			contactInfoLabel.setValue( 
					"<i><b>" + nameStr + "</i></b>" + "<br>"
				+	"Address: " + "<i><b>" + addressStr + "</i></b>" + "<br>"
				+	"Phone: " + "<i><b>" + phoneStr + "</i></b>"
		);
			
			
		} catch ( Exception e ) {
			if ( logger.isDebugEnabled()) logger.debug( "No Contact data to show" );
			
			contactLabel.setValue( "" );
			contactInfoLabel.setValue( "<i><b>No contact information has been specified yet ... </i></b>" );
			
		}
		
	}

	private void showContractDetails() {

		contractEditButton.setCaption( "Edit" );
		
		try {
			
			String contractTypeStr = this.contract.getContractType().getName();
			String vatStr = ( this.contract.isVatReverse() ? "Yes" : "No" );
			String dateStartStr = ( this.contract.getStartDate() != null 
								? DateTimeFormat.forPattern("dd.MM.yyyy").print( this.contract.getStartDate()) : "Unknown" );
			String dateEndStr = ( this.contract.getEndDate() != null 
								? DateTimeFormat.forPattern("dd.MM.yyyy").print( this.contract.getEndDate()) : "Unknown" );
			String paidStr = ( this.contract.getPaid() != null 
								? this.contract.getPaid().toString() + ".00" : "0.00" );
			String invoicedStr = ( this.contract.getInvoiced() != null 
								? this.contract.getInvoiced().toString() + ".00" : "0.00" );
			String totalStr = ( this.contract.getTotal() != null 
								? this.contract.getTotal().toString() + ".00" : "0.00" );
			
			contractLabel.setValue( "Contract Details" );

			contractInfoLabel.setValue( 
					"Contract Type: " + "<i><b>" + contractTypeStr + "</i></b>  "
				+	"VAT Reverse: "	+ "<i><b>" + vatStr + "</i></b>" + "<br>"
				+	"Contract Start: " + "<i><b>" + dateStartStr + "</i></b>  "
				+	"Estimated End: " + "<i><b>" + dateEndStr + "</i></b>" + "<br>"
				+	"Paid: " + "<i><b>" + paidStr + "</i></b>  "
				+	"Invoiced: " + "<i><b>" + invoicedStr + "</i></b>  "
				+	"Total: " + "<i><b>" + totalStr + "</i></b>"
		);
			
			
		} catch ( Exception e ) {
			if ( logger.isDebugEnabled()) logger.debug( "No Contract Details to show" );
			
			contractLabel.setValue( "" );
			contractInfoLabel.setValue( "<i><b>No Contract Details information have been specified yet ... </i></b>" );
			
		}
	
	}

	private void showEmployeesDetails() {

		employeesEditButton.setCaption( "Edit" );
		
		
		try {
			Collection<Employee> employees = contract.getContractor().getEmployees();
			
			if ( employees.size() <=0 ) throw new Exception();
			
			employeesLabel.setValue( "Employees" );
			employeesInfoLabel.setValue( "Number of employees entered: " + "<i><b>" + employees.size() + "</i></b>" );
			
		} catch ( Exception e ) {
			if ( logger.isDebugEnabled()) logger.debug( "No Employees data to show" );
			
			employeesLabel.setValue( "" );
			employeesInfoLabel.setValue( "<i><b>No Contract Details information have been specified yet ... </i></b>" );
			
		}
	}
	
	private void viewToData() {

	}
	
	private void save() {
		
		if ( this.model.getContractorsModel() != null && modifiedEntityFlag ) {
		
			if ( modType == ModType.New ) {
				this.model.getContractorsModel().addContract( this.contract );
			} else if ( modType == ModType.Edit ) {
				this.model.getContractorsModel().modifyContract( this.contract );
			}
		}
	}

	@Override
	public void added() {
		
		modifiedEntityFlag = true;

		dataToView();
		
	}

	@Override
	public void edited() {

		modifiedEntityFlag = true;

		dataToView();
	}
	
	@Override
	public void deleted() {

		modifiedEntityFlag = true;

		dataToView();
	}
	

}
