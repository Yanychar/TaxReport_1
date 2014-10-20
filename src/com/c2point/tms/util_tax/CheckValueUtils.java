package com.c2point.tms.util_tax;

import java.math.BigDecimal;

public class CheckValueUtils {

	public static String notNull( String str ) {
		
		return ( str != null ? str : "" );
	}

	public static boolean isEmpty( String str ) {
		 
		if (( str != null ) && ( str.trim().length() > 0 ))
			return false;
		else
			return true;
	}	

	public static BigDecimal notNull( BigDecimal number ) {
		
		return ( number != null ? number : BigDecimal.ZERO );
	}


}
