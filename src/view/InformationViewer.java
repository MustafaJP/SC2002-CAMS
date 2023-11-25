package view;

import java.util.ArrayList;
import java.util.Arrays;

import models.Camp;
import models.Enquiries;
import models.Suggestions;
/**
 * This class provides static methods to display formatted information
 * about various entities like camps, enquiries, and suggestions. It is used to present
 * data in a structured and readable format.
 */
public class InformationViewer {

	/**
	 * Generates headers for camp information display based on the provided flags.
	 *
	 * @param toggle Indicates whether to include visibility in the header.
	 * @param role   Indicates whether to include role-specific information.
	 * @return An array of strings representing the headers for camp information.
	 */
	public static String[] campInfoHeader(boolean toggle, boolean role) {
		if (role) {
			return new String[]{
					"Camp Name","Role", "Staff-In-Charge", "Description", "Location", "Faculty",
					"Total Slots (Attendee+Commitee)", "Total Committee Slots", "Start Date", "End Date",
					"Registration Deadline", "Empty Slots (Attendee+Committee)", "Empty Committee Slots"
			};
		} else if (!toggle) {
			return new String[]{
					"Camp Name","Staff-In-Charge", "Description", "Location", "Faculty",
					"Total Slots (Attendee+Commitee)", "Total Committee Slots", "Start Date", "End Date",
					"Registration Deadline", "Empty Slots (Attendee+Committee)", "Empty Committee Slots"
			};
		} else {
			return new String[]{
					"Camp Name","Staff-In-Charge", "Description", "Location", "Faculty",
					"Total Slots (Attendee+Commitee)", "Total Committee Slots", "Start Date", "End Date",
					"Registration Deadline", "Empty Slots (Attendee+Committee)", "Empty Committee Slots",
					"Visibility"
			};
		}
	}

	/**
	 * Generates headers for displaying registrant information.
	 *
	 * @return An array of strings representing the headers for registrant information.
	 */

	public static String[] registrantsHeader() {
		return new String[]{
				"User ID", "Name", "Faculty", "Role", "NTU Email"
		};
	}
	/**
	 * Generates headers for displaying committee member information.
	 *
	 * @param points Indicates whether to include points information in the header.
	 * @return An array of strings representing the headers for committee information.
	 */
	public static String[] comsHeader(boolean points) {
		if (points) {
			return new String[]{
					"User ID", "Name", "Faculty", "Points", "NTU Email"
			};
		} else {
			return new String[]{
					"User ID", "Name", "Faculty", "NTU Email"
			};
		}
	}
	/**
	 * Generates headers for enquiry information display.
	 *
	 * @param skipsender   Indicates whether to skip displaying sender information.
	 * @param skipreplier  Indicates whether to skip displaying replier information.
	 * @return An array of strings representing the headers for enquiry information.
	 */
	public static String[] enquiriesInfoHeader(boolean skipsender, boolean skipreplier) {
		if (skipsender) {
			return new String[]{
					"Enquiry Index", "Enquiry", "Camp", "Reply", "Replier", "Status"
			};
		} else if (skipreplier) {
			return new String[]{
					"Enquiry Index", "Enquiry", "Sender", "Camp", "Reply", "Status"
			};
		} else {
			return new String[]{
					"Enquiry Index", "Enquiry", "Sender", "Replier", "Reply", "Status"
			};
		}
	}

	/**
	 * Generates headers for suggestions information display.
	 *
	 * @param skipsender Indicates whether to skip displaying sender information.
	 * @return An array of strings representing the headers for suggestions information.
	 */
	public static String[] suggestionsInfoHeader(boolean skipsender) {
		if (skipsender) {
			return new String[]{
					"Suggestion Index", "Suggestion", "Camp", "Status"
			};
		} else {
			return new String[]{
					"Suggestion Index", "Suggestion", "Sender", "Status"
			};
		}
	}

