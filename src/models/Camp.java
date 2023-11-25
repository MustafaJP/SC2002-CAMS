package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;



public class Camp implements Comparable<Camp> {
	/**
	 * The name of the camp.
	 */
	private String campName;
	/**
	 * The staff member in charge of the camp.
	 */
	private Staff staffInCharge;
	/**
	 * Total number of slots available in the camp, including both attendee and committee slots.
	 */
	private int totalSlots;
	/**
	 * Number of slots allocated for camp committee members (maximum 10 and minimum 1)
	 */
	private int campCommitteeSlots;
	/**
	 * A description of the camp.
	 */
	private String description;
	/**
	 * The location where the camp is held.
	 */
	private String location;
	/**
	 * The closing date for camp registration.
	 */
	private Date registrationClosingDate;
	/**
	 * Array holding the start and end dates of the camp.
	 */
	private Date startAndEndDates[] = new Date[2];
	/**
	 * Enum indicating the faculty allowed to participate in the camp.
	 */
	private FACULTIES facultyAllowed;
	/**
	 * List of students attending the camp.
	 */
	private ArrayList<Student> campAttendees = new ArrayList<Student>();
	/**
	 * List of camp committee members.
	 */
	private ArrayList<CampCommitteeMember> campCommittee = new ArrayList<CampCommitteeMember>();
	/**
	 * Number of vacant slots in the camp (both attendee and committee).
	 */
	private int emptySlots;
	/**
	 * Number of vacant committee slots in the camp.
	 */
	private int emptyCommitteeSlots;
	/**
	 * Boolean indicating the visibility status of the camp.
	 */
	private boolean visibility;
	/**
	 * List of students who have withdrawn from the camp.
	 */
	private ArrayList<Student> withdrawn = new ArrayList<Student>();
	/**
	 * List of suggestions made for the camp.
	 */
	private ArrayList<Suggestions> suggestionslist = new ArrayList<Suggestions>();
	/**
	 * List of enquiries made about the camp.
	 */
	private ArrayList<Enquiries> enquirylist = new ArrayList<Enquiries>();

	/**
	 * Constructor to create a Camp object with a specified name.
	 *
	 * @param name The name of the camp.
	 */
	public Camp(String name) {
		this.campName = name;
	}
	/**
	 * Constructor to create a Camp object with a staff member in charge. This constructor includes
	 * interactive prompts to set various attributes of the camp.
	 *
	 * @param staff The staff member in charge of the camp.
	 * @throws Exception if there are issues with input handling.
	 */
	@SuppressWarnings("resource")
	public Camp(Staff staff) throws Exception {
		this.staffInCharge = staff;
		Scanner s = new Scanner(System.in);
		do {
			System.out.println("\tPlease enter name of the camp: ");
			System.out.print("\t");
			this.campName = s.nextLine();
			if (this.campName.isEmpty() || this.campName.length() > 100) {
				System.out.println("\tInvalid input. Camp name cannot be empty or exceed 100 characters.");
			}
		} while (this.campName.isEmpty() || this.campName.length() > 100);

		do {
			System.out.println("\tPlease enter description of the camp: ");
			System.out.print("\t");
			this.description = s.nextLine();
			if (this.description.length() > 500) {
				System.out.println("\tInvalid input. Description cannot exceed 500 characters.");
			}
		} while (this.description.length() > 500);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false); 
		Date startDate=null, endDate=null, deadlineDate=null;
		boolean validInput;
		Date currentDate = new Date();

