package com.c2point.tms.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	TestSimpleTests.class,
})

public class TmsTestSuit {

	private static Logger logger = LogManager.getLogger( SuitTemporal.class.getName());


	@BeforeClass
	public static void setUp() {
		
		logger.debug( "TmsTestSuit Before!" );
	}
	
	@AfterClass
	public void tearDown() {
		logger.debug( "TmsTestSuit After!" );
	}
	

}
