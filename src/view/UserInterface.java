package view;

import java.util.Scanner;

import controllers.UserHandler;
/**
 * The UserInterface class is an abstract class providing a template for user interfaces in the application.
 * It includes methods to prompt password change, display header information, and abstract methods for specific interface implementations.
 */
public abstract class UserInterface {
	/**
	 * Prompts the user to change their password from the default one for security reasons.
	 * The user can choose to change the password immediately or do it later.
	 */
	@SuppressWarnings("resource")
	public static void promptChangePswd() {

		System.out.println(
				"\u001B[31m" +
						"\n\tFor security reasons, you are advised to change your password from the default one.\n" +
						"\u001B[31;1m \tPress\u001B[32;1m Y\u001B[31;1m to change password. Press \u001B[32;1m N \u001B[31;1m if you choose to do it later." +
						"\u001B[0m"
				);
		Scanner s = new Scanner(System.in);
		System.out.print("\t");
		String c = s.nextLine();
		System.out.println("=".repeat(100));
		if (c.equalsIgnoreCase("Y")) {
			UserHandler.changePswd();

		} else if (c.equalsIgnoreCase("N")) {
			System.out.println("\u001B[34m" + "\n\tContinuing to main page...\n" + "\u001B[0m");

		}

	}
	/**
	 * Displays the topmost header of the user interface.
	 * This method prints a fixed title "Profile" at the top of the interface.
	 */
	public void showTopmostHeader() {
		String title = "Profile";
		System.out.println("=".repeat(100));
		System.out.println(String.format("%" + ((100 - title.length()) / 2 + title.length()) + "s", title));
		System.out.println("=".repeat(100));
	}
	/**
	 * Abstract method to display specific byline information.
	 * Implementations of this method should provide specific byline content for different user interfaces.
	 */
	public abstract void showByLine();
	/**
	 * Abstract method to display the user's profile header.
	 * Implementations of this method should provide specific header content based on the user's profile details.
	 */
	public abstract void showProfileHeader();
	/**
	 * Abstract method to display the main menu page.
	 * Implementations of this method should provide specific menu options relevant to the user's role and functionalities.
	 */
	public abstract void showMenuPage();
	/**
	 * Combines various display components to show the complete page.
	 * This method orchestrates the display of the topmost header, profile header, byline, and the main menu page.
	 */
	public void showWholePage() {
		this.showTopmostHeader();
		this.showProfileHeader();
		this.showByLine();
		this.showMenuPage();
	}

}