		do {
			try {

				System.out.println("\tPlease enter start date of the camp (DD/MM/YYYY): ");
				System.out.print("\t");
				startDate = sdf.parse(s.nextLine());
				if (startDate.before(currentDate)) {
					throw new IllegalArgumentException("Start date must be later than the current date.");
				}
				validInput=true;}catch (ParseException e) {
					System.out.println("\tInvalid date format. Please enter the date in DD/MM/YYYY format.");
					validInput = false;
				} catch (IllegalArgumentException e) {
					System.out.println("\t" + e.getMessage());
					validInput = false;
				}
		} while (!validInput);
		validInput=false;
		do {try {
			System.out.println("\tPlease enter end date of the camp (DD/MM/YYYY): ");
			System.out.print("\t");
			endDate = sdf.parse(s.nextLine());
			if (endDate.before(startDate)) {
				throw new IllegalArgumentException("End date cannot be before start date.");
			}
			validInput=true;}catch (ParseException e) {
				System.out.println("\tInvalid date format. Please enter the date in DD/MM/YYYY format.");
				validInput = false;
			} catch (IllegalArgumentException e) {
				System.out.println("\t" + e.getMessage());
				validInput = false;
			}
		} while (!validInput);
		validInput=false;
		do {try {
			System.out.println("\tPlease enter registration deadline of the camp (DD/MM/YYYY): ");
			System.out.print("\t");
			deadlineDate = sdf.parse(s.nextLine());
			if (deadlineDate.before(currentDate)) {
				throw new IllegalArgumentException("Registration deadline must be later than the current date.");
			}


			if (deadlineDate.after(startDate)) {
				throw new IllegalArgumentException("Registration deadline must be before the start date.");
			}

			validInput=true;}catch (ParseException e) {
				System.out.println("\tInvalid date format. Please enter the date in DD/MM/YYYY format.");
				validInput = false;
			} catch (IllegalArgumentException e) {
				System.out.println("\t" + e.getMessage());
				validInput = false;
			}
		} while (!validInput);
		validInput=false;
		this.startAndEndDates[0] = startDate;
		this.startAndEndDates[1] = endDate;
		this.registrationClosingDate = deadlineDate;




		String locationInput;

		do {
			System.out.println("\tPlease enter location of the camp: ");
			System.out.print("\t");
			locationInput = s.nextLine();


			if (locationInput.isEmpty()) {
				System.out.println("\tInvalid input. Location cannot be empty.");
			} else if (locationInput.length() > 100) { 
				System.out.println("\tInvalid input. Location cannot exceed 100 characters.");
			}
		} while (locationInput.isEmpty() || locationInput.length() > 100);


		this.location = locationInput;

		String facultyAllo;


		do {
			try {
				System.out.println("\tPlease enter the faculty allowed - if whole school is allowed, then just write NTU: ");
				System.out.print("\t");
				facultyAllo = s.nextLine();


				this.facultyAllowed = FACULTIES.valueOf(facultyAllo);
				if(!(this.facultyAllowed.equals(staff.getFaculty()) || this.facultyAllowed.equals(FACULTIES.NTU))) {
					System.out.println("\tYou can only create camps that are open to either your own faculty or to the whole of NTU");
					continue;
				}
				validInput = true; 
			} catch (IllegalArgumentException e) {

				System.out.println("\tInvalid input. Please enter a valid faculty name.");
				validInput = false; 
			}
		} while (!validInput);
		int totalSlots, campCommitteeSlots;


		do {
			try {
				System.out.println("\tPlease enter total number of slots in the camp: ");
				System.out.print("\t");
				totalSlots = Integer.parseInt(s.nextLine());

				if (totalSlots <= 0) {
					throw new IllegalArgumentException("\tTotal slots must be a positive integer.");
				}

				this.totalSlots = totalSlots;
				this.emptySlots = totalSlots;
				break; 

			} catch (NumberFormatException e) {
				System.out.println("\tInvalid input. Please enter a valid integer.");
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} while (true);


		do {
			try {
				System.out.println("\tPlease enter number of camp committee slots: ");
				System.out.print("\t");
				campCommitteeSlots = Integer.parseInt(s.nextLine());

				if (campCommitteeSlots <= 0 || campCommitteeSlots > 10) {
					throw new IllegalArgumentException("Camp committee slots must be a positive integer and cannot exceed 10.");
				}

				if (campCommitteeSlots > totalSlots) {
					throw new IllegalArgumentException("Camp committee slots cannot exceed total slots.");
				}

				this.campCommitteeSlots = campCommitteeSlots;
				this.emptyCommitteeSlots = campCommitteeSlots;
				break; 

			} catch (NumberFormatException e) {
				System.out.println("\tInvalid input. Please enter a valid integer.");
			} catch (IllegalArgumentException e) {
				System.out.println("\t" + e.getMessage());
			}
		} while (true);

		String vis;

		do {
			System.out.println("\tPlease enter visibility status of camp (ON/OFF): ");
			System.out.print("\t");
			vis = s.nextLine();

			if (!vis.equalsIgnoreCase("ON") && !vis.equalsIgnoreCase("OFF")) {
				System.out.println("\tInvalid input. Please enter 'ON' or 'OFF'.");
			}
		} while (!vis.equalsIgnoreCase("ON") && !vis.equalsIgnoreCase("OFF"));

		this.visibility = vis.equalsIgnoreCase("ON");


	}

