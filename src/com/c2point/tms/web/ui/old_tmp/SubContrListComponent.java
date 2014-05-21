package com.c2point.tms.web.ui.old_tmp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vaadin.dialogs.ConfirmDialog;

import com.c2point.tms.entity.Organisation;
import com.c2point.tms.web.ui.listeners.OrgModelListener;
import com.c2point.tms.web.ui.subcontract.SubContrModel;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.Or;
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

public class SubContrListComponent extends VerticalLayout implements OrgModelListener {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager
			.getLogger(SubContrListComponent.class.getName());

	private static int BUTTON_WIDTH = 25;

	private SubContrModel model;

	private Button addButton;
	private Label counterText;
	private Table subContrTable;
	private TextField searchText;

	private boolean processed = false;
	private long selectedID = -1;
	
	
	public SubContrListComponent( SubContrModel model ) {
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
		// Sites list
		// Search field ( Search icon + search text field )

		// Add button to add Site
		addButton = new Button();
		addButton.setSizeUndefined();
		addButton.setIcon(new ThemeResource("icons/16/add16.png"));
		// addButton.setDescription( model.getApp().getResourceStr(
		// "personnel.list.add.tooltip" ));
		addButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
//				new Notification( "Button Press",
//						"Add Site button has been pressed").show(Page.getCurrent());
/*				
				Site site = new Site(); 
				SiteEditDlg dlg = new SiteEditDlg( site, model, ModType.New );
        		
        		UI.getCurrent().addWindow( dlg );
        		dlg.center();
*/				
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

		// Add Sites List

		subContrTable = new Table();
		subContrTable.setSizeFull();
		subContrTable.setWidth("100%");

		// Configure table
		subContrTable.setSelectable(true);
		subContrTable.setNullSelectionAllowed(false);
		subContrTable.setMultiSelect(false);
		subContrTable.setColumnCollapsingAllowed(false);
		subContrTable.setColumnReorderingAllowed(false);
		subContrTable.setImmediate(true);
		subContrTable.setSizeFull();

		subContrTable.addContainerProperty( "code", String.class, null);
		subContrTable.addContainerProperty( "name", String.class, null);
		subContrTable.addContainerProperty( "button", Button.class, null);
		subContrTable.addContainerProperty( "data", Organisation.class, null );

		subContrTable.setVisibleColumns( new Object [] { "code", "name", "button" });
		subContrTable.setColumnHeaders( new String[] { "Code", "Name", "" });

		subContrTable.setColumnWidth( "code", (int) (BUTTON_WIDTH * 2.5) );
		subContrTable.setColumnWidth("button", BUTTON_WIDTH );
		subContrTable.setColumnExpandRatio( "name", 1 );

		// Handles the click in the item. NOT USED  YET!
		subContrTable.addItemClickListener( new ItemClickListener() {

			private static final long serialVersionUID = 1L;

			public void itemClick(ItemClickEvent event) {

            	if ( selectedID == ( Long )event.getItemId() && !processed ) {
            		processed = true;
            		if ( logger.isDebugEnabled()) logger.debug( "Selection was NOT changed" );
  /*          		
            		SiteEditDlg dlg = new SiteEditDlg( model.getSelectedSite(), model, ModType.Edit );
            		
            		UI.getCurrent().addWindow( dlg );
            		dlg.center();
   */         		
            		
            	} else {
            		processed = false;
            		if ( logger.isDebugEnabled()) logger.debug( "Selection was changed" );
            		
            	} 
            	
            	
            }
        });

		// Handle selection of item
		subContrTable.addValueChangeListener( new ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange( ValueChangeEvent event ) {

				if ( logger.isDebugEnabled()) {
	        		logger.debug( "Sites table item changed selection status. ItemId = " + ( getSelectId() >= 0 ? getSelectId() : "NO_SELECTION" ));
	        		
	        	}
				
				// Store selected item ID.ID assigned during Item creation
        		processed = false;
        		selectedID = getSelectId();
        		if ( selectedID > 0 ) {
        			model.setSelectedSubContr(( Organisation ) subContrTable.getItem( selectedID ).getItemProperty( "data" ).getValue());
        		} else {
        			model.setSelectedSubContr( null );
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
		this.addComponent( subContrTable );
		this.addComponent( searchLayout );

		this.setExpandRatio( subContrTable, 1.0f );

		subContrTable.setSortContainerPropertyId( "name" );
		
	}

	public void setModel( SubContrModel model ) {

		this.model = model;
		
		dataFromModel();

		
	}

	public void selectItem( Organisation subContr ) {
		
		if ( subContr != null ) {
			selectItem( subContr.getId());
		}
	}
	
	private long getSelectId() {
		
		
		return ( subContrTable.getValue() != null ? ( Long )subContrTable.getValue() : -1 );
	}
	
	public void selectItem( Object subContrId ) {

		subContrTable.setValue( subContrId );
		
	}
	
	public void selectItem( long subContrId ) {

		subContrTable.setValue( subContrId );
		
	}
	
	private void dataFromModel() {

	// Fill List of Reports
		
		// Store current selection
		long tmpSelectedID = getSelectId();
		// Remove all content
		subContrTable.removeAllItems();
		
		// Add items
		if ( model != null && model.getSubContractors() != null ) {
			for ( Organisation subContr : model.getSubContractors()) {
				if ( subContr != null ) {
					
					addOrUpdateItem( subContr );
					
				}
			}
			
			
		}
		
		
		// Sort
		subContrTable.sort();
		
		// Restore selection
		// if not possible than select FIRST item
		// Check that selection can be restored
		if ( tmpSelectedID >= 0 && subContrTable.getItem( selectedID ) != null  ) {

			selectItem( selectedID );
			
		} else {

			selectItem( subContrTable.firstItemId() );
			
		}
		subContrTable.setCurrentPageFirstItemId( getSelectId());
		
	}
	
	private  void addOrUpdateItem( Organisation subContr ) {

		Item item = subContrTable.getItem( subContr.getId());
		
		if ( item == null ) {

			if ( logger.isDebugEnabled()) logger.debug( "Item will be added: " + subContr );
			item = subContrTable.addItem( subContr.getId() );
			
		} else {
			if ( logger.isDebugEnabled()) logger.debug( "Item exists already. Will be modified: " + subContr );
		}

//		LocalDate date = new LocalDate( report.getYear(), report.getMonth(), 1 );
		
		
		item.getItemProperty( "code" ).setValue( subContr.getCode());
		item.getItemProperty( "name" ).setValue( subContr.getName());
		item.getItemProperty( "data" ).setValue( subContr );
		
		// Add Delete button
		final NativeButton delButton = new NativeButton();
        delButton.setIcon( new ThemeResource( "icons/16/delete16.png"));
        delButton.setDescription( "Press the button to delete Site from Reporting" ); //model.getApp().getResourceStr( "company.list.delete.tooltip" ));

		delButton.setHeight( Integer.toString( BUTTON_WIDTH ) + "px" );
		delButton.setStyleName("v-nativebutton-deleteButton");
		delButton.addStyleName("v-nativebutton-link");
		delButton.setStyleName(Runo.BUTTON_LINK);

		delButton.setData( subContr );
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
						"Do you want to delete '" + (( Organisation )delButton.getData()).getName() + "' Subcontractor?", //template, 
						"OK", //model.getApp().getResourceStr( "general.button.ok" ), 
						"Cancel", //model.getApp().getResourceStr( "general.button.cancel" ), 
						new ConfirmDialog.Listener() {
							private static final long serialVersionUID = 1L;

							@Override
							public void onClose( ConfirmDialog dialog ) {
								if ( dialog.isConfirmed()) {
									// Confirmed to continue
									if ( !model.delete(( Organisation ) delButton.getData())) {

										Notification.show( "Error", "Cannot delete Subcontractor", Notification.Type.ERROR_MESSAGE );
									}
	
								}
							}

				});

			}

		});

