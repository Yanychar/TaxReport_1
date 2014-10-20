package com.c2point.tms.test;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.c2point.tms.util.xml.*;
import com.c2point.tms.entity_tax.Address;
import com.c2point.tms.entity_tax.Contact;
import com.c2point.tms.entity_tax.Contractor;
import com.c2point.tms.entity_tax.IDType;

public class TestContactor extends TmsTestCase {
	
	private static Logger logger = LogManager.getLogger( TestContactor.class.getName());

	@Before
	public void setUp() {

		super.setUp();
		
	}

	
	// Test. Creation
	// 	- with null values
	//	- with basic fields
	//	- set enums and set them to NULL
	
	// Test. Convert to XML
	//   - Not null fields shall be found
	//   - NUL fields shall be missed
	
	// Test 4. Storage of enum in XML
	//   - Not null fields shall be found
	//   - NUL fields shall be missed
	
	
	
	@Test
	public void testCreate_1() {
		
		Contractor contractor;
		
		contractor = new Contractor();
		
		assertNotNull( "Failed constructor", contractor );

		
	}
		
	@Test
	public void testCreate_2() {
		
		Contractor contractor;
		
		contractor = new Contractor( "Rakennus Nordin Oy", "12345678-9" );
		
		assertNotNull( "Failed constructor", contractor );
		
		assertEquals( "Wrong value", "Rakennus Nordin Oy", contractor.getName());
		assertEquals( "Wrong value", "12345678-9", contractor.getFinnishBusinessID());
		
		assertNotNull( "Wrong value", contractor.getId());
		assertNull( "Should be null", contractor.getCode());
		assertNull( "Should be null", contractor.getForeignBusinessID());
		assertNotNull( "Should be null", contractor.getIdType());
		assertEquals( "Wrong value", "Vat", contractor.getIdType().toString());
		assertNull( "Should be null", contractor.getCountryCode());
		assertNull( "Should be null", contractor.getContact());
		
	}
		
		
	// Set enum fields and validation
	@Test
	public void testCreate_3() {

		Contractor contractor;
		
		contractor = new Contractor( "Rakennus Nordin Oy", "12345678-9" );
		
		assertNotNull( "Failed constructor", contractor );
		
		contractor.setIdType( IDType.Vat );

		assertEquals( "Wrong value", IDType.Vat, contractor.getIdType());

		contractor.setIdType( null );

		assertNull( "Should be null", contractor.getIdType());
		
	}

	@Test
	public void testConvertToXML_Enum() {
		
		Contractor contractor;
		String XMLstr = null;
		
		// 1. Just simple conversion
		contractor = new Contractor( "Rakennus Nordin Oy", "12345678-9" );
		assertNotNull( "Failed constructor", contractor );

		XMLstr = getXML( contractor );
		assertNotNull( "Should not be null", XMLstr );
		
	 	assertFalse( "Element shall NOT be in XML string", StringUtils.contains( XMLstr, "ctype" ));
	 	assertFalse( "Element shall NOT be in XML string", StringUtils.contains( XMLstr, "idType" ));

		// 2. Setup enum values and validate conversion
	 	
		contractor.setIdType( IDType.Vat );

		XMLstr = getXML( contractor );
		assertNotNull( "Should not be null", XMLstr );

	 	assertTrue( "Element shall be in XML string", StringUtils.contains( XMLstr, "<idtype>Vat</idtype>" ));
		
		// 3. Setup enum to NULL and validate conversion
	 	
		contractor.setIdType( null );
		
		XMLstr = getXML( contractor );
		assertNotNull( "Should not be null", XMLstr );

	 	assertFalse( "Element shall NOT be in XML string", StringUtils.contains( XMLstr, "ctype" ));
	 	assertFalse( "Element shall NOT be in XML string", StringUtils.contains( XMLstr, "idType" ));
		
	}

	@Test
	public void testConvertToXML_WithContact() {
		
		Contractor contractor;
		String XMLstr = null;
		
		// 1. Just simple conversion
		contractor = new Contractor( "Rakennus Nordin Oy", "12345678-9" );
		assertNotNull( "Failed constructor", contractor );

		  // Create Contact instance and add to Contractor
		Contact contact = new Contact(
				"Ivan", "Ivanov", "+358 40 123467898", "test@email.com", 
				null 
		);
		
		contractor.setContact( contact );
		
		XMLstr = getXML( contractor );
		assertNotNull( "Should not be null", XMLstr );
		
		assertNotNull( "Should not be null", XMLstr );
		assertTrue( "Element shall be in XML string", StringUtils.contains( XMLstr, "<fname>Ivan</fname>" ));
		assertTrue( "Element shall be in XML string", StringUtils.contains( XMLstr, "<lname>Ivanov</lname>" ));
		assertTrue( "Element shall be in XML string", StringUtils.contains( XMLstr, "<phone>+358 40 123467898</phone>" ));
		assertTrue( "Element shall be in XML string", StringUtils.contains( XMLstr, "<email>test@email.com</email>" ));
		
	}
	
	@Test
	public void testConvertToXML_WithContact_AndAddress() {
		
		Contractor contractor;
		String XMLstr = null;
		
		// 1. Just simple conversion
		contractor = new Contractor( "Rakennus Nordin Oy", "12345678-9" );
		assertNotNull( "Failed constructor", contractor );

		  // Create Contact instance and add to Contractor
		Contact contact = new Contact(
				"Ivan", "Ivanov", "+358 40 123467898", "test@email.com", 
				new Address( "Krasnoarmejskoperekopsaq 417 B", "012345", "Sejnajakouvolajoki", "FI" )
		);
		
		contractor.setContact( contact );
		
		XMLstr = getXML( contractor );
		assertNotNull( "Should not be null", XMLstr );
		
		assertTrue( "Element shall be in XML string", StringUtils.contains( XMLstr, "<fname>Ivan</fname>" ));
		assertTrue( "Element shall be in XML string", StringUtils.contains( XMLstr, "<lname>Ivanov</lname>" ));
		assertTrue( "Element shall be in XML string", StringUtils.contains( XMLstr, "<phone>+358 40 123467898</phone>" ));
		assertTrue( "Element shall be in XML string", StringUtils.contains( XMLstr, "<email>test@email.com</email>" ));
		assertTrue( "Element shall be in XML string", StringUtils.contains( XMLstr, "<street>Krasnoarmejskoperekopsaq 417 B</street>" ));
		assertTrue( "Element shall be in XML string", StringUtils.contains( XMLstr, "<index>012345</index>" ));
		assertTrue( "Element shall be in XML string", StringUtils.contains( XMLstr, "<city>Sejnajakouvolajoki</city>" ));
		assertTrue( "Element shall be in XML string", StringUtils.contains( XMLstr, "<ccode>FI</ccode>" ));

	}
	
	@Test
	public void testConvertToXML_WithContract() {
		
		Contractor contractor;
		String XMLstr = null;
		
		// 1. Just simple conversion
		contractor = new Contractor( "Rakennus Nordin Oy", "12345678-9" );
		assertNotNull( "Failed constructor", contractor );

		  // Create Contact instance and add to Contractor
		XMLstr = getXML( contractor );
		assertNotNull( "Should not be null", XMLstr );
		
		
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
