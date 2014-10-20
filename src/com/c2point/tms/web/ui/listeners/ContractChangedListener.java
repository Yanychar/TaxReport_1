package com.c2point.tms.web.ui.listeners;

import java.util.EventListener;

import com.c2point.tms.entity_tax.Contract;

public interface ContractChangedListener extends EventListener {

	public void added();
	public void edited();
	public void deleted();

}
