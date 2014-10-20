package com.c2point.tms.test;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContextListener;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.c2point.tms.util.xml.*;
import com.c2point.tms.application.TmsContextListener;
import com.c2point.tms.datalayer.DataFacade;
import com.c2point.tms.datalayer.OrganisationFacade;
import com.c2point.tms.entity.Organisation;
import com.c2point.tms.entity_tax.Address;
import com.c2point.tms.entity_tax.Contact;
import com.c2point.tms.entity_tax.IDType;
import com.c2point.tms.entity_tax.Person;

public class TestDBOrg extends TmsTestCase {
	
	private static Logger logger = LogManager.getLogger( TestDBOrg.class.getName());

	@Before
	public void setUp() {

		super.setUp();
		
	}

	@Test
	public void testCreate_1() {
		
		Organisation org = new Organisation( "0001", "Rakennus Nordic Oy" );
		org.setAddress( "Punavuorenkatu 2545, 001233 Sejnapuna4poki" );
		org.setTunnus( "154352-7" );
		org.setPhone( "0401234567" );
		org.setEmail( "test.email@email.com" );

		org.setIdType( IDType.Vat );
		org.setCountryCode( "FI" );

		org = OrganisationFacade.getInstance().addOrganisation( org );
		assertNotNull( "Failed write Org to DB", org );

		org = new Organisation( "0002", "Other Company Oy" );
		org.setAddress( "Sinkkitie 2545, 001233 Sejnapuna4poki" );
		org.setTunnus( "222222-2" );
		org.setPhone( "0409876543" );
		org.setEmail( "test2.email@email.com" );

		org.setIdType( IDType.TReg );
		org.setCountryCode( "RU" );

		
		org = OrganisationFacade.getInstance().addOrganisation( org );
		assertNotNull( "Failed write Org to DB", org );

		
		Collection<Organisation> list = DataFacade.getInstance().list( Organisation.class );
		assertEquals( "Wrong number of records", 2, list.size());
		
		Iterator<Organisation> it = list.iterator();
		
		while (it.hasNext()) {
			org = it.next();

			logger.debug( "IDType: " + org.getIdType());
			assertNotNull( "Wrong ID Type", org.getIdType() );
			logger.debug( "CCode: " + org.getCountryCode());
			assertNotNull( "Wrong Country Code", org.getCountryCode() );
		}
		
	}
	
	
	
}
