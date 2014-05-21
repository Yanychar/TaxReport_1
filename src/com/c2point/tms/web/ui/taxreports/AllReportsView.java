package com.c2point.tms.web.ui.taxreports;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;

import com.c2point.tms.entity.taxreport.TaxReport;
import com.c2point.tms.web.ui.listeners.TaxReportModelListener;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.Runo;

public class AllReportsView extends VerticalLayout implements TaxReportModelListener {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger( AllReportsView.class.getName());

	private TaxReportsModel model;

	private Table 			reportsTable = new Table();
	private Label 			companyInfo = new Label( "", ContentMode.HTML );
	
	public AllReportsView() {

		this( null );
		
	}
	
	public AllReportsView( TaxReportsModel model ) {
		super();

		initUI();
		
		if ( model != null ) {
			this.model = model;
		} else {
			this.model = createModel();
		}

		dataFromModel();
		
	}
	
	protected void initUI() {

		setMargin( true );
		setSpacing( true );
		setSizeFull();

		
//		Component progressBar = getProgressBar();
		Component companyInfo = getCompanyInfo();
//		Component reportsTable = getReportsTable();
		Button addButton = new Button ( "New Report" );
		Component reportsComp = getReportsComponent();
//		Component buttonBar = getButtonBar();
		
		
//		vl.addComponent( progressBar );
		addComponent( companyInfo );
		addComponent( addButton );
		addComponent( reportsComp );
//		vl.addComponent( buttonBar );
		setExpandRatio( reportsComp, 1.0f );
		

		addButton.addClickListener( new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick( ClickEvent event ) {

				createNewReport();
				
			}
			
			
		}); 
		
		
	}

	private Component getCompanyInfo() {

		HorizontalLayout hl = new HorizontalLayout();
		hl.setMargin( true );
		hl.setSpacing( true );
		

//		Button editCompanyInfo = new Button( "Edit" ); 
		
		hl.addComponent( companyInfo );
//		hl.addComponent( editCompanyInfo );
		
		
		
		/*		
		companyInfo.setRows( 10 );
		companyInfo.setColumns( 50 );
		
		companyInfo.setCaption( "Filer Information" );
*/
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

	private boolean processed = false;
	private Object selectedID = null;
	
	private Component getReportsTable() {
		
		reportsTable.setCaption( "List of Tax Reports" );
		
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

						return new Label( date.monthOfYear().getAsText( getLocale()) + ", " + date.year().getAsText() );
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
            	
            	if ( selectedID == event.getItemId() && !processed ) {
            		processed = true;
            		if ( logger.isDebugEnabled()) logger.debug( "Selection was NOT changed" );
            		
            	} else {
            		processed = false;
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
	        		logger.debug( "TaxReport table item changed selection status to " + reportsTable.getValue());
	        		
	        	}
				
				// Store selected item ID.ID assigned during Item creation
        		processed = false;
        		selectedID = reportsTable.getValue();
        				
			}
		});

		
		return reportsTable;
	}
	
	private void dataFromModel() {

	// Fill Filer

		companyInfo.setValue(
//				  "<p>" + "Filer Information" + "</p>"		
				  "<h3><u>" + model.getMainOrg().getName() + "</u></h3>" + "<br>"
				+ "Code: " + "<b>" + model.getMainOrg().getCode() + "</b>" + "<br>"  //model.getFiler().getCode()));
				+ "Y-Tunnus: " + "<b>" + model.getMainOrg().getTunnus() + "</b>" + "<br>" 
				+ "Address: " + "<b>" + model.getMainOrg().getAddress() + "</b>" + "<br>"
		
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
		
		
		item.getItemProperty( "data" ).setValue( report);
		item.getItemProperty( "date" ).setValue( report.getDate());//.monthOfYear().getAsText( getLocale()) + ", " + date.year().getAsText());
		item.getItemProperty( "type" ).setValue( report.getType().toString() );
		item.getItemProperty( "status" ).setValue( report.getStatus().toString() );
		item.getItemProperty( "org" ).setValue( model.getMainOrg().getName());
		
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

					if ( logger.isDebugEnabled()) logger.debug( "Close button has been pressed. No ReportType to return" );
					
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
		
	
	private TaxReportsModel createModel() {
		
		TaxReportsModel model = new TaxReportsModel();
		
		model.initReports();
		
		model.addListener( this );
		
		return model;
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
	
}
