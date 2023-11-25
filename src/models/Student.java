package models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import controllers.DatabaseSearchManager;
import controllers.StudentHandler;
/**
 * This class extends the User class and represents a student user.
 * It includes functionalities such as registering for camps, managing blocked dates,
 * handling enquiries, and managing camp coordinator roles.
 */
public class Student extends User implements Comparable<Student> {
	/**
	 * List of camps that the student is registered in.
	 */
	private ArrayList<Camp> regCamps = new ArrayList<Camp>();
	/**
	 * List of dates during which the student is unavailable or blocked.
	 */
	private ArrayList<Date[]> blockeddates = new ArrayList<Date[]>();
	/**
	 * Name of the camp if the student is also a camp coordinator (set to NA if student is not a coordinator in any camp yet)
	 */
	private String coordinator;
	/**
	 * List of enquiries made by the student along with their resolution status.
	 */
	private HashMap<Enquiries, Boolean> myenqlist = new HashMap<Enquiries, Boolean>();
	/**
	 * Constructs a Student object with a specified user account ID.
	 * 
	 * @param id the user account ID
	 */
	public Student(String id) {
		super(id);
	}
	/**
	 * Constructs a Student object with detailed information including user account ID, name, password, faculty, registered camps list, blocked dates list, and coordinator role.
	 * @param id the user account ID
	 * @param nm the name of the student
	 * @param pwd the hashed password of the student
	 * @param f the faculty of the student as an enum
	 * @param reglist the list of camps the student is registered in
	 * @param datelist the list of dates during which the student is unavailable or blocked
	 * @param coord the name of the camp if the student is also a camp coordinator
	 */
	public Student(String id, String nm, String pwd, FACULTIES f, ArrayList<Camp> reglist, ArrayList<Date[]> datelist,
			String coord) {
		super(id, nm, pwd, f);
		if (reglist == null) {
			this.regCamps = new ArrayList<Camp>();
		} else {
			this.regCamps = reglist;
		}
		if (datelist == null) {
			this.blockeddates = new ArrayList<Date[]>();
		} else {
			this.blockeddates = datelist;
		}
		this.coordinator = coord;


	}
	/**
	 * Constructs a Student object with detailed information including user account ID, name, password, faculty, registered camps list, blocked dates list, and coordinator role.
	 * This constructor is used when the faculty is provided as a String.
	 * 
	 * @param id the user account ID
	 * @param nm the name of the student
	 * @param pwd the hashed password of the student
	 * @param f the faculty of the student as a String
	 * @param reglist the list of camps the student is registered in
	 * @param datelist the list of dates during which the student is unavailable or blocked
	 * @param coord the name of the camp if the student is also a camp coordinator
	 */
	public Student(String id, String nm, String pwd, String f, ArrayList<Camp> reglist, ArrayList<Date[]> datelist,
			String coord) {
		super(id, nm, pwd, f);
		if (reglist == null) {
			this.regCamps = new ArrayList<Camp>();
		} else {
			this.regCamps = reglist;
		}
		if (datelist == null) {
			this.blockeddates = new ArrayList<Date[]>();
		} else {
			this.blockeddates = datelist;
		}
		this.coordinator = coord;

	}
	// Getters
	/**
	 * Gets the list of enquiries and their resolution status for the student.
	 * 
	 * @return A HashMap representing the enquiries and their status (true for resolved, false otherwise).
	 */
	public HashMap<Enquiries, Boolean> getEnqList() {
		return this.myenqlist;
	}
	/**
	 * Gets the list of dates during which the student is unavailable or blocked.
	 * 
	 * @return An ArrayList of Date arrays, each array represents a range of blocked dates.
	 */
	public ArrayList<Date[]> getBlockeddates() {
		return this.blockeddates;
	}
	/**
	 * Gets the name of the camp if the student is also a camp coordinator.
	 * 
	 * @return The name of the camp 
	 */
	public String getCoordinator() {
		return this.coordinator;
	}
	/**
	 * Gets the list of camps the student is registered in.
	 * 
	 * @return An ArrayList of Camp objects that the student is registered for.
	 */	
	public ArrayList<Camp> getRegCamps() {
		return this.regCamps;
	}
	// Setters
	/**
	 * Sets the list of blocked dates for the student.
	 * 
	 * @param dates The list of blocked date ranges.
	 */
	public void setBlockeddates(ArrayList<Date[]> dates) {
		this.blockeddates=dates;
	}
	/**
	 * Adds a new enquiry to the student's list of enquiries with an initial status of false (unresolved).
	 * 
	 * @param e The enquiry to be added.
	 */
	public void setEnqList(Enquiries e) {
		this.myenqlist.put(e, false);

	}
	/**
	 * Sets or updates the list of enquiries and their statuses for the student.
	 * 
	 * @param myenqlist A HashMap representing the enquiries and their statuses.
	 */
	public void setEnqList(HashMap<Enquiries, Boolean> myenqlist) {
		this.myenqlist=myenqlist;

	}
	/**
	 * Updates the list of camps the student is registered in.
	 * 
	 * @param reglist An ArrayList of Camp objects to be set as the student's registered camps.
	 */
	public void setRegList(ArrayList<Camp> reglist ) {
		this.regCamps=reglist;
	}
	/**
	 * Marks a specific enquiry as answered in the student's list of enquiries.
	 * 
	 * @param e The enquiry to be marked as answered.
	 */
	public void setAnsweredwhenAnswered(Enquiries e) {
		this.myenqlist.replace(e, false, true);
	}
	// Student management methods
	/**
	 * Removes an enquiry from the student's list of enquiries.
	 * 
	 * @param e The enquiry to be removed.
	 */	
	public void remEnq(Enquiries e) {
		this.myenqlist.remove(e);
	}
	/**
	 * Adds a camp to the list of camps the student is registered in.
	 * 
	 * @param c The camp to be added.
	 */
	public void addRegCamps(Camp c) {
		this.regCamps.add(c);
	}
	/**
	 * Removes a camp from the list of camps the student is registered in and updates blocked dates accordingly.
	 * 
	 * @param c The camp to be removed.
	 */
	public void remRegCamps(Camp c) {
		this.regCamps.remove(c);
		this.blockeddates.remove(c.getdates());
	}
	/**
	 * Adds dates to the list of blocked dates for the student.
	 * 
	 * @param dates The dates to be added.
	 */
	public void addBlockeddates(Date[] dates) {
		this.blockeddates.add(dates);
	}
	/**
	 * Sets or updates the coordinator role of the student.
	 * 
	 * @param coordinator The name of the camp for which the student is the coordinator.
	 */
	public void setCoordinator(String coordinator) {
		this.coordinator = coordinator;
	}
	/**
	 * Registers the student for a specified camp.
	 * 
	 * @param cmp The camp for which the student is registering.
	 * @return true if the registration is successful, false otherwise.
	 */
	@SuppressWarnings("resource")
	public boolean Register(Camp cmp) {
		Scanner sc = new Scanner(System.in);
		Camp c = DatabaseSearchManager.SearchCampsDatabase(cmp);
		if (StudentHandler.hasStudentRegisteredAlready(this, c)) {
			System.out.println("\tYou have already registered for this camp!");

			return false;
		}
		if (c.getfacultyAllowed() != FACULTIES.NTU && c.getfacultyAllowed() != this.getFaculty()) {
			System.out.println("\tCamp '" + c.getcampName() + "' is not open to your faculty for registration!");

			return false;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy");
		Date d = new Date();
		if (d.getTime() > c.getregistrationClosingDate().getTime()) {
			System.out.println("\tRegistration Deadline missed!");
			System.out.println("\tThe registration deadline for Camp '" + c.getcampName() + "' was "
					+ formatter.format(c.getregistrationClosingDate()) + " .");
			System.out.println("\tRegistrations for this camp are no longer open.");

			return false;
		}
		if (StudentHandler.hasStudentWithdrawnBefore(this, c)) {
			System.out.println("\tYou have already withdrawn from Camp '" + c.getcampName()
			+ "' ! You are not allowed to re-register!");

			return false;
		}
		if (StudentHandler.checkDateClash(this, c)) {
			System.out.println(
					"\tThe dates of this camp clash with dates of other camps you have already registered for! ");

			return false;
		}
		if (!c.isThereAnyVacancy()) {
			System.out.println("\tCamp '" + c.getcampName() + "' is already full! No more vacancy!");

			return false;
		}
		if (!c.isThereEmptySlotForAttendee() && (this instanceof CampCommitteeMember)) {
			System.out.println(
					"\tThere are no more vacancies for Attendees. Only Camp Committee Positions are available.\n\tBut since you are a Camp Coordinator already for Camp '"
							+ this.coordinator + "', you can no longer register as a Coordinator here!");

			return false;
		}
		if (!c.isThereEmptySlotForAttendee()) {
			System.out.println(
					"\tThere are no more vacancies for Attendees. But you can still register as a Camp Committee Member.\n\tType Y to continue or Type N to discontinue registration.");
			System.out.print("\t");
			String approval = sc.nextLine();
			if (approval.equalsIgnoreCase("N")) {

				return false;
			}
			CampCommitteeMember ccm = new CampCommitteeMember(this.getUserAccountId(), this.getUserName(),
					this.getPassword(), this.getFaculty(), this.regCamps, this.blockeddates, c.getcampName(), c);
			if (StudentHandler.SuccessfulRegistrationHandler(ccm, c)) {
				System.out.println("\tYou are successfully registered as a Camp Committee Member for Camp '"
						+ c.getcampName() + "' !");
			}

			return true;
		}
		if (!c.isThereVacancyInCommittee()) {
			System.out.println(
					"\tThere are no more vacancies in Camp Committee. But you can still register as a Camp Attendee.\n\tType Y to continue or Type N to discontinue registration.");
			System.out.print("\t");
			String approval = sc.nextLine();
			if (approval.equalsIgnoreCase("N")) {

				return false;
			}

			if (StudentHandler.SuccessfulRegistrationHandler(this, c)) {
				System.out.println(
						"\tYou are successfully registered as a Camp Attendee for Camp '" + c.getcampName() + "' !");
			}

			return false;
		}
		if ((this instanceof CampCommitteeMember)) {
			System.out.println("\tSince you are already a Camp Coordinator for Camp '" + this.coordinator
					+ "', you can only register as an Attendee for this Camp.\n\tType Y to continue or Type N to discontinue registration.");
			System.out.print("\t");
			String approval = sc.nextLine();
			if (approval.equalsIgnoreCase("N")) {

				return false;
			}

			if (StudentHandler.SuccessfulRegistrationHandler(this, c)) {
				System.out.println(
						"\tYou are successfully registered as a Camp Attendee for Camp '" + c.getcampName() + "' !");
			}

			return false;
		}
		System.out.println(
				"\tPlease choose whether you want to register as a Camp Attendee (Enter A) or as a Camp Coordinator (Enter C)");
		System.out.print("\t");
		String role = sc.nextLine();
		if (role.equalsIgnoreCase("A")) {
			if (StudentHandler.SuccessfulRegistrationHandler(this, c)) {
				System.out.println(
						"\tYou are successfully registered as a Camp Attendee for Camp '" + c.getcampName() + "' !");

				return false;
			}

		} else if (role.equalsIgnoreCase("C")) {
			CampCommitteeMember ccm = new CampCommitteeMember(this.getUserAccountId(), this.getUserName(),
					this.getPassword(), this.getFaculty(), this.regCamps, this.blockeddates, c.getcampName(), c);
			if (StudentHandler.SuccessfulRegistrationHandler(ccm, c)) {
				System.out.println("\tYou are successfully registered as a Camp Committee Member for Camp '"
						+ c.getcampName() + "' !");

				return true;
			}
		}

		return true;

	}
	/**
	 * Allows the student to withdraw from a registered camp.
	 * 
	 * @param cm The camp from which the student wants to withdraw.
	 */
	public void Withdraw(Camp cm) {
		Camp c = DatabaseSearchManager.SearchCampsDatabase(cm);
		if (c.getcampName().equals(this.coordinator)) {
			System.out.println("\tYou are the Camp Coordinator for Camp '" + c.getcampName()
			+ "' ! Camp Coordinators cannot withdraw !");
			return;
		}
		if (!this.regCamps.contains(c)) {
			System.out.println("\tYou have not yet registered for Camp '" + c.getcampName() + "' !");
			return;
		}
		if (StudentHandler.SuccessfulWithdrawal(this, c)) {
			System.out.println("\tYou have been successfully de-registered from Camp '" + c.getcampName() + "' !");
			return;
		}
	}
	/**
	 * Compares this Student with another Student for order based on the user account ID.
	 * 
	 * @param o The Student to be compared.
	 * @return a negative integer, zero, or a positive integer as this Student's ID is less than, equal to, or greater than the specified Student's ID.
	 */
	@Override
	public int compareTo(Student o) {

		String comparename = (o).getUserAccountId();


		return this.getUserAccountId().compareTo(comparename);
	}


}
