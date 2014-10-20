package com.c2point.tms.web.ui.listeners;

import java.util.EventListener;

import com.c2point.tms.entity_tax.Employee;

public interface EmployeesModelListener extends EventListener {

	public void added( Employee employee );
	public void edited( Employee employee );
	public void deleted( Employee employee );
	public void selected( Employee employee );

}
