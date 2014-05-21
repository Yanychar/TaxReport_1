package com.c2point.tms.test;

import org.junit.After;
import org.junit.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TmsTestCase {

	private static Logger logger = LogManager.getLogger( TestSimpleTests.class.getName());
	
	@Before
	public void setUp() {

		logger.debug( "  " + this.getClass().getSimpleName() + " Start ...");
		
	}

	
	@After
	public void tearDown() {

		logger.debug( "  ... " + this.getClass().getSimpleName() + " End");
		
	}


}
