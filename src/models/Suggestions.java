package models;

import java.util.ArrayList;
import java.util.List;
/**
 * This class extends the Messages class to represent suggestions made by users.
 * It includes functionality for managing suggestion-related data, such as indexing and content.
 */
public class Suggestions extends Messages {
	/**
	 * The total number of suggestions made within the system.
	 */
	private static int totalSuggestions = 0;
	/**
	 * Constructs a Suggestions object with a specific suggestion index.
	 * 
	 * @param number the unique suggestion index
	 */
	public Suggestions(int number) {
		super(number);
	}
	/**
	 * Constructs a Suggestions object with a suggestion text and the sender (CampCommitteeMember).
	 * Automatically increments the total count of suggestions.
	 * 
	 * @param sg the suggestion text
	 * @param stud the CampCommitteeMember who is sending the suggestion
	 */
	public Suggestions(String sg, CampCommitteeMember stud) {
		super(sg, stud.getCamp(), ++totalSuggestions, stud);

	}
	/**
	 * Constructs a Suggestions object with a suggestion text, a replacement index, and the sender (CampCommitteeMember).
	 * 
	 * @param sg the suggestion text
	 * @param replace the index to replace with the current suggestion index
	 * @param stud the CampCommitteeMember who is sending the suggestion
	 */
	public Suggestions(String sg, int replace, CampCommitteeMember stud) {
		super(sg, stud.getCamp(), replace, stud);
	}
	// Getters
	/**
	 * Retrieves the total number of suggestions made.
	 * 
	 * @return the total count of suggestions
	 */	
	public static int getTotalSuggestions() {
		return totalSuggestions;
	}
	// Setters
	/**
	 * Sets the total count of suggestions.
	 * 
	 * @param totalSugg the new total count of suggestions
	 */
	public static void setTotalSuggestions(int totalSugg) {
		totalSuggestions = totalSugg;
	}
	// Suggestion management methods
	/**
	 * Converts the suggestion details to a String array format for display.
	 * The format includes suggestion index, text, associated camp, status, and optionally the sender.
	 * 
	 * @param skipsender if true, the sender's information is omitted from the details
	 * @return a 2D String array representing the details of the suggestion
	 */	
	public String[][] toStringArray(boolean skipsender) {
		List<String[]> detailsList = new ArrayList<>();


		detailsList.add(new String[]{"Suggestion Index", String.valueOf(this.getMessageIndex())});
		detailsList.add(new String[]{"Suggestion", this.getS()});
		detailsList.add(new String[]{"Camp", this.getC().getcampName()});
		detailsList.add(new String[]{"Status", this.isStatus() ? "Accepted" : "Being Processed"});


		if (!skipsender) {
			detailsList.add(new String[]{"Sender", this.getSender().getUserAccountId()});
		}

		return detailsList.toArray(new String[0][]);
	}


}
