package com.c2point.tms.web.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.c2point.tms.testing.TestData;
import com.c2point.tms.web.ui.taxreports.AllReportsView;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;

public class MainView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger( MainView.class.getName());

	private TabSheet mainTabSheet;
	private SubTabSheet tab1;
	private SubTabSheet tab2;
	private SubTabSheet tab3;
	
	public MainView() {
//		this.app = app;
	}
	
	public void initWindow() {
		setSizeFull();
		setSpacing( true );
		
		getTabComponent();
		addComponent( mainTabSheet );
		setExpandRatio( mainTabSheet, 1.0F );
		
	}


	Tab taxTab = null;
	private Component getTabComponent() {

		mainTabSheet = new MainTabSheet();
		
		mainTabSheet.setSizeFull();
		
		tab1 = new SubTabSheet();
		tab1.addTab( new VerticalLayout(), "1" );
		tab1.addTab( new VerticalLayout(), "2" );
		
		taxTab = tab1.addTab( new AllReportsView( tab1, TestData.getTestOrganisation()), "Tax Reports" );
		
		mainTabSheet.addTab( tab1, "One" );
		

		tab2 = new SubTabSheet();
		tab2.addTab( null, "X" );
		tab2.addTab( null, "Y" );
		tab2.addTab( null, "Z" );
		mainTabSheet.addTab( tab2, "Two" );
		
		tab3 = new SubTabSheet();
		tab3.addTab( null, "" );
		tab3.addTab( null, "" );
		mainTabSheet.addTab( tab3, "Three" );

		return mainTabSheet;
	}
	
	public Tab getTaxTab() { return taxTab; }




}