	// Getters
	/**
	 * Gets the staff member in charge of the camp.
	 *
	 * @return The staff member responsible for overseeing the camp.
	 */
	public Staff getstaffInCharge() {
		return this.staffInCharge;
	}
	/**
	 * Retrieves the list of students attending the camp.
	 *
	 * @return An ArrayList of Student objects representing camp attendees.
	 */
	public ArrayList<Student> getStudent() {
		return this.campAttendees;
	}
	/**
	 * Retrieves the list of camp committee members.
	 *
	 * @return An ArrayList of CampCommitteeMember objects representing the committee members.
	 */
	public ArrayList<CampCommitteeMember> getcampCommittee() {
		return this.campCommittee;
	}
	/**
	 * Gets the name of the camp.
	 *
	 * @return The name of the camp.
	 */
	public String getcampName() {
		return this.campName;
	}
	/**
	 * Retrieves the faculty allowed to participate in the camp.
	 *
	 * @return A FACULTIES enum value representing the faculty allowed.
	 */
	public FACULTIES getfacultyAllowed() {
		return this.facultyAllowed;
	}
	/**
	 * Retrieves the start and end dates of the camp.
	 *
	 * @return An array of Date objects where the first element is the start date and the second is the end date.
	 */
	public Date[] getdates() {
		return this.startAndEndDates;
	}
	/**
	 * Retrieves the location of the camp.
	 *
	 * @return A String representing the camp's location.
	 */
	public String getlocation() {
		return this.location;
	}
	/**
	 * Retrieves the total number of slots available in the camp. This includes both attendee and committee slots.
	 *
	 * @return An integer representing the total number of slots in the camp.
	 */
	public int getTotalSlots() {
		return this.totalSlots;
	}
	/**
	 * Retrieves the number of empty slots available in the camp.
	 *
	 * @return An integer representing the number of empty slots.
	 */
	public int getEmptySlots() {
		return this.emptySlots;
	}
	/**
	 * Retrieves the number of empty committee slots in the camp.
	 *
	 * @return An integer representing the number of empty committee slots.
	 */
	public int getEmptyComSlots() {
		return this.emptyCommitteeSlots;
	}
	/**
	 * Retrieves the number of slots allocated for the camp committee.
	 *
	 * @return An integer representing the number of camp committee slots.
	 */
	public int getcampCommitteeSlots() {
		return this.campCommitteeSlots;
	}
	/**
	 * Retrieves the registration closing date for the camp.
	 *
	 * @return A Date object representing the registration deadline.
	 */
	public Date getregistrationClosingDate() {
		return this.registrationClosingDate;
	}
	/**
	 * Retrieves the description of the camp.
	 *
	 * @return A String containing the description of the camp.
	 */
	public String getdescription() {
		return this.description;
	}
	/**
	 * Checks if the camp is visible or not.
	 *
	 * @return A boolean value where true indicates the camp is visible.
	 */
	public boolean getVisibility() {
		return this.visibility;
	}
	/**
	 * Retrieves the list of students who have withdrawn from the camp.
	 *
	 * @return An ArrayList of Student objects who have withdrawn.
	 */
	public ArrayList<Student> getWithdrawn() {
		return this.withdrawn;
	}
	/**
	 * Retrieves the list of suggestions made for the camp.
	 *
	 * @return An ArrayList of Suggestions objects.
	 */
	public ArrayList<Suggestions> getSuggList() {
		return this.suggestionslist;
	}
	/**
	 * Retrieves the list of enquiries made about the camp.
	 *
	 * @return An ArrayList of Enquiries objects.
	 */
	public ArrayList<Enquiries> getEnqList() {
		return this.enquirylist;
	}
	// Setters
	/**
	 * Sets the name of the camp. The name is used to identify the camp and should be unique.
	 *
	 * @param name The new name for the camp. It should not be empty and should have a reasonable length.
	 */
	public void setCampName(String name) {
		this.campName = name;
	}
	/**
	 * Assigns a staff member as the person in charge of the camp.
	 * 
	 * @param st The Staff object representing the staff member to be assigned as the in-charge of the camp.
	 */
	public void setStaff(Staff st) {
		this.staffInCharge = st;
	}
	/**
	 * Sets the start date of the camp.
	 *
	 * @param sd The Date object representing the new start date.
	 */
	public void setStartDate(Date sd) {
		this.startAndEndDates[0] = sd;
	}
	/**
	 * Sets the end date of the camp.
	 *
	 * @param ed The Date object representing the new end date.
	 */
	public void setEndDate(Date ed) {
		this.startAndEndDates[1] = ed;
	}
	/**
	 * Sets both the start and end dates of the camp.
	 *
	 * @param sd The Date object representing the new start date.
	 * @param ed The Date object representing the new end date.
	 */
	public void setBothDates(Date sd, Date ed) {
		this.startAndEndDates[0] = sd;
		this.startAndEndDates[1] = ed;
	}
	/**
	 * Sets the registration deadline for the camp. This is the final date by which participants can register for the camp.
	 *
	 * @param rd The new registration deadline date for the camp. Should be set in a way that it precedes the camp's start date.
	 */
	public void setRegDeadline(Date rd) {
		this.registrationClosingDate = rd;
	}
	/**
	 * Sets the total number of slots available in the camp, including both attendee and committee slots. 
	 * It also resets the count of empty slots to this new total, assuming all slots are available initially.
	 *
	 * @param n The new total number of slots for the camp. It should be a positive integer reflecting the capacity of the camp.
	 */
	public void setTotalSlots(int n) {
		this.totalSlots = n;
		this.emptySlots = n;
	}
	/**
	 * Sets the number of slots allocated specifically for camp committee members. 
	 * This also resets the count of empty committee slots to the new total, assuming all committee slots are initially available.
	 *
	 * @param n The new number of committee slots for the camp. This should be a positive integer and should not exceed the total slots of the camp.
	 */
	public void setComSlots(int n) {
		this.campCommitteeSlots = n;
		this.emptyCommitteeSlots = n;
	}
	/**
	 * Sets the location of the camp.
	 *
	 * @param l A String representing the new location of the camp.
	 */
	public void setPlace(String l) {
		this.location = l;
	}
	/**
	 * Sets the description of the camp.
	 *
	 * @param d A String containing the new description of the camp.
	 */
	public void setDesc(String d) {
		this.description = d;
	}
	/**
	 * Sets the faculty allowed to participate in the camp.
	 *
	 * @param f A FACULTIES enum value representing the new allowed faculty.
	 */
	public void setFaculty(FACULTIES f) {
		this.facultyAllowed = f;
	}
	// Camp management methods
	/**
	 * Increments the count of empty slots in the camp.
	 */
	public void incEmptySlot() {
		this.emptySlots++;
	}
	/**
	 * Decrements the count of empty slots in the camp.
	 */
	public void decEmptySlot() {
		this.emptySlots--;
	}
	/**
	 * Increments the count of empty committee slots in the camp.
	 */
	public void incComEmptyslot() {
		this.emptyCommitteeSlots++;
	}
	/**
	 * Decrements the count of empty committee slots in the camp.
	 */
	public void decComEmptyslot() {
		this.emptyCommitteeSlots--;
	}
	/**
	 * Removes a student from the list of attendees and adds them to the withdrawn list. Also increments the count
	 * of empty slots in the camp.
	 *
	 * @param s The student to be removed from the list of attendees.
	 */
	public void remAttendee(Student s) {
		this.campAttendees.remove(s);
		this.incEmptySlot();
		this.withdrawn.add(s);

	}
	/**
	 * Checks if there are any vacancies in the camp, including both attendee and committee slots.
	 *
	 * @return A boolean where true indicates there are empty slots available.
	 */
	public boolean isThereAnyVacancy() {
		return this.emptySlots != 0;
	}
	/**
	 * Checks if there are any vacancies for attendees in the camp, excluding committee slots.
	 *
	 * @return A boolean where true indicates there are empty attendee slots available.
	 */
	public boolean isThereEmptySlotForAttendee() {
		return (this.emptySlots - this.emptyCommitteeSlots != 0);
	}
	/**
	 * Checks if there are any vacancies in the camp committee.
	 *
	 * @return A boolean where true indicates there are empty committee slots available.
	 */
	public boolean isThereVacancyInCommittee() {
		return this.emptyCommitteeSlots != 0;
	}
	/**
	 * Adds a student as an attendee to the camp. This method first checks if there are available slots for attendees,
	 * excluding the committee slots. If there is space, the student is added to the list of camp attendees and the count
	 * of empty slots is decremented. If the camp is already full, an error message is displayed indicating no more vacancies.
	 *
	 * @param s The student to be added as an attendee to the camp.
	 */
	public void addAttendee(Student s) {
		if (this.emptySlots - this.emptyCommitteeSlots != 0) {
			this.campAttendees.add(s);
			this.decEmptySlot();
		} else {
			System.out.println("\tCamp is already full! No more vacancy!");
		}

	}
	/**
	 * Adds a student as an attendee to the camp without checking for vacancies.
	 *
	 * @param s The student to be added as an attendee.
	 */
	public void addAttendeeNormal(Student s) {
		this.campAttendees.add(s);
	}
	/**
	 * Adds a member to the camp committee without checking for vacancies.
	 *
	 * @param cm The CampCommitteeMember to be added.
	 */
	public void addComMembNormal(CampCommitteeMember cm) {
		this.campCommittee.add(cm);
	}

