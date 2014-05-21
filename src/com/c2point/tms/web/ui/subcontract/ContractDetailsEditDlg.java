package com.c2point.tms.web.ui.subcontract;


import java.text.NumberFormat;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.c2point.tms.application.Taxreport_1UI;
import com.c2point.tms.entity.taxreport.Contract;
import com.c2point.tms.util.CheckValueUtils;
import com.c2point.tms.web.ui.ButtonBar;
import com.c2point.tms.web.ui.ModType;
import com.c2point.tms.web.ui.listeners.ContractChangedListener;
import com.vaadin.data.util.converter.StringToLongConverter;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ContractDetailsEditDlg extends Window {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger( ContractDetailsEditDlg.class.getName());
	
	private Contract				contract;
	private ContractChangedListener	changedListener;
	private ModType					modType;
	
	private OptionGroup	contractTypeGroup;
	
	private OptionGroup	vatReverseGroup;
	
	private TextField	invoicedField;
	private TextField	paidField;
	private TextField	advancedField;
	private TextField	totalField;
	
	protected DateField startDF;
	protected DateField endDF;

	
	public ContractDetailsEditDlg( Contract contract, ContractChangedListener changedListener, ModType modType ) {

		if ( contract == null ) {
			throw new IllegalArgumentException( "Valid Contract shall be specified!" );
		}
		
		this.contract = contract;
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
		content.setSpacing( true );
		content.setSizeUndefined();
	
		
		Label separator1 = new Label( "<hr/>", ContentMode.HTML );
		separator1.setWidth( "100%" );
		Label separator2 = new Label( "<hr/>", ContentMode.HTML );
		separator2.setWidth( "100%" );


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

		StringToLongConverter converter = new StringToLongConverter() {

			@Override
			protected NumberFormat getFormat( Locale locale ) {
				
				NumberFormat format = super.getFormat( locale );
				format.setGroupingUsed( false );
				
				return format;
			}
		};
		
		invoicedField = new TextField( "Invoiced so far (€): " );
		invoicedField.setMaxLength( 10 );
		invoicedField.setConverter( converter );		
		invoicedField.setNullSettingAllowed( true );
		invoicedField.setNullRepresentation( "0" );
		invoicedField.setImmediate( true );
		
		paidField = new TextField( "Paid so far (€): " );
		paidField.setMaxLength( 10 );
		paidField.setConverter( converter );		
		paidField.setNullSettingAllowed( true );
		paidField.setNullRepresentation( "0" );
		paidField.setImmediate( true );
		
		

		advancedField = new TextField( "Advance payments (€): " );
		advancedField.setMaxLength( 10 );
		advancedField.setConverter( converter );		
		advancedField.setNullSettingAllowed( true );
		advancedField.setNullRepresentation( "0" );
		advancedField.setImmediate( true );

		totalField = new TextField( "Contract total value (€): " );
		totalField.setMaxLength( 10 );
		totalField.setConverter( converter );		
		totalField.setNullSettingAllowed( true );
		totalField.setNullRepresentation( "0" );
		totalField.setImmediate( true );
		
		ButtonBar btb = ButtonBar.getOkCancelBar();
		btb.setEnabled( ButtonBar.ButtonType.Ok, true );
		btb.addClickListener( ButtonBar.ButtonType.Ok, new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick( ClickEvent event ) {

				if ( validate()) {
					viewToData();
					save();
					close();
				}
				
			}
			
		});
		
		btb.addClickListener( ButtonBar.ButtonType.Cancel, new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				close();
				
			}
			
		});
		
		content.addComponent( contractTypeGroup );
		content.addComponent( vatReverseGroup );
		content.addComponent( separator1 );
		content.addComponent( startDF );
		content.addComponent( endDF );
		content.addComponent( separator2 );
		content.addComponent( invoicedField );
		content.addComponent( paidField );
		content.addComponent( advancedField );
		content.addComponent( totalField );
		content.addComponent( btb );
		
		this.setContent( content );
	}

	private void setCaption() {

		switch ( modType ) {
			case New: {
				setCaption( "New Details" );
				return;
			}
			case Edit: {
				setCaption( "Edit Details" );
				return;
			}
			case View: {
				setCaption( "View Details" );
				return;
			}
		}
	}
	
	private void dataToView() {

		contractTypeGroup.addItem( Contract.ContractType.Contract );
		contractTypeGroup.addItem( Contract.ContractType.Leasing );
		contractTypeGroup.addItem( Contract.ContractType.Service );

		contractTypeGroup.setItemCaption( Contract.ContractType.Contract, Contract.ContractType.Contract.getName());
		contractTypeGroup.setItemCaption( Contract.ContractType.Leasing, Contract.ContractType.Leasing.getName());
		contractTypeGroup.setItemCaption( Contract.ContractType.Service, Contract.ContractType.Service.getName());
		
		contractTypeGroup.setValue( contract.getContractType());
		
		vatReverseGroup.addItem( true );
		vatReverseGroup.addItem( false );

		vatReverseGroup.setItemCaption( true, "Yes" );
		vatReverseGroup.setItemCaption( false, "No" );
		
		vatReverseGroup.setValue( contract.isVatReverse());

		startDF.setValue( contract.getStartDate() != null ? contract.getStartDate().toDate() : null );
		endDF.setValue( contract.getEndDate() != null ? contract.getEndDate().toDate() : null );
		
		invoicedField.setValue( contract.getInvoiced() != null ? Long.toString( contract.getInvoiced()) : null );
		paidField.setValue( contract.getPaid() != null ? Long.toString( contract.getPaid()) : null );
		advancedField.setValue( contract.getAdvanced() != null ? Long.toString( contract.getAdvanced()) : null );
		totalField.setValue( contract.getTotal() != null ? Long.toString( contract.getTotal()) : null );

	}

	private void viewToData() {

		if ( modType != ModType.View ) {		
			
			contract.setContractType(( Contract.ContractType )contractTypeGroup.getValue());
			
			contract.setVatReverse(( Boolean )vatReverseGroup.getValue());
			
			contract.setStartDate( startDF.getValue());
			contract.setEndDate( endDF.getValue());
			
			contract.setInvoiced( !CheckValueUtils.isEmpty( invoicedField.getValue()) 
									? Long.parseLong( invoicedField.getValue()) 
									: null
			);		
			contract.setPaid( !CheckValueUtils.isEmpty( paidField.getValue())
									? Long.parseLong( paidField.getValue())
									: null
			);		
			contract.setAdvanced( !CheckValueUtils.isEmpty( advancedField.getValue())
									? Long.parseLong( advancedField.getValue())
									: null
			);		
			contract.setTotal( !CheckValueUtils.isEmpty( totalField.getValue())
									? Long.parseLong( totalField.getValue())
									: null
			);		
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
	
	private boolean validate() {		
		
		try {
			contractTypeGroup.validate();
			vatReverseGroup.validate();
			startDF.validate();
			endDF.validate();
			invoicedField.validate();
			paidField.validate();
			advancedField.validate(); 
			totalField.validate();
			
			return true;
			
		} catch ( Exception e ) {
			if ( logger.isDebugEnabled() ) logger.debug( "Data not valid" );
			
		}
		
		return false;
		
	}

}
