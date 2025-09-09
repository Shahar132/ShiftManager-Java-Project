package Manegers;

import SingleObjectClasses.Shift;
import Interfaces.IFunctionsLab;
import Interfaces.IShiftManagement;
import Interfaces.Observer;
import Interfaces.Subject;
import SingleObjectClasses.Employee;
import Enums.Rank;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class ShiftManager implements IShiftManagement, Subject {
	private List<Shift> shifts = new ArrayList<>();
	private List<Observer> observers = new ArrayList<>();
	private EmployeeDataBase employeeDataBase;

	// Constructor
	public ShiftManager(EmployeeDataBase employeeDataBase) {
		this.employeeDataBase = employeeDataBase;
	}

	public boolean hasShifts() {
		return !shifts.isEmpty();
	}

	public void setEmployeeDataBase(EmployeeDataBase employeeDataBase) {
		this.employeeDataBase = employeeDataBase;
	}

	@Override
	public void printShiftsByDay() {
		System.out.println("Enter day:");
		System.out.println("1) Monday\n2) Tuesday\n3) Wednesday\n4) Thursday\n5) Friday\n6) Saturday\n7) Sunday");
		int choice = IFunctionsLab.IntegerChoiceRange(1, 7);
		LocalDate today = LocalDate.now();
		LocalDate searchDate = today.with(java.time.DayOfWeek.of(choice));

		boolean found = false;
		for (Shift shift : shifts) {
			if (shift.getStartDateTime() != null && shift.getStartDateTime().toLocalDate().equals(searchDate)) {
				System.out.println(shift);
				found = true;
			}
		}

		if (!found) {
			System.out.println("No shifts found on " + searchDate.getDayOfWeek());
		}
	}

	@Override
	public void printShiftsByWeek() {
		System.out.println("1) Future Shifts\n2) Past Shifts");
		int choice = IFunctionsLab.IntegerChoiceRange(1, 2);

		LocalDate today = LocalDate.now();
		LocalDate startOfWeek;
		LocalDate endOfWeek;

		if (choice == 1) {
			startOfWeek = today.plusDays(1);
			endOfWeek = today.plusDays(7);
		} else {
			startOfWeek = today.minusDays(6);
			endOfWeek = today;
		}

		boolean found = false;
		for (Shift shift : shifts) {
			if (shift.getStartDateTime() != null) {
				LocalDate shiftDate = shift.getStartDateTime().toLocalDate();
				if (!shiftDate.isBefore(startOfWeek) && !shiftDate.isAfter(endOfWeek)) {
					System.out.println(shift);
					found = true;
				}
			}
		}

		if (!found) {
			System.out.println("No shifts found in the specified week.");
		}
	}

	@Override
	public void printShiftsByMonth() {
		System.out.println("Enter month (yyyy-MM): ");
		int year = IFunctionsLab.yearInput();
		int month = IFunctionsLab.monthInput();
		String monthStr = String.format("%04d-%02d", year, month);
		LocalDate searchDate = LocalDate.parse(monthStr + "-01", DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDate startOfMonth = searchDate.with(TemporalAdjusters.firstDayOfMonth());
		LocalDate endOfMonth = searchDate.with(TemporalAdjusters.lastDayOfMonth());
		boolean found = false;

		for (Shift shift : shifts) {
			if (shift.getStartDateTime() != null) {
				LocalDate shiftDate = shift.getStartDateTime().toLocalDate();
				if (!shiftDate.isBefore(startOfMonth) && !shiftDate.isAfter(endOfMonth)) {
					System.out.println(shift);
					found = true;
				}
			}
		}

		if (!found) {
			System.out.println("No shifts found in " + monthStr);
		}
	}

	@Override
	public void inlayEmployee() {
		System.out.println("Enter Employee ID: ");
		listEmployees(); // Print employees for selection
		String employeeId = IFunctionsLab.inputId();
		Employee employee = employeeDataBase.findEmployeeById(employeeId);

		if (employee == null) {
			System.out.println("Employee not found.");
			return;
		}
		for (int i = 0; i < Shift.counter; i++) {
			System.out.println("Shift ID:" + i);
		}

		System.out.println("Enter Shift ID: ");
		int shiftId = IFunctionsLab.IntegerChoiceRange(0, Shift.counter - 1);
		Shift shift = findShiftById(shiftId);

		if (shift == null) {
			System.out.println("Shift not found or removed .");
			return;
		}

		if (shift.getEmployee() != null) {
			System.out.println("Shift already has an employee assigned.");
			return;
		}

		shift.setEmployee(employee);
		notifyObservers("Employee " + employee.getFirstName() + " " + employee.getLastName()
				+ " has been inlaid to Shift ID: " + shiftId);
	}

	private void listEmployees() {
		// Print all employees for selection
		for (Employee employee : employeeDataBase.getAllEmployees()) {
			System.out.println(employee);
		}
	}

	private Employee findEmployeeById(String employeeId) {
		return employeeDataBase.findEmployeeById(employeeId);
	}

	private Shift findShiftById(int shiftId) {
		for (Shift shift : shifts) {
			if (shift.getId() == shiftId) {
				return shift;
			}
		}
		return null;
	}

	@Override
	public void addShift() {
		Shift newShift = new Shift();
		shifts.add(newShift);
		System.out.println("Shift added: " + newShift.getId());
	}

	@Override
	public void updateShift() {
		System.out.println("Enter shift ID to update: ");
		int shiftId = IFunctionsLab.IntegerChoiceRange(0, Shift.counter - 1);
		Shift shiftToUpdate = findShiftById(shiftId);

		if (shiftToUpdate != null) {
			System.out.println("Enter new start time (yyyy-MM-dd HH:mm:ss): ");
			String startTime = IFunctionsLab.formatTime();
			System.out.println("Enter new end time (yyyy-MM-dd HH:mm:ss): ");
			String endTime = IFunctionsLab.formatTime();

			LocalDateTime newStart = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			LocalDateTime newEnd = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

			if (newEnd.isBefore(newStart)) {
				System.out.println("End time cannot be before start time. Returning to main menu.");
				return;
			}

			shiftToUpdate.setStartDateTime(newStart);
			shiftToUpdate.setEndDateTime(newEnd);
			notifyObservers("Shift with ID: " + shiftToUpdate.getId() + " has been updated.");
			notifyAdminsAndSupervisors("Shift ID: " + shiftToUpdate.getId() + " has been updated.");
		} else {
			System.out.println("Shift not found.");
		}
	}

	@Override
	public void removeShift() {
		System.out.println("Enter shift ID to remove: ");
		int shiftId = IFunctionsLab.IntegerChoiceRange(0, Shift.counter - 1);
		Shift shiftToRemove = findShiftById(shiftId);

		if (shiftToRemove != null) {
			shifts.remove(shiftToRemove);
			notifyObservers("Shift ID: " + shiftToRemove.getId() + " has been removed.");
			System.out.println("Shift removed.");
		} else {
			System.out.println("Shift not found.");
		}
	}

	public void checkIn(Employee employee) {
		Shift shift = new Shift();
		shift.setEmployee(employee);
		shift.startShift();
		shifts.add(shift); // Ensure shift is added when started
		notifyObservers("Employee " + employee.getFirstName() + " " + employee.getLastName() + " checked in.");
	}

	public void checkOut(Employee employee) {
		Shift shift = getOngoingShiftForEmployee(employee);
		if (shift != null) {
			shift.endShift();
			notifyObservers("Employee " + employee.getFirstName() + " " + employee.getLastName() + " checked out.");
		} else {
			System.out.println(
					"No ongoing shift found for employee: " + employee.getFirstName() + " " + employee.getLastName());
		}
	}

	private Shift getOngoingShiftForEmployee(Employee employee) {
		for (Shift shift : shifts) {
			if (shift.getEmployee() != null && shift.getEmployee().equals(employee) && shift.getEndDateTime() == null) {
				return shift;
			}
		}
		return null;
	}

	public void undoLastShift(Employee employee) {
		Shift shift = getOngoingShiftForEmployee(employee);
		if (shift != null) {
			shifts.remove(shift);
			notifyObservers("Employee " + employee.getFirstName() + " " + employee.getLastName() + " undo last shift.");
		} else {
			System.out.println(
					"No ongoing shift to undo for employee: " + employee.getFirstName() + " " + employee.getLastName());
		}
	}

	public void printShifts() {
		if (shifts.isEmpty()) {
			System.out.println("No shifts available.");
			return;
		}

		for (Shift shift : shifts) {
			System.out.println(shift);
		}
	}

	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(String message) {
		for (Observer observer : observers) {
			observer.update(message);
		}
	}

	public void notifyAdminsAndSupervisors(String message) {
		for (Observer observer : observers) {
			Employee employee = (Employee) observer;
			if (employee.getRank() == Rank.ADMINISTRATOR || employee.getRank() == Rank.SUPERVISER) {
				observer.update(message);
			}
		}
	}

	public void searchShiftsByDate(String date) {
		LocalDate searchDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
		boolean found = false;
		for (Shift shift : shifts) {
			if (shift.getStartDateTime() != null && shift.getStartDateTime().toLocalDate().equals(searchDate)) {
				System.out.println(shift);
				found = true;
			}
		}
		if (!found) {
			System.out.println("No shifts found on " + date);
		}
	}

	public void searchShiftsById(int shiftId) {
		for (Shift shift : shifts) {
			if (shift.getId() == shiftId) {
				System.out.println(shift);
				return;
			}
		}
		System.out.println(" shift Not found or removed with ID: " + shiftId);
	}

	public void searchShiftsByEmployeeId(String employeeId) {
		boolean found = false;
		for (Shift shift : shifts) {
			if (shift.getEmployee() != null && shift.getEmployee().getId().equals(employeeId)) {
				System.out.println(shift);
				found = true;
			}
		}
		if (!found) {
			System.out.println("No shifts found for Employee ID: " + employeeId);
		}
	}

	public void printShiftsForEmployee(Employee employee) {
		System.out.println("Shifts for employee: " + employee.toString());
		boolean found = false;
		for (Shift shift : shifts) {
			if (shift.getEmployee() != null && shift.getEmployee().equals(employee)) {
				System.out.println(shift);
				found = true;
			}
		}
		if (!found) {
			System.out
					.println("No shifts found for employee: " + employee.getFirstName() + " " + employee.getLastName());
		}
	}

	public void printShiftsForEmployeeByDay(Employee employee) {
		LocalDate today = LocalDate.now();
		boolean found = false;
		for (Shift shift : shifts) {
			if (shift.getEmployee() != null && shift.getEmployee().equals(employee) && shift.getStartDateTime() != null
					&& shift.getStartDateTime().toLocalDate().equals(today)) {
				System.out.println(shift);
				found = true;
			}
		}
		if (!found) {
			System.out.println("No shifts found for employee: " + employee.getFirstName() + " " + employee.getLastName()
					+ " for today.");
		}
	}

	public void printShiftsForEmployeeByWeek(Employee employee) {
		LocalDate today = LocalDate.now();
		LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
		LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY));
		boolean found = false;
		for (Shift shift : shifts) {
			if (shift.getEmployee() != null && shift.getEmployee().equals(employee) && shift.getStartDateTime() != null
					&& !shift.getStartDateTime().toLocalDate().isBefore(startOfWeek)
					&& !shift.getStartDateTime().toLocalDate().isAfter(endOfWeek)) {
				System.out.println(shift);
				found = true;
			}
		}
		if (!found) {
			System.out.println("No shifts found for employee: " + employee.getFirstName() + " " + employee.getLastName()
					+ " for this week.");
		}
	}

	public void printShiftsForEmployeeByMonth(Employee employee) {
		LocalDate today = LocalDate.now();
		LocalDate startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
		LocalDate endOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
		boolean found = false;
		for (Shift shift : shifts) {
			if (shift.getEmployee() != null && shift.getEmployee().equals(employee) && shift.getStartDateTime() != null
					&& !shift.getStartDateTime().toLocalDate().isBefore(startOfMonth)
					&& !shift.getStartDateTime().toLocalDate().isAfter(endOfMonth)) {
				System.out.println(shift);
				found = true;
			}
		}
		if (!found) {
			System.out.println("No shifts found for employee: " + employee.getFirstName() + " " + employee.getLastName()
					+ " for this month.");
		}
	}
}