	/**
	 * Adds a member to the camp committee if there is available space in the committee slots.
	 * If the committee is full, prints an error message.
	 *
	 * @param cm The CampCommitteeMember to be added.
	 */
	public void addComMemb(CampCommitteeMember cm) {
		if (this.emptyCommitteeSlots != 0) {
			this.campCommittee.add(cm);
			this.decComEmptyslot();
			this.decEmptySlot();
		} else {
			System.out.println("\tCamp Committee is already full! No more vacancy!");
		}
	}

	/**
	 * Toggles the visibility status of the camp. If the camp is visible, it becomes invisible and vice versa.
	 */
	public void toggleVisibility() {

		this.visibility = !this.visibility;

	}
	/**
	 * Adds a student to the list of those who have withdrawn from the camp.
	 *
	 * @param s The student who has withdrawn from the camp.
	 */
	public void addWithdrawn(Student s) {
		this.withdrawn.add(s);
	}
	/**
	 * Adds a suggestion to the list of suggestions made for the camp.
	 *
	 * @param s The suggestion to be added.
	 */
	public void addSuggestion(Suggestions s) {
		this.suggestionslist.add(s);
	}
	/**
	 * Removes a suggestion from the list of suggestions made for the camp.
	 *
	 * @param s The suggestion to be removed.
	 */
	public void remSuggestion(Suggestions s) {
		this.suggestionslist.remove(s);
	}
	/**
	 * Adds an enquiry to the list of enquiries made about the camp.
	 *
	 * @param e The enquiry to be added.
	 */
	public void addEnq(Enquiries e) {
		this.enquirylist.add(e);
	}
	/**
	 * Removes an enquiry from the list of enquiries made about the camp.
	 *
	 * @param e The enquiry to be removed.
	 */
	public void remEnq(Enquiries e) {
		this.enquirylist.remove(e);
	}
	/**
	 * Sets the number of empty (available) slots in the camp. This count includes both attendee and committee slots.
	 *
	 * @param n The new number of empty slots in the camp. It should be a non-negative integer.
	 */
	public void setEmptySlots(int n) {
		this.emptySlots = n;
	}
	/**
	 * Sets the number of empty (available) slots specifically for the camp committee. 
	 *
	 * @param n The new number of empty committee slots in the camp. It should be a non-negative integer and not exceed the total number of committee slots.
	 */
	public void setEmptyCommitteeSlots(int n) {
		this.emptyCommitteeSlots = n;
	}
	/**
	 * Sets the visibility status of the camp.
	 *
	 * @param b A boolean where true indicates the camp should be visible.
	 */

