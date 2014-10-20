package com.c2point.tms.web.ui.subcontract;

import java.util.Collection;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.format.DateTimeFormat;

import com.c2point.tms.entity_tax.Contact;
import com.c2point.tms.entity_tax.Contract;
import com.c2point.tms.entity_tax.Contractor;
import com.c2point.tms.entity_tax.Employee;
import com.c2point.tms.web.ui.ButtonBar;
import com.c2point.tms.web.ui.listeners.ContractChangedListener;
import com.c2point.tms.web.ui.ModType;
import com.c2point.tms.web.ui.taxreports.OneReportModel;
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

public class ContractorViewDlg extends Window implements ContractChangedListener {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(ContractorViewDlg.class
			.getName());

	private Contractor contractor;
	private OneReportModel model;
	private ModType modType;

	private Label companyLabel;
	private Button companyEditButton;
	private Label companyInfoLabel;

	private Label contactLabel;
	private Label contactInfoLabel;

	private Label contractLabel;
	private Button contractEditButton;
	private Label contractInfoLabel;

	private Label employeesLabel;
	private Button employeesEditButton;
	private Label employeesInfoLabel;
	private Table employeesInfoTable;

	private boolean modifiedEntityFlag = false;

	public ContractorViewDlg( Contractor contractor, OneReportModel model, ModType modType ) {

		if ( contractor == null || model == null ) {
			throw new IllegalArgumentException(
					"Valid Contractor and SiteModel shall be specified!");
		}

		this.contractor = contractor;
		this.model = model;
		this.modType = modType;

		initUI();

		dataToView();

	}

	public ContractorViewDlg( OneReportModel model, ModType modType ) {
		this(null, model, modType);

		this.contractor = this.model.getContractorsModel().getSelectedContractor();

	}

	/*
	 * 
	 * 
	 * companyLabel; companyEditButton; companyInfoLabel;
	 * 
	 * contactLabel; contactEditButton; contactInfoLabel;
	 * 
	 * contractLabel; contractEditButton; contractInfoLabel;
	 * 
	 * employeesLabel; employeesEditButton; employeesInfoLabel;
	 * employeesInfoTable;
	 */