	/**
	 * Displays a list of camp names.
	 *
	 * @param campsList The list of Camp objects to be displayed.
	 */
	public static void campNamesViewer(ArrayList<Camp> campsList) {
		int i=1;
		System.out.println();
		for (Camp c : campsList) {
			System.out.println("\t\t"+i+". "+c.getcampName());
			i++;
		}
		System.out.println();
	}
	/**
	 * Prints detailed information for a specific camp.
	 *
	 * @param filtered The Camp object to display information for.
	 * @param headers  The headers for the information to be displayed.
	 * @param skiptoggle Indicates whether to skip specific details in the display.
	 */
	public static void printCampofCor(Camp filtered, String[] headers, boolean skiptoggle) {
		String[][] contentArray = filtered.toStringArray(skiptoggle);
		System.out.println("=".repeat(100));
		int padding = (98 - contentArray[0][1].length()) / 2;
		String leftPad = " ".repeat(padding);
		String rightPad = " ".repeat(98 - padding - contentArray[0][1].length());
		System.out.println("|" + leftPad + contentArray[0][1] + rightPad + "|");
		System.out.println("|" + "-".repeat(98) + "|");

		for (int i = 1; i < headers.length; i++) {
			String header = headers[i];

			String content = Arrays.stream(contentArray)
					.filter(pair -> pair[0].equals(header))
					.findFirst()
					.map(pair -> pair[1])
					.orElse(""); 
			System.out.printf("| %-45s | %-48s |\n", header, content);
		}
		System.out.println("=".repeat(100)+"\n");
	}
	/**
	 * Prints detailed information for a list of camps.
	 *
	 * @param filtered The list of Camp objects to display information for.
	 * @param headers  The headers for the information to be displayed.
	 * @param skiptoggle Indicates whether to skip specific details in the display.
	 */
	public static void printCamps(ArrayList<Camp> filtered, String[] headers, boolean skiptoggle) {
		for (Camp c : filtered) {
			String[][] contentArray = c.toStringArray(skiptoggle);
			System.out.println("=".repeat(100));
			int padding = (98 - contentArray[0][1].length()) / 2;
			String leftPad = " ".repeat(padding);
			String rightPad = " ".repeat(98 - padding - contentArray[0][1].length());
			System.out.println("|" + leftPad + contentArray[0][1] + rightPad + "|");
			System.out.println("|" + "-".repeat(98) + "|");

			for (int i = 1; i < headers.length; i++) {
				String header = headers[i];

				String content = Arrays.stream(contentArray)
						.filter(pair -> pair[0].equals(header))
						.findFirst()
						.map(pair -> pair[1])
						.orElse(""); 
				System.out.printf("| %-45s | %-48s |\n", header, content);
			}
			System.out.println("=".repeat(100)+"\n");
		}
	}
	/**
	 * Prints detailed information for a list of enquiries.
	 *
	 * @param enqlist The list of Enquiries to display information for.
	 * @param headers The headers for the information to be displayed.
	 * @param skipreplier Indicates whether to skip displaying replier information.
	 * @param skipsender Indicates whether to skip displaying sender information.
	 */
	public static void printEnquiry(ArrayList<Enquiries> enqlist, String[] headers, boolean skipreplier, boolean skipsender) {
		for (Enquiries e : enqlist) {
			System.out.println("=".repeat(100));
			int padding = (98 - headers[0].length()) / 2;
			String leftPad = " ".repeat(padding);
			String rightPad = " ".repeat(95 - padding - headers[0].length());
			System.out.println("|" + leftPad + headers[0]+": "+e.getMessageIndex() + rightPad + "|");
			System.out.println("|" + "-".repeat(98) + "|");


			String[][] contentArray = e.toStringArray(skipreplier,skipsender); 

			for (int i = 1; i < headers.length; i++) {
				String header = headers[i];

				String content = Arrays.stream(contentArray)
						.filter(pair -> pair[0].equals(header))
						.findFirst()
						.map(pair -> pair[1])
						.orElse(""); 
				System.out.printf("| %-45s | %-48s |\n", header, content);
			}
			System.out.println("=".repeat(100)+"\n");
		}
	}
	/**
	 * Prints detailed information for a list of suggestions.
	 *
	 * @param suglist The list of Suggestions to display information for.
	 * @param headers The headers for the information to be displayed.
	 * @param skipsender Indicates whether to skip displaying sender information.
	 */
	public static void printSuggestion(ArrayList<Suggestions> suglist, String[] headers, boolean skipsender) {
		for (Suggestions s : suglist) {
			System.out.println("=".repeat(100));
			int padding = (98 - headers[0].length()) / 2;
			String leftPad = " ".repeat(padding);
			String rightPad;
			if(s.getMessageIndex()>=10) {
				rightPad = " ".repeat(94 - padding - headers[0].length());
			} else {
				rightPad = " ".repeat(95 - padding - headers[0].length());
			}
			System.out.println("|" + leftPad + headers[0]+": "+s.getMessageIndex() + rightPad + "|");
			System.out.println("|" + "-".repeat(98) + "|");


			String[][] contentArray = s.toStringArray(skipsender); 

			for (int i = 1; i < headers.length; i++) {
				String header = headers[i];

				String content = Arrays.stream(contentArray)
						.filter(pair -> pair[0].equals(header))
						.findFirst()
						.map(pair -> pair[1])
						.orElse(""); 
				System.out.printf("| %-45s | %-48s |\n", header, content);
			}
			System.out.println("=".repeat(100)+"\n");
		}
	}


}
