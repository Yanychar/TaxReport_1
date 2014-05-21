package com.c2point.tms.datalayer;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.c2point.tms.entity.Organisation;
import com.c2point.tms.entity.taxreport.TaxReport;

public class TaxReportsFacade {

	private static Logger logger = LogManager.getLogger( TaxReportsFacade.class.getName()); 
	
	private static TaxReportsFacade instance = null;
	
	public static TaxReportsFacade getInstance() {
		if ( instance == null ) {
			
			instance = new TaxReportsFacade();
			
		}
		return instance;
		
	}

	public List<TaxReport> list( Organisation org ) {
		
		if ( org == null )
			throw new IllegalArgumentException( "Organisation cannot be null to list TaxReports!" );

		EntityManager em = DataFacade.getInstance().createEntityManager();
		TypedQuery<TaxReport> query = null;
		List<TaxReport> results = null;
		
		try {
			query = em.createNamedQuery( "listAll", TaxReport.class )
						.setParameter( "org", org );
			results = query.getResultList();
			if ( logger.isDebugEnabled()) logger.debug( "**** Fetched list of TaxReports. Size = " + results.size());
		} catch ( NoResultException e ) {
			if ( logger.isDebugEnabled()) logger.debug( "No TaxReports found!" );
		} catch ( Exception e ) {
			results = null;
			logger.error( e );
		} finally {
			em.close();
		}
		
		return results;
		
	}
	
	
	public TaxReport addReport( TaxReport report ) {

		if ( report == null ) {

			logger.error( "Tax Report cannot be null or emptyl!" );
		
			return null;
		}
			
		if ( report.getOrganisation() == null ) {

			logger.error( "Organisation must be assigned to TaxReport!" );
		
			return null;
		}
			
		try {
			report = DataFacade.getInstance().insert( report );
		} catch ( Exception e) {
			
			logger.error( "Cannot add TaxReport\n" + e );
			
			return null;
		}
		
		if ( logger.isDebugEnabled())
				logger.debug( "New TaxReport was added: " + report );
		
		return report;
	}
	
}
