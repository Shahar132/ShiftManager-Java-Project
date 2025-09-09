package Interfaces;

import SingleObjectClasses.Employee;

public interface IEmployeeManagement {
	boolean systemEntry();

	Employee findEmployee();

	void addNewEmployee();

	void updateEmployee();

	void removeEmployee();

	void printAllEmployee();
}
