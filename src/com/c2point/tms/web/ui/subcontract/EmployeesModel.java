package com.c2point.tms.web.ui.subcontract;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.event.EventListenerList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.c2point.tms.entity_tax.Address;
import com.c2point.tms.entity_tax.Employee;
import com.c2point.tms.web.ui.listeners.EmployeesModelListener;

public class EmployeesModel {

	private static Logger logger = LogManager.getLogger( EmployeesModel.class.getName());

	protected EventListenerList		listenerList = new EventListenerList(); 
	
	private Collection<Employee> 	employees;
	private Employee				selectedEmployee;

	public EmployeesModel() {
		this( null );
	}
	
	public EmployeesModel( Collection<Employee> employees ) {
	
		if ( employees != null ) {
			setEmployees( employees );
		} else {
			setEmployees( new ArrayList<Employee>());
		}
		
		initModel();
		
	}
	
	public boolean initModel() {

		// Add couple of test items
//		employees.add( new Employee( "Ivan", "Ivanov", new Address( "Pokrovskaja Street 24 B 17", "012345", "Espoo", "FI" )));
//		employees.add( new Employee( "Petr", "Petrov", new Address( "Itamerenkatu 22", "98765", "Helsinki", "FI" )));
//		employees.add( new Employee( "Ivan", "Ivanov", new Address( "Tynis Maagi pik 19", "65432", "Tallinn", "ES" )));
		
		
		
		
//		sites = new ArrayList<Site>();

//		
//		Collection<TaxReport> cl = TaxReportsFacade.getInstance().list( (( Taxreport_1UI )UI.getCurrent()).getSessionData().getUser().getOrganisation()); 
		
//		reports.addAll( cl );
		
		
		return true;
	}
	
	
	public Collection<Employee> getEmployees() { return this.employees; }
	public void setEmployees( Collection<Employee> employees ) { this.employees = employees; }

/*	
	public Employee getSelectedEmployee() { return this.selectedEmployee; }
	public void setSelectedEmployee( Employee employee ) {
		
		if ( employee != null && this.selectedEmployee != null && employee.getId() != this.selectedEmployee.getId()
				||
				employee == null && this.selectedEmployee != null
			 	||
			 	employee != null && this.selectedEmployee == null
				
				) {
			this.selectedEmployee = employee;
		
			fireEmployeeSelected( this.selectedEmployee );

			if ( logger.isDebugEnabled()) logger.debug( "EmployeesModel changed selected Employee!" );
		}
	}
*/	
	public Employee addEmployee( Employee employee ) {

		if ( employee != null ) {

			employees.add( employee );
			
			fireEmployeeAdded( employee );
		}
		
		return employee;
	}
	
	public boolean deleteEmployee( Employee employee ) {

		boolean bRes = false;
		try {
			bRes = employees.remove( employee );
			
			fireEmployeeDeleted( employee );
			
		} catch ( Exception e ) {
			logger.error( "Cannot delete Employee:" + employee );
		}
		
		return bRes;
	}
	
	public boolean modifyEmployee( Employee employee ) {

		boolean bRes = false;

		if ( employee != null ) {
			
			fireEmployeeEdited( employee );
			
			bRes = true;
			
		}
		
		
		return bRes;
	}
	
	private void fireEmployeeAdded( Employee employee ) {
		Object[] listeners = listenerList.getListenerList();

	    for ( int i = listeners.length-2; i >= 0; i -= 2) {
	    	if ( listeners[ i ] == EmployeesModelListener.class ) {
	    		(( EmployeesModelListener )listeners[ i + 1 ] ).added( employee );
	         }
	     }
	}

	private void fireEmployeeEdited( Employee employee ) {
		Object[] listeners = listenerList.getListenerList();

	    for ( int i = listeners.length-2; i >= 0; i -= 2) {
	    	if ( listeners[ i ] == EmployeesModelListener.class ) {
	    		(( EmployeesModelListener )listeners[ i + 1 ] ).edited( employee );
	         }
	     }
	}

	private void fireEmployeeDeleted( Employee employee ) {
		Object[] listeners = listenerList.getListenerList();

	    for ( int i = listeners.length-2; i >= 0; i -= 2) {
	    	if ( listeners[ i ] == EmployeesModelListener.class ) {
	    		(( EmployeesModelListener )listeners[ i + 1 ] ).deleted( employee );
	         }
	     }
	}

	private void fireEmployeeSelected( Employee employee ) {
		Object[] listeners = listenerList.getListenerList();

	    for ( int i = listeners.length-2; i >= 0; i -= 2) {
	    	if ( listeners[ i ] == EmployeesModelListener.class ) {
	    		(( EmployeesModelListener )listeners[ i + 1 ] ).selected( employee );
	         }
	     }
	}

	
	public void addListener( EmployeesModelListener listener ) {
		listenerList.add( EmployeesModelListener.class, listener );
	}
	
	
	/*
	 * Business method  
	 */
	

}