        item.getItemProperty( "button" ).setValue( delButton );//buttonsSet );

		subContrTable.sort();
        
	}
	
	
	
	@Override
	public void added( Organisation subContr ) {
		
		if ( logger.isDebugEnabled()) logger.debug( "SubcontractorList received 'Organisation Added' event" );

		addOrUpdateItem( subContr );
		
		selectItem( subContr );
		
	}

	@Override
	public void edited( Organisation subContr ) {

		if ( logger.isDebugEnabled()) logger.debug( "SubcontractorList received 'Organisation Edited' event" );

		addOrUpdateItem( subContr );
		
	}

	@Override
	public void deleted( Organisation subContr ) {

		if ( logger.isDebugEnabled()) logger.debug( "SubcontractorList received 'Organisation Deleted' event" );
		
		// Save prev item
//		final Object current = site;
		selectItem( subContr );

		Object future = subContrTable.prevItemId( subContr.getId());
		if ( future == null ) {
			future = subContrTable.firstItemId();
		}

		// delete Table item
		if ( subContrTable.containsId( subContr.getId())) {

			subContrTable.removeItem( subContr.getId());

			if ( future != null )
				selectItem( future );
			
		}
		
	}

	@Override
	public void selected( Organisation subContr ) {
		// Nothing to do here. It is returned event
	}
	
	private boolean searchFieldUpdated( String searchStr ) {
		
		boolean found = false;

		IndexedContainer container = ( IndexedContainer )subContrTable.getContainerDataSource();
		
		container.removeAllContainerFilters();
		if ( searchStr != null && searchStr.length() > 0 ) {
			Filter filter = new Or(
					new SimpleStringFilter( "name",	searchStr, true, false ),
					new SimpleStringFilter( "code",	searchStr, true, false )
					);
			
			container.addContainerFilter( filter );
			
			
		}
		
		found = container.size() > 0;
		
		return found;
	}

}
