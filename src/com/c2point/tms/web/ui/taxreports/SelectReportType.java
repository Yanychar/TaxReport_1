package com.c2point.tms.web.ui.taxreports;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;

import com.c2point.tms.datalayer.tax.TaxReportInfoIF;
import com.c2point.tms.entity.taxreport.ReportType;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class SelectReportType extends Window {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LogManager.getLogger( SelectReportType.class.getName());

	private SelectReportType.Listener	listener;
	private TaxReportInfoIF 			trInfo;
	
	private ReportType 	type;
	private LocalDate	date;
	
	private ComboBox comboYear;
	private ComboBox comboMonth;
	
	private boolean		confirmed;
	
	public SelectReportType( TaxReportInfoIF trInfo, SelectReportType.Listener listener ) {
		this( trInfo, ReportType.Employees, listener );
	}
	
	public SelectReportType( TaxReportInfoIF trInfo, ReportType type, SelectReportType.Listener listener ) {
		super();
		
		this.trInfo = trInfo;
		this.listener = listener;
		
		this.type = type;
		initDateField();
		
		confirmed = false;
		
		initUI();
		
	}
	
	
	public ReportType select() {
		
		center();
		// Open it in the UI
		UI.getCurrent().addWindow( this );		
		
		return type; 
	}
	
	public boolean getConfirmed() { return this.confirmed; }
	public ReportType getReportType() { return this.type; }
	
//	public int getMonth() { return this.date.getDayOfMonth(); }
//	public int getYear() { return this.date.getYear(); }
	public LocalDate getDate() { return this.date; }
	
	private void initUI() {
		
		this.setWidth( "20em" );
		this.setHeight( "20em" );
		this.setModal( true );
		
		this.setCaption( "New Tax report" );
		
		// Close listener implementation
        this.addCloseListener( new CloseListener() {

            private static final long serialVersionUID = 1L;

            public void windowClose(CloseEvent ce) {

                // Only process if still enabled
                if ( SelectReportType.this.isEnabled()) {

                	SelectReportType.this.setEnabled( false ); // avoid double processing
                	
                    if ( SelectReportType.this.listener != null ) {
                    	
                    	if ( logger.isDebugEnabled()) logger.debug( "OnClose listener will be call" );
                    	
                    	SelectReportType.this.listener.onClose( SelectReportType.this );
                    	
                    }
                }
            }
        });
		
		
		VerticalLayout vl = new VerticalLayout();
		vl.setSizeFull();
		vl.setMargin( true );
		vl.setSpacing( true );

		
		// Calendar Fields
		HorizontalLayout calendarFields = new HorizontalLayout();
		calendarFields.setSpacing( true );
		calendarFields.setSizeFull();

		calendarFields.addComponent( getMonthCombo());
		calendarFields.addComponent( getYearCombo());
		Label glue = new Label( "" );
		calendarFields.addComponent( glue );
		calendarFields.setExpandRatio( glue,  1.0f );
		
		// Report Type fields
		
		OptionGroup optionGroup = new OptionGroup( "Select Report Type:");
		optionGroup.setMultiSelect( false );
		optionGroup.setNullSelectionAllowed( false );
		optionGroup.setImmediate( true );
        
		optionGroup.addItem( ReportType.Employees );
		optionGroup.addItem( ReportType.Contracts );

		optionGroup.setItemCaption( ReportType.Employees, ReportType.Employees.toString());
		optionGroup.setItemCaption( ReportType.Contracts, ReportType.Contracts.toString());
		
		optionGroup.select( this.type );
		
		optionGroup.addValueChangeListener( new ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange( ValueChangeEvent event ) {
				
				SelectReportType.this.type = ( ReportType )event.getProperty().getValue();
				
			}
			
		});
		
		// Just button
		Button okButton = new Button( "OK" );
		Button cancelButton = new Button( "Cancel" );
		
		okButton.addClickListener( new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick( ClickEvent event) {
				
				SelectReportType.this.confirmed = true;

				if ( SelectReportType.this.isEnabled()) {

					SelectReportType.this.setEnabled( false );
					
                    if ( SelectReportType.this.listener != null ) {

                    	if ( logger.isDebugEnabled()) logger.debug( "OnClose listener will be call" );
                    	SelectReportType.this.listener.onClose( SelectReportType.this );
                    	
                    }

					SelectReportType.this.close();
				}
			}
			
		});
		
		cancelButton.addClickListener( new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick( ClickEvent event) {
				
				SelectReportType.this.confirmed = false;

				if ( SelectReportType.this.isEnabled()) {

					SelectReportType.this.setEnabled( false );
					
                    if ( SelectReportType.this.listener != null ) {

                    	if ( logger.isDebugEnabled()) logger.debug( "OnClose listener will be call" );
                    	SelectReportType.this.listener.onClose( SelectReportType.this );
                    	
                    }

					SelectReportType.this.close();
				}
			}
			
		});
		
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSizeFull();
		hl.addComponent( okButton );
		hl.addComponent( cancelButton );
		
		vl.addComponent( calendarFields );
		vl.addComponent( optionGroup );
		vl.addComponent( new Label( " " ));
		vl.addComponent( hl );
		
		this.setContent( vl );
		
	}

	public interface Listener {
		
		public void onClose( SelectReportType dlg );
	}

	private void initDateField() {
		
		if ( trInfo != null ) {
		
			this.date = trInfo.getNewReportTime( this.type );
			
			if ( this.date == null ) {
				this.date = LocalDate.now();
			}

		} else {
			this.date = LocalDate.now();
		}
		
	}

	private Component getMonthCombo() {
	
		comboMonth = new ComboBox( "Month:" );
		comboMonth.setWidth( "20ex" );
		comboMonth.setImmediate( true );
		
		comboMonth.addValueChangeListener( new ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				
				SelectReportType.this.date = SelectReportType.this.date.withMonthOfYear(
						( Integer ) comboMonth.getValue());
				
				logger.debug( "Clas of value is: " + comboMonth.getValue().getClass().getSimpleName());
				
			}
			
		});

		LocalDate tmpDate = new LocalDate( 2000, 1, 1 ); 
		for ( int i = 1; i <= 12; i++ ) {
			
			comboMonth.addItem ( i );
			comboMonth.setItemCaption( i, tmpDate.monthOfYear().getAsText( getLocale()));
			
			tmpDate = tmpDate.plusMonths( 1 );
			
		}

		comboMonth.select( this.date.getMonthOfYear());

		return comboMonth; 
	}
	
	private Component getYearCombo() {
		
		comboYear = new ComboBox( "Year:" );
		comboYear.setWidth( "10ex" );
		comboYear.setImmediate( true );

		comboYear.addValueChangeListener( new ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				SelectReportType.this.date = SelectReportType.this.date.withYear(
						( Integer ) comboYear.getValue());
				
				logger.debug( "Class of value is: " + comboMonth.getValue().getClass().getSimpleName());
				logger.debug( "Value is: " + comboMonth.getValue());
				
			}
			
		});

		LocalDate tmpDate = LocalDate.now().minusYears( 1 );
		for ( int i = 1; i <= 3; i++ ) {
			
			comboYear.addItem ( tmpDate.getYear());
			comboYear.setItemCaption( i, tmpDate.year().getAsString());

			tmpDate = tmpDate.plusYears( 1 );
			
		}

		comboYear.select( this.date.getYear());

		return comboYear; 
	}
		
	
}
