package com.c2point.tms.web.ui.taxreports;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.c2point.tms.entity.taxreport.Site;
import com.c2point.tms.util.CheckValueUtils;
import com.c2point.tms.web.ui.listeners.SitesModelListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class SiteInfo extends CustomComponent implements SitesModelListener {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger( SiteInfo.class.getName());

	private Site	site;

//	private Panel siteAddrInfo;
//	private Panel siteContactInfo;
	private VerticalLayout siteAddrInfo;
	private VerticalLayout siteContactInfo;

	private Label	siteAddressCaption;
	private Label	siteAddressLabel;
	
	private Label	siteContactCaption;
	private Label	siteContactLabel;
	
	

	public SiteInfo() {
		this( null );
	}

	public SiteInfo( Site site ) {
		super();


		initUI();
		
		selected( site );	
	}

	public void initUI() {
/*
		VerticalLayout content = new VerticalLayout();
		
		siteAddrInfo = new Panel();
		siteContactInfo = new Panel();
		
		content.addComponent( siteAddrInfo );
		content.addComponent( siteContactInfo );
		
		// Site Address info
		siteAddressLabel = new Label();
		siteAddressLabel.setContentMode( ContentMode.HTML );
		
		VerticalLayout vl = new VerticalLayout();
		siteAddrInfo.setContent( vl );
		vl.addComponent( siteAddressLabel );
		
		// Set the size as undefined at all levels
		siteAddrInfo.setSizeUndefined();
		siteContactInfo.setSizeUndefined();
		content.setSizeUndefined();
		setSizeUndefined();

		content.setMargin( true );
		content.setSpacing( true );
		
		setCompositionRoot( content );
*/
		VerticalLayout content = new VerticalLayout();
		
		siteAddrInfo = new VerticalLayout();

		siteAddressCaption = new Label();
		siteAddressCaption.setContentMode( ContentMode.HTML );
		siteAddressCaption.addStyleName( "h1" );
		
		siteAddressLabel = new Label();
		siteAddressLabel.setContentMode( ContentMode.HTML );

		content.addComponent( siteAddressCaption );
		content.addComponent( siteAddressLabel );
		
		siteContactInfo = new VerticalLayout();

		siteContactCaption = new Label();
		siteContactCaption.setContentMode( ContentMode.HTML );
		siteContactCaption.addStyleName( "h2" );
		
		siteContactLabel = new Label();
		siteContactLabel.setContentMode( ContentMode.HTML );
		
		content.addComponent( siteContactCaption );
		content.addComponent( siteContactLabel );
		
		content.setMargin( true );
		content.setSpacing( true );
		
		setCompositionRoot( content );
		
	}

	public void showSite( Site site ) {

		if ( site != null ) {
			
//			siteAddrInfo.setCaption( CheckValueUtils.notNull( site.getName()));
//			siteContactInfo.setCaption( "Site Contact" );
			
			siteAddressCaption.setValue( CheckValueUtils.notNull( site.getName()) );
			String str = "";

			// Site info
			if ( site.getAddress() != null ) {
				if ( !CheckValueUtils.isEmpty( site.getAddress().getStreet())) {
					
					str = str.concat( "<b>Site Address: </b> " 
							 	+ "<u>"
							 	+ CheckValueUtils.notNull( site.getAddress().getStreet()) + ", "
								+ CheckValueUtils.notNull( site.getAddress().getIndex()) + " " 
								+ CheckValueUtils.notNull( site.getAddress().getCity()) + ", "
								+ CheckValueUtils.notNull( site.getAddress().getCountryCode()) 
								+ "</u>"
								+ "<br>"
								);
				} else if( !CheckValueUtils.isEmpty( site.getAddress().getDescription())) {
					str = str.concat( "<b>Description: </b> " 
						 	+ "<u>"
							+ CheckValueUtils.notNull( site.getAddress().getDescription())
							+ "</u>"
							+ "<br>"
							);
				}
				
			}

			if ( !CheckValueUtils.isEmpty( site.getSiteVeroID())) {
				str = str.concat( "<b>Site vero ID: </b> " 
					 			+ "<u>"
								+ site.getSiteVeroID()
								+ "</u>"
								+ "<br>"
						);
			}
			if ( !CheckValueUtils.isEmpty( site.getSiteNumber())) {
				str = str.concat( "<b>Site Number: </b> " 
								+ "<u>"
								+ site.getSiteNumber()
								+ "</u>"
								+ "<br>"
						);
			}
			
			
			siteAddressLabel.setValue( str ); 
		
			// Now Contact info
			siteContactCaption.setValue( "<br>"
										+ "Contact Person" );
			str = "";

			// Site info
			if ( site.getContact() != null ) {

				str = str.concat(  
					 	  CheckValueUtils.notNull( site.getContact().getFirstLastName()) 
						+ "<br>"
						);
				
				str = str.concat( "<b>Phone: </b> " 
						+ CheckValueUtils.notNull( site.getContact().getPhoneNumber())
						+ "<br>"
						);
				
				str = str.concat( "<b>Email: </b> " 
						+ CheckValueUtils.notNull( site.getContact().getEmail())
						+ "<br>"
						);
					
					
				if ( site.getContact().getAddress() != null && site.getContact().getAddress().getStreet() != null ) {

					str = str.concat( "<b>Address: </b> " 
						 	+ CheckValueUtils.notNull( site.getContact().getAddress().getStreet()) + ", "
							+ CheckValueUtils.notNull( site.getContact().getAddress().getIndex()) + " " 
							+ CheckValueUtils.notNull( site.getContact().getAddress().getCity()) + ", "
							+ CheckValueUtils.notNull( site.getContact().getAddress().getCountryCode()) 
							+ "<br>"
							);
					
				}
				
				siteContactLabel.setValue( str ); 
				
			}
				
		}
		
	}

	public void clear() {
		
		siteAddressCaption.setValue( "" );
		siteAddressLabel.setValue( "" );
		
		siteContactCaption.setValue( "" );
		siteContactLabel.setValue( "" );
		
	}

	@Override
	public void added( Site site ) {
		
	}

	@Override
	public void edited( Site site ) {
		selected( site );
	}

	@Override
	public void deleted(Site site) {
		
	}

	@Override
	public void selected( Site site ) {

		if ( site != null ) {
			showSite( site );
		} else {
			clear();
		}
		
	}
	
}
