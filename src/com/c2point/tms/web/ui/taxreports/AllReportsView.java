package com.c2point.tms.web.ui.taxreports;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;

import com.c2point.tms.entity.Organisation;
import com.c2point.tms.entity.taxreport.TaxReport;
import com.c2point.tms.web.ui.AbstractMainView;
import com.c2point.tms.web.ui.listeners.TaxReportModelListener;
import com.c2point.tms.web.ui.MainView;
import com.c2point.tms.web.ui.taxreports.FullReportView;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.Runo;

public class AllReportsView extends AbstractMainView implements TaxReportModelListener {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger( AllReportsView.class.getName());

	private TabSheet		parentTabSheet;
	
	private TaxReportsModel model;

	private Label 			companyInfo;
	private Button 			addButton;
	private Table 			reportsTable;
	private Button 			selectButton;
	
	private boolean processed = false;
	private Object selectedID = null;
	
	public AllReportsView( TabSheet parentTabSheet, Organisation org ) {
		super();

		this.parentTabSheet = parentTabSheet;
		this.model = new TaxReportsModel( org );
		
		dataFromModel();

		model.addListener( this );
		
	}
	
	protected void initUI() {

		setMargin( true );
		setSpacing( true );
		setSizeFull();

		
		Component companyInfo = getCompanyInfo();

		addButton = new Button();
		addButton.setSizeUndefined();
		addButton.setIcon(new ThemeResource("icons/16/add16.png"));

		addButton.addClickListener( new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick( ClickEvent event ) {

				createNewReport();
				
			}
			
			
		}); 
		
		Component reportsComp = getReportsComponent();

		selectButton = new Button( "Select" );
//		selectButton.setSizeUndefined();
		selectButton.setEnabled( false );

		selectButton.addClickListener( new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick( ClickEvent event ) {

				Object itemId = reportsTable.getValue();
				if ( itemId != null ) {
					
					TaxReport report = null;
					
					try {
						
						report = ( TaxReport )reportsTable.getItem( itemId ).getItemProperty( "data" ).getValue();
						
					} catch( Exception e ) {
						logger.error( "Cannot fetch TaxReport from Table item ('data' property)\n" + e );
					}
					
					if ( report != null ) {

						gotoSitesView( report );
						
					}
				}
				
				
			}
			
			
		}); 
		
		addComponent( companyInfo );
		addComponent( addButton );
		addComponent( reportsComp );
		addComponent( selectButton );
//		vl.addComponent( buttonBar );
		setExpandRatio( reportsComp, 1.0f );
		
	}

	private Component getCompanyInfo() {

		HorizontalLayout hl = new HorizontalLayout();
		hl.setMargin( true );
		hl.setSpacing( true );
		

		companyInfo = new Label( "", ContentMode.HTML ); 
		
		hl.addComponent( companyInfo );

		return hl;
	}
	
	private Component getReportsComponent() {
		
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSizeFull();
		
		Panel infoPanel = new Panel( "Info" );
		infoPanel.setStyleName( Runo.PANEL_LIGHT );
		
		hl.addComponent( getReportsTable());
		hl.addComponent( infoPanel );
		
		return hl;
	}

	private Component getReportsTable() {
		
		reportsTable = new Table();
		
		reportsTable.setSelectable( true );
		reportsTable.setNullSelectionAllowed( false );
		reportsTable.setMultiSelect( false );
		reportsTable.setColumnCollapsingAllowed( false );
		reportsTable.setColumnReorderingAllowed( false );
		reportsTable.setImmediate( true );
		reportsTable.setSizeFull();
		
		reportsTable.addContainerProperty( "date", LocalDate.class, null );
		reportsTable.addContainerProperty( "type", String.class, null );
		reportsTable.addContainerProperty( "status", String.class, null );
		reportsTable.addContainerProperty( "org", String.class, null );
//		reportsTable.addContainerProperty( "delete", Button.class, null );
		reportsTable.addContainerProperty( "data", TaxReport.class, null );

		reportsTable.setSortAscending( true );
		reportsTable.setSortContainerPropertyId( "date" );

		reportsTable.addGeneratedColumn("date", new Table.ColumnGenerator() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Object generateCell( Table source, Object itemId,
					Object columnId ) {
				
				// Get the object stored in the cell as a property
				Property prop = source.getItem( itemId ).getItemProperty( columnId );
				if ( prop.getType().equals( LocalDate.class )) {
					
					LocalDate date = ( LocalDate ) prop.getValue(); 
					if ( date != null ) {

						return new Label( date.monthOfYear().getAsText( getLocale()) + ", " + date.year().getAsText());
					}
				}
				
				return null;
			}
			
		});		
		
		reportsTable.setVisibleColumns( new Object [] { "date", "type", "status", "org" });
		reportsTable.setColumnHeaders( new String[] { "Date", "Type", "Status", "Company" }); 

		
		
