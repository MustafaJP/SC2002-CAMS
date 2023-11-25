package mainapp;

import java.io.IOException;

import controllers.UserHandler;
import models.DataManager;
/**
 * The MainApp class is the entry point of the application.
 * It contains the main method and functionalities to initialize and launch the application.
 */
public class MainApp {
	/**
	 * Initializes and launches the application.
	 * This method loads the necessary data using DataManager and then directs the user to the login portal.
	 */
	public static void launchApp() {

		DataManager.dataLoader();

		UserHandler.loginPortal();
	}
	/**
	 * The main method that serves as the entry point for the application.
	 *
	 * @param args Command-line arguments passed to the application (not used).
	 * @throws IOException If an I/O error occurs during application initialization or operation.
	 */
	public static void main(String[] args) throws IOException {
		launchApp();

	}
}

