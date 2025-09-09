package Manegers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DataBase {

	private static DataBase instance;
	private HashMap<String, ArrayList<String>> passwords;

	private DataBase() {
		this.passwords = new HashMap<>();
	}

	public static DataBase getInstance() {
		if (instance == null) {
			instance = new DataBase();
		}
		return instance;
	}

	public boolean addUser(String pass, String username) {
		ArrayList<String> users = passwords.get(pass);
		if (users == null) {
			passwords.put(pass, new ArrayList<>(Arrays.asList(username)));
			System.out.println("Debug: Added new user: " + username + " with password: " + pass);
			return true;
		} else {
			if (users.contains(username)) {
				return false;
			} else {
				users.add(username);
				System.out.println("Debug: Added user to existing password: " + username + " with password: " + pass);
				return true;
			}
		}
	}

	public boolean isUser(String pass, String username) {
		boolean isUser = passwords.get(pass) != null && passwords.get(pass).contains(username);
		System.out.println("Debug: Checking user: " + username + " with password: " + pass + " - Result: " + isUser);
		return isUser;
	}

	public boolean removeUser(String pass, String username) {
		ArrayList<String> users = passwords.get(pass);
		if (users != null && users.contains(username)) {
			users.remove(username);
			if (users.isEmpty()) {
				passwords.remove(pass); // Remove the password entry if no users are left
			}
			System.out.println("Debug: Removed user: " + username);
			return true;
		}
		return false;
	}
}
