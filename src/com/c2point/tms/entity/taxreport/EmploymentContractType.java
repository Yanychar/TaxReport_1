package com.c2point.tms.entity.taxreport;

public enum EmploymentContractType {

	Direct( 1 ), Leased( 2 ), SelfEmployed( 3 ), Trainee( 4 ), Voluntary( 5 );
	
	private int value = -1;
	
	EmploymentContractType( int value ) {
		this.value = value;
		
	}
	
	public int value() {
		return this.value;
	}

	public static EmploymentContractType fromValue( int i ) {
		for ( EmploymentContractType type : EmploymentContractType.values()) {
            if ( type.value == i ) {
                return type;
            }
        }
        throw new IllegalArgumentException( Integer.toString( i ));
	}
	
}
