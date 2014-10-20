package com.c2point.tms.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.c2point.tms.entity.SessionData;
import com.c2point.tms.testing.TestData;
import com.c2point.tms.web.ui.MainView;
import com.c2point.tms.web.ui.taxreports.FullReportView;
import com.c2point.tms.web.ui.taxreports.AllReportsView;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("taxreport_1")
public class Taxreport_1UI extends UI {

	private static Logger logger = LogManager.getLogger( Taxreport_1UI.class.getName());

	private SessionData sessionData;
	
	private MainView mainView;
	
	
	@Override
	protected void init(VaadinRequest request) {

		sessionData = new SessionData();

		TestData.initOrganisationAndUser();
		
		if ( mainView == null ) {
			
			mainView = new MainView();
			mainView.initWindow();
			
		}

		setContent( mainView );
			
	}

	public SessionData getSessionData() {
		if ( sessionData == null ) {
			sessionData = new SessionData();
			
			TestData.initOrganisationAndUser();
			
		}

		return sessionData;
	}

	

//	private TaxWizard wizard;
    private VerticalLayout mainLayout;

/*    
	private void startWizard() {
		

//		setContent( new TaxReportsPage());
		
		mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        mainLayout.setMargin(true);
        setContent(mainLayout);


        // create the Wizard component and add the steps
        wizard = new TaxWizard();
        wizard.addPage( new TaxReportsPage( "Report Selection" ));
        wizard.addPage( new SitesSelectionPage( "Edit Sites Information" ));      
        wizard.addPage( new ThirdTestPage( "TestThird Page" ));      
        
        
//        wizard.setHeight("600px");
//        wizard.setWidth("800px");
        wizard.setSizeFull();

        mainLayout.addComponent(wizard);
        mainLayout.setComponentAlignment(wizard, Alignment.TOP_CENTER);
	
	}
*/
    
/*    
	private void startSitesView() {

		mainLayout = new FullReportView();

		mainLayout.setSizeFull();
        mainLayout.setMargin( true );
        setContent( mainLayout );
        
	}
	
	
	private void startReportsView() {

		mainLayout = new AllReportsView( TestData.getTestOrganisation());

		mainLayout.setSizeFull();
        mainLayout.setMargin( true );
        setContent( mainLayout );
		
	}
*/
	public String getResourceStr( String key ) {
		
		try {
			return this.getSessionData().getBundle().getString( key );
		} catch (Exception e) {
			logger.error(  "Could not find string resource '" + key + "'" );
		}
		return "";
	}

	
}