//		reportsTable.setColumnWidth( "buttons", BUTTON_WIDTH * 3 );
		
		// Handles the click in the item. NOT USED  YET!
		reportsTable.addItemClickListener( new ItemClickListener() {

			private static final long serialVersionUID = 1L;

			public void itemClick(ItemClickEvent event) {

            	if ( logger.isDebugEnabled()) {
            		if ( event.isDoubleClick()) 
                		logger.debug( "TaxReport table item had been DOUBLE clicked" );
            		else 
            			logger.debug( "TaxReport table item had been clicked" );
            		
            	}
            	
            	if ( selectedID == event.getItemId() ) {
            		if ( logger.isDebugEnabled()) logger.debug( "Selection was NOT changed. Can be used for Open/Edit" );
            		
            		if ( !processed ) {
            			
            			processed = true;
            			
        				Object itemId = reportsTable.getValue();
        				
        				if ( itemId != null ) {
        					
        					TaxReport report = null;
        					
        					try {
        						
        						report = ( TaxReport )reportsTable.getItem( itemId ).getItemProperty( "data" ).getValue();
        						
        					} catch( Exception e ) {
        						logger.error( "Cannot fetch TaxReport from Table item ('data' property)\n" + e );
                    			processed = false;

        					}
	
	       					if ( report != null ) {
	
	    						gotoSitesView( report );
	    						
	    					}
            			
        				} else {
        					
                			processed = false;
        					
        				}
            			
            			
            		}
            		
            		
            		
            	} else {
            		if ( logger.isDebugEnabled()) logger.debug( "Selection was changed" );
            		
            	} 
            	
            	
            }
        });

		// Handle selection of item
		reportsTable.addValueChangeListener( new ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange( ValueChangeEvent event ) {

				if ( logger.isDebugEnabled()) {
	        		logger.debug( "TaxReport table selection changed to id = " + reportsTable.getValue());
	        		
	        	}
				
				// Store selected item ID.ID assigned during Item creation
//        		processed = false;
				
        		selectedID = reportsTable.getValue();
        				
				updateUI();
				
			}
		});

		
		return reportsTable;
	}
	
	private void dataFromModel() {

	// Fill Filer

		companyInfo.setValue(
//				  "<p>" + "Filer Information" + "</p>"		
				  "<h3><u>" + model.getSelectedOrganisation().getName() + "</u></h3>" + "<br>"
				+ "Code: " + "<b>" + model.getSelectedOrganisation().getCode() + "</b>" + "<br>"  //model.getFiler().getCode()));
				+ "Y-Tunnus: " + "<b>" + model.getSelectedOrganisation().getTunnus() + "</b>" + "<br>" 
				+ "Address: " + "<b>" + model.getSelectedOrganisation().getAddress() + "</b>" + "<br>"
		
		);
		
		
	// Fill List of Reports
		
		// Store current selection
		Object selectedID = reportsTable.getValue();
		// Remove all content
		reportsTable.removeAllItems();
		
		// Add items
		if ( model.getReports() != null ) {
			for ( TaxReport report : model.getReports()) {
				if ( report != null ) {
					
					addOrUpdateItem( report );
					
				}
			}
			
			
		}
		
		
		// Sort
		reportsTable.sort();
		
		// Restore selection
		// if not possible than select FIRST item
		// Check that selection can be restored
		if ( reportsTable.getItem( selectedID ) != null  ) {

			reportsTable.setValue( selectedID );
			
		} else {

			reportsTable.setValue( reportsTable.firstItemId() );
			
		}
		reportsTable.setCurrentPageFirstItemId( reportsTable.getValue());
		
		
		
	}

	private  void addOrUpdateItem( TaxReport report ) {

		Item item = reportsTable.getItem( report.getId());
		
		if ( item == null ) {

			if ( logger.isDebugEnabled()) logger.debug( "Item will be added: " + report );
			item = reportsTable.addItem( report.getId());
			
		} else {
			if ( logger.isDebugEnabled()) logger.debug( "Item exists already. Will be modified: " + report );
		}

//		LocalDate date = new LocalDate( report.getYear(), report.getMonth(), 1 );
		
		item.getItemProperty( "data" ).setValue( report );
		item.getItemProperty( "date" ).setValue( report.getDate());//.monthOfYear().getAsText( getLocale()) + ", " + date.year().getAsText());
		item.getItemProperty( "type" ).setValue( report.getType().toString() );
		item.getItemProperty( "status" ).setValue( report.getStatus().toString() );
		item.getItemProperty( "org" ).setValue( model.getSelectedOrganisation().getName());
		
	}
	
	private void createNewReport() {
		
		new SelectReportType( model.getTaxReportInfo(), new SelectReportType.Listener() {
			
			@Override
			public void onClose( SelectReportType dlg ) {
				
				if ( dlg.getConfirmed()) {

					if ( logger.isDebugEnabled()) 
						logger.debug( "OK button has been pressed. Selected ReportType: " + dlg.getReportType()
									+ ", Date: " + dlg.getDate()
						);

					TaxReport report = model.createNewReport( dlg.getReportType(), dlg.getDate());
					
				} else {

					if ( logger.isDebugEnabled()) logger.debug( "Cancel button has been pressed. No ReportType to return" );
					
				}
				
			}
		}).select();

		
		/*		
*/		
		
	}

	private void askCorrectionTypeAndGoNext( TaxReport report) {
/*		
		new SelectCorrectionType( report, model, new SelectCorrectionType.Listener() {
			
			@Override
			public void onClose( SelectCorrectionType dlg, CorrectionType type ) {
				
				if ( type != null && dlg.getConfirmed()) {

					if ( logger.isDebugEnabled())  logger.debug( "CorrectionType '" + type + "' has been selected. GO NEXT!" );
					
				} else {
					
					if ( logger.isDebugEnabled())  logger.debug( "No type CorrectionType has been selected. Nothing to do!" );
				}
				
			}
		}).select();
*/
	}	
		
	private void gotoSitesView( TaxReport report ) {
		
		if ( report != null && parentTabSheet != null ) {
			
			FullReportView fullReportView = new FullReportView( this, report );
			
			parentTabSheet.replaceComponent( this, fullReportView );
			
		}
		
		
	}

	public void returnToAllReports( AbstractMainView view ) {

		parentTabSheet.replaceComponent( view, this );
		
		processed = false;
		
	}
	
	private void updateUI() {
		
		boolean selectionFlag = reportsTable.getValue() != null;
		
		selectButton.setEnabled( selectionFlag );
	}
	
	@Override
	public void added( TaxReport report ) {

		if ( logger.isDebugEnabled()) logger.debug( "TaxReportAdded event received by View" );
		
		if ( report != null ) {
			
			addOrUpdateItem( report );
			
			reportsTable.setValue( report.getId());
			reportsTable.setCurrentPageFirstItemId( report.getId());
			
		} else {
			Notification.show( "Error", "Could not add report! Contact service support.", Type.ERROR_MESSAGE );
		}
		
	}

	@Override
	public void edited(TaxReport report) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleted(TaxReport report) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initDataAtStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initDataReturn() {
		// TODO Auto-generated method stub
		
	}
	
}
