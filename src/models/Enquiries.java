package models;

import java.util.ArrayList;
import java.util.List;
/**
 * This class represents an enquiry in the system, extending the Messages class.
 * Each enquiry has a unique identifier, a message, a sender, a camp associated with it,
 * a helper who responds to the enquiry, and a reply.
 */
public class Enquiries extends Messages {
	/**
	 * The total number of enquiries made within the system.
	 */
	private static int totalEnquiries = 0;
	/**
	 * The user who is handling or responding to the enquiry.
	 */
	private User helper = null;
	/**
	 * The response or reply to the enquiry.
	 */
	private String reply = new String("");
	/**
	 * Constructs an Enquiry with a given number.
	 * @param number The unique identifier for the enquiry.
	 */
	public Enquiries(int number) {
		super(number);
	}

	/**
	 * Constructs an Enquiry with a specific message, student, and camp.
	 * Increments the total number of enquiries.
	 * @param sg The enquiry message.
	 * @param stud The student making the enquiry.
	 * @param c The camp associated with the enquiry.
	 */
	public Enquiries(String sg, Student stud, Camp c) {
		super(sg, c, ++totalEnquiries, stud);

	}
	/**
	 * Constructs an Enquiry with a specific message, replacement number, student, and camp.
	 * @param sg The enquiry message.
	 * @param replace The number to replace the total enquiries count.
	 * @param stud The student making the enquiry.
	 * @param c The camp associated with the enquiry.
	 */
	public Enquiries(String sg, int replace, Student stud, Camp c) {
		super(sg, c, replace, stud);

	}
	// Getters
	/**
	 * Returns the helper (user) who responded to the enquiry.
	 * @return A User object representing the helper.
	 */
	public User getHelper() {
		return this.helper;
	}
	/**
	 * Returns the total number of enquiries.
	 * @return Total number of enquiries as an integer.
	 */
	public static int getTotalEnquiries() {
		return totalEnquiries;
	}
	/**
	 * Returns the reply to the enquiry.
	 * @return The reply as a String.
	 */
	public String getReply() {
		return this.reply;
	}
	// Setters
	/**
	 * Sets the helper (user) who responded to the enquiry.
	 * @param hr The User object to be set as the helper.
	 */
	public void setHelper(User hr) {
		this.helper = hr;
	}
	/**
	 * Sets the total number of enquiries.
	 * @param totalenq The total number of enquiries to set.
	 */
	public static void setTotalEnquiries(int totalenq) {
		totalEnquiries = totalenq;
	}
	/**
	 * Sets the reply to the enquiry.
	 * @param ans The reply to be set.
	 */
	public void setReply(String ans) {
		this.reply = ans;
	}
	// Enquiry management methods
	/**
	 * Converts the enquiry to a String representation, with options to skip the replier or sender.
	 * @param skipreplier If true, skips adding the replier's information to the String.
	 * @param skipsender If true, skips adding the sender's information to the String.
	 * @return A String representation of the enquiry.
	 */
	public String toString(boolean skipreplier, boolean skipsender) {
		String ans = (this.reply.equalsIgnoreCase("")) ? "NA" : this.reply;
		String state = (this.isStatus() == true) ? "Processed" : "Being Processed";
		if (skipsender) {
			String replier = (this.helper == null) ? "NA" : this.helper.getUserAccountId();
			return "#" + this.getMessageIndex() + "        " + this.getS() + "        " + this.getC().getcampName() + "        " + ans
					+ "        " + replier + "        " + state;
		}
		if (!skipreplier) {
			String replier = (this.helper == null) ? "NA" : this.helper.getUserAccountId();

			return "#" + this.getMessageIndex() + "        " + this.getS() + "        " + this.getSender().getUserAccountId()
					+ "        " + replier + "        " + ans + "        " + state;
		} else {
			return "#" + this.getMessageIndex() + "        " + this.getS() + "        " + this.getSender().getUserAccountId()
					+ "        " + this.getC().getcampName() + "        " + ans + "        " + state;
		}
	}
	/**
	 * Converts the enquiry to a 2D String array representation, with options to skip the replier or sender.
	 * @param skipreplier If true, skips adding the replier's information to the array.
	 * @param skipsender If true, skips adding the sender's information to the array.
	 * @return A 2D String array representation of the enquiry.
	 */
	public String[][] toStringArray(boolean skipreplier, boolean skipsender) {
		List<String[]> details = new ArrayList<>();

		details.add(new String[]{"Enquiry Index", "#" + this.getMessageIndex()});
		details.add(new String[]{"Enquiry", this.getS()});
		details.add(new String[]{"Camp", this.getC().getcampName()});
		details.add(new String[]{"Reply", this.reply.equalsIgnoreCase("") ? "NA" : this.reply});
		details.add(new String[]{"Status", this.isStatus() ? "Processed" : "Being Processed"});

		if (!skipsender) {
			details.add(new String[]{"Sender", this.getSender().getUserAccountId()});
		}
		if (!skipreplier) {
			String replier = (this.helper == null) ? "NA" : this.helper.getUserAccountId();
			details.add(new String[]{"Replier", replier});
		}

		return details.toArray(new String[0][]);
	}



}
