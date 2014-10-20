package com.c2point.tms.entity;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Project extends SimplePojo {

	private static Logger logger = LogManager.getLogger( Project.class.getName());

    private String code;
	private String name;

	private Date start;
	private Date endPlanned;
	private Date endReal;
	
	private Organisation organisation;
	
		
	private TmsUser 	projectManager;
	
	private String address;
	
	private boolean 	closed;
	
	protected Project() {
		this( "", "" );
	}
	
	public Project(String code, String name) {
		super();
		this.code = ( code != null ? code.trim().toUpperCase() : code );
		this.name = name;
	}
	public Project( Project project ) {
		this( project.getCode(), project.getName());
	}

	public void update( Project project ) {
		this.name = project.getName();

		this.start = project.getStart();
		this.endPlanned = project.getEndPlanned();
		this.endReal = project.getEndReal();
		
//		this.organisation = project.;
		
//		this.projectTasks = new HashMap<String, ProjectTask>();
			
		this.projectManager = project.getProjectManager();
		
		this.address = project.getAddress();
	}


	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code.trim().toUpperCase();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the projectManager
	 */
	public TmsUser getProjectManager() {
		return projectManager;
	}

	/**
	 * @param projectManager the projectManager to set
	 */
	public void setProjectManager( TmsUser projectManager ) {
		this.projectManager = projectManager;
	}

	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation( Organisation organisation ) {
		this.organisation = organisation;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEndPlanned() {
		return endPlanned;
	}

	public void setEndPlanned(Date endPlanned) {
		this.endPlanned = endPlanned;
	}

	public Date getEndReal() {
		return endReal;
	}

	public void setEndReal(Date endReal) {
		this.endReal = endReal;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	/**** Business Methods  ***/
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		String str = "";
		try {
			str = "Project [";
			str = str.concat( "code=" + (code != null ? code + ", " : "NULL"));
			str = str.concat( "name=" + (name != null ? name + ", " : "NULL"));
			str = str.concat( "projectManager=" + (projectManager != null ? projectManager : "NULL") + " " );
//			str = str.concat( "tasks=" + ( tasks != null ? tasks : "NULL"));
			str = str.concat( "]" );
		} catch (Exception e) {
			logger.error( "Created NOT COMPLETED string str: " + str );
		}
		 
		return str;
	}
	
	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	
	public boolean isEnded() {
		
 		return isClosed(); 
	}
	
}