	private void initUI() {

		setCaption();
		setModal( true );

		VerticalLayout content = new VerticalLayout();
		content.setMargin(new MarginInfo(true, true, false, true));
		// content.setSpacing( true );
		content.setSizeUndefined();

		Label separator1 = new Label("<hr/>", ContentMode.HTML);
		separator1.setWidth("100%");
		Label separator2 = new Label("<hr/>", ContentMode.HTML);
		separator2.setWidth("100%");

		ButtonBar btb = ButtonBar.getCloseBar();
		btb.addClickListener(ButtonBar.ButtonType.Close, new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				viewToData();
				save();
				close();

			}

		});

		content.addComponent(initContractorPart());
		content.addComponent(initContactPart());
		content.addComponent(separator1);
		content.addComponent(initContractPart());
		content.addComponent(separator2);
		content.addComponent(initPeoplePart());
		content.addComponent(btb);

		this.setContent(content);
	}

	private Component initContractorPart() {

		HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);

		companyLabel = new Label();
		companyLabel.setContentMode(ContentMode.HTML);
		companyLabel.addStyleName("h1");

		companyEditButton = new Button();
		companyEditButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				ModType modType = ModType.Edit;
				if ( ContractorViewDlg.this.contractor == null) {

					ContractorViewDlg.this.contractor = new Contractor();
					modType = ModType.New;

				}

				ContractorEditDlg dlg = new ContractorEditDlg(
						ContractorViewDlg.this.contractor,
						ContractorViewDlg.this, modType );

				UI.getCurrent().addWindow(dlg);
				dlg.center();

			}

		});

		companyInfoLabel = new Label();
		companyInfoLabel.setContentMode(ContentMode.HTML);

		VerticalLayout companyVtLayout = new VerticalLayout();

		companyVtLayout.addComponent(companyLabel);
		companyVtLayout.addComponent(companyInfoLabel);

		layout.addComponent(companyVtLayout);
		layout.addComponent(companyEditButton);

		return layout;
	}

	private Component initContactPart() {

		HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);

		contactLabel = new Label();
		contactLabel.setContentMode(ContentMode.HTML);
		contactLabel.addStyleName("h2");

		contactInfoLabel = new Label();
		contactInfoLabel.setContentMode(ContentMode.HTML);

		VerticalLayout vtLayout = new VerticalLayout();

		vtLayout.addComponent(contactLabel);
		vtLayout.addComponent(contactInfoLabel);

		layout.addComponent(vtLayout);

		return layout;
	}

	private Component initContractPart() {

		HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);

		contractLabel = new Label();
		contractLabel.setContentMode(ContentMode.HTML);
		contractLabel.addStyleName("h2");

		contractEditButton = new Button();
		contractEditButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				ContractDetailsEditDlg dlg = new ContractDetailsEditDlg(
						ContractorViewDlg.this.contractor.getContract(), ContractorViewDlg.this,
						modType);

				UI.getCurrent().addWindow(dlg);
				dlg.center();

			}

		});

		contractInfoLabel = new Label();
		contractInfoLabel.setContentMode(ContentMode.HTML);

		VerticalLayout vtLayout = new VerticalLayout();

		vtLayout.addComponent(contractLabel);
		vtLayout.addComponent(contractInfoLabel);

		layout.addComponent(vtLayout);
		layout.addComponent(contractEditButton);

		return layout;
	}

	private Component initPeoplePart() {

		HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);

		employeesLabel = new Label();
		employeesLabel.setContentMode(ContentMode.HTML);
		employeesLabel.addStyleName("h2");

		employeesEditButton = new Button();
		employeesEditButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				if (ContractorViewDlg.this.contractor == null) {

					ContractorViewDlg.this.contractor = new Contractor();
					modType = ModType.New;

				}

				EmployeesViewDlg dlg = new EmployeesViewDlg(
						contractor.getEmployees().values(), 
						ContractorViewDlg.this,
						modType);

				UI.getCurrent().addWindow(dlg);
				dlg.center();

			}

		});

		employeesInfoLabel = new Label();
		employeesInfoLabel.setContentMode(ContentMode.HTML);

		employeesInfoTable = new Table();
		employeesInfoTable.setHeight("4em");
		employeesInfoTable.setSelectable(false);

		VerticalLayout vtLayout = new VerticalLayout();

		vtLayout.addComponent(employeesLabel);
		vtLayout.addComponent(employeesInfoLabel);
		vtLayout.addComponent(employeesInfoTable);

		layout.addComponent(vtLayout);
		layout.addComponent(employeesEditButton);

		return layout;
	}

	private void setCaption() {

		switch (modType) {
		case New: {
			setCaption("New Contract");
			return;
		}
		case Edit: {
			setCaption("Edit Contract");
			return;
		}
		case View: {
			setCaption("View Contract");
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

		companyEditButton.setCaption("Edit");


		if ( this.contractor != null ) {

			companyLabel.setValue( this.contractor.getName() != null
					? this.contractor.getName() : "" );
		
			String countryStr;
			try {
				
				Locale obj = new Locale( "", this.contractor.getCountryCode());
				countryStr = ( obj != null ? obj.getDisplayCountry() : null );
				
			} catch (Exception e) {

				if (logger.isDebugEnabled()) logger.debug( "CountryCode is not specified or valid");

				countryStr = "";
			}
		
			String idStr_1;
			String idStr_2;

			idStr_1 = ( this.contractor.getFinnishBusinessID() != null && this.contractor.getFinnishBusinessID().length() > 0 ? 
						"Finnish Business ID: " + this.contractor.getFinnishBusinessID()
						: null );
			idStr_2 = ( this.contractor.getForeignBusinessID() != null && this.contractor.getForeignBusinessID().length() > 0 ?
						"Foreign-issued ID: " + this.contractor.getForeignBusinessID()
						: null );

			companyInfoLabel.setValue(
					  ( countryStr != null ? "<i><b>" + countryStr + "</i></b>" : "" )
					+ ( idStr_1 != null ? "<br>" + "<i><b>" + idStr_1 + "</i></b>" : "" )
					+ ( idStr_2 != null ? "<br>" + "<i><b>" + idStr_2 + "</i></b>" : "" )
			);

		} else {
			companyLabel.setValue("");
			companyInfoLabel
					.setValue("<i><b>No Contractor has been specified yet ... </i></b>");
		}
		
	}

	private void showContact() {

		Contact contact;
		
		try {
			contact = this.contractor.getContact();
		} catch ( Exception e ) {
			contact = null;
		}

		if( contact != null ) {


			String nameStr = contact.getFirstLastName();
			String addressStr = contact.getAddress().oneLineAddress();
			String phoneStr = contact.getPhoneNumber();

			contactLabel.setValue("Contact Person");

			contactInfoLabel.setValue(
					  ( nameStr != null ? "<i><b>" + nameStr + "</i></b>" + "<br>" : "" )
					+ ( addressStr != null ? "Address: " + "<i><b>" + addressStr + "</i></b>" + "<br>" : "" )
					+ ( phoneStr != null ? "Phone: " + "<i><b>" + phoneStr + "</i></b>" : "" )
			);

		} else {
			if (logger.isDebugEnabled())
				logger.debug("No Contact data to show");

			contactLabel.setValue("");
			contactInfoLabel
					.setValue("<i><b>No contact information has been specified yet ... </i></b>");

		}

	}

	private void showContractDetails() {

		contractEditButton.setCaption("Edit");

		String contractTypeStr;
		String vatStr;
		String dateStartStr;
		String dateEndStr;
		String paidStr;
		String invoicedStr;
		String totalStr;
		
		
		if ( this.contractor != null && this.contractor.getContract() != null ) {

			contractTypeStr = ( this.contractor.getContract().getContractType() != null 
					? this.contractor.getContract().getContractType().getName() 
					: null );
			
			if ( this.contractor.getContract().isVatReverse() != null ) {
				vatStr = ( this.contractor.getContract().isVatReverse() ? "Yes" : "No");
			} else {
				vatStr = null;
			}
			dateStartStr = ( this.contractor.getContract().getStartDate() != null 
								? DateTimeFormat.forPattern("dd.MM.yyyy").print( this.contractor.getContract().getStartDate()) 
								: null );
			dateEndStr =  ( this.contractor.getContract().getEndDate() != null 
								? DateTimeFormat.forPattern( "dd.MM.yyyy" ).print( this.contractor.getContract().getEndDate())
								: null );  // "Unknown"
			paidStr = ( this.contractor.getContract().getPaid() != null 
								? this.contractor.getContract().getPaid().toString() + ".00"
								: null ); //"0.00"
			invoicedStr = ( this.contractor.getContract().getInvoiced() != null 
								? this.contractor.getContract().getInvoiced().toString() + ".00"
								: null );
			totalStr = ( this.contractor.getContract().getTotal() != null 
								? this.contractor.getContract().getTotal().toString() + ".00"
								: null );

			contractLabel.setValue("Contract Details");

			contractInfoLabel.setValue(
					  ( contractTypeStr!= null ? "Contract Type: " + "<i><b>" + contractTypeStr + "</i></b>&nbsp;&nbsp;" : "" )
					+ ( vatStr != null ? "VAT Reverse: " + "<i><b>" + vatStr + "</i></b>" : "" )
					+ "<br>"
					+ ( dateStartStr != null ? "Contract Start: " + "<i><b>" + dateStartStr + "</i></b>&nbsp;&nbsp;" : "" )
					+ ( dateEndStr != null ? "Estimated End: " + "<i><b>" + dateEndStr + "</i></b>  " : "" )
					+ "<br>"
					+ ( paidStr != null ? "Paid: " + "<i><b>" + paidStr + "</i></b>&nbsp;&nbsp;&nbsp;" : "" )
					+ ( invoicedStr != null ? "Invoiced: " + "<i><b>" + invoicedStr + "</i></b>&nbsp;&nbsp;&nbsp;" : "" )
					+ ( totalStr != null ? "Total: " + "<i><b>" + totalStr + "</i></b>" : "" )
			);
			
			if ( contractTypeStr == null && dateStartStr == null && dateEndStr == null 
				&& paidStr == null && invoicedStr == null && totalStr == null ) {

				if (logger.isDebugEnabled()) logger.debug("No Contract Details to show");

				contractLabel.setValue("");
				contractInfoLabel
						.setValue("<i><b>No Contract Details information have been specified yet ... </i></b>");
				
			}
		} else {
			if (logger.isDebugEnabled()) logger.debug("No Contract Details to show");

			contractLabel.setValue("");
			contractInfoLabel
					.setValue("<i><b>No Contract Details information have been specified yet ... </i></b>");

		}

	}

	private void showEmployeesDetails() {

		employeesEditButton.setCaption("Edit");

		try {
			Collection<Employee> employees = contractor.getEmployees().values();

			if (employees.size() <= 0)
				throw new Exception();

			employeesLabel.setValue("Employees");
			employeesInfoLabel.setValue("Number of employees entered: "
					+ "<i><b>" + employees.size() + "</i></b>");

		} catch (Exception e) {
			if (logger.isDebugEnabled())
				logger.debug("No Employees data to show");

			employeesLabel.setValue("");
			employeesInfoLabel
					.setValue("<i><b>No Contract Details information have been specified yet ... </i></b>");

		}
	}

	private void viewToData() {

	}

	private void save() {

		if (this.model.getContractorsModel() != null && modifiedEntityFlag) {

			if (modType == ModType.New) {
				this.model.getContractorsModel().addContractor( this.contractor );
			} else if (modType == ModType.Edit) {
				this.model.getContractorsModel().modifyContractor(this.contractor );
			}
		}
	}

	@Override
	public void added() {

		modifiedEntityFlag = true;

		dataToView();

		model.modifySite();		
	}

	@Override
	public void edited() {

		modifiedEntityFlag = true;

		dataToView();

		model.modifySite();		
	}

	@Override
	public void deleted() {

		modifiedEntityFlag = true;

		dataToView();

		model.modifySite();		
	}

}
