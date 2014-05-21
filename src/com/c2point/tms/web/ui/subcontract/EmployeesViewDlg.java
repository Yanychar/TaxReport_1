package com.c2point.tms.web.ui.subcontract;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.vaadin.dialogs.ConfirmDialog;

import com.c2point.tms.entity.taxreport.Employee;
import com.c2point.tms.util.CheckValueUtils;
import com.c2point.tms.web.ui.ButtonBar;
import com.c2point.tms.web.ui.listeners.ContractChangedListener;
import com.c2point.tms.web.ui.listeners.EmployeesModelListener;
import com.c2point.tms.web.ui.ModType;
import com.vaadin.data.Item;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.Runo;

public class EmployeesViewDlg extends Window implements EmployeesModelListener {

	private static Logger logger = LogManager.getLogger( EmployeesViewDlg.class.getName());

	private static final long serialVersionUID = 1L;

	private static int BUTTON_WIDTH = 25;
	
	private EmployeesModel			model;
	private ContractChangedListener	changedListener;
	private ModType					modType;
	
	private Button		addButton;
	private Table		employeesTable;
	private TextField	searchText;
	
	private Label 		infoCaption;
	private Label 		infoData_1;
	

	
	public EmployeesViewDlg( Collection<Employee> employees, ContractChangedListener changedListener, ModType modType ) {

		if ( employees == null  ) {
			throw new IllegalArgumentException( "Valid List of Contacts (can be 0 sized) shall be specified!" );
		}
		
		this.model = new EmployeesModel( employees );
		this.changedListener = changedListener;
		this.modType = modType;
		
		initUI();
		
		dataToView();

	}

	private void initUI() {

		setCaption( "Employees" );
		setModal( true );
		
		setWidth( "50%" );
		setHeight( "50%" );
		
		VerticalLayout content = new VerticalLayout();
//		content.setMargin( new MarginInfo( true, true, false, true ));
//		content.setMargin( true );
//		content.setSpacing( true );
//		content.setSizeUndefined();
		content.setSizeFull();
	
		HorizontalSplitPanel hSplit = new HorizontalSplitPanel();
		hSplit.setStyleName( "small" );
		hSplit.setSplitPosition( 50, Unit.PERCENTAGE );
		
		
		
		employeesTable = new Table();
		initTable();
		
		addButton = new Button();
		addButton.setSizeUndefined();
		addButton.setIcon(new ThemeResource("icons/16/add16.png"));
		// addButton.setDescription( model.getApp().getResourceStr(
		// "personnel.list.add.tooltip" ));
		addButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				if ( logger.isDebugEnabled()) logger.debug( "Add Contract button has been pressed!" );
				
				Employee employee = new Employee(); 
				EmployeeEditDlg dlg = new EmployeeEditDlg( employee, model, ModType.New );
        		
        		UI.getCurrent().addWindow( dlg );
        		dlg.center();
				
			}

		});

		HorizontalLayout searchLayout = new HorizontalLayout();
		searchLayout.setWidth("100%");
		searchLayout.setSpacing(true);

		Label searchIcon = new Label();
		searchIcon.setIcon(new ThemeResource("icons/16/search.png"));
		searchIcon.setWidth( "2em" );
		searchText = new TextField();
		searchText.setWidth("100%");
		searchText.setNullSettingAllowed(true);
		searchText.setInputPrompt( "Search ...");
		searchText.setImmediate( true );
		
		searchText.addTextChangeListener( new TextChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void textChange( TextChangeEvent event ) {
				
				searchFieldUpdated( event.getText());
				
			}
			
		});
		

		searchLayout.addComponent( searchIcon );
		searchLayout.addComponent( searchText );

		searchLayout.setExpandRatio( searchText, 1.0f );
		
		
		VerticalLayout listLayout = new VerticalLayout();
		listLayout.setSizeFull();
		listLayout.setSpacing( true );
		listLayout.setMargin( true );
		
		listLayout.addComponent( addButton );
		listLayout.addComponent( employeesTable );
		listLayout.addComponent( searchLayout );
		
		listLayout.setExpandRatio( employeesTable, 1.0f );
		
		infoCaption = new Label();
		infoCaption.setContentMode( ContentMode.HTML );
		infoCaption.addStyleName( "h2" );
		
		
		infoData_1 = new Label();
		infoData_1.setContentMode( ContentMode.HTML );
		infoData_1.setImmediate( true );
		
		VerticalLayout infoLayout = new VerticalLayout();
		infoLayout.setMargin( new MarginInfo( true, true, false, true ));
