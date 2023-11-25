package controllers;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * This class is responsible for handling logo-related functionalities in the application.
 */
public class Images {

	/**
	 * Displays the CAMS logo by reading from a text file.
	 * The method reads the contents of a text file line by line and prints it to the console,
	 * thus displaying the CAMS logo in a textual format.
	 *
	 * @throws FileNotFoundException if the file containing the CAMS logo cannot be found.
	 * @throws InterruptedException if the thread is interrupted while printing the logo.
	 */
	@SuppressWarnings("resource")
	public static void CAMS_LOGO() throws FileNotFoundException, InterruptedException {
		File file = new File ("/Users/mohorbanerjee/eclipse-workspace/OOPCAMSFinalcodes/src/controllers/CAMS_Logos.txt");
		Scanner sc = new Scanner(file);
		while(sc.hasNextLine()){
			String data = sc.nextLine();
			System.out.println(data);
		}

	}


}