package com.c2point.tms.test;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.c2point.tms.util.xml.*;
import com.c2point.tms.entity.taxreport.Address;
import com.c2point.tms.entity.taxreport.Contact;

public class TestContact extends TmsTestCase {
	
	private static Logger logger = LogManager.getLogger( TestContact.class.getName());

	@Before
	public void setUp() {

		super.setUp();
		
	}

	
	// Test 1. Creation
	//  with null values
	
	// Test 2. Convert to XML
	//   - Not null fields shall be found
	//   - NUL fields shall be missed
	
	
	@Test
	public void testCreate_1() {
		
		Contact contact;
		
		contact = new Contact(
				"Serge", "Seva", 
				new Address( "Sinkkitie 42 A", "452113", "Espoo", "FI" ) 
		);
		
		assertNotNull( "Failed constructor", contact );
		
		assertEquals( "Wrong value", "Serge", contact.getFirstName());
		assertEquals( "Wrong value", "Seva", contact.getLastName());
		assertNull( "Should be null", contact.getPhoneNumber());
		assertNull( "Should be null", contact.getEmail());
		
		contact = new Contact(
				"Ivan", "Ivanov", "+358 40 123467898", "test@email.com", 
				null 
		);
		
		assertNotNull( "Failed constructor", contact );
		
		assertEquals( "Wrong value", "Ivan", contact.getFirstName());
		assertEquals( "Wrong value", "Ivanov", contact.getLastName());
		assertEquals( "Wrong value", "+358 40 123467898", contact.getPhoneNumber());
		assertEquals( "Wrong value", "test@email.com", contact.getEmail());
		
	}

	@Test
	public void testConvertToXML() {
		
		Contact contact;
		String XMLstr = null;
		
		// 1. Just simple conversion
		contact = new Contact(
				"Ivan", "Ivanov", "+358 40 123467898", "test@email.com", 
				null 
		);
		
		XMLstr = getXML( contact );
		
		assertNotNull( "Should not be null", XMLstr );
		assertTrue( "Element shall be in XML string", StringUtils.contains( XMLstr, "fname" ));
		assertTrue( "Element shall be in XML string", StringUtils.contains( XMLstr, "lname" ));
		assertTrue( "Element shall be in XML string", StringUtils.contains( XMLstr, "phone" ));
		assertTrue( "Element shall be in XML string", StringUtils.contains( XMLstr, "email" ));
		
		assertTrue( "Value shall be in XML string", StringUtils.contains( XMLstr, "Ivan" ));
		assertTrue( "Value shall be in XML string", StringUtils.contains( XMLstr, "Ivanov" ));
		assertTrue( "Value shall be in XML string", StringUtils.contains( XMLstr, "+358 40 123467898" ));
		assertTrue( "Value shall be in XML string", StringUtils.contains( XMLstr, "test@email.com" ));
		
		// 2. NULL fields shall be missed in XML
		contact = new Contact(
				"Ivan", "Ivanov", 
				null 
		);
		
		XMLstr = getXML( contact );
		
		assertNotNull( "Should not be null", XMLstr );
		assertTrue( "Element shall be in XML string", StringUtils.contains( XMLstr, "<fname>Ivan</fname>" ));
		assertTrue( "Element shall be in XML string", StringUtils.contains( XMLstr, "<lname>Ivanov</lname>" ));
		assertFalse( "Element shall NOT be in XML string", StringUtils.contains( XMLstr, "phone" ));
		assertFalse( "Element shall NOT be in XML string", StringUtils.contains( XMLstr, "email" ));
		assertFalse( "Value shall NOT be in XML string", StringUtils.contains( XMLstr, "+358 40 123467898" ));
		assertFalse( "Value shall NOT be in XML string", StringUtils.contains( XMLstr, "test@email.com" ));
		

		contact.setFirstName( null );
		contact.setLastName( null );
		contact.setPhoneNumber( "+358 40 123467898" );
		contact.setEmail( "test@email.com" );

		XMLstr = getXML( contact );
		
		assertNotNull( "Should not be null", XMLstr );
		assertFalse( "Element shall NOT be in XML string", StringUtils.contains( XMLstr, "fname" ));
		assertFalse( "Element shall NOT be in XML string", StringUtils.contains( XMLstr, "lname" ));
		assertTrue( "Element shall be in XML string", StringUtils.contains( XMLstr, "phone" ));
		assertTrue( "Element shall be in XML string", StringUtils.contains( XMLstr, "email" ));
		
		assertFalse( "Value shall NOT be in XML string", StringUtils.contains( XMLstr, "Ivan" ));
		assertFalse( "Value shall NOT be in XML string", StringUtils.contains( XMLstr, "Ivanov" ));
		assertTrue( "Value shall be in XML string", StringUtils.contains( XMLstr, "+358 40 123467898" ));
		assertTrue( "Value shall be in XML string", StringUtils.contains( XMLstr, "test@email.com" ));
		
		
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
