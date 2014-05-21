package com.c2point.tms.testing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.c2point.tms.application.Taxreport_1UI;
import com.c2point.tms.entity.Organisation;
import com.c2point.tms.entity.TmsUser;
import com.c2point.tms.entity.taxreport.IDType;
import com.c2point.tms.datalayer.DataFacade;
import com.c2point.tms.datalayer.OrganisationFacade;
import com.vaadin.ui.UI;

public class TestData {

	private static Logger logger = LogManager.getLogger( TestData.class.getName());
	
	public static void init() {
		
		logger.error( "ATTENTION!!! Still Test Data has been used. Fill Reports !!!" );
		
		
		Organisation org = new Organisation( "0001", "Rakennus Nordic Oy" );
		org.setAddress( "Punavuorenkatu 2545, 001233 Sejnapuna4poki" );
		org.setTunnus( "154352-7" );
		org.setPhone( "0401234567" );
		org.setEmail( "test.email@email.com" );

		org.setIdType( IDType.Vat );
		org.setCountryCode( "FI" );

		
		org = OrganisationFacade.getInstance().addOrganisation( org );
		
		TmsUser user = new TmsUser( "123456", "Serguei", "Sevastianov" );
		user.setOrganisation( org );

		user = DataFacade.getInstance().insert( user );
		
		((Taxreport_1UI) UI.getCurrent()).getSessionData().setUser( user );

		
		
		
		
		
		
		
	}
	
}
