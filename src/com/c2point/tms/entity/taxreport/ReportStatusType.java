package com.c2point.tms.entity.taxreport;

public enum ReportStatusType {
	Basic,
	Correction,
	Deletion,
	New			// New as type used during NEW report creation. 
				// Will be changed to "Basic" automatically befor sending to Tax Office
}
