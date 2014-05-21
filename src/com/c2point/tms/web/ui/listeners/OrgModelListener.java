package com.c2point.tms.web.ui.listeners;

import java.util.EventListener;

import com.c2point.tms.entity.Organisation;

public interface OrgModelListener extends EventListener {

	public void added( Organisation organisation );
	public void edited( Organisation organisation );
	public void deleted( Organisation organisation );
	public void selected( Organisation organisation );

}
