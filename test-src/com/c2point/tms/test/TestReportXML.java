package com.c2point.tms.test;

import javax.xml.bind.JAXBException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.c2point.tms.util.xml.*;
import com.c2point.tms.entity.taxreport.*;
import com.c2point.tms.entity_tax.Address;
import com.c2point.tms.entity_tax.Contact;
import com.c2point.tms.entity_tax.Contract;
import com.c2point.tms.entity_tax.Contractor;
import com.c2point.tms.entity_tax.IDType;
import com.c2point.tms.entity_tax.Person;
import com.c2point.tms.entity_tax.ReportType;
import com.c2point.tms.entity_tax.Site;
import com.c2point.tms.entity_tax.TaxReport;


public class TestReportXML extends TmsTestCase {
	
	private static Logger logger = LogManager.getLogger( TestReportXML.class.getName());

	@Before
	public void setUp() {

		super.setUp();
		
	}

	// What to test (ideas):
	//  - ReportType = null
	
	
	// Test 1. Just simple report creation and simple validation. No exceptions
	@Test
	public void testCreate_1() {
		
		TaxReport	report;
		Site		site1, site2;
		Person		person;
		Address 	siteAddress, contactAddress;
		Contact 	contact;
		Contract 	contract1, contract2;
		Contractor contractor1, contractor2;
		
		String 		XMLstr;

		
		report = new TaxReport( ReportType.Employees );
		
		// Add Site 1
		site1 = new Site();
		site1.setSiteVeroID( "12345" );
		site1.setAddress( new Address( "Kaxovskaja 9 B 876", "452113", "Helsinki", "FI" ));
		site1.setContact( new Contact( 
				"Petr", "Petrov", "040 0987654", "petr.petrov@pet.com", 
				new Address( "Sinkkitie 42 A", "452113", "Espoo", "FI" ) 
		));
		
		
		
		contractor1 = new Contractor( "Rakennus Nordin Oy", "12345678-9" );
		contractor1.setIdType( IDType.Vat );
		contractor1.setContact( new Contact(
				"Ivan", "Ivanov", "+358 40 123467898", "test@email.com", 
				null 
		));
		
		
		
		contract1 = new Contract( 
				contractor1,
				Contract.ContractType.Contract
		);
		contract1.setVatReverse( true );
		contract1.setInvoiced( 10000 );
		contract1.setPaid( 20000 );
		contract1.setAdvanced( 30000 );
		contract1.setStartDate(( LocalDate )null );
		contract1.setEndDate(( LocalDate )null );
		
		contractor2 = new Contractor( "AndiAmo Oy", "98765453-1" );
		contractor2.setIdType( IDType.Vat );
		contractor2.setContact( new Contact(
				"Petr", "Sidorov", "+358 40 9876543", "test_2@email.com", 
				null 
		));
		
		
		
		contract2 = new Contract( 
				contractor2,
				Contract.ContractType.Contract
				
		);
		contract2.setVatReverse( true );
		contract2.setInvoiced( 10000 );
		contract2.setPaid( 20000 );
		contract2.setAdvanced( 30000 );
		contract2.setStartDate(( LocalDate )null );
		contract2.setEndDate(( LocalDate )null );

		
				
		site1.addContract( contract1 );
		site1.addContract( contract2 );
/*		
		// Create contact  = person + address
		// Assign contact to site

		// Create Contractor and ContractDetails
		contractor = new Contractor( "Subcontractor 1 Oy", "11111-1" );
		details = new ContractDetails( true, BigDecimal.valueOf( 100000 ), new LocalDate( 2001, 1, 1), new LocalDate( 2001, 31, 12 ));
		//create contract
		//Fill contract
		contract = new Contract( contractor, details );
		
		// Now add Contract to Site 1
		site.addContract( contract );	
		
		// Add contract 2
*/		
		
		
		
		
		// Add Site 2
		site2 = new Site(); 
		site2.setSiteVeroID( "98765" );
		site2.setAddress( new Address( "Bolotnikovskaja 5 A 123", "113452", "Espoo", "FI" ) );
		site2.setContact( new Contact( 
				"Ivan", "Ivanov", "040 1234567", "ivan.ivanov@iva.com", 
				new Address( "Sinkkitie 51 B", "452113", "Espoo", "FI" ) 
		));

		
		
/*		
		assertEquals( "Wrong value", "Serge", contact.getFirstName());
		assertEquals( "Wrong value", "Seva", contact.getLastName());
		assertNull( "Should be null", contact.getPhoneNumber());
		assertNull( "Should be null", contact.getEmail());
		
		contact = new Contact( "Ivan", "Ivanov", "+358 40 123467898", "test@email.com" );
		
		assertNotNull( "Failed constructor", contact );
		
		assertEquals( "Wrong value", "Ivan", contact.getFirstName());
		assertEquals( "Wrong value", "Ivanov", contact.getLastName());
		assertEquals( "Wrong value", "+358 40 123467898", contact.getPhoneNumber());
		assertEquals( "Wrong value", "test@email.com", contact.getEmail());
*/
		
		// Add sites to report
		report.addSite( site1 );
		report.addSite( site2 );
		
		assertNotNull( "Failed constructor TaxReport", report );

		XMLstr = getXML( report );

		assertNotNull( "Failed to convert TaxReport", XMLstr );
		
	}

	private String getXML( Object object ) {

		String XMLstr = null;
		
		try {
			XMLstr = XMLconverter.convertToXML( object );
			logger.debug( "XML:\n" + XMLstr );
		} catch (JAXBException e) {
			logger.error( e );
			fail( "Cannot convert to XML" );
		}

		return XMLstr;
		
	}
}
