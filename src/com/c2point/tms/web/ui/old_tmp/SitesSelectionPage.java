package com.c2point.tms.web.ui.old_tmp;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;

import com.c2point.tms.entity.taxreport.CorrectionType;
import com.c2point.tms.entity.taxreport.Site;
import com.c2point.tms.entity.taxreport.TaxReport;
import com.c2point.tms.web.ui.wizard.ButtonBar;
import com.c2point.tms.web.ui.wizard.TaxWizardPage;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;

public class SitesSelectionPage extends TaxWizardPage {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger( SitesSelectionPage.class.getName());

	
	public SitesSelectionPage( String caption ) {
		super( caption );
	
		initUI();

		
	}
	
	protected void initUI() {

		setSizeFull();
		
		VerticalLayout vl = new VerticalLayout();
		
		vl.setMargin( true );
		vl.setSpacing( true );
		vl.setSizeFull();
		
		Component companyInfo1 = new Label( "Test String 1" );
		Component companyInfo2 = new Label( "Test String 2" );
		Component companyInfo3 = new Label( "Test String 3" );

/* Start */	
		
		Collection<Site> projects = null;
		
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory( "default", null );

		EntityManager em = emf.createEntityManager();
		try {
			Query query = em.createQuery("SELECT A FROM " + TaxReport.class.getSimpleName() + " A");
			// projects = 
					query.getResultList();
		} catch (PersistenceException e) {
			throw e;
		} finally {
			em.close();
		}
		
/* Finish */		
		vl.addComponent( companyInfo1 );
		vl.addComponent( companyInfo2 );
		vl.addComponent( companyInfo3 );
		
		this.setContent( vl );
		
	}

}
