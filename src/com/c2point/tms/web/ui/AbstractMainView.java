package com.c2point.tms.web.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.c2point.tms.application.Taxreport_1UI;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public abstract class AbstractMainView extends VerticalLayout {
	private static Logger logger = LogManager.getLogger( AbstractMainView.class.getName());
	
	private enum InitType { NOT_STARTED, NOT_INIT, INIT } ;
	private InitType state;

	public AbstractMainView() {
		logger.debug( "AbstractMainView created" );

		state = InitType.NOT_STARTED;
		
		initUI();
	}

	protected abstract void initUI();
	
	public final void initData( ) {
		if ( state == InitType.NOT_STARTED ) {
			state = InitType.NOT_INIT;
		} // else 
		if ( state == InitType.NOT_INIT ) {
			logger.debug( "First time initialization..." );
			initDataAtStart();
			state = InitType.INIT;
		} else if ( state == InitType.INIT ) {
			logger.debug( "Repeate initialization..." );
			initDataReturn();
		}
	}
	
	protected abstract void initDataAtStart();
	protected abstract void initDataReturn();
	
	public Taxreport_1UI getTmsApplication() { return ( Taxreport_1UI )UI.getCurrent(); }
}
