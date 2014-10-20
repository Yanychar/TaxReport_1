package com.c2point.tms.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TaskReport extends AbstractReport {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger( TaskReport.class.getName());

	private float	hours;

	private String	comment;	
	
	/**
	 * 
	 */
	public TaskReport() {
		super();
	}

	public TaskReport modifyReport( TaskReport otherReport ) {
		
		return this;
	}

	public float getHours() {
		return hours;
	}

	public void setHours( float hours ) {
		this.hours = hours;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public Project getProject() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
