package com.c2point.tms.web.ui.taxreports;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.c2point.tms.application.Taxreport_1UI;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class FullReportView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger( FullReportView.class.getName());
	
	private SitesModel				model;

	private SitesListComponent		sitesListComp;
	private SiteInfo				siteInfoComp;
	private ContractsListComponent	contractsListComp;
	private Component 				contractorInfoComp;
	
	
	public FullReportView() {

		this( null );
		
	}
	
	public FullReportView( SitesModel model ) {
		super();

		
		initUI();
		
		if ( model != null ) {
			this.model = model;
		} else {
			this.model = createModel();
		}


		this.model.addListener( sitesListComp );
		this.model.addListener( siteInfoComp );
		this.model.addListener( contractsListComp );
		this.model.addListener( contractsListComp );

		sitesListComp.setModel( this.model );
		contractsListComp.setModel( this.model );
		
	}
	
	public void initUI() {
		
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
		
		// Add header
		Label header = new Label();
		header.setStyleName( "h1" );
		header.setValue( "Tax Reporting. April, 2014" );
		
		// At the end put everything in one
		
		this.addComponent( header );
		this.addComponent( sitesSplit );
		
		this.setExpandRatio( sitesSplit, 1.0f );
		
	}
	

	private SitesModel createModel() {
		
		SitesModel model = new SitesModel();
		
		model.setMainOrg( 
				(( Taxreport_1UI )UI.getCurrent()).getSessionData().getUser().getOrganisation()
		);
		
		
		model.initReports();
		
		
		return model;
	}

	
}
