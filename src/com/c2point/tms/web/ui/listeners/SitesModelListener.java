package com.c2point.tms.web.ui.listeners;

import java.util.EventListener;

import com.c2point.tms.entity_tax.Site;

public interface SitesModelListener extends EventListener {

	public void added( Site site );
	public void edited( Site site );
	public void deleted( Site site );
	public void selected( Site site );

}
