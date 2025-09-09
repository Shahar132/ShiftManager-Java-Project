package SingleObjectClasses;

import AbstractClasses.Person;
import Enums.Rank;
import Interfaces.Observer;

public class Employee extends Person implements Observer {
	private String role;
	private Rank rank;
	private String password;
	private String user;

	public Employee(String id, String FName, String LName, Date date, String role, Rank rank) {
		super(id, FName, LName, date);
		this.rank = rank;
		this.role = role;
		this.password = id;
		this.user = FName;
	}

	public String getPassword() {
		return password;
	}

	public String getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "Employee [Role=" + role + ", Rank=" + rank + ", First Name=" + getFirstName() + ", Last Name="
				+ getLastName() + ", Id=" + getId() + ", Birth Day=" + getBDay() + "]";
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	@Override
	public void update(String message) {
		System.out.println("Notification for " + getFirstName() + " " + getLastName() + ": " + message);
	}
}
