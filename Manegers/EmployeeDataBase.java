package Manegers;

import java.util.ArrayList;
import SingleObjectClasses.*;
import Enums.*;
import Factories.*;
import Interfaces.*;
import java.util.List;

public class EmployeeDataBase implements IEmployeeManagement {
	private ArrayList<Employee> workerList = new ArrayList<>();
	private DataBase db = DataBase.getInstance();
	private Employee loggedInEmployee;
	private ShiftManager shiftManager;

	public EmployeeDataBase(ShiftManager shiftManager) {
		this.shiftManager = shiftManager;

		// Initialize with predefined employees
		Employee admin = EmployeeFactory.createEmployee("123456789", "Avi", "Shak", new Date(27, 3, 1977), "CEO",
				Rank.ADMINISTRATOR);
		Employee supervisor = EmployeeFactory.createEmployee("987654321", "John", "Doe", new Date(15, 5, 1985),
				"Supervisor", Rank.SUPERVISER);
		Employee worker1 = EmployeeFactory.createEmployee("111111111", "Alice", "Smith", new Date(12, 12, 1990),
				"Worker", Rank.WORKER);
		Employee worker2 = EmployeeFactory.createEmployee("222222222", "Bob", "Johnson", new Date(20, 6, 1992),
				"Worker", Rank.WORKER);
		Employee worker3 = EmployeeFactory.createEmployee("333333333", "Charlie", "Brown", new Date(5, 8, 1994),
				"Worker", Rank.WORKER);
		Employee worker4 = EmployeeFactory.createEmployee("444444444", "David", "Williams", new Date(25, 9, 1988),
				"Worker", Rank.WORKER);
		Employee worker5 = EmployeeFactory.createEmployee("555555555", "Eve", "Davis", new Date(30, 4, 1991), "Worker",
				Rank.WORKER);

		workerList.add(admin);
		workerList.add(supervisor);
		workerList.add(worker1);
		workerList.add(worker2);
		workerList.add(worker3);
		workerList.add(worker4);
		workerList.add(worker5);

		db.addUser(admin.getPassword(), admin.getUser());
		db.addUser(supervisor.getPassword(), supervisor.getUser());
		db.addUser(worker1.getPassword(), worker1.getUser());
		db.addUser(worker2.getPassword(), worker2.getUser());
		db.addUser(worker3.getPassword(), worker3.getUser());
		db.addUser(worker4.getPassword(), worker4.getUser());
		db.addUser(worker5.getPassword(), worker5.getUser());

		shiftManager.registerObserver(admin);
		shiftManager.registerObserver(supervisor);
	}

	@Override
	public boolean systemEntry() {
		System.out.println("Enter username: ");
		String username = IFunctionsLab.stringInput();
		System.out.println("Enter password: ");
		String password = IFunctionsLab.inputPass();

		boolean isUser = db.isUser(password, username);

		if (isUser) {
			loggedInEmployee = findEmployeeByUsername(username);
			return true;
		}
		return false;
	}

	public Employee getLoggedInEmployee() {
		return loggedInEmployee;
	}

	private Employee findEmployeeByUsername(String username) {
		for (Employee employee : workerList) {
			if (employee.getUser().equals(username)) {
				return employee;
			}
		}
		return null;
	}

	@Override
	public Employee findEmployee() {
		System.out.println("Enter employee ID: ");
		String id = IFunctionsLab.inputId();
		for (Employee employee : workerList) {
			if (employee.getId().equals(id)) {
				System.out.println(employee);
				return employee;
			}
		}
		System.out.println("Employee not found.");
		return null;
	}

	public Employee findEmployeeById(String employeeId) {
		for (Employee employee : workerList) {
			if (employee.getId().equals(employeeId)) {
				return employee;
			}
		}
		return null;
	}

	public List<Employee> getAllEmployees() {
		return workerList;
	}

