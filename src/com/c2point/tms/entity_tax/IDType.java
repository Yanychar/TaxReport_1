package com.c2point.tms.entity_tax;

public enum IDType {
	Vat( 1 ), TReg( 2 ), Tin( 3 ), Person( 4 );
	
	private int value = -1;
	
	IDType( int value ) {
		this.value = value;
		
	}
	
	public int value() {
		return this.value;
	}

	public static IDType fromValue( int i ) {
		for ( IDType type : IDType.values()) {
            if ( type.value == i ) {
                return type;
            }
        }
        throw new IllegalArgumentException( Integer.toString( i ));
	}
};

