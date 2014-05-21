package com.c2point.tms.util;

import java.util.Locale;

import com.c2point.tms.application.Taxreport_1UI;
import com.c2point.tms.entity.taxreport.Address;
import com.c2point.tms.entity.taxreport.IDType;
import com.c2point.tms.entity.taxreport.EmploymentContractType;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.UI;

public class UIhelper {

	
	public static void fillCountryCombo( ComboBox field ) {
		String[] locales = Locale.getISOCountries();
		 
		for ( String countryCode : locales) {
	 
			Locale obj = new Locale( "", countryCode );

			field.addItem( obj.getCountry());
			field.setItemCaption( obj.getCountry(), obj.getDisplayCountry());
			
		}
	}
	
	public static boolean selectCountryInCombo( ComboBox field, String countryCode ) {
		
		boolean bRes = false;
		
		if ( countryCode == null ) {
			Locale obj = (( Taxreport_1UI )UI.getCurrent()).getSessionData().getLocale();
			if ( obj != null && obj.getCountry() != null ) {
				countryCode = obj.getCountry();
			} else {
				countryCode = "FI";
			}
			
		}
		
		field.setValue( countryCode );
		
		bRes = true;
		
		return bRes;
	}

	public static boolean selectCountryInCombo( ComboBox field, Address address ) {
		
		return selectCountryInCombo( field, ( address != null ? address.getCountryCode() : null )); 

	}

	public static void fillForeignIDTypeGroup( OptionGroup group ) {
		
		group.addItem( IDType.Vat );
		group.addItem( IDType.TReg );
		group.addItem( IDType.Tin );
		group.addItem( IDType.Person );

		group.setItemCaption( IDType.Vat, "VAT number" );
		group.setItemCaption( IDType.TReg, "Trade registration ID" );
		group.setItemCaption( IDType.Tin, "Tax player ID" );
		group.setItemCaption( IDType.Person, "Personal ID code" );
		
		
	}

	public static void fillEmploymentContractType( ComboBox field ) {

		field.addItem( EmploymentContractType.Direct );
		field.setItemCaption( EmploymentContractType.Direct, "Direct employment" );
		
		field.addItem( EmploymentContractType.Leased );
		field.setItemCaption( EmploymentContractType.Leased, "Leased employee" );
		
		field.addItem( EmploymentContractType.SelfEmployed );
		field.setItemCaption( EmploymentContractType.SelfEmployed, "Independent, self-employed, etc." );
		
		field.addItem( EmploymentContractType.Trainee );
		field.setItemCaption( EmploymentContractType.Trainee, "Trainee worker" );
		
		field.addItem( EmploymentContractType.Voluntary );
		field.setItemCaption( EmploymentContractType.Voluntary, "Voluntary worker" );
		
	}
	
	public static boolean selectForeignIDTypeGroup( OptionGroup group, IDType type ) {
		
		try {
			group.setValue( type );
		} catch ( Exception e ) {
			return false;
		}
		return true;
	}
	
}
