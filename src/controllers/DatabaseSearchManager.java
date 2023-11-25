package controllers;

import java.util.ArrayList;

import models.Camp;
import models.CampCommitteeMember;
import models.DataManager;
import models.Enquiries;
import models.Mailbox;
import models.Messages;
import models.Staff;
import models.Student;
import models.Suggestions;
import models.User;
/**
 * The DatabaseSearchManager class provides methods for searching various databases 
 * within the system. It offers specialized search functionality for camps, students, staff, 
 * committee members, suggestions, enquiries, and mailboxes using different search criteria. 
 */
public class DatabaseSearchManager {
	/**
	 * Searches for a Camp object in the database using a Camp object as the search key.
	 * 
	 * @param c The Camp object to search for.
	 * @return The matching Camp object from the database, or null if not found.
	 */
	public static Camp SearchCampsDatabase(Camp c) {
		try {
			return DataManager.getCampsDatabase().get(DataManager.getCampsDatabase().indexOf(c));}
		catch(Exception e) {
			return null;
		}
	}
	/**
	 * Searches for a Camp object in the database using a camp name.
	 * 
	 * @param cname The name of the camp to search for.
	 * @return The matching Camp object from the database, or null if not found.
	 */
	public static Camp SearchCampsDatabase(String cname) {
		try {
			return DataManager.getCampsDatabase().get(DataManager.getCampsDatabase().indexOf(new Camp(cname)));}
		catch(Exception e) {
			return null;
		}

	}
	/**
	 * Searches for a Student object in the database using a User object as the search key.
	 * 
	 * @param s The User object to search for.
	 * @return The matching Student object from the database, or null if not found.
	 */
	public static Student SearchStudentDatabase(User s) {
		try {
			return DataManager.getStudentDatabase().get(DataManager.getStudentDatabase().indexOf(s));}
		catch(Exception e) {
			return null;
		}
	}
	/**
	 * Searches for a Student object in the database using a student's name.
	 * 
	 * @param stuname The name of the student to search for.
	 * @return The matching Student object from the database, or null if not found.
	 */
	public static Student SearchStudentDatabase(String stuname) {
		try {
			return DataManager.getStudentDatabase().get(DataManager.getStudentDatabase().indexOf(new User(stuname)));}
		catch(Exception e) {
			return null;
		}
	}
	/**
	 * Searches for a Staff object in the database using a User object as the search key.
	 * 
	 * @param s The User object to search for.
	 * @return The matching Staff object from the database, or null if not found.
	 */
	public static Staff SearchStaffDatabase(User s) {
		try {
			return DataManager.getStaffDatabase().get(DataManager.getStaffDatabase().indexOf(s));}
		catch(Exception e) {
			return null;
		}
	}
	/**
	 * Searches for a Staff object in the database using a staff member's name.
	 * 
	 * @param stafname The name of the staff member to search for.
	 * @return The matching Staff object from the database, or null if not found.
	 */
	public static Staff SearchStaffDatabase(String stafname) {
		try {
			return DataManager.getStaffDatabase().get(DataManager.getStaffDatabase().indexOf(new User(stafname)));}
		catch(Exception e) {
			return null;
		}
	}
	/**
	 * Searches for a CampCommitteeMember object in the database using a User object as the search key.
	 * 
	 * @param ccm The User object representing a Camp Committee Member to search for.
	 * @return The matching CampCommitteeMember object from the database, or null if not found.
	 */
	public static CampCommitteeMember SearchCampComMemDatabase(User ccm) {
		try {
			return DataManager.getCampComMemDatabase().get(DataManager.getCampComMemDatabase().indexOf(ccm));}
		catch(Exception e) {
			return null;
		}
	}
	/**
	 * Searches for a CampCommitteeMember object in the database using a member's name.
	 * 
	 * @param ccmname The name of the Camp Committee Member to search for.
	 * @return The matching CampCommitteeMember object from the database, or null if not found.
	 */
	public static CampCommitteeMember SearchCampComMemDatabase(String ccmname) {
		try {
			return DataManager.getCampComMemDatabase().get(DataManager.getCampComMemDatabase().indexOf(new User(ccmname)));}
		catch(Exception e) {
			return null;
		}
	}
	/**
	 * Searches for a Suggestions object in the database using a Messages object as the search key.
	 * 
	 * @param sugg The Messages object to search for.
	 * @return The matching Suggestions object from the database, or null if not found.
	 */
	public static Suggestions SearchSuggestionsDatabase(Messages sugg) {
		try {
			return DataManager.getSuggestionsDatabase().get(DataManager.getSuggestionsDatabase().indexOf(sugg));}
		catch(Exception e) {
			return null;
		}
	}
	/**
	 * Searches for a Suggestions object in the database using a suggestion index.
	 * 
	 * @param suggindex The index of the suggestion to search for.
	 * @return The matching Suggestions object from the database, or null if not found.
	 */
	public static Suggestions SearchSuggestionsDatabase(int suggindex) {
		try {
			return DataManager.getSuggestionsDatabase()
					.get(DataManager.getSuggestionsDatabase().indexOf(new Messages(null, null, suggindex, null)));}
		catch(Exception e) {
			return null;
		}
	}
	/**
	 * Searches for an Enquiries object in the database using a Messages object as the search key.
	 * 
	 * @param enq The Messages object to search for.
	 * @return The matching Enquiries object from the database, or null if not found.
	 */
	public static Enquiries SearchEnquiriesDatabase(Messages enq) {
		try {
			return DataManager.getEnquiriesDatabase().get(DataManager.getEnquiriesDatabase().indexOf(enq));}
		catch(Exception e) {
			return null;
		}
	}
	/**
	 * Searches for an Enquiries object in the database using an enquiry index.
	 * 
	 * @param enqindex The index of the enquiry to search for.
	 * @return The matching Enquiries object from the database, or null if not found.
	 */
	public static Enquiries SearchEnquiriesDatabase(int enqindex) {
		try {
			return DataManager.getEnquiriesDatabase()
					.get(DataManager.getEnquiriesDatabase().indexOf(new Messages(null, null, enqindex, null)));}
		catch(Exception e) {
			return null;
		}
	}
	/**
	 * Searches for a Mailbox object in the database using a Mailbox object as the search key.
	 * 
	 * @param m The Mailbox object to search for.
	 * @return The matching Mailbox object from the database, or null if not found.
	 */
	public static Mailbox SearchMailboxDatabase(Mailbox m) {
		try {
			return DataManager.getMailboxDatabase().get(DataManager.getMailboxDatabase().indexOf(m));}
		catch(Exception e) {
			return null;
		}
	}
	/**
	 * Searches for a Mailbox object in the database using the mailbox owner's name.
	 * 
	 * @param owner The name of the mailbox owner to search for.
	 * @return The matching Mailbox object from the database, or null if not found.
	 */
	public static Mailbox SearchMailboxDatabase(String owner) {
		try {
			return DataManager.getMailboxDatabase()
					.get(DataManager.getMailboxDatabase().indexOf(new Mailbox(new ArrayList<String>(), owner)));}
		catch(Exception e) {
			return null;
		}
	}
}
