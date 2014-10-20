package com.c2point.tms.web.ui.taxreports;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vaadin.dialogs.ConfirmDialog;

import com.c2point.tms.entity_tax.Contract;
import com.c2point.tms.entity_tax.Site;
import com.c2point.tms.web.ui.ModType;
import com.c2point.tms.web.ui.listeners.ContractsModelListener;
import com.c2point.tms.web.ui.listeners.SitesModelListener;
import com.c2point.tms.web.ui.subcontract.ContractViewDlg;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.Runo;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ContractsListComponent extends VerticalLayout implements ContractsModelListener, SitesModelListener {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager
			.getLogger(ContractsListComponent.class.getName());

	private static int BUTTON_WIDTH = 25;

	private SitesModel model;

	private Button addButton;
	private Label counterText;
	private Table contractsTable;
	private TextField searchText;

	private boolean processed = false;
	private long selectedID = -1;
	
	
	public ContractsListComponent() {
		
		this( null );

	}

	public ContractsListComponent( SitesModel model ) {
		super();

		initUI();
		
		setModel( model );
		
	}

	public void initUI() {

		this.setSizeFull();
		this.setSpacing(true);
		this.setMargin(true);

		// Has 3 vertical oriented components:
		// "Add" button and counters
		// Contracts list
		// Search field ( Search icon + search text field )

		// Add button to add Contract
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

				if ( model.getContractorsModel() != null ) {
				
					Contract contract = new Contract(); 
					ContractViewDlg dlg = new ContractViewDlg( contract, model, ModType.New );
	        		
	        		UI.getCurrent().addWindow( dlg );
	        		dlg.center();
				}
				
			}

		});

		counterText = new Label();
		counterText.setContentMode(ContentMode.HTML);
		counterText.setImmediate( true );
		counterText.setValue("<b>0/0</b>");

		HorizontalLayout addLayout = new HorizontalLayout();
		addLayout.setWidth("100%");
		// addLayout.setSpacing(true);

		
		addLayout.addComponent(addButton);
		addLayout.addComponent(counterText);

		// Add Contracts List

		contractsTable = new Table();
		contractsTable.setSizeFull();

		// Configure table
		contractsTable.setSelectable(true);
		contractsTable.setNullSelectionAllowed(false);
		contractsTable.setMultiSelect(false);
		contractsTable.setColumnCollapsingAllowed(false);
		contractsTable.setColumnReorderingAllowed(false);
		contractsTable.setImmediate(true);
		contractsTable.setSizeFull();

		contractsTable.addContainerProperty( "name", String.class, null);
		contractsTable.addContainerProperty( "button", Button.class, null);
		contractsTable.addContainerProperty( "data", Contract.class, null );

		contractsTable.setVisibleColumns( new Object [] { "name", "button" });
		contractsTable.setColumnHeaders( new String[] { "Name", "" });

		contractsTable.setColumnWidth( "code", (int) (BUTTON_WIDTH * 2.5) );
		contractsTable.setColumnWidth("button", BUTTON_WIDTH );
		contractsTable.setColumnExpandRatio( "name", 1 );

		// Handles the click in the item. NOT USED  YET!
		contractsTable.addItemClickListener( new ItemClickListener() {

			private static final long serialVersionUID = 1L;

			public void itemClick(ItemClickEvent event) {

            	if ( selectedID == ( Long )event.getItemId() && !processed ) {
            		processed = true;
            		if ( logger.isDebugEnabled()) logger.debug( "Selection was NOT changed" );

            		Contract contract = null;
            		try {
	            		contract = ( Contract )event.getItem().getItemProperty( "data" ).getValue();
	            		
	            		if ( contract == null ) throw new Exception( "Cannot find Contract in the table" );
	            		
						ContractViewDlg dlg = new ContractViewDlg( contract, model, ModType.Edit );
	            		
	            		UI.getCurrent().addWindow( dlg );
	            		dlg.center();
	            		
            		} catch ( Exception e ) {
            			logger.error( "Cannot fetch Contract from selection!" );
            		}
            		
            		
            	} else {
            		processed = false;
            		if ( logger.isDebugEnabled()) logger.debug( "Selection was changed" );
            		
            	} 
            	
            	
            }
        });

		// Handle selection of item
		contractsTable.addValueChangeListener( new ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange( ValueChangeEvent event ) {

				if ( logger.isDebugEnabled()) {
	        		logger.debug( "Contracts table item changed selection status. ItemId = " + ( getSelectId() >= 0 ? getSelectId() : "NO_SELECTION" ));
	        		
	        	}
				
				// Store selected item ID.ID assigned during Item creation
        		processed = false;
        		selectedID = getSelectId();
        		if ( selectedID > 0 ) {
        			model.getContractorsModel().setSelectedContract(( Contract ) contractsTable.getItem( selectedID ).getItemProperty( "data" ).getValue());
        		} else {
        			model.getContractorsModel().setSelectedContract( null );
        		}
        				
			}
		});

		
		
		
		// Add search field

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

		this.addComponent( addButton );
		this.addComponent( contractsTable );
