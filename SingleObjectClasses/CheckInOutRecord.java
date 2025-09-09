package SingleObjectClasses;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class CheckInOutRecord {

	private Employee employee;
	private List<Shift> shifts;
	private Stack<ShiftMemento> shiftHistory;

	public CheckInOutRecord(Employee employee) {
		this.employee = employee;
		this.shifts = new ArrayList<>();
		this.shiftHistory = new Stack<>();
	}

	public void startShift() {
		Shift shift = new Shift();
		shift.setEmployee(employee);
		shift.startShift();
		shifts.add(shift);
		shiftHistory.push(shift.saveToMemento());
	}

	public void endShift() {
		if (shifts.size() > 0) {
			Shift currentShift = shifts.get(shifts.size() - 1);
			if (currentShift.getEndDateTime() == null) {
				currentShift.endShift();
				shiftHistory.push(currentShift.saveToMemento());
			} else {
				System.out.println("No active shift to end.");
			}
		} else {
			System.out.println("No shift to end.");
		}
	}

	public void undoLastShift() {
		if (!shiftHistory.isEmpty()) {
			ShiftMemento memento = shiftHistory.pop();
			Shift shift = shifts.get(shifts.size() - 1);
			shift.restoreFromMemento(memento);
			if (shift.getEndDateTime() != null) {
				shifts.remove(shifts.size() - 1);
			}
		} else {
			System.out.println("No shift to undo.");
		}
	}

	public void printShiftDetails() {
		System.out.println("Shifts for employee: " + employee.toString());
		for (Shift shift : shifts) {
			System.out.println("Shift ID: " + shift.getId());
			System.out.println("Start Time: " + formatDateTime(shift.getStartDateTime()));
			System.out.println("End Time: " + formatDateTime(shift.getEndDateTime()));
			System.out.println("Duration: " + shift.getShiftDuration());
			System.out.println("--------------------------------");
		}
	}

	// Format LocalDateTime to a readable string
	private String formatDateTime(LocalDateTime dateTime) {
		if (dateTime == null)
			return "N/A";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return dateTime.format(formatter);
	}

	public Employee getEmployee() {
		return employee;
	}
}
