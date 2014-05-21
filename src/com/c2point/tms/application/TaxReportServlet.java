package com.c2point.tms.application;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;


@WebServlet(value = "/*", asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = Taxreport_1UI.class)
public class TaxReportServlet extends VaadinServlet {

	private static final long serialVersionUID = 1L;


}
