package view;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

import controllers.DURATION;
import controllers.DatabaseSearchManager;
import controllers.SearchFilters;
import controllers.UserHandler;
import models.Camp;
import models.DataManager;
import models.Enquiries;
import models.Student;
/**
 * This class implements CampsViewer and OwnCampsViewer interfaces, providing functionality for students
 * to view and search for camps, view camps they are registered for, and manage enquiries related to camps.
 */
public class StudentView implements CampsViewer,OwnCampsViewer{
	/**
	 * Displays a default view of all camps open to the student's faculty and are visible.
	 *
	 * @return true if there are camps to display, false otherwise.
	 */
	@Override
	public boolean defaultCampsView() {
		try {
			ArrayList<Camp> filtered=SearchFilters.filterByUserGroup(DataManager.getCampsDatabase(),DatabaseSearchManager.SearchStudentDatabase(UserHandler.getCurrentUserInstance()).getFaculty().toString());

			filtered=SearchFilters.filterByVisibility(filtered);
			if(filtered.isEmpty()) {
				throw new ListDoesntExistYetException("\tCamp not available");
			}
			Collections.sort(filtered);
			String[] headers = InformationViewer.campInfoHeader(false, false);
			InformationViewer.printCamps(filtered, headers,true);
			return true;}catch(ListDoesntExistYetException e) {
				System.out.println(e.getMessage());
				return false;
			}
	}
	/**
	 * Displays camps open for registration to the student's faculty.
	 *
	 * @return true if there are camps to display, false otherwise.
	 */
	public  boolean viewCampsOpenForReg() {
		try {
			ArrayList<Camp> filtered=SearchFilters.filterByUserGroup(DataManager.getCampsDatabase(),DatabaseSearchManager.SearchStudentDatabase(UserHandler.getCurrentUserInstance()).getFaculty().toString());
			filtered=SearchFilters.filterByVisibility(filtered);
			filtered=SearchFilters.filterByIfWithdrawn(filtered,(Student) UserHandler.getCurrentUserInstance());
			filtered=SearchFilters.filterByAvailability_TotalSlots(filtered);
			Date currentDate = new Date();
			filtered=SearchFilters.filterBeforeClosingDate(filtered,currentDate );
			if(filtered.isEmpty()) {
				throw new ListDoesntExistYetException("\tCamp not available");
			}
			Collections.sort(filtered);
			String[] headers = InformationViewer.campInfoHeader(false, false);
			InformationViewer.printCamps(filtered, headers,true);
			return true;}catch(ListDoesntExistYetException e) {
				System.out.println(e.getMessage());
				return false;
			}
	}
	/**
	 * Allows the student to search for camps by their name.
	 */
	@SuppressWarnings("resource")
	@Override
	public void searchByCampName() {
		try {
			Scanner sc=new Scanner(System.in);
			System.out.print("\tPlease enter name of camp: ");
			System.out.print("\t");
			String name=sc.nextLine();
			ArrayList<Camp> filtered=SearchFilters.filterByUserGroup(DataManager.getCampsDatabase(),DatabaseSearchManager.SearchStudentDatabase(UserHandler.getCurrentUserInstance()).getFaculty().toString());
			filtered=SearchFilters.filterByVisibility(filtered);
			filtered=SearchFilters.filterCampsByCampName(filtered,name );
			if(filtered.isEmpty()) {

				throw new ListDoesntExistYetException("\tCamp not available");
			}
			Collections.sort(filtered);
			String[] headers = InformationViewer.campInfoHeader(false, false);
			InformationViewer.printCamps(filtered, headers,true);
		}catch(ListDoesntExistYetException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * Allows the student to search for camps by detailed criteria like location, staff-in-charge, etc.
	 */
	@SuppressWarnings("resource")
	@Override
	public void searchByCampDetails() {
		try {
			ArrayList<Camp> filtered=SearchFilters.filterByUserGroup(DataManager.getCampsDatabase(),DatabaseSearchManager.SearchStudentDatabase(UserHandler.getCurrentUserInstance()).getFaculty().toString());
			filtered=SearchFilters.filterByVisibility(filtered);

			Scanner sc=new Scanner(System.in);
			System.out.println("\tPlease enter camp name (type 'N' to skip):");
			System.out.print("\t");
			String name=sc.nextLine();
			if(!name.equalsIgnoreCase("N")) {
				filtered=SearchFilters.filterCampsByCampName(filtered,name );}
			System.out.println("\tPlease enter camp location (type 'N' to skip):");
			System.out.print("\t");
			String place=sc.nextLine();
			if(!place.equalsIgnoreCase("N")) {
				filtered=SearchFilters.filterByLocation(filtered, place);}

			System.out.println("\tPlease enter user ID of staff-in-charge (type 'N' to skip):");
			System.out.print("\t");
			String teacher=sc.nextLine();
			if(!teacher.equalsIgnoreCase("N")) {
				filtered=SearchFilters.filterByStaff(filtered, teacher,true);}
			System.out.println("\tPlease enter 'A' to view only those camps that are currently open for your registration (type 'N' to skip):");
			System.out.print("\t");
			String choice=sc.nextLine();
			if(choice.equalsIgnoreCase("A")) {
				filtered=SearchFilters.filterByIfWithdrawn(filtered,(Student) UserHandler.getCurrentUserInstance());
				filtered=SearchFilters.filterByAvailability_TotalSlots(filtered);
				Date currentDate = new Date();
				filtered=SearchFilters.filterBeforeClosingDate(filtered,currentDate );
				System.out.println("\tPlease enter 'C' to view only those camps that are currently open for committee registration (type 'N' to skip):");
				System.out.print("\t");
				String choice2=sc.nextLine();
				if(choice2.equalsIgnoreCase("C")) {
					filtered=SearchFilters.filterByAvailability_ComSlots(filtered);
				}
			}
			String begin, last;
			Date sd = null, ld = null;
			boolean validStartDate = false, validEndDate = false;

			do {
				System.out.println("\tPlease enter start date of camp in DD/MM/YYYY format (type 'N' to skip):");
				System.out.print("\t");
				begin = sc.nextLine();

				if (begin.equalsIgnoreCase("N")) {
					validStartDate = true;
				} else {
					try {
						sd = new SimpleDateFormat("dd/MM/yyyy").parse(begin);
						validStartDate = true;
					} catch (ParseException e) {
						System.out.println("\tInvalid date format. Please enter the date in DD/MM/YYYY format or type 'N' to skip.");
					}
				}
			} while (!validStartDate);



			do {
				System.out.println("\tPlease enter end date of camp in DD/MM/YYYY format (type 'N' to skip):");
				System.out.print("\t");
				last = sc.nextLine();

				if (last.equalsIgnoreCase("N")) {
					validEndDate = true;
				} else {
					try {
						ld = new SimpleDateFormat("dd/MM/yyyy").parse(last);
						validEndDate = true;

						if (sd != null && ld.before(sd)) {
							System.out.println("\tEnd date cannot be before the start date. Please enter a valid end date.");
							validEndDate = false;
						}
					} catch (ParseException e) {
						System.out.println("\tInvalid date format. Please enter the date in DD/MM/YYYY format or type 'N' to skip.");
					}
				}
			} while (!validEndDate);


			if (sd != null) {
				filtered = SearchFilters.filterStartingOn(filtered, sd);
			}

			if (ld != null) {

				filtered = SearchFilters.filterEndingOn(filtered, ld);
			}

			if(filtered.isEmpty()) {

				throw new ListDoesntExistYetException("\tCamp not available");
			}
			Collections.sort(filtered);
			String[] headers = InformationViewer.campInfoHeader(false, false);
			InformationViewer.printCamps(filtered, headers,true);
		}catch(ListDoesntExistYetException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Allows the student to search for camps based on their duration.
	 */
	@SuppressWarnings("resource")
	@Override
	public  void searchByCampDuration() {
		try {
			ArrayList<Camp> filtered=SearchFilters.filterByUserGroup(DataManager.getCampsDatabase(),DatabaseSearchManager.SearchStudentDatabase(UserHandler.getCurrentUserInstance()).getFaculty().toString());
			filtered=SearchFilters.filterByVisibility(filtered);
			Scanner sc=new Scanner(System.in);
			System.out.print("\tPlease enter duration of camp (either in number of days or as LONG/NORMAL/SHORT): ");
			System.out.print("\t");
			String dur=sc.nextLine();
			try {
				int a=Integer.parseInt(dur);
				filtered=SearchFilters.filterByDuration(filtered, a);
			}catch(Exception e){
				filtered=SearchFilters.filterByDuration(filtered, DURATION.valueOf(dur));
			}
			if(filtered.isEmpty()) {

				throw new ListDoesntExistYetException("\tCamp not available");
			}
			Collections.sort(filtered);
			String[] headers = InformationViewer.campInfoHeader(false, false);
			InformationViewer.printCamps(filtered, headers,true);
		}catch(ListDoesntExistYetException e) {
			System.out.println(e.getMessage());
		}

	}
	/**
	 * Displays the camps that the student has registered for, including their role (attendee or coordinator).
	 *
	 * @return true if there are camps to display, false otherwise.
	 */
	@Override
	public boolean viewYourCamps() {
		try {
			ArrayList<Camp> getlist=DatabaseSearchManager.SearchStudentDatabase(UserHandler.getCurrentUserInstance()).getRegCamps();
			if(getlist.isEmpty()) {
				throw new ListDoesntExistYetException("\tCamp not available");
			}
			Collections.sort(getlist);
			String[] headers = InformationViewer.campInfoHeader(false, true);
			for(Camp c:getlist) {
				Camp c1=DatabaseSearchManager.SearchCampsDatabase(c);
				String[][] contentArray = c1.toStringArrayWithRole(DatabaseSearchManager.SearchStudentDatabase(UserHandler.getCurrentUserInstance()).getCoordinator()); 
				System.out.println("=".repeat(100));
				int padding = (98 - contentArray[0][1].length()) / 2;
				String leftPad = " ".repeat(padding);
				String rightPad = " ".repeat(98 - padding - contentArray[0][1].length());
				System.out.println("|" + leftPad + contentArray[0][1] + rightPad + "|");
				System.out.println("|" + "-".repeat(98) + "|");

				for (int i = 0; i < headers.length; i++) {
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
			return true;}catch(ListDoesntExistYetException e) {
				System.out.println(e.getMessage());
				return false;
			}

	}
	/**
	 * Displays enquiries sent by the student.
	 */
	@SuppressWarnings("unchecked")
	public void viewEnquiriesSentByYou() {
		try {
			ArrayList<Enquiries> enqlist=DataManager.getEnquiriesDatabase();

			enqlist=(ArrayList<Enquiries>) SearchFilters.filterMessagesBySender(enqlist, UserHandler.getCurrentUserInstance());
			if(enqlist.isEmpty()) {
				throw new ListDoesntExistYetException("\tNo enquiries submitted yet!");
			}
			Collections.sort(enqlist);		
			String[] headers = InformationViewer.enquiriesInfoHeader(true, false);
			InformationViewer.printEnquiry(enqlist, headers,false,true);}catch(ListDoesntExistYetException e) {
				System.out.println(e.getMessage());
			}

	}
	/**
	 * Displays enquiries sent by the student, filtered by their processing status.
	 *
	 * @param state The processing state of the enquiries to be displayed (processed or unprocessed).
	 * @return true if there are enquiries to display, false otherwise.
	 */
	@SuppressWarnings("unchecked")
	public boolean viewEnquiriesSentByYou(boolean state) {
		ArrayList<Enquiries> enqlist=DataManager.getEnquiriesDatabase();
		enqlist = (ArrayList<Enquiries>) SearchFilters.filterMessagesBySender(enqlist,
				UserHandler.getCurrentUserInstance());
		enqlist=(ArrayList<Enquiries>) SearchFilters.filterMessagesByStatus(enqlist, state);
		if(enqlist.isEmpty()) {
			return false;
		}
		Collections.sort(enqlist);
		String[] headers = InformationViewer.enquiriesInfoHeader(true, false);
		InformationViewer.printEnquiry(enqlist, headers,false,true);
		return true;

	}


}
