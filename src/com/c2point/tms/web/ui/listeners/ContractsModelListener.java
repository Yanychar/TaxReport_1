package com.c2point.tms.web.ui.listeners;

import java.util.EventListener;

import com.c2point.tms.entity.taxreport.Contract;

public interface ContractsModelListener extends EventListener {

	public void added( Contract contract );
	public void edited( Contract contract );
	public void deleted( Contract contract );
	public void selected( Contract contract );

}