//		this.addComponent( searchLayout );

		this.setExpandRatio( contractsTable, 1.0f );

		contractsTable.setSortContainerPropertyId( "name" );
		
	}

	public void setModel( SitesModel model ) {

		if ( this.model != model ) {
			
			if ( this.model != null && this.model.getContractorsModel() != null ) {
				this.model.getContractorsModel().deleteListener( this );
			}
			
			this.model = model;
			
			if ( this.model != null && this.model.getContractorsModel() != null ) {
				
				this.model.getContractorsModel().deleteListener( this );
				this.model.getContractorsModel().addListener( this );
			}
		}
		
		
		dataFromModel();
		
	}

	public void selectItem( Contract contract ) {
		
		if ( contract != null ) {
			selectItem( contract.getId());
		}
	}
	
	private long getSelectId() {
		
		
		return ( contractsTable.getValue() != null ? ( Long )contractsTable.getValue() : -1 );
	}
	
	public void selectItem( Object siteId ) {

		contractsTable.setValue( siteId );
		
	}
	
	public void selectItem( long siteId ) {

		contractsTable.setValue( siteId );
		
	}
	
	private void dataFromModel() {

	// Fill List of Reports
		
		// Store current selection
		long tmpSelectedID = getSelectId();
		// Remove all content
		contractsTable.removeAllItems();
		
		// Add items
		if ( model != null && model.getContractorsModel() != null && model.getContractorsModel().getContracts() != null ) {
			for ( Contract contract : model.getContractorsModel().getContracts()) {
				if ( contract != null ) {
					
					addOrUpdateItem( contract );
					
				}
			}
			
			
		}
		
		
		// Sort
		contractsTable.sort();
		
		// Restore selection
		// if not possible than select FIRST item
		// Check that selection can be restored
		if ( tmpSelectedID >= 0 && contractsTable.getItem( selectedID ) != null  ) {

			selectItem( selectedID );
			
		} else {

			selectItem( contractsTable.firstItemId() );
			
		}
		contractsTable.setCurrentPageFirstItemId( getSelectId());
		
	}
	
	private  void addOrUpdateItem( Contract contract ) {

		Item item = contractsTable.getItem( contract.getId());
		
		if ( item == null ) {

			if ( logger.isDebugEnabled()) logger.debug( "Item will be added: " + contract );
			item = contractsTable.addItem( contract.getId() );
			
		} else {
			if ( logger.isDebugEnabled()) logger.debug( "Item exists already. Will be modified: " + contract );
		}

//		LocalDate date = new LocalDate( report.getYear(), report.getMonth(), 1 );
		
		
		item.getItemProperty( "name" ).setValue( contract.getContractor().getName());
		item.getItemProperty( "data" ).setValue( contract );
		
		// Add Delete button
		final NativeButton delButton = new NativeButton();
        delButton.setIcon( new ThemeResource( "icons/16/delete16.png"));
        delButton.setDescription( "Press the button to delete Contract from Reporting" ); //model.getApp().getResourceStr( "company.list.delete.tooltip" ));

		delButton.setHeight( Integer.toString( BUTTON_WIDTH ) + "px" );
		delButton.setStyleName("v-nativebutton-deleteButton");
		delButton.addStyleName("v-nativebutton-link");
		delButton.setStyleName(Runo.BUTTON_LINK);

		delButton.setData( contract );
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
						"Do you want to delete '" 
								+ (( Contract )delButton.getData()).getContractor().getName() + "' Contractor?", //template, 
						"OK", //model.getApp().getResourceStr( "general.button.ok" ), 
						"Cancel", //model.getApp().getResourceStr( "general.button.cancel" ), 
						new ConfirmDialog.Listener() {
							private static final long serialVersionUID = 1L;

							@Override
							public void onClose( ConfirmDialog dialog ) {
								if ( dialog.isConfirmed()) {
									// Confirmed to continue
									if ( !model.getContractorsModel().deleteContract(( Contract ) delButton.getData())) {

										Notification.show( "Error", "Cannot delete Contract", Notification.Type.ERROR_MESSAGE );
									}
	
								}
							}

				});

			}

		});

        item.getItemProperty( "button" ).setValue( delButton );//buttonsSet );

		contractsTable.sort();
        
	}
	
	
	
	@Override
	public void added( Contract contract ) {
		
		if ( logger.isDebugEnabled()) logger.debug( "ContractsList received 'Contract Added' event" );

		addOrUpdateItem( contract );
		
		selectItem( contract );
		
	}

	@Override
	public void edited( Contract contract ) {

		if ( logger.isDebugEnabled()) logger.debug( "ContractsList received 'Contract Edited' event" );

		addOrUpdateItem( contract );
		
	}

	@Override
	public void deleted( Contract contract ) {

		if ( logger.isDebugEnabled()) logger.debug( "ContractsList received 'Contract Deleted' event" );
		
		// Save prev item
//		final Object current = contract;
		selectItem( contract );

		Object future = contractsTable.prevItemId( contract.getId());
		if ( future == null ) {
			future = contractsTable.firstItemId();
		}

		// delete Table item
		if ( contractsTable.containsId( contract.getId())) {

			contractsTable.removeItem( contract.getId());

			if ( !contractsTable.containsId( future )) {
				future = contractsTable.firstItemId();
			} 
			
			selectItem( future );
			
		}
		
	}

	@Override
	public void selected( Contract contract ) {
		// Nothing to do here. It is returned event
	}
	
	private boolean searchFieldUpdated( String searchStr ) {
		
		boolean found = false;

		IndexedContainer container = ( IndexedContainer )contractsTable.getContainerDataSource();
		
		container.removeAllContainerFilters();
		if ( searchStr != null && searchStr.length() > 0 ) {
			Filter filter = new SimpleStringFilter( "name",	searchStr, true, false );
			
			container.addContainerFilter( filter );
			
			
		}
		
		found = container.size() > 0;
		
		return found;
	}

	@Override
	public void added(Site site) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edited(Site site) {

		if ( logger.isDebugEnabled()) logger.debug( "SitesTable received 'Site Edited' event" );

//		addOrUpdateItem( site );
		
	}

	@Override
	public void deleted(Site site) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selected( Site site ) {

		dataFromModel();

		if ( this.model != null && this.model.getContractorsModel() != null ) {
			
			this.model.getContractorsModel().addListener( this );
		}
		
	}

}
