package com.c2point.tms.entity_tax;

public enum ReportStatusType {
	Basic,
	Correction,
	Deletion,
	New,			// New and NewFilled types that are used during NEW report creation. 
	NewFilled;			// Will be changed to "Basic" automatically before sending to Tax Office
	
	public String toString() {
		
		switch ( this ) {
			case Basic:
				return "Basic";
			case Correction:
				return "Correction";
			case Deletion:
				return "Deletion";
			case New: 
				return "New";
			case NewFilled:
				return "New. Filled";
		}
		return "UNKNOWN";
	}
}
