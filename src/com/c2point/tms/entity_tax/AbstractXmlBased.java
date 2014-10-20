package com.c2point.tms.entity_tax;

public class AbstractXmlBased {

	private boolean synchro;
	
	protected void setSyncronized() { this.synchro = true; }
	protected void setChanged() { this.synchro = false; }
	protected boolean isSynchronized() { return this.synchro; }
	
}
