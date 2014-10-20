package com.c2point.tms.util_tax;

import com.c2point.tms.entity_tax.Address;


public class ValidationHelper {

	public static boolean validateString( String str, int min, int max ) {

		return str != null && ( min < 0 || str.length() >= min ) && ( max < 0 || str.length() >= max );  
		
	}
	
	public static boolean validateString( String str, int max ) { return validateString( str, 1, max ); }
	public static boolean validateString( String str ) { return validateString( str, 1, -1 ); }
	
	public static boolean validateID( String id, String pattern, int min, int max ) {
		
		boolean bRes = validateString( id, min, max );
		
		if ( bRes && pattern != null && pattern.length() > 0 ) {
			
			bRes = bRes && id.matches( pattern ); 
		}
		
		return bRes;
	}
	
	public static boolean validateID( String id, int minlen, int maxLen ) {
		
		return validateID( id, null, minlen, maxLen );
		
	}
	public static boolean validateID( String id, int maxLen ) {
		
		return validateID( id, null, 3, maxLen );
		
	}
	
	public static boolean validateSiteAddress( Address address ) {
		
		return 
				address != null 
			&& 
				( ValidationHelper.validateString( address.getStreet())
			  ||  ValidationHelper.validateString( address.getDescription())
		);
	

	
	}
	

	/*	
	public boolean validateSiteId( String id ) {
		
		return validateID( id, 3, 35 );

	}
		
	public boolean validateSiteNumber( String id ) {
		
		return validateID( id, 3, 50 );

	}
*/		
	
}
