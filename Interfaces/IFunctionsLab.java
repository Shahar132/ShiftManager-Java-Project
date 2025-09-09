package Interfaces;

import java.util.Scanner;

public interface IFunctionsLab {
	public static Scanner scan = new Scanner(System.in);

	public static int IntegerChoiceRange(int min, int max) {
		int choice = 0;
		do {
			choice = checkInputStringToInt();
			System.out.println((choice < min || choice > max) ? "Invalid Option, Try Again" : "Good.");
		} while (choice < min || choice > max);
		return choice;
	}

	public static String inputId() {
		String id;
		System.out.println("Enter ID [9 digits]:");
		do {
			id = Integer.toString(checkInputStringToIntNegetive());
			System.out.println((id.length() != 9) ? "Invalid ID,try again" : "Good.");
		} while (id.length() != 9);
		return id;
	}

	public static String inputPass() {
		String pass;
		System.out.println("Enter ID [9 digits]:");
		do {
			pass = Integer.toString(checkInputStringToIntNegetive());
			System.out.println((pass.length() != 9) ? "Invalid ID,try again" : "Good.");
		} while (pass.length() != 9);
		return pass;
	}

	public static int dayInput() {
		System.out.println("Enter Day [1 - 31]:");
		int day = IntegerChoiceRange(1, 31);
		return day;
	}

	public static int monthInput() {
		System.out.println("Enter Month [1 - 12]:");
		int month = IntegerChoiceRange(1, 12);
		return month;
	}

	public static int yearInput() {
		System.out.println("Enter Year [1950 - 2024]:");
		int year = IntegerChoiceRange(1950, 2024);
		return year;
	}

	public static int hourInput() {
		System.out.println("Enter hour [00 - 23]:");
		int hour = IntegerChoiceRange(0, 23);
		return hour;
	}

	public static int minuteInput() {
		System.out.println("Enter minute [0 - 60]:");
		int minute = IntegerChoiceRange(0, 60);
		return minute;
	}

	public static int secondsInput() {
		System.out.println("Enter seconds [0 - 60]:");
		int seconds = IntegerChoiceRange(0, 60);
		return seconds;
	}

	public static String[] DateToStringInput() {
		String[] str;
		str = scan.nextLine().split("/");
		return str;
	}

	public static String formatDate() {
		int year = yearInput();
		int month = monthInput();
		int day = dayInput();
		return String.format("%04d-%02d-%02d", year, month, day); // Ensure leading zeros
	}

	public static String formatTime() {
		int year = yearInput();
		int month = monthInput();
		int day = dayInput();
		int hour = hourInput();
		int minute = minuteInput();
		int seconds = secondsInput();
		return String.format("%04d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, seconds); // Ensure
																										// leading zeros
	}

	public static int checkInputStringToIntNegetive() {
		boolean success = false;
		int num = 0;
		while (!success) {
			try {
				String choiceText = scan.nextLine();
				num = Integer.parseInt(choiceText);
				success = true;
			} catch (Exception inputTextException) {
				System.out.println("You should type a number!");
			}
		}
		if (num < 0) {
			num *= (-1);
		}
		return num;
	}

	public static int checkInputStringToInt() {
		boolean success = false;
		int num = 0;
		while (!success) {
			try {
				String choiceText = scan.nextLine();
				num = Integer.parseInt(choiceText);
				success = true;
			} catch (Exception inputTextException) {
				System.out.println("You should type a number!");
			}
		}
		return num;
	}

	public static String lastNameInput() {
		System.out.println("Please enter last name:");
		return stringInput();
	}

	public static String firstNameInput() {
		System.out.println("Please enter first name:");
		return stringInput();
	}

	public static String stringInput() {
		String str;
		do {
			str = scan.nextLine();
		} while (!isValidAlphabetString(str));
		return str;
	}

	private static boolean isValidAlphabetString(String input) {
		// Check if the input contains only alphabets
		return input.matches("^[a-zA-Z]+$");
	}
}
