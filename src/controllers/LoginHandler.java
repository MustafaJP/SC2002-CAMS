package controllers;

import java.util.ArrayList;

import models.User;
/**
 * This class provides functionality for managing user logins. 
 * It includes methods to check if a user exists in a given database and to verify if the 
 * provided password matches the stored password for a user.
 */
public class LoginHandler {
	/**
	 * Checks if a given user exists in a specified database.
	 *
	 * @param database The database (list of users) to search within.
	 * @param newuser  The user to search for in the database.
	 * @return true if the user exists in the database, false otherwise.
	 */
	public static boolean doesUserExist(ArrayList<? extends User> database, User newuser) {
		if (database.contains(newuser)) {
			return true;
		}
		return false;

	}
	/**
	 * Checks if the input password matches the stored password for a specific user.
	 *
	 * @param inputpswd  The password entered by the user.
	 * @param userrecord The user record whose password is to be matched against.
	 * @return true if the input password matches the stored password, false otherwise.
	 */
	public static boolean doesPswdMatch(String inputpswd, User userrecord) {
		if (PasswordManager.matchPassword(inputpswd, userrecord.getPassword())) {
			return true;
		}
		return false;

	}
}
