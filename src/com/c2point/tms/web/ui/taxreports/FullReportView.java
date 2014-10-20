package com.c2point.tms.web.ui.taxreports;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;

import com.c2point.tms.application.Taxreport_1UI;
import com.c2point.tms.entity_tax.ReportType;
import com.c2point.tms.entity_tax.TaxReport;
import com.c2point.tms.web.ui.AbstractMainView;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class FullReportView extends AbstractMainView {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger( FullReportView.class.getName());
	
	private AllReportsView			backToAllReports;
	
	private SitesModel				model;

	private Button 					backButton;
	
	private Label 					header;
	private SitesListComponent		sitesListComp;
	private SiteInfo				siteInfoComp;
	private ContractsListComponent	contractsListComp;
	private Component 				contractorInfoComp;
	
	public FullReportView(  AllReportsView backToAllReports, TaxReport report ) {
		
		super();

		this.backToAllReports = backToAllReports;
		
		this.model = createModel( report );

		dataToView();
		
		this.model.addListener( sitesListComp );
		this.model.addListener( siteInfoComp );
		this.model.addListener( contractsListComp );
		this.model.addListener( contractsListComp );

		sitesListComp.setModel( this.model );
		contractsListComp.setModel( this.model );

		
	}
	
	public void initUI() {
		
		this.setMargin( true );
		this.setSpacing( true );
		this.setSizeFull();
		
		sitesListComp = new SitesListComponent( null );
		siteInfoComp = new SiteInfo( null );
		contractsListComp = new ContractsListComponent();
		contractorInfoComp = new Label( "Contract Information", ContentMode.HTML );
		contractorInfoComp.addStyleName( "h2" );
		
		
		

		// Contractors List and one Contract Info
		HorizontalSplitPanel contractsPanel = new HorizontalSplitPanel();

		contractsPanel.setSplitPosition( 50, Unit.PERCENTAGE );
		contractsPanel.setSizeFull();
		contractsPanel.setLocked( false );
				
		contractsPanel.setFirstComponent( contractsListComp );
		contractsPanel.setSecondComponent( contractorInfoComp );
		
		// Vertical Site Info and Contractors Panel
		
		VerticalLayout vl = new VerticalLayout();
		vl.setSizeFull();
		
		vl.addComponent( siteInfoComp );
		vl.addComponent( contractsPanel );
		vl.setExpandRatio( contractsPanel, 1.0f );
		
		// HorisontalSplit: List of Sites and the rest
		HorizontalSplitPanel sitesSplit  = new HorizontalSplitPanel();
		sitesSplit.setSplitPosition( 25, Unit.PERCENTAGE );
		sitesSplit.setSizeFull();
		sitesSplit.setLocked( false );

		sitesSplit.setFirstComponent( sitesListComp );		
//		sitesSplit.setSecondComponent( siteInfoComp );
		sitesSplit.setSecondComponent( vl );
		
		backButton = new Button( "Return to Reports" );
		backButton.setIcon(new ThemeResource("icons/16/left16.png"));
		backButton.setStyleName( Reindeer.BUTTON_DEFAULT );
		backButton.setImmediate( true );
		
		backButton.addClickListener( new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				if ( logger.isDebugEnabled()) logger.debug( "'Return to Reports' button had been clicked!");
				
				gotoReportsView();
				
				
			}
			
		});
		
		// Add header
		header = new Label();
		header.setStyleName( "h1" );
		
		// At the end put everything in one
		
		this.addComponent( backButton );
		this.addComponent( header );
		this.addComponent( sitesSplit );
		
		this.setExpandRatio( sitesSplit, 1.0f );
		
	}
	
/*
	private SitesModel createModel() {
		
		SitesModel model = new SitesModel();
		
		model.setMainOrg( 
				(( Taxreport_1UI )UI.getCurrent()).getSessionData().getUser().getOrganisation()
		);
		
		
		model.initReports();
		
		
		return model;
	}
*/
	
	private SitesModel createModel( TaxReport report) {
		
		SitesModel model = new SitesModel( report );
		
		model.setMainOrg( 
				(( Taxreport_1UI )UI.getCurrent()).getSessionData().getUser().getOrganisation()
		);
		
		
		return model;
	}

	private void dataToView() {
		
		String headerString = "Report: ";
		
		ReportType type = this.model.getReport().getType();
		if ( type != null ) {
			headerString = headerString.concat( type.getName());
		}
		
		LocalDate date = this.model.getReport().getDate();
		if ( date != null ) {
			headerString = headerString.concat( " " + date.monthOfYear().getAsText( getLocale()) + ", " + date.year().getAsText());
		}
		
		header.setValue( headerString );
		
	}

	private void gotoReportsView() {
		
		if ( this.backToAllReports != null ) {
			
			backToAllReports.returnToAllReports( this );
		}
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
