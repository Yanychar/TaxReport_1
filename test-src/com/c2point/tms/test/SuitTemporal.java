package com.c2point.tms.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@RunWith(Suite.class)
@SuiteClasses({ 
//	TestSimpleTests.class,
//	TestContact.class,
//	TestContactor.class,
	TestReportXML.class,
	TestReportDB.class,
//	TestDBOrg.class,
})

public class SuitTemporal {

	private static Logger logger = LogManager.getLogger( SuitTemporal.class.getName());


	@BeforeClass
	public static void setUp() {
		
		logger.debug( "SuitTemporal START... !" );
	}
	
	@AfterClass
	public static void tearDown() {
		logger.debug( "...SuitTemporal END!" );
	}
	
}