	@Override
	public void addNewEmployee() {
		String id = IFunctionsLab.inputId();
		for (Employee employee : workerList) {
			if (employee.getId().equals(id)) {
				System.out.println("Employee with ID " + id + " already exists. The employee was not added.");
				return;
			}
		}
		String firstName = IFunctionsLab.firstNameInput();
		for (Employee employee : workerList) {
			if (employee.getFirstName().equals(firstName)) {
				System.out.println(
						"Employee with name  already exists. The employee was not added.(Try a different username)");
				return;
			}
		}
		String lastName = IFunctionsLab.lastNameInput();
		System.out.println("Enter date of birth:");
		Date date = new Date(IFunctionsLab.dayInput(), IFunctionsLab.monthInput(), IFunctionsLab.yearInput());
		System.out.println("Enter role:");
		String role = IFunctionsLab.stringInput();

		Rank rank = null;
		while (rank == null) {
			try {
				System.out.println("Enter new rank(indx):\n 1)WORKER\n 2)SUPERVISER\n 3)ADMINISTRATOR)");
				int choice = IFunctionsLab.IntegerChoiceRange(1, 3);
				rank = Rank.get(choice - 1);
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid rank. Please enter a valid rank (WORKER, SUPERVISER, ADMINISTRATOR).");
			}
		}

		Employee newEmployee = EmployeeFactory.createEmployee(id, firstName, lastName, date, role, rank);
		workerList.add(newEmployee);
		db.addUser(newEmployee.getPassword(), newEmployee.getUser());

		// Register as an observer if the rank is SUPERVISER or ADMINISTRATOR
		if (newEmployee.getRank() == Rank.SUPERVISER || newEmployee.getRank() == Rank.ADMINISTRATOR) {
			shiftManager.registerObserver(newEmployee);
			shiftManager.notifyAdminsAndSupervisors("New employee " + newEmployee.getFirstName() + " "
					+ newEmployee.getLastName() + " has been added.");
		} else {
			System.out.println("Employee " + newEmployee.getFirstName() + " " + newEmployee.getLastName()
					+ " is a WORKER and will not receive notifications.");
		}
	}

	@Override
	public void updateEmployee() {
		Employee employee = findEmployee();
		if (employee != null) {
			String employeeId = employee.getId();
			Rank newRank = null;

			String firstName = IFunctionsLab.firstNameInput();
			for (Employee employee1 : workerList) {
				if (employee1.getFirstName().equals(firstName)) {
					System.out.println(
							"Employee with name  already exists. The employee was not added.(Try a different username)");
					return;
				}
			}
			String lastName = IFunctionsLab.lastNameInput();
			System.out.println("Enter date of birth:");
			Date date = new Date(IFunctionsLab.dayInput(), IFunctionsLab.monthInput(), IFunctionsLab.yearInput());
			System.out.println("Enter new role:");
			String role = IFunctionsLab.stringInput();

			if (employee.getRank() != Rank.ADMINISTRATOR) {
				while (newRank == null) {
					System.out.println("Enter new rank(indx):\n 1)WORKER\n 2)SUPERVISER\n 3)ADMINISTRATOR");

					int choice = IFunctionsLab.IntegerChoiceRange(1, 3);
					Rank rank = Rank.get(choice - 1);
					try {
						newRank = rank;
					} catch (IllegalArgumentException e) {
						System.out.println(
								"Invalid rank. Please enter one of the following: WORKER, SUPERVISER, ADMINISTRATOR.");
					}
				}
			} else {
				System.out.println("Cannot change the rank of an ADMINISTRATOR.");
				return; // Return early if the rank cannot be changed
			}

			// Remove the old employee
			workerList.remove(employee);
			db.removeUser(employee.getPassword(), employee.getUser());
			shiftManager.removeObserver(employee);

			// Create and add the new employee
			Employee newEmployee = EmployeeFactory.createEmployee(employeeId, firstName, lastName, date, role, newRank);
			workerList.add(newEmployee);
			db.addUser(newEmployee.getPassword(), newEmployee.getUser());

			// Register as an observer if the new rank is SUPERVISER or ADMINISTRATOR
			if (newEmployee.getRank() == Rank.SUPERVISER || newEmployee.getRank() == Rank.ADMINISTRATOR) {
				shiftManager.registerObserver(newEmployee);
				shiftManager.notifyAdminsAndSupervisors("Employee " + newEmployee.getFirstName() + " "
						+ newEmployee.getLastName() + " has been updated.");
			} else {
				System.out.println("Employee " + newEmployee.getFirstName() + " " + newEmployee.getLastName()
						+ " is a WORKER and will not receive notifications.");
			}
		} else {
			System.out.println("Employee not found.");
		}
	}

	@Override
	public void removeEmployee() {
		Employee employee = findEmployee();
		if (employee != null) {
			if (employee.getRank() == Rank.ADMINISTRATOR && countAdmins() == 1) {
				System.out.println("Cannot remove the last administrator.");
				return;
			}
			if (employee.equals(loggedInEmployee)) {
				System.out.println("You cannot remove yourself from the system.");
				return;
			}
			workerList.remove(employee);
			db.removeUser(employee.getPassword(), employee.getUser());
			shiftManager.removeObserver(employee);
			System.out.println("Employee removed successfully.");
		}
	}

	@Override
	public void printAllEmployee() {
		for (Employee employee : workerList) {
			System.out.println(employee);
		}
	}

	private int countAdmins() {
		int count = 0;
		for (Employee employee : workerList) {
			if (employee.getRank() == Rank.ADMINISTRATOR) {
				count++;
			}
		}
		return count;
	}
}
