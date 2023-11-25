package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
/**
 * This class extends the Student class to represent a student who serves as a committee 
 * member in a camp. It includes functionalities for managing points earned by the member, handling suggestions 
 * given by the member, and keeping track of the associated camp.
 */
public class CampCommitteeMember extends Student {
	/**
	 * A PointsHolder object for managing the points of the committee member.
	 */
	private PointsHolder points = new PointsHolder();
	/**
	 * The Camp object representing the camp associated with the committee member.
	 */
	private Camp camp = null;
	/**
	 * A HashMap containing suggestions submitted by the committee member, mapped to their acceptance status.
	 */
	private HashMap<Suggestions, Boolean> suggList = new HashMap<Suggestions, Boolean>();;
	/**
	 * Constructs a new CampCommitteeMember with the specified ID.
	 * @param id The unique identifier of the committee member.
	 */
	public CampCommitteeMember(String id) {
		super(id);
	}
	/**
	 * Constructs a new CampCommitteeMember with specified details.
	 * @param id The unique identifier of the committee member.
	 * @param nm The name of the committee member.
	 * @param pwd The password of the committee member.
	 * @param f The faculty of the committee member.
	 * @param regList A list of camps registered by the committee member.
	 * @param dateList A list of dates blocked by the committee member.
	 * @param coord The coordinator status of the committee member.
	 * @param c The associated camp of the committee member.
	 */
	public CampCommitteeMember(String id, String nm, String pwd, FACULTIES f, ArrayList<Camp> regList,
			ArrayList<Date[]> dateList, String coord, Camp c) {
		super(id, nm, pwd, f, regList, dateList, coord);
		this.camp = c;
	}
	/**
	 * Constructs a new CampCommitteeMember with specified details.
	 * @param id The unique identifier of the committee member.
	 * @param nm The name of the committee member.
	 * @param pwd The password of the committee member.
	 * @param f The faculty (as a String) of the committee member.
	 * @param regList A list of camps registered by the committee member.
	 * @param dateList A list of dates blocked by the committee member.
	 * @param coord The coordinator status of the committee member.
	 * @param c The associated camp of the committee member.
	 */
	public CampCommitteeMember(String id, String nm, String pwd, String f, ArrayList<Camp> regList,
			ArrayList<Date[]> dateList, String coord, Camp c) {
		super(id, nm, pwd, f, regList, dateList, coord);
		this.camp = c;
	}
	// Getters
	/**
	 * Gets the PointsHolder for this committee member.
	 * @return The PointsHolder object.
	 */
	public PointsHolder getPointsHolder() {
		return this.points;
	}
	/**
	 * Gets the suggestion list for this committee member.
	 * @return A HashMap containing suggestions and their acceptance status.
	 */
	public HashMap<Suggestions, Boolean> getSuggList() {
		return this.suggList;
	}
	/**
	 * Gets the associated camp for this committee member.
	 * @return The associated Camp object.
	 */
	public Camp getCamp() {
		return this.camp;
	}
	// Setters
	/**
	 * Adds a suggestion to the suggestion list with an initial status of false (not accepted).
	 * @param s The suggestion to be added.
	 */
	public void setSuggList(Suggestions s) {
		this.suggList.put(s, false);

	}
	/**
	 * Marks a suggestion as accepted in the suggestion list.
	 * @param s The suggestion to be marked as accepted.
	 */
	public void setAcceptedWhenAccepted(Suggestions s) {
		this.suggList.replace(s, false, true);
	}
	/**
	 * Sets the associated camp for this committee member.
	 * @param camp The Camp to be associated with this committee member.
	 */
	public void setCamp(Camp camp) {
		this.camp = camp;
	}
	// Camp Committee Member management methods
	/**
	 * Removes a suggestion from the suggestion list.
	 * @param s The suggestion to be removed.
	 */
	public void remSugg(Suggestions s) {
		this.suggList.remove(s);
	}

}
