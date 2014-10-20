package com.c2point.tms.web.ui.taxreports;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.c2point.tms.entity_tax.CorrectionType;
import com.c2point.tms.entity_tax.TaxReport;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class SelectCorrectionType extends Window {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LogManager.getLogger( SelectCorrectionType.class.getName());
	
	private SelectCorrectionType.Listener	listener;
	
	private TaxReport 		report;
	private TaxReportsModel model;

	private CorrectionType 	type;

	private boolean		confirmed;
	
	public SelectCorrectionType( TaxReport report, TaxReportsModel model, SelectCorrectionType.Listener listener ) {
		
		super();
		
		this.report = report;
		this.model = model;
		this.listener = listener;
		
		this.type = CorrectionType.Edit;

		confirmed = false;
		
		initUI();
		
	}
	
	
	public void select() {
		
		center();
		// Open it in the UI
		UI.getCurrent().addWindow( this );		
		
	}
	
	public boolean getConfirmed() { return this.confirmed; }
	public CorrectionType getCorrectionType() { return this.type; }
	
	
	private void initUI() {
		
		this.setWidth( "20em" );
		this.setHeight( "20em" );
		this.setModal( true );
		
		this.setCaption( "Select Correction Type" );
		
		// Close listener implementation
        this.addCloseListener( new CloseListener() {

            private static final long serialVersionUID = 1L;

            public void windowClose(CloseEvent ce) {

                // Only process if still enabled
                if ( SelectCorrectionType.this.isEnabled()) {

                	SelectCorrectionType.this.setEnabled( false ); // avoid double processing
                	
                    if ( SelectCorrectionType.this.listener != null ) {
                    	
                    	if ( logger.isDebugEnabled()) logger.debug( "OnClose listener will be call with CLOSE button" );
                    	
                    	SelectCorrectionType.this.listener.onClose( SelectCorrectionType.this, null );
                    	
                    }
                }
            }
        });
		
		
		VerticalLayout vl = new VerticalLayout();
		vl.setSizeFull();
		vl.setMargin( true );
		vl.setSpacing( true );

		
		// Info Fields
		
		Label infoLabel = new Label();
		infoLabel.setContentMode( ContentMode.HTML );
		
		infoLabel.setValue(
//				  "<p>" + "Filer Information" + "</p>"		
				  "<h3><u>" + model.getSelectedOrganisation().getName() + "</u></h3>" + "<br>"  //model.getFiler().getName() ));
				+ "Date: " + "<b>" + report.getDate().monthOfYear().getAsText() + ", " + report.getDate().year().getAsText() + "</b>" + "<br>"
				+ "Type: " + "<b>" + report.getType() + "</b>" + "<br>" 
		
		);
		
		
		// Just buttons
		Button editButton = new Button( "Correction" );
		Button deleteButton = new Button( "Deletion" );
		
		editButton.addClickListener( new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick( ClickEvent event) {
				
				SelectCorrectionType.this.type = CorrectionType.Edit;
				
				SelectCorrectionType.this.closeDlg();
			}
			
		});
		deleteButton.addClickListener( new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick( ClickEvent event) {
				
				SelectCorrectionType.this.type = CorrectionType.Delete;
				
				SelectCorrectionType.this.closeDlg();
			}
			
		});
		
		
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSizeFull();
		hl.addComponent( editButton );
		hl.addComponent( deleteButton );
		
		vl.addComponent( infoLabel );
		vl.addComponent( new Label( " " ));
		vl.addComponent( hl );
		
		this.setContent( vl );
		
	}

	private void closeDlg() {
		
		SelectCorrectionType.this.confirmed = true;
		
		if ( this.isEnabled()) {
	
			this.setEnabled( false );
			
	        if ( this.listener != null ) {
	
	        	this.listener.onClose( this, this.type );
	        	
	        }
	
			super.close();
		}
	}
	
	public interface Listener {
		
		public void onClose( SelectCorrectionType dlg, CorrectionType type );
	}

	
}