//		infoLayout.setMargin( true );
		infoLayout.setSpacing( true );
		infoLayout.setSizeUndefined();
		
		infoLayout.addComponent( infoCaption );
		infoLayout.addComponent( infoData_1 );
		
		hSplit.setFirstComponent( listLayout );
		hSplit.setSecondComponent( infoLayout );
		hSplit.setSplitPosition( 50 );
		
		
		
		ButtonBar btb = ButtonBar.getCloseBar();
//		btb.setEnabled( ButtonBar.ButtonType.Ok, true );
//		btb.removeButton( ButtonBar.ButtonType.Cancel );
		btb.addClickListener( ButtonBar.ButtonType.Close, new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick( ClickEvent event ) {

				viewToData();
				save();
				close();
				
			}
			
		});
/*		
		btb.addClickListener( ButtonBar.ButtonType.Cancel, new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				close();
				
			}
			
		});
*/		
		content.addComponent( hSplit );
//		content.addComponent( listLayout );
		content.addComponent( btb );
		
		content.setExpandRatio( hSplit, 1 );
		
		this.setContent( content );
	}

	private void dataToView() {

		
		initTableData();
		
	}

	private void viewToData() {
/*
		if ( modType != ModType.View ) {		
			
			contract.setContractType(( Contract.ContractType )contractTypeGroup.getValue());
			
			contract.setVatReverse(( Boolean )vatReverseGroup.getValue());
			
			contract.setStartDate( startDF.getValue());
			contract.setEndDate( endDF.getValue());
			
			contract.setInvoiced( Long.parseLong( invoicedField.getValue()));
			contract.setPaid( Long.parseLong( paidField.getValue()));
			contract.setAdvanced( Long.parseLong( advancedField.getValue()));
			contract.setTotal( Long.parseLong( totalField.getValue()));
		}
*/		
	}
	
	private void save() {
		
		if ( modType == ModType.New ) {
//			this.model.addSite( this.site );
		} else if ( modType == ModType.Edit ) {
//			this.model.modifySite( this.site );
		}
		 
	}
	

	private void initTable() {
		
		employeesTable.setSizeFull();
		
		// Configure table
		employeesTable.setSelectable( true );
		employeesTable.setNullSelectionAllowed( false );
		employeesTable.setMultiSelect( false );
		employeesTable.setColumnCollapsingAllowed(false);
		employeesTable.setColumnReorderingAllowed(false);
		employeesTable.setImmediate(true);

		employeesTable.addContainerProperty( "name", String.class, null);
		employeesTable.addContainerProperty( "button", Button.class, null);
		employeesTable.addContainerProperty( "data", Employee.class, null );

		employeesTable.setVisibleColumns( new Object [] { "name", "button" });
		employeesTable.setColumnHeaders( new String[] { "Name", "" });

		employeesTable.setColumnWidth("button", ( int )( BUTTON_WIDTH )); //* 2.5 ));
		employeesTable.setColumnExpandRatio( "name", 1 );

		SelectionItemListener scl = new SelectionItemListener( employeesTable ); 
		employeesTable.addItemClickListener( scl );
		employeesTable.addValueChangeListener( scl );
		
	}

	private void initTableData() {

		// Store current selection
		long tmpSelectedID = getSelectId();
		// Remove all content
		employeesTable.removeAllItems();
		
		// Add items
		if ( model != null && model.getEmployees() != null ) {
			for ( Employee employee : model.getEmployees()) {
				if ( employee != null ) {
					
					addOrUpdateItem( employee );
					
				}
			}
			
			
		}
		
		
		// Sort
		employeesTable.setSortContainerPropertyId( "name" );
		employeesTable.sort();
		
		// Restore selection
		// if not possible than select FIRST item
		// Check that selection can be restored
		if ( tmpSelectedID >= 0 && employeesTable.getItem( tmpSelectedID ) != null  ) {
	
			selectItem( tmpSelectedID );
			
		} else {
	
			selectItem( employeesTable.firstItemId() );
			
		}
		
		employeesTable.setCurrentPageFirstItemId( getSelectId());
		
		model.addListener( this );
	}
	
	private  void addOrUpdateItem( Employee employee ) {

		Item item = employeesTable.getItem( employee.getId());
		
		if ( item == null ) {

			if ( logger.isDebugEnabled()) logger.debug( "Item will be added: " + employee );
			item = employeesTable.addItem( employee.getId() );
			
		} else {
			if ( logger.isDebugEnabled()) logger.debug( "Item exists already. Will be modified: " + employee );
		}

//		LocalDate date = new LocalDate( report.getYear(), report.getMonth(), 1 );
		
		
		item.getItemProperty( "name" ).setValue( employee.getLastFirstName());
		item.getItemProperty( "data" ).setValue( employee );
		
		// Add Delete button
		final NativeButton delButton = new NativeButton();
        delButton.setIcon( new ThemeResource( "icons/16/delete16.png"));
        delButton.setDescription( "Press the button to delete Empoyee from the list" ); //model.getApp().getResourceStr( "company.list.delete.tooltip" ));

		delButton.setHeight( Integer.toString( BUTTON_WIDTH ) + "px" );
		delButton.setStyleName("v-nativebutton-deleteButton");
		delButton.addStyleName("v-nativebutton-link");
		delButton.setStyleName(Runo.BUTTON_LINK);

		delButton.setData( employee );
		delButton.setImmediate( true );

        delButton.addClickListener( new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				if ( logger.isDebugEnabled()) logger.debug( "Delete button was been pressed" );

				// Confirm removal
//				String template = model.getApp().getResourceStr( "confirm.company.delete" );
//				Object[] params = { (( Organisation )current ).getName() };
//				template = MessageFormat.format( template, params );
				
				ConfirmDialog.show( UI.getCurrent(), 
						"Delete", //model.getApp().getResourceStr( "confirm.general.header" ), 
						"Do you want to delete '" + (( Employee )delButton.getData()).getFirstLastName() + "' from the list?", //template, 
						"OK", //model.getApp().getResourceStr( "general.button.ok" ), 
						"Cancel", //model.getApp().getResourceStr( "general.button.cancel" ), 
						new ConfirmDialog.Listener() {
							private static final long serialVersionUID = 1L;

							@Override
							public void onClose( ConfirmDialog dialog ) {
								if ( dialog.isConfirmed()) {
									// Confirmed to continue
									if ( !model.deleteEmployee(( Employee ) delButton.getData())) {

										Notification.show( "Error", "Cannot remove Employee", Notification.Type.ERROR_MESSAGE );
									}
	
								}
							}

				});

			}

		});

        item.getItemProperty( "button" ).setValue( delButton );//buttonsSet );

		employeesTable.sort();
        
	}
	
	
	
	private void showEmployeeData( Employee employee ) {

		if ( employee != null ) { 
			infoCaption.setValue( "Employee Data:" );
			
			infoData_1.setValue( 
//							  "<p>" + "Filer Information" + "</p>"		
//							  "<h3><u>" + model.getMainOrg().getName() + "</u></h3>" + "<br>"
							( !CheckValueUtils.isEmpty( employee.getFinnishTunnus()) 
								? "Finnish ID: " + "<b>" + employee.getFinnishTunnus() + "</b>" + "<br>"  
								: "Tax Number: " + "<b>" + employee.getTaxNumber() + "</b>" + "<br>" )  
						+	( employee.getContract() != null && employee.getContract().getType() != null
								? "Contract Type:" + "<b>" + employee.getContract().getType().toString() + "</b>" + "<br>"
								: "" )
						
						+ "<hr/>"
						
						+	( employee.getContract() != null && employee.getContract().getStartDate() != null
								? "Start in Site:" + "<b>" 
									+ DateTimeFormat.forPattern("dd.MM.yyyy").print( employee.getContract().getStartDate())
									 + "</b>" + "<br>"
								: "" )
						+	( employee.getContract() != null && employee.getContract().getEndDate() != null
								? "Estimated End:" + "<b>" 
									+ DateTimeFormat.forPattern("dd.MM.yyyy").print( employee.getContract().getEndDate())
									+ "</b>" + "<br>"
								: "" )
								
						+ "<hr/>"
						
						+	( employee.getContract() != null && employee.getContract().getDays() != null
								? "Days worked:" + "<b>" + employee.getContract().getDays() + "</b>" + "<br>"
								: "" )
						+	( employee.getContract() != null && employee.getContract().getHours() != null
								? "Hours worked:" + "<b>" + employee.getContract().getHours() + "</b>" + "<br>"
								: "" )
								
						+ "<hr/>"
						
						+ "Tax Residence Address:"	+ "<br>"
						+	( employee.getTaxAddress() != null 
							? "<b>" + employee.getTaxAddress().oneLineAddress() + "</b>"
							: "" )
						
						+ "<hr/>"
				
						+ "Address in Finland:" + "<br>"
						+	( employee.getFinAddress() != null 
							? "<b>" + employee.getFinAddress().oneLineAddress() + "</b>"
							: "" )
						

						
/*					
					
							+ "Code: " + "<b>" + model.getMainOrg().getCode() + "</b>" + "<br>"  //model.getFiler().getCode()));
							+ "Y-Tunnus: " + "<b>" + model.getMainOrg().getTunnus() + "</b>" + "<br>" 
							+ "Address: " + "<b>" + model.getMainOrg().getAddress() + "</b>" + "<br>"
*/
					
	
					
/*					
					"Some data"
					+ "<br>" + "Other data 2"
					+ "<br>" + "Other data 3"
					+ "<br>" + "Other data 4"
*/					
			);
			
		} else {
			infoCaption.setValue( "No data" );
			infoData_1.setValue( "" );
		}
		
	}

	private long getSelectId() {
		
		
		return ( employeesTable.getValue() != null ? ( Long )employeesTable.getValue() : -1 );
	}
	
	public void selectItem( Object id ) {

		employeesTable.setValue( id );
		
	}

	public void selectItem( long id ) {

		employeesTable.setValue( id );
		
	}

	public void selectItem( Employee employee ) {

		employeesTable.setValue( employee.getId());
		
	}

	@Override
	public void added( Employee employee ) {

		if ( logger.isDebugEnabled()) logger.debug( "EmployeesList received 'Employee Added' event" );

		addOrUpdateItem( employee );
		
		selectItem( employee );
		
		if ( changedListener != null ) {
			this.changedListener.added();
		}
		
	}

	@Override
	public void edited( Employee employee ) {

		if ( logger.isDebugEnabled()) logger.debug( "EmployeesList received 'Employee Edited' event" );

		addOrUpdateItem( employee );

		selectItem( employee );

		showEmployeeData( employee );
		
		if ( changedListener != null ) {
			this.changedListener.edited();
		}
		
	}

	@Override
	public void deleted( Employee employee ) {

		if ( logger.isDebugEnabled()) logger.debug( "EmployeesList received 'Employee Deleted' event" );
		
		// Save prev item
//		final Object current = site;
		selectItem( employee );

		Object future = employeesTable.prevItemId( employee.getId());
		if ( future == null ) {
			future = employeesTable.firstItemId();
		}

		// delete Table item
		if ( employeesTable.containsId( employee.getId())) {

			employeesTable.removeItem( employee.getId());

			if ( future != null )
				selectItem( future );
			
		}
		
		if ( changedListener != null ) {
			this.changedListener.deleted();
		}
		
	}

	@Override
	public void selected( Employee employee ) {
		// TODO Auto-generated method stub
		
	}
	

	class SelectionItemListener implements ItemClickListener, ValueChangeListener {
		
		private static final long serialVersionUID = 1L;

		private Table table;
		private Window dlg;

		private boolean processed = false;
		private long selectedID = -1;
		
		@SuppressWarnings("unused")
		private SelectionItemListener() {}
		
		public SelectionItemListener( Table table ) {
			this.table = table;
		}
		
		public void itemClick(ItemClickEvent event) {

        	if ( selectedID == ( Long )event.getItemId() && !processed ) {
        		processed = true;
        		if ( logger.isDebugEnabled()) logger.debug( "Selection was NOT changed" );
        		
        		if ( table.getValue() != null ) {
        		
        			Employee employee = ( Employee )table.getItem( selectedID ).getItemProperty( "data" ).getValue();
        			
        			if ( employee != null ) {
	        		
						EmployeeEditDlg dlg = new EmployeeEditDlg( employee, model, ModType.Edit );
		        		
		        		UI.getCurrent().addWindow( dlg );
		        		dlg.center();
		        		
        			}
        			
        		}
        		
        	} else {
        		processed = false;
        		if ( logger.isDebugEnabled()) logger.debug( "Selection was changed" );
        		
        	} 
        	
        	
        }
		public void valueChange( ValueChangeEvent event ) {

			if ( logger.isDebugEnabled()) {
        		logger.debug( "Sites table item changed selection status. ItemId = " + ( getSelectId() >= 0 ? getSelectId() : "NO_SELECTION" ));
        		
        	}
			
			// Store selected item ID.ID assigned during Item creation
    		processed = false;
    		selectedID = getSelectId();
    		
			Employee employee = ( Employee )table.getItem( selectedID ).getItemProperty( "data" ).getValue();
    		showEmployeeData( employee );
    		

		}
	}

	private boolean searchFieldUpdated( String searchStr ) {
		
		boolean found = false;

		IndexedContainer container = ( IndexedContainer )employeesTable.getContainerDataSource();
		
		container.removeAllContainerFilters();
		if ( searchStr != null && searchStr.length() > 0 ) {
			
			Filter filter = new SimpleStringFilter( "name",	searchStr, true, false );
			
			container.addContainerFilter( filter );
			
			
		}
		
		found = container.size() > 0;
		
		return found;
	}

	
}
