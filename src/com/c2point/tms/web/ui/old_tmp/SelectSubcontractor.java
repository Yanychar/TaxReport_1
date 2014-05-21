package com.c2point.tms.web.ui.old_tmp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.c2point.tms.entity.Organisation;
import com.c2point.tms.web.ui.ButtonBar;
import com.c2point.tms.web.ui.ModType;
import com.c2point.tms.web.ui.subcontract.SubContrModel;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class SelectSubcontractor extends Window {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger( SelectSubcontractor.class.getName());
	
	private SubContrModel				model;

	private SubContrListComponent		subContrListComp;
	
	
	public SelectSubcontractor( Organisation mainOrg ) {

		if ( mainOrg == null ) {
			throw new IllegalArgumentException( "Valid Organisation shall be specified!" );
		}

		initUI();
		
		this.model = new SubContrModel( mainOrg );
		
		
		this.model.addListener( subContrListComp );

		subContrListComp.setModel( this.model );
		
	}
	
	public void initUI() {

		setCaption( "Select Contractor" );
		setModal( true );
		
		VerticalLayout content = new VerticalLayout();
		content.setMargin( new MarginInfo( true, true, false, true ));
//		setMargin( true );
		content.setSpacing( true );
		content.setSizeUndefined();

		subContrListComp = new SubContrListComponent( null );
		
		ButtonBar btb = new ButtonBar();
		btb.setEnabled( ButtonBar.ButtonType.Ok, true );
		btb.addClickListener( ButtonBar.ButtonType.Ok, new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick( ClickEvent event ) {

				// TODO:  Pass selected Subcontractor back to Contract
				//
				close();
				
			}
			
		});
		
		btb.addClickListener( ButtonBar.ButtonType.Cancel, new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				close();
				
			}
			
		});

		content.addComponent( subContrListComp );
		content.addComponent( btb );

		content.setExpandRatio( subContrListComp, 1.0f );
		
		setContent( content );
		
	}
	


	
}
