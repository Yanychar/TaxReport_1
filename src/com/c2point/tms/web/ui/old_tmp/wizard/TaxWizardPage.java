package com.c2point.tms.web.ui.old_tmp.wizard;

import com.vaadin.ui.Panel;

public class TaxWizardPage extends Panel {

	private static final long serialVersionUID = 1L;

	private String 	caption = "";
	
	public TaxWizardPage() {
		this( "" );
	}
	
	public TaxWizardPage( String caption ) {
		super();
		
		setCaption( caption );
	}
	
	public void setCaption( String caption ) { 
//		super.setCaption(caption);
		this.caption = caption; 
	}
	public String getCaption() { return this.caption; }
	
	public boolean validateForNext() {
		
		return true;
	}
	
	public boolean validateForBack() {
		
		return true;
	}
	
}
