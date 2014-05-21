package com.c2point.tms.web.ui.old_tmp.wizard;

import java.util.EventListener;

public interface WizardListener extends EventListener {
	
	public void newPageAdded( WizardModel model, int index );
	public void pageActivated( WizardModel model, int index );
	public void pageDeactivated( WizardModel model, int index );
}
