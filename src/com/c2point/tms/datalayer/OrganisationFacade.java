package com.c2point.tms.datalayer;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.c2point.tms.entity.Organisation;

public class OrganisationFacade {
	private static Logger logger = LogManager.getLogger(OrganisationFacade.class.getName());

	public static OrganisationFacade getInstance() {

		return new OrganisationFacade();
	}
	
	private OrganisationFacade() {}

	/* Fetch metadata methods */
	public Organisation getOrganisation( String code ) {
		if ( logger.isDebugEnabled()) logger.debug( "getOrganication with code ==" + code );
		Organisation org = null;
		// Try to fetch it from DB
		EntityManager em = DataFacade.getInstance().createEntityManager();
		try {
			// Fetched Organisation with specified code. Should be one Organisation only!!!  
			TypedQuery<Organisation> q = em.createNamedQuery( "findOrganisationByCode", Organisation.class )
					.setParameter("code", code );
			org = q.getSingleResult();
		} catch ( NoResultException e ) {
			org = null;
			logger.debug( "Not found: NoResultException for Organisation.code: '" + code + "'" );
		} catch ( NonUniqueResultException e ) {
			org = null;
			logger.error( "It should be one Organisation only for Organisation.code: '" + code + "'" );
		} catch ( Exception e ) {
			org = null;
			logger.error( e );
		} finally {
			em.close();
		}
		
//		org = validateRootDepartment( org );
		
		return org;

	}
	public Organisation addOrganisation( Organisation org ) {
		
		if ( org == null ) {
			logger.error( "org cannot be null to add Organisation!" );
			return null;
		}
		// 	Find in DB
		Organisation oldOrg = getOrganisation( org.getCode());
		// If not found than
		if ( oldOrg == null ) {
			if ( logger.isDebugEnabled()) logger.debug( org + " does not exist in Db. Will be added!" );
			
			// Add Organisation and its ServiceOwner
			try {
				org = DataFacade.getInstance().insert( org );

				if ( logger.isDebugEnabled()) logger.debug( org + " was added to db successfully" );
			} catch ( Exception e ) {
				logger.error("Cannot add " + org );
				logger.error( e );
				return null;
			}
			
			org = DataFacade.getInstance().find( Organisation.class, org.getId());
			
		} else {
			logger.error( org + " exists in DB already! Cannot be added" );
			return null;
		}
		
		return org;
	}

}

