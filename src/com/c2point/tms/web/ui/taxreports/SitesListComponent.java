package com.c2point.tms.web.ui.taxreports;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vaadin.dialogs.ConfirmDialog;

import com.c2point.tms.entity.taxreport.Site;
import com.c2point.tms.web.ui.ModType;
import com.c2point.tms.web.ui.listeners.SitesModelListener;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
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
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class SitesListComponent extends VerticalLayout implements SitesModelListener {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager
			.getLogger(SitesListComponent.class.getName());

	private static int BUTTON_WIDTH = 25;

	private SitesModel model;

	private Button addButton;
	private Label counterText;
	private Table sitesTable;
	private TextField searchText;

	private boolean processed = false;
	private Long selectedID = null;
	
	public SitesListComponent( SitesModel model ) {
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
				
				Site site = new Site(); 
				SiteEditDlg dlg = new SiteEditDlg( site, model, ModType.New );
        		
        		UI.getCurrent().addWindow( dlg );
        		dlg.center();
				
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

		sitesTable = new Table();

		// Configure table
		sitesTable.setSelectable(true);
		sitesTable.setNullSelectionAllowed(false);
		sitesTable.setMultiSelect(false);
		sitesTable.setColumnCollapsingAllowed(false);
		sitesTable.setColumnReorderingAllowed(false);
		sitesTable.setImmediate(true);
		sitesTable.setSizeFull();

		sitesTable.addContainerProperty( "code", String.class, null);
		sitesTable.addContainerProperty( "name", String.class, null);
		sitesTable.addContainerProperty( "button", Button.class, null);
		sitesTable.addContainerProperty( "data", Site.class, null );

		sitesTable.setVisibleColumns( new Object [] { "code", "name", "button" });
		sitesTable.setColumnHeaders( new String[] { "Code", "Name", "" });

		sitesTable.setColumnWidth( "code", (int) (BUTTON_WIDTH * 2.5) );
		sitesTable.setColumnWidth("button", BUTTON_WIDTH );
		sitesTable.setColumnExpandRatio( "name", 1 );

		// Handles the click in the item. NOT USED  YET!
		sitesTable.addItemClickListener( new ItemClickListener() {

			private static final long serialVersionUID = 1L;

			public void itemClick(ItemClickEvent event) {

				if ( logger.isDebugEnabled()) {
					logger.debug( "event.getItemId() class: " + event.getItemId().getClass().getSimpleName() + " event.getItemId(): " + event.getItemId() );
					logger.debug( "selectedID class: " + selectedID.getClass().getSimpleName() + " selectedID: " + selectedID );
				}
				
				
            	if ( selectedID != null && selectedID.equals( event.getItemId())) {
            		
            		if ( logger.isDebugEnabled()) logger.debug( "Selection was NOT changed" );

//            		if ( processed == false ) {
            			
            			processed = true;
            			
	            		SiteEditDlg dlg = new SiteEditDlg( model.getSelectedSite(), model, ModType.Edit );
	            		
	            		dlg.addCloseListener( new CloseListener() {
							private static final long serialVersionUID = 1L;

	                        public void windowClose( CloseEvent e ) {
	                    		if ( logger.isDebugEnabled()) logger.debug( "SiteEditDlg will be closed!" );
	                			processed = false;
	                    		
	                        }
	                    });	            		
	            		
	            		UI.getCurrent().addWindow( dlg );
	            		dlg.center();
	            		
//	            	} 
            	}
            }
        });

		// Handle selection of item
		sitesTable.addValueChangeListener( new ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange( ValueChangeEvent event ) {

				if ( logger.isDebugEnabled()) {
	        		logger.debug( "Sites table item changed selection status. ItemId = " + ( getSelectId() >= 0 ? getSelectId() : "NO_SELECTION" ));
	        		
	        	}
				
				// Store selected item ID.ID assigned during Item creation
				
				
        		processed = false;
				
        		selectedID = getSelectId();

        		if ( selectedID != null && logger.isDebugEnabled()) {
					logger.debug( "ID class: " + selectedID.getClass().getSimpleName() + " Value: " + selectedID );
				}
        		
        		if ( selectedID >= 0 ) {
        			
        			model.setSelectedSite(( Site ) sitesTable.getItem( selectedID ).getItemProperty( "data" ).getValue());
        			
        		} else {
        			model.setSelectedSite( null );
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
		this.addComponent( sitesTable );
		this.addComponent( searchLayout );

		this.setExpandRatio( sitesTable, 1.0f );

		sitesTable.setSortContainerPropertyId( "name" );
		
	}

	public void setModel( SitesModel model ) {

		this.model = model;
		
		dataFromModel();

		
	}

	private long getSelectId() {
		
		
		return ( sitesTable.getValue() != null ? ( Long )sitesTable.getValue() : -1 );
	}
	
	public void selectItem( Site site ) {
		
		if ( site != null ) {
			selectItem( site.getId());
		}
	}
	
	public void selectItem( Object siteId ) {

		sitesTable.setValue( siteId );
		
	}
	
	public void selectItem( long siteId ) {

		sitesTable.setValue( siteId );
		
	}
	
	private void dataFromModel() {

	// Fill List of Reports
		
		// Store current selection
		long tmpSelectedID = getSelectId();
		// Remove all content
		sitesTable.removeAllItems();
		
		// Add items
		if ( model != null && model.getSites() != null ) {
			for ( Site site : model.getSites()) {
				if ( site != null ) {
					
					addOrUpdateItem( site, false );
					
				}
			}
			
			
		}
		
		
		// Sort
		sitesTable.sort();
		
		// Restore selection
		// if not possible than select FIRST item
		// Check that selection can be restored
		if ( tmpSelectedID >= 0 && sitesTable.getItem( selectedID ) != null  ) {

			selectItem( selectedID );
			
		} else {

			selectItem( sitesTable.firstItemId() );
			
		}
		sitesTable.setCurrentPageFirstItemId( getSelectId());
		
	}
	
	private  void addOrUpdateItem( Site site ) {
		addOrUpdateItem( site, false );
	}
	
	private  void addOrUpdateItem( Site site, boolean sortAfter ) {

		Item item = sitesTable.getItem( site.getId());
		
		if ( item == null ) {

			if ( logger.isDebugEnabled()) logger.debug( "Item will be added: " + site );
			item = sitesTable.addItem( site.getId() );
			
		} else {
			if ( logger.isDebugEnabled()) logger.debug( "Item exists already. Will be modified: " + site );
		}

//		LocalDate date = new LocalDate( report.getYear(), report.getMonth(), 1 );
		
		
		item.getItemProperty( "code" ).setValue( site.getSiteNumber());
		item.getItemProperty( "name" ).setValue( site.getName());
		item.getItemProperty( "data" ).setValue( site );
		
		// Add Delete button
		final NativeButton delButton = new NativeButton();
        delButton.setIcon( new ThemeResource( "icons/16/delete16.png"));
        delButton.setDescription( "Press the button to delete Site from Reporting" ); //model.getApp().getResourceStr( "company.list.delete.tooltip" ));

		delButton.setHeight( Integer.toString( BUTTON_WIDTH ) + "px" );
		delButton.setStyleName("v-nativebutton-deleteButton");
		delButton.addStyleName("v-nativebutton-link");
		delButton.setStyleName(Runo.BUTTON_LINK);

		delButton.setData( site );
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
						"Do you want to delete '" + (( Site )delButton.getData()).getName() + "' Site?", //template, 
						"OK", //model.getApp().getResourceStr( "general.button.ok" ), 
						"Cancel", //model.getApp().getResourceStr( "general.button.cancel" ), 
						new ConfirmDialog.Listener() {
							private static final long serialVersionUID = 1L;

							@Override
							public void onClose( ConfirmDialog dialog ) {
								if ( dialog.isConfirmed()) {
									// Confirmed to continue
									if ( !model.deleteSite(( Site ) delButton.getData())) {

										Notification.show( "Error", "Cannot delete Site", Notification.Type.ERROR_MESSAGE );
									}
	
								}
							}

				});

			}

		});

        item.getItemProperty( "button" ).setValue( delButton );//buttonsSet );

        if ( sortAfter ) {
        	sitesTable.sort();
        }
        
	}
	
	
	
	@Override
	public void added( Site site ) {
		
		if ( logger.isDebugEnabled()) logger.debug( "SitesList received 'Site Added' event" );

		addOrUpdateItem( site );
		
		selectItem( site );
		
	}

	@Override
	public void edited( Site site ) {

		if ( logger.isDebugEnabled()) logger.debug( "SitesList received 'Site Edited' event" );

		addOrUpdateItem( site );
		
	}

	@Override
	public void deleted( Site site ) {

		if ( logger.isDebugEnabled()) logger.debug( "SitesList received 'Site Deleted' event" );
		
		// Save prev item
//		final Object current = site;
		selectItem( site );

		Object future = sitesTable.prevItemId( site.getId());
		if ( future == null ) {
			future = sitesTable.firstItemId();
		}

		// delete Table item
		if ( sitesTable.containsId( site.getId())) {

			sitesTable.removeItem( site.getId());

			if ( future != null )
				selectItem( future );
			
		}
		
	}

	@Override
	public void selected( Site site ) {
		// Nothing to do here. It is returned event
	}
	
	private boolean searchFieldUpdated( String searchStr ) {
		
		boolean found = false;

		IndexedContainer container = ( IndexedContainer )sitesTable.getContainerDataSource();
		
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
