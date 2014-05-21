package com.c2point.tms.application;

import com.c2point.tms.entity.SessionData;
import com.c2point.tms.testing.TestData;
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

	private SessionData sessionData;
	
	VerticalLayout defaultLayout = new VerticalLayout();
	
	@Override
	protected void init(VaadinRequest request) {

		sessionData = new SessionData();
		
		TestData.init();
		
		
		defaultLayout = new VerticalLayout();
		defaultLayout.setMargin(true);
		setContent(defaultLayout);

//		startWizard();
//		startSitesView();
		startReportsView();
			
	}

	public SessionData getSessionData() {
		if ( sessionData == null ) {
			sessionData = new SessionData();
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
	private void startSitesView() {

		mainLayout = new FullReportView();

		mainLayout.setSizeFull();
        mainLayout.setMargin( true );
        setContent( mainLayout );
        
	}
	
	
	private void startReportsView() {

		
		mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        mainLayout.setMargin( true );
        setContent( mainLayout );
		
        AllReportsView view = new AllReportsView();
        view.setSizeFull();
        		
        mainLayout.addComponent( view );
        mainLayout.setComponentAlignment( view, Alignment.TOP_CENTER );
		
	}
	
}

