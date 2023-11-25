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
import models.CampCommitteeMember;
import models.DataManager;
import models.Student;
import models.Suggestions;
/**
 * This class extends StaffComCommonPrivelegesViewer class and implements CampsViewer and OwnCampsViewer interfaces.
 * It provides functionality for staff members to view camps, registrants, committee members,
 * suggestions, and enquiries related to camps, as well as search functionality for camps based on various criteria.
 */
public class StaffView extends StaffComCommonPrivelegesViewer implements CampsViewer,OwnCampsViewer{
	/**
	 * Displays a default view of all camps.
	 *
	 * @return true if there are camps to display, false otherwise.
	 */
	@Override
	public boolean defaultCampsView() {
		try {
			ArrayList<Camp> filteredfinal=DataManager.getCampsDatabase();
			if(filteredfinal.isEmpty()) {
				throw new ListDoesntExistYetException("Camp not available");
			}
			Collections.sort(filteredfinal);
			String[] headers = InformationViewer.campInfoHeader(false, false);
			InformationViewer.printCamps(filteredfinal, headers,true);
			return true;}catch(ListDoesntExistYetException e) {
				System.out.println("\t"+e.getMessage());
				return false;
			}
	}
	/**
	 * Allows the user to search for camps by their name.
	 */
	@SuppressWarnings("resource")
	@Override
	public void searchByCampName() {

		try {
			Scanner sc=new Scanner(System.in);
			System.out.println("\tPlease enter name of camp: ");
			System.out.print("\t");
			String name=sc.nextLine();
			ArrayList<Camp> filteredfinal=DataManager.getCampsDatabase();
			filteredfinal=SearchFilters.filterCampsByCampName(filteredfinal,name );
			if(filteredfinal.isEmpty()) {

				throw new ListDoesntExistYetException("Camp not available");
			}
			Collections.sort(filteredfinal);
			String[] headers = InformationViewer.campInfoHeader(false, false);
			InformationViewer.printCamps(filteredfinal, headers,true);
		}catch(ListDoesntExistYetException e) {
			System.out.println("\t"+e.getMessage());
			return;
		}
	}
	/**
	 * Displays registrants (both students and committee members) for a specified camp.
	 *
	 * @param c The camp for which registrants are to be displayed.
	 */
	@Override
	public void viewRegistrants(Camp c) {
		try {
			ArrayList<Student> temp=DatabaseSearchManager.SearchCampsDatabase(c).getStudent();
			ArrayList<CampCommitteeMember> temp2=DatabaseSearchManager.SearchCampsDatabase(c).getcampCommittee();
			ArrayList<Student> all=new ArrayList<Student>();
			all.addAll(temp);
			all.addAll(temp2);
			if(all.isEmpty()) {
				throw new ListDoesntExistYetException("No registrants yet!");
			}
			Collections.sort(all);
			String[] headers = InformationViewer.registrantsHeader();
			for(Student y:all) {
				y=DatabaseSearchManager.SearchStudentDatabase(y);
				ArrayList<String[]> contentArray = new ArrayList<>();

				String role = temp.contains(y) ? "Attendee" : "Committee Member";

				contentArray.add(new String[]{"User ID", y.getUserAccountId()});
				contentArray.add(new String[]{"Name", y.getUserName()});
				contentArray.add(new String[]{"Faculty", y.getFaculty().toString()});
				contentArray.add(new String[]{"Role", role});
				contentArray.add(new String[]{"NTU Email", y.getUserAccountId() + "@e.ntu.edu.sg"});

				System.out.println("=".repeat(100));
				int padding = (98 - contentArray.get(0)[0].length()) / 2;
				String leftPad = " ".repeat(padding);
				String rightPad = " ".repeat(98 - padding - contentArray.get(0)[1].length());
				System.out.println("|" + leftPad + contentArray.get(0)[1] + rightPad + "|");
				System.out.println("|" + "-".repeat(98) + "|");

				for (int i = 0; i < headers.length; i++) {
					String header = headers[i];

					String content = contentArray.stream()
							.filter(pair -> pair[0].equals(header))
							.findFirst()
							.map(pair -> pair[1])
							.orElse("");
					System.out.printf("| %-45s | %-48s |\n", header, content);
				}

				System.out.println("=".repeat(100)+"\n");

			}}catch(ListDoesntExistYetException e) {
				System.out.println("\t"+e.getMessage());

			}

	}
	/**
	 * Displays committee members for a specified camp.
	 *
	 * @param c The camp for which committee members are to be displayed.
	 */
	@Override
	public void viewCom(Camp c) {
		try {
			ArrayList<CampCommitteeMember> temp2=DatabaseSearchManager.SearchCampsDatabase(c).getcampCommittee();
			if(temp2.isEmpty()) {
				throw new ListDoesntExistYetException("No camp committee members yet!");
			}
			Collections.sort(temp2);		
			String[] headers = InformationViewer.comsHeader(true);
			for(CampCommitteeMember y:temp2) {
				y=DatabaseSearchManager.SearchCampComMemDatabase(y);
				ArrayList<String[]> contentArray = new ArrayList<>();

				contentArray.add(new String[]{"User ID", y.getUserAccountId()});
				contentArray.add(new String[]{"Name", y.getUserName()});
				contentArray.add(new String[]{"Faculty", y.getFaculty().toString()});
				contentArray.add(new String[]{"Points", String.valueOf(y.getPointsHolder().getPt())});
				contentArray.add(new String[]{"NTU Email", y.getUserAccountId() + "@e.ntu.edu.sg"});

				System.out.println("=".repeat(100));
				int padding = (98 - contentArray.get(0)[0].length()) / 2;
				String leftPad = " ".repeat(padding);
				String rightPad = " ".repeat(98 - padding - contentArray.get(0)[0].length());
				System.out.println("|" + leftPad + contentArray.get(0)[1] + rightPad + "|");
				System.out.println("|" + "-".repeat(98) + "|");

				for (int i = 0; i < headers.length; i++) {
					String header = headers[i];

					String content = contentArray.stream()
							.filter(pair -> pair[0].equals(header))
							.findFirst()
							.map(pair -> pair[1])
							.orElse(""); 
					System.out.printf("| %-45s | %-48s |\n", header, content);
				}

				System.out.println("=".repeat(100)+"\n");
			}}catch(ListDoesntExistYetException e) {
				System.out.println("\t"+e.getMessage());

			}

	}
	/**
	 * Allows the user to search for camps based on their duration.
	 */
	@SuppressWarnings("resource")
	@Override
	public void searchByCampDuration() {
		try {
			ArrayList<Camp> filteredfinal=DataManager.getCampsDatabase();
			Scanner sc=new Scanner(System.in);
			System.out.println("\tPlease enter duration of camp (either in number of days or as LONG/NORMAL/SHORT): ");
			System.out.print("\t");
			String dur=sc.nextLine();
			try {
				int a=Integer.parseInt(dur);
				filteredfinal=SearchFilters.filterByDuration(filteredfinal, a);
			}catch(Exception e){
				filteredfinal=SearchFilters.filterByDuration(filteredfinal, DURATION.valueOf(dur));
			}
			if(filteredfinal.isEmpty()) {

				throw new ListDoesntExistYetException("\tCamp not available");
			}
			Collections.sort(filteredfinal);
			String[] headers = InformationViewer.campInfoHeader(false, false);
			InformationViewer.printCamps(filteredfinal, headers,true);
		}catch(ListDoesntExistYetException e) {
			System.out.println("\t"+e.getMessage());

		}

	}
	/**
	 * Displays the camps started by the current user.
	 *
	 * @return true if there are camps to display, false otherwise.
	 */
	@SuppressWarnings("resource")
	@Override
	public boolean viewYourCamps() {
		try {
			ArrayList<Camp> owncamps=DatabaseSearchManager.SearchStaffDatabase(UserHandler.getCurrentUserInstance()).getCampsStarted();
			System.out.println("\tTo see all the camps under you, press 'A'");
			System.out.println("\tTo see camps that still have empty slots, press 'E'");
			System.out.println("\tTo see camps that are already full, press 'F'");
			Scanner sc=new Scanner(System.in);
			System.out.print("\t");
			String sh=sc.nextLine();
			if(sh.equalsIgnoreCase("E")) {
				owncamps.replaceAll(c->DatabaseSearchManager.SearchCampsDatabase(c));
				owncamps=SearchFilters.filterByAvailability_TotalSlots(owncamps);

			}
			else if(sh.equalsIgnoreCase("F")) {
				owncamps.replaceAll(c->DatabaseSearchManager.SearchCampsDatabase(c));
				owncamps=SearchFilters.filterByNonAvailability_TotalSlots(owncamps);
			}
			if(owncamps.isEmpty()) {

				throw new ListDoesntExistYetException("Camp not available");
			}
			Collections.sort(owncamps);		
			String[] headers = InformationViewer.campInfoHeader(false, false);
			for(Camp c:owncamps) {
				Camp c1=DatabaseSearchManager.SearchCampsDatabase(c);
				String[][] contentArray = c1.toStringArray(false); 
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

			return true;
		}catch(ListDoesntExistYetException e) {
			System.out.println("\t"+e.getMessage());
			return false;

		}

	}
	/**
	 * Allows the user to search for camps based on specific details like location, staff-in-charge, etc.
	 */
	@SuppressWarnings("resource")
	@Override
	public void searchByCampDetails() {
		try {
			ArrayList<Camp> filteredfinal=DataManager.getCampsDatabase();
			Scanner sc=new Scanner(System.in);
			System.out.println("\tPlease enter camp name (type 'N' to skip):");
			System.out.print("\t");
			String name=sc.nextLine();
			if(!name.equalsIgnoreCase("N")) {
				filteredfinal=SearchFilters.filterCampsByCampName(filteredfinal,name );}
			System.out.println("\tPlease enter camp location (type 'N' to skip):");
			System.out.print("\t");
			String place=sc.nextLine();
			if(!place.equalsIgnoreCase("N")) {
				filteredfinal=SearchFilters.filterByLocation(filteredfinal, place);}

			System.out.println("\tPlease enter user ID of staff-in-charge (type 'N' to skip):");
			System.out.print("\t");
			String teacher=sc.nextLine();
			if(!teacher.equalsIgnoreCase("N")) {
				filteredfinal=SearchFilters.filterByStaff(filteredfinal, teacher,true);}
			System.out.println("\tPlease enter 'A' to view only those camps that are currently open for registration (type 'N' to skip):");
			System.out.print("\t");
			String choice=sc.nextLine();
			if(choice.equalsIgnoreCase("A")) {

				filteredfinal=SearchFilters.filterByAvailability_TotalSlots(filteredfinal);
				filteredfinal=SearchFilters.filterByVisibility(filteredfinal);
				Date currentDate = new Date();
				filteredfinal=SearchFilters.filterBeforeClosingDate(filteredfinal,currentDate );
				System.out.println("\tPlease enter 'C' to view only those camps that are currently open for committee registration (type 'N' to skip):");
				System.out.print("\t");
				String choice2=sc.nextLine();
				if(choice2.equalsIgnoreCase("C")) {
					filteredfinal=SearchFilters.filterByAvailability_ComSlots(filteredfinal);
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
				filteredfinal = SearchFilters.filterStartingOn(filteredfinal, sd);
			}
			if (ld != null) {
				filteredfinal = SearchFilters.filterEndingOn(filteredfinal, ld);
			}

			if(filteredfinal.isEmpty()) {

				throw new ListDoesntExistYetException("\tCamp not available");
			}
			Collections.sort(filteredfinal);
			String[] headers = InformationViewer.campInfoHeader(false, false);
			InformationViewer.printCamps(filteredfinal, headers,true);
		}catch(ListDoesntExistYetException e) {
			System.out.println("\t"+e.getMessage());

		}

	}
	/**
	 * Displays suggestions for a specified camp.
	 *
	 * @param c The camp for which suggestions are to be displayed.
	 */
	@SuppressWarnings("unchecked")
	public void viewSuggForCamp(Camp c) {
		try {
			ArrayList<Suggestions> suglist=DataManager.getSuggestionsDatabase();
			suglist=(ArrayList<Suggestions>) SearchFilters.filterMessagesByCampName(suglist, c.getcampName());
			if(suglist.isEmpty()) {
				throw new ListDoesntExistYetException("No suggestions yet!");
			}
			Collections.sort(suglist);
			String[] headers = InformationViewer.suggestionsInfoHeader(false);
			InformationViewer.printSuggestion(suglist,headers,false);}catch(ListDoesntExistYetException e) {
				System.out.println("\t"+e.getMessage());

			}

	}
	/**
	 * Displays suggestions for a specified camp, filtered by their processing state.
	 *
	 * @param c     The camp for which suggestions are to be displayed.
	 * @param state The processing state of the suggestions to be displayed (processed or unprocessed).
	 * @return true if there are suggestions to display, false otherwise.
	 */
	@SuppressWarnings("unchecked")
	public boolean viewSuggForCamp(Camp c,boolean state) {
		ArrayList<Suggestions> suglist=DataManager.getSuggestionsDatabase();
		suglist=(ArrayList<Suggestions>) SearchFilters.filterMessagesByCampName(suglist, c.getcampName());
		suglist=(ArrayList<Suggestions>) SearchFilters.filterMessagesByStatus(suglist, state);
		if(suglist.isEmpty()) {
			return false;
		}
		Collections.sort(suglist);
		String[] headers = InformationViewer.suggestionsInfoHeader(false);
		InformationViewer.printSuggestion(suglist,headers,false);
		return true;
	}



}
