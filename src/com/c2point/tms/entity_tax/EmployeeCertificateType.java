package com.c2point.tms.entity_tax;

public enum EmployeeCertificateType {

	Yes( 1 ), No( 2 );
	
	private int value = -1;
	
	EmployeeCertificateType( int value ) {
		this.value = value;
		
	}
	
	public int value() {
		return this.value;
	}

	public static EmployeeCertificateType fromValue( int i ) {
		for ( EmployeeCertificateType type : EmployeeCertificateType.values()) {
            if ( type.value == i ) {
                return type;
            }
        }
        throw new IllegalArgumentException( Integer.toString( i ));
	}
	
}
