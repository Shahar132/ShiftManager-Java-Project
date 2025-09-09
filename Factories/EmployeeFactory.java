package Factories;

import SingleObjectClasses.Employee;
import Enums.Rank;
import SingleObjectClasses.Date;

public class EmployeeFactory {

	public static Employee createEmployee(String id, String firstName, String lastName, Date date, String role,
			Rank rank) {
		return new Employee(id, firstName, lastName, date, role, rank);
	}
}
