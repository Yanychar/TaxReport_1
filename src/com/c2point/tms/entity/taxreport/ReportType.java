package com.c2point.tms.entity.taxreport;

public enum ReportType {
	Employees,
	Contracts;

	public String getName() {
		
		switch ( this ) {
			case Employees:
				return "Employees";
			case Contracts:
				return "Contracts";
		}
		
		return "Unknown";
	}
}