	public void setVis(boolean b) {
		this.visibility = b;
	}

	/**
	 * Compares this Camp object with another object for equality based on the camp name.
	 *
	 * @param o The object to be compared with this Camp.
	 * @return true if the given object is a Camp with the same name, false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		Camp othercamp = (Camp) o;
		if ((othercamp.getcampName()).equals(this.getcampName())) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Converts the camp details into a two-dimensional array of strings for display purposes.
	 *
	 * @param skiptoggle A boolean indicating whether to skip including the visibility status.
	 * @return A two-dimensional String array containing the camp details.
	 */
	public String[][] toStringArray(boolean skiptoggle) {
		SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy");
		List<String[]> detailsList = new ArrayList<>();

		detailsList.add(new String[]{"Camp Name", this.campName});
		detailsList.add(new String[]{"Staff-In-Charge", String.valueOf(this.staffInCharge.getUserAccountId())});
		detailsList.add(new String[]{"Description", this.description});
		detailsList.add(new String[]{"Location", this.location});
		detailsList.add(new String[]{"Faculty", this.facultyAllowed.toString()});
		detailsList.add(new String[]{"Total Slots (Attendee+Commitee)", String.valueOf(this.totalSlots)});
		detailsList.add(new String[]{"Total Committee Slots", String.valueOf(this.campCommitteeSlots)});
		detailsList.add(new String[]{"Start Date", formatter.format(this.startAndEndDates[0])});
		detailsList.add(new String[]{"End Date", formatter.format(this.startAndEndDates[1])});
		detailsList.add(new String[]{"Registration Deadline", formatter.format(this.registrationClosingDate)});
		detailsList.add(new String[]{"Empty Slots (Attendee+Committee)", String.valueOf(this.emptySlots)});
		detailsList.add(new String[]{"Empty Committee Slots", String.valueOf(this.emptyCommitteeSlots)});

		if (!skiptoggle) {
			String visibilityStr = this.visibility ? "ON" : "OFF";
			detailsList.add(new String[]{"Visibility", visibilityStr});
		}

		return detailsList.toArray(new String[0][]);
	}
	/**
	 * Converts the camp details, including a specific role, into a two-dimensional array of strings.
	 *
	 * @param coor A string representing the role to be included in the array.
	 * @return A two-dimensional String array containing the camp details with the specified role.
	 */
	public String[][] toStringArrayWithRole(String coor) {
		SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy");
		String role = this.campName.equals(coor) ? "Committee Member" : "Attendee";
		return new String[][] {
			{"Camp Name", this.campName},
			{"Role", role},
			{"Staff-In-Charge", String.valueOf(this.staffInCharge.getUserAccountId())},
			{"Description", this.description},
			{"Location", this.location},
			{"Faculty", this.facultyAllowed.toString()},
			{"Total Slots (Attendee+Commitee)", String.valueOf(this.totalSlots)},
			{"Total Committee Slots", String.valueOf(this.campCommitteeSlots)},
			{"Start Date", formatter.format(this.startAndEndDates[0])},
			{"End Date", formatter.format(this.startAndEndDates[1])},
			{"Registration Deadline", formatter.format(this.registrationClosingDate)},
			{"Empty Slots (Attendee+Committee)", String.valueOf(this.emptySlots)},
			{"Empty Committee Slots", String.valueOf(this.emptyCommitteeSlots)}
		};
	}
	/**
	 * Compares this camp with another Camp object based on the camp name in lexicographical order.
	 *
	 * @param o The Camp object to be compared with this one.
	 * @return A negative integer, zero, or a positive integer as this camp name is less than, equal to, or greater than the specified camp's name.
	 */
	@Override
	public int compareTo(Camp o) {

		String comparename = (o).campName;


		return this.campName.compareTo(comparename);

	}
	/**
	 * Returns a string representation of the Camp object, formatted for display.
	 *
	 * @return A formatted string containing the details of the camp.
	 */
	@Override
	public String toString() {
		SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy");
		String formatData="%-25s %-35s %-30s %-25s %-30s %-40s %-40s %-60s %-60s %-60s %-35s %-35s %-25s";
		String holder = this.visibility ? "ON" : "OFF";
		return String.format(formatData,this.campName,this.staffInCharge.getUserAccountId(),this.description
				, this.location ,this.facultyAllowed.toString(),
				this.totalSlots,this.campCommitteeSlots ,formatter.format(this.startAndEndDates[0])
				,formatter.format(this.startAndEndDates[1]) ,formatter.format(this.registrationClosingDate)
				,this.emptySlots , this.emptyCommitteeSlots , holder);
	}


}