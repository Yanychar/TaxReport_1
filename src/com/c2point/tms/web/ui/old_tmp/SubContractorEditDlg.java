package com.c2point.tms.web.ui.old_tmp;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.joda.time.LocalDate;

import com.c2point.tms.application.Taxreport_1UI;
import com.c2point.tms.entity.Organisation;
import com.c2point.tms.entity.taxreport.Address;
import com.c2point.tms.entity.taxreport.Contact;
import com.c2point.tms.entity.taxreport.Contract;
import com.c2point.tms.entity.taxreport.Contract.ContractType;
import com.c2point.tms.entity.taxreport.Contractor;
import com.c2point.tms.entity.taxreport.Person;
import com.c2point.tms.entity.taxreport.Site;
import com.c2point.tms.web.ui.ButtonBar;
import com.c2point.tms.web.ui.ModType;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class SubContractorEditDlg extends Window {

	private static final long serialVersionUID = 1L;

	private Contract		contract;
	private SitesModel		model;
	private ModType			modType;
	
	private Label 		companyLabel;
	private Button 		selectButton;

	private OptionGroup	contractTypeGroup;
	
	private OptionGroup	vatReverseGroup;
	
	private TextField	invoicedField;
	private TextField	paidField;
	private TextField	advancedField;
	private TextField	totalField;
	
	protected DateField startDF;
	protected DateField endDF;

	
	public SubContractorEditDlg( Contract contract, SitesModel model, ModType modType ) {

		if ( contract == null || model == null ) {
			throw new IllegalArgumentException( "Valid Contract and SiteModel shall be specified!" );
		}
		
		this.contract = contract;
		this.model = model;
		this.modType = modType;
		
		initUI();
		
		dataToView();

	}

	public SubContractorEditDlg( SitesModel model, ModType modType ) {
		this( null, model, modType );
		
		this.contract = this.model.getContractorsModel().getSelectedContract();
		
	}
	private void initUI() {

		setCaption();
		setModal( true );
		
		VerticalLayout content = new VerticalLayout();
		content.setMargin( new MarginInfo( true, true, false, true ));
//		content.setSpacing( true );
		content.setSizeUndefined();
		
		HorizontalLayout companyLayout = new HorizontalLayout();
		companyLayout.setMargin( true );
		companyLayout.setSpacing( true );
		
		companyLabel = new Label();
		companyLabel.setContentMode( ContentMode.HTML );
		companyLabel.addStyleName( "h2" );
		
		selectButton = new Button();
		selectButton.addClickListener( new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick( ClickEvent event ) {

				SelectSubcontractor dlg = new SelectSubcontractor( model.getMainOrg());
        		
        		UI.getCurrent().addWindow( dlg );
        		dlg.center();
				
			}
			
		});
		
		companyLayout.addComponent( companyLabel );
		companyLayout.addComponent( selectButton );
		
		Label separator1 = new Label( "<hr/>", ContentMode.HTML );
		separator1.setWidth( "100%" );
		Label separator2 = new Label( "<hr/>", ContentMode.HTML );
		separator2.setWidth( "100%" );

		FormLayout contractDataLayout = new FormLayout();
		contractDataLayout.setMargin( new MarginInfo( true, true, false, true ));
		contractDataLayout.setSpacing( true );
		contractDataLayout.setSizeUndefined();

		contractTypeGroup = new OptionGroup( "Contract Type: ");
		contractTypeGroup.setStyleName( "horizontal" );
		contractTypeGroup.setImmediate( true );
		
		vatReverseGroup = new OptionGroup( "VAT reverse charge: ");
		vatReverseGroup.setStyleName( "horizontal" );
		vatReverseGroup.setImmediate( true );
		
		startDF = new DateField( "Contract start date: " );
		startDF.setLocale( (( Taxreport_1UI )UI.getCurrent()).getSessionData().getLocale());
		startDF.setDateFormat( "dd.MM.yyyy" );
		startDF.setResolution( Resolution.DAY );
		startDF.setImmediate(true);

		endDF = new DateField( "Contract end date: " );
		endDF.setLocale( (( Taxreport_1UI )UI.getCurrent()).getSessionData().getLocale());
		endDF.setDateFormat( "dd.MM.yyyy" );
		endDF.setResolution( Resolution.DAY );
		endDF.setImmediate( true );

		invoicedField = new TextField( "Invoiced so far (€): " );
		invoicedField.setMaxLength( 10 );
		invoicedField.setConverter(Long.class);
//		invoicedField.setInputPrompt( "Enter amount ..." );
		invoicedField.setImmediate( true );
		
		paidField = new TextField( "Paid so far (€): " );
		paidField.setMaxLength( 10 );
//		paidField.setConverter(Long.class);
		paidField.setInputPrompt( "Enter amount ..." );
		paidField.setImmediate( true );

		advancedField = new TextField( "Advance payments (€): " );
		advancedField.setMaxLength( 10 );
//		advancedField.setConverter(Long.class);
		advancedField.setInputPrompt( "Enter amount ..." );
		advancedField.setImmediate( true );

		totalField = new TextField( "Contract total value (€): " );
		totalField.setMaxLength( 10 );
//		totalField.setConverter(Long.class);
		totalField.setInputPrompt( "Enter amount ..." );
		totalField.setImmediate( true );
		
		contractDataLayout.addComponent( contractTypeGroup );
		contractDataLayout.addComponent( vatReverseGroup );
		contractDataLayout.addComponent( startDF );
		contractDataLayout.addComponent( endDF );
		contractDataLayout.addComponent( invoicedField );
		contractDataLayout.addComponent( paidField );
		contractDataLayout.addComponent( advancedField );
		contractDataLayout.addComponent( totalField );
		
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
		
		
		content.addComponent( companyLayout );
		content.addComponent( separator1 );
		content.addComponent( contractDataLayout );
		content.addComponent( btb );
		
		this.setContent( content );
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

		companyLabel.setValue( "Test Company Name Oy" );
		selectButton.setCaption( "Select" );
		
		contractTypeGroup.addItem( Contract.ContractType.Contract );
		contractTypeGroup.addItem( Contract.ContractType.Leasing );
		contractTypeGroup.addItem( Contract.ContractType.Service );

		contractTypeGroup.setItemCaption( Contract.ContractType.Contract, "Contracting" );
		contractTypeGroup.setItemCaption( Contract.ContractType.Leasing, "Leasing" );
		contractTypeGroup.setItemCaption( Contract.ContractType.Service, "Services" );
		
		contractTypeGroup.setValue( contract.getContractType());
		
		vatReverseGroup.addItem( true );
		vatReverseGroup.addItem( false );

		vatReverseGroup.setItemCaption( true, "Yes" );
		vatReverseGroup.setItemCaption( false, "No" );
		
		vatReverseGroup.setValue( contract.isVatReverse());

		startDF.setValue( contract.getStartDate() != null ? contract.getStartDate().toDate() : null );
		endDF.setValue( contract.getEndDate() != null ? contract.getEndDate().toDate() : null );
		
		
	}

	private void viewToData() {

		if ( modType != ModType.View ) {		
			
			if ( contract.getContractor() == null ) {
				
				contract.setContractor( new Contractor());
			}
			
			contract.getContractor().setName( "" );
			
			
			contract.setContractType(( Contract.ContractType )contractTypeGroup.getValue());
			
			contract.setVatReverse(( Boolean )vatReverseGroup.getValue());
			
			contract.setStartDate( startDF.getValue());
			contract.setEndDate( endDF.getValue());
		}
	}
	
	private void save() {
		
		if ( modType == ModType.New ) {
//			this.model.addSite( this.site );
		} else if ( modType == ModType.Edit ) {
//			this.model.modifySite( this.site );
		}
		 
	}
	

}
