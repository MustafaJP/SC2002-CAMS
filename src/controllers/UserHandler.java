package controllers;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import models.DataManager;
import models.FACULTIES;
import models.Mailbox;
import models.Staff;
import models.Student;
import models.User;
import view.StaffInterface;
import view.StudentInterface;
import view.UserInterface;
/**
 * This class manages user interactions such as logging in, logging out, 
 * changing passwords, and navigating the main page based on user roles.
 */
public class UserHandler {
	/**
	 * The current user instance who is logged in.
	 */
	private static User currentUserInstance = null;
	/**
	 * The main page interface corresponding to the current user.
	 */
	private static UserInterface mainpage = null;

	// Getters

	/**
	 * Gets the current user instance.
	 *
	 * @return The current logged-in user.
	 */
	public static User getCurrentUserInstance() {
		return currentUserInstance;
	}
	/**
	 * Gets the main page interface for the current user.
	 *
	 * @return The main page interface.
	 */
	public static UserInterface getMainpage() {
		return mainpage;
	}

	// Setters

	/**
	 * Sets the current user instance.
	 *
	 * @param currentUserInstance The user to set as the current user.
	 */
	public static void setCurrentUserInstance(User currentUserInstance) {
		UserHandler.currentUserInstance = currentUserInstance;
	}
	/**
	 * Sets the main page interface for the current user.
	 *
	 * @param mainpage The main page interface to set.
	 */
	public static void setMainpage(UserInterface mainpage) {
		UserHandler.mainpage = mainpage;
	}

	// User interaction methods

	/**
	 * Handles the login portal. Authenticates users and redirects to the main page.
	 */
	public static void loginPortal() {
		authenticate();
		if (currentUserInstance.getPassword().equals(PasswordManager.defaultpassword)) {
			UserInterface.promptChangePswd();
		}
		UserHandler.hyperLinkToMainPage();

	}
	/**
	 * Handles the logout and checks for password change.
	 */
	public static void logoutToCheckPasswordChange() {
		authenticate();
	}
	/**
	 * Handles normal logout procedure and updates data accordingly.
	 */
	public static void logoutNormally() {
		System.out.println("\tLogging out...");
		DataManager.dataUpdation();
		currentUserInstance = null;
		mainpage = null;
		System.out.println("\tYou have been logged out successfully!");
		loginPortal();
	}
	/**
	 * Allows the user to change their password.
	 */
	@SuppressWarnings("resource")
	public static void changePswd() {
		Scanner s = new Scanner(System.in);
		String oldp,newp;
		System.out.println("\n\tWelcome to Change Password System");
		System.out.print("\tPlease enter your OLD password: ");
		oldp = s.nextLine();
		while (!PasswordManager.matchPassword(oldp,currentUserInstance.getPassword())) {
			System.out.print("\n\tIncorrect OLD password. Please try again: ");
			oldp = s.nextLine();
		}
		do {
			System.out.print("\n\tPlease enter your NEW password: ");
			newp = s.nextLine();
		} while (!PasswordManager.storeNewPassword(newp, currentUserInstance));
		System.out.println("\n\u001B[32;1m \tYour password has been changed successfully!");
		System.out.println("\u001B[31m \tPlease re-login to verify the change\u001B[0m");
		UserHandler.logoutToCheckPasswordChange();

	}
	/**
	 * Redirects the current user to their respective main page.
	 */
	public static void hyperLinkToMainPage() {
		mainpage.showWholePage();
	}
	/**
	 * Authenticates users by verifying their login credentials.
	 */
	@SuppressWarnings("resource")
	public static void authenticate() {
		try {
			Images.CAMS_LOGO();
		} catch (FileNotFoundException | InterruptedException e) {
			e.printStackTrace();
		}
		Scanner s = new Scanner(System.in);
		while (true) {
			System.out.print("\n\tPlease enter your NTU Network User ID (the part before @ in your school email): ");
			String id = s.nextLine();
			User tempuser = new User(id, null, "", FACULTIES.NTU);

			if (LoginHandler.doesUserExist(DataManager.getStaffDatabase(), tempuser)) {
				tempuser = new Staff(id, null, DatabaseSearchManager.SearchStaffDatabase(tempuser).getPassword(),
						FACULTIES.NTU, null);

			} else if (LoginHandler.doesUserExist(DataManager.getStudentDatabase(), tempuser)) {
				tempuser = new Student(id, null, DatabaseSearchManager.SearchStudentDatabase(tempuser).getPassword(),
						FACULTIES.NTU, null, null, null);
			} else {
				System.out.println("\tUser ID could not be found. Please try again!");
				continue;
			}
			System.out.print("\tPlease enter your password: ");
			String p = s.nextLine();
			if (LoginHandler.doesPswdMatch(p, tempuser)) {
				if (tempuser instanceof Staff) {
					currentUserInstance = DatabaseSearchManager.SearchStaffDatabase(tempuser);

					setMainpage(new StaffInterface());

				} else if (tempuser instanceof Student) {
					if (LoginHandler.doesUserExist(DataManager.getCampComMemDatabase(), tempuser)) {
						currentUserInstance = DatabaseSearchManager.SearchCampComMemDatabase(tempuser);

					} else {
						currentUserInstance = DatabaseSearchManager.SearchStudentDatabase(tempuser);

					}
					setMainpage(new StudentInterface());
				}
				if (DataManager.getMailboxDatabase()
						.contains(new Mailbox(new ArrayList<String>(), currentUserInstance.getUserAccountId()))) {
					;
					currentUserInstance.setUserInbox(
							DatabaseSearchManager.SearchMailboxDatabase(currentUserInstance.getUserAccountId()));
				}
				break;
			} else {
				System.out.println("\tPassword does not match. Please try again!");

			}

		}

	}

}
