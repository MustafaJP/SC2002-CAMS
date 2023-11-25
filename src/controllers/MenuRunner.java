package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import models.Camp;
import models.CampCommitteeMember;
import models.DataManager;
import models.Staff;
import models.Student;
import models.Suggestions;
import view.CampCommitteeMemberInterface;
import view.CampCommitteeMemberView;
import view.InformationViewer;
import view.StaffView;
import view.StudentInterface;
import view.StudentView;
/**
 * This class contains methods to run different menu options for various user roles,
 * including students, staff, and camp committee members. It handles user interactions and
 * navigations through different menu choices.
 */
public class MenuRunner {
	/**
	 * Runs the menu for student users. It handles various student-related functionalities
	 * like viewing and registering for camps, submitting, editing, and deleting enquiries, 
	 * and checking notifications.
	 */
	@SuppressWarnings("resource")
	public static void studentMenuRunner() {
		Scanner s = new Scanner(System.in);
		System.out.print("\tPlease enter a menu option: ");
		int choice=0;
		try{
			choice = s.nextInt();}
		catch(Exception e) {
			System.out.println("\u001B[31;1m" + "\n\tPlease enter a valid \u001B[32;1minteger\u001B[31;1m!\n" + "\u001B[0m");
			UserHandler.getMainpage().showMenuPage();

			return;
		}
		s.nextLine();
		StudentView sv = new StudentView();
		EnquiryManager eqm = new EnquiryManager();
		switch (choice) {
		case 1:
			UserHandler.changePswd();
			UserHandler.hyperLinkToMainPage();
			break;
		case 2:
			System.out.println("\n\tYou can view the list of camps in two formats: ");
			System.out.println("\t1. View all camps");
			System.out.println("\t2. View only camps open for your registration");
			System.out.println("\n\tPlease choose a format (1/2): ");
			System.out.print("\t");
			int innerchoice = s.nextInt();
			s.nextLine();

			switch (innerchoice) {
			case 1:
				sv.defaultCampsView();
				break;
			case 2:
				sv.viewCampsOpenForReg();
				break;
			}
			break;
		case 3:
			System.out.println("\n\tYou can search for a camp using these filters: ");
			System.out.println("\t1. Search by camp name");
			System.out.println("\t2. Search by camp details (location, dates, vacancies, etc)");
			System.out.println("\t3. Search by camp duration");
			System.out.println("\n\tPlease choose a filter (1/2/3): ");
			System.out.print("\t");
			innerchoice = s.nextInt();
			s.nextLine();
			switch (innerchoice) {

			case 1:
				sv.searchByCampName();
				break;
			case 2:
				sv.searchByCampDetails();
				break;
			case 3:
				sv.searchByCampDuration();
				break;
			}
			break;
		case 4:
			System.out.println("\n\tThese are the camps that are currently open for your registration: ");
			System.out.println("");
			if(!sv.viewCampsOpenForReg()) {
				break;
			}

			System.out.println("\tPlease enter the name of the camp that you wish to register for: ");
			System.out.print("\t");
			String c = s.nextLine();
			((Student) UserHandler.getCurrentUserInstance()).Register(new Camp(c));
			break;
		case 5:
			System.out.println("\n\tThese are the camps that you are currently registered for: ");
			sv.viewYourCamps();
			break;

		case 6:
			System.out.println("\n\tThese are the camps that you are currently registered for: ");
			System.out.println("");
			if(!sv.viewYourCamps()) {
				System.out.println("You have not registered for any camp yet");
				break;
			}
			System.out.println("\tPlease enter the name of the camp that you wish to withdraw from: ");
			System.out.print("\t");
			String cm = s.nextLine();
			((Student) UserHandler.getCurrentUserInstance()).Withdraw(new Camp(cm));
			break;
		case 7:
			eqm.submit(UserHandler.getCurrentUserInstance());
			break;
		case 8:
			System.out.println("\n\tThese are the enquiries that you have submitted:");
			System.out.println("");
			sv.viewEnquiriesSentByYou();
			break;
		case 9:
			System.out.println("\n\tPress 1 to edit an enquiry. Press 2 to delete an enquiry");
			System.out.print("\t");
			int option = s.nextInt();
			s.nextLine();
			System.out.println("\tThese are the enquiries that you have submitted and which haven't been processed yet:");
			System.out.println("");
			if(!sv.viewEnquiriesSentByYou(false)) {
				if(option==1) {
					System.out.println("\tYou don't have any enquiries that can be edited!");
				} else if(option==2) {
					System.out.println("\tYou don't have any enquiries that can be deleted!");
				}
				break;
			}

			if (option == 1) {
				System.out.println(
						"\tPlease choose the index of the enquiry (type without #) that you wish to edit (You can only edit enquiries that have not been processed yet!): ");
			} else if (option == 2) {
				System.out.println(
						"\tPlease choose the index of the enquiry (type without #) that you wish to delete (You can only delete enquiries that have not been processed yet!): ");
			}
			System.out.print("\t");
			int enqindex = s.nextInt();
			s.nextLine();


			if (option == 1) {
				eqm.edit(DatabaseSearchManager.SearchEnquiriesDatabase(enqindex), UserHandler.getCurrentUserInstance());
			} else if (option == 2) {
				eqm.delete(DatabaseSearchManager.SearchEnquiriesDatabase(enqindex), UserHandler.getCurrentUserInstance());
			}
			break;
		case 10:
			if (!(UserHandler.getCurrentUserInstance() instanceof CampCommitteeMember)) {
				if(DataManager.getMailboxDatabase().contains(UserHandler.getCurrentUserInstance().getUserInbox())) {
					System.out.println(DatabaseSearchManager.SearchMailboxDatabase(UserHandler.getCurrentUserInstance().getUserInbox()));
					DataManager.getMailboxDatabase().remove(UserHandler.getCurrentUserInstance().getUserInbox());
					UserHandler.getCurrentUserInstance().getUserInbox().clearNotifications();
				} else {
					System.out.println(UserHandler.getCurrentUserInstance().getUserInbox());
				}
				break;
			}
			else{UserHandler.setMainpage(new CampCommitteeMemberInterface());
			UserHandler.getMainpage().showWholePage();

			return;}
		case 11:
			if (!(UserHandler.getCurrentUserInstance() instanceof CampCommitteeMember)) {
				UserHandler.logoutNormally();

				return;
			}
			if(DataManager.getMailboxDatabase().contains(UserHandler.getCurrentUserInstance().getUserInbox())) {
				System.out.println(DatabaseSearchManager.SearchMailboxDatabase(UserHandler.getCurrentUserInstance().getUserInbox()));
				DataManager.getMailboxDatabase().remove(UserHandler.getCurrentUserInstance().getUserInbox());
				UserHandler.getCurrentUserInstance().getUserInbox().clearNotifications();
			} else {
				System.out.println(UserHandler.getCurrentUserInstance().getUserInbox());
			}
			break;
		case 12:
			if (!(UserHandler.getCurrentUserInstance() instanceof CampCommitteeMember)) {
				System.out.println("\u001B[31;1m" + "\n\tPlease choose an option from \u001B[32;1m1-11\u001B[31;1m only!\n" + "\u001B[0m");
				UserHandler.getMainpage().showMenuPage();

				return;
			}
			UserHandler.logoutNormally();

			return;
		default:
			if (!(UserHandler.getCurrentUserInstance() instanceof CampCommitteeMember)) {
				System.out.println("\u001B[31;1m" + "\n\tPlease choose an option from \u001B[32;1m1-11\u001B[31;1m only!\n" + "\u001B[0m");

			} else {
				System.out.println("\u001B[31;1m" + "\n\tPlease choose an option from \u001B[32;1m1-12\u001B[31;1m only!\n" + "\u001B[0m");
			}
			UserHandler.getMainpage().showMenuPage();

			return;

		}

		if (UserHandler.getCurrentUserInstance() instanceof CampCommitteeMember) {
			if (choice == 10) {

				return;
			}
		}
		System.out.println("\u001B[34m" + "\n\tPlease enter \u001B[31m'X'\u001B[34m to go back to main page" + "\u001B[0m");
		System.out.print("\t");
		String exit = s.nextLine();
		if (exit.equalsIgnoreCase(exit)) {
			System.out.println("\u001B[34m" + "\n\tContinuing to main page...\n" + "\u001B[0m");
			UserHandler.hyperLinkToMainPage();
		}

	}
	/**
	 * Runs the menu for staff users. It includes functionalities such as creating and managing
	 * camps, viewing and responding to suggestions and enquiries, and generating reports.
	 */
	@SuppressWarnings("resource")
	public static void staffMenuRunner() {
		Scanner s = new Scanner(System.in);
		System.out.print("\tPlease enter a menu option: ");
		int choice=0;
		try{
			choice = s.nextInt();}
		catch(Exception e) {
			System.out.println("\u001B[31;1m" + "\n\tPlease enter a valid \u001B[32;1minteger\u001B[31;1m!\n" + "\u001B[0m");
			UserHandler.getMainpage().showMenuPage();

			return;
		}
		s.nextLine();
		StaffView sv = new StaffView();
		switch (choice) {
		case 1:
			UserHandler.changePswd();
			UserHandler.hyperLinkToMainPage();
			break;
		case 2:
			((Staff) UserHandler.getCurrentUserInstance()).creationOfCamps();
			break;
		case 3:
			sv.defaultCampsView();
			break;
		case 4:
			System.out.println("\n\tYou can search for a camp using these filters: ");
			System.out.println("\t1. Search by camp name");
			System.out.println("\t2. Search by camp details (location, dates, vacancies, etc)");
			System.out.println("\t3. Search by camp duration");
			System.out.println("\n\tPlease choose a filter (1/2/3): ");
			System.out.print("\t");
			int innerchoice = s.nextInt();
			s.nextLine();
			switch (innerchoice) {

			case 1:
				sv.searchByCampName();
				break;
			case 2:
				sv.searchByCampDetails();
				break;
			case 3:
				sv.searchByCampDuration();
				break;
			}
			break;
		case 5:
			sv.viewYourCamps();
			break;
		case 6:
			System.out.println("\n\tPress 1 to edit a camp under you. Press 2 to delete a camp under you");
			System.out.print("\t");
			int option = s.nextInt();
			s.nextLine();
			System.out.println();
			if (option == 1) {
				((Staff) UserHandler.getCurrentUserInstance()).editCamp();
			} else if (option == 2) {
				((Staff) UserHandler.getCurrentUserInstance()).deleteCamp();
			}
			break;

		case 7:
			((Staff) UserHandler.getCurrentUserInstance()).visibilityToggler();
			break;
		case 8:
			System.out.println("\n\tThese are all the camps under you: ");
			ArrayList<Camp> ownemptycamps = DatabaseSearchManager.SearchStaffDatabase(UserHandler.getCurrentUserInstance()).getCampsStarted();
			if(ownemptycamps.isEmpty()) {
				System.out.println("\tYou don't have any camps under you");
				break;
			}
			InformationViewer.campNamesViewer(ownemptycamps);
			System.out.println("\tPlease enter the name of the camp whose list of registrants you wish to see");
			System.out.print("\t");
			String campname = s.nextLine();
			sv.viewRegistrants(new Camp(campname));
			break;

		case 9:
			System.out.println("\n\tThese are all the camps under you: ");
			ownemptycamps = DatabaseSearchManager.SearchStaffDatabase(UserHandler.getCurrentUserInstance()).getCampsStarted();
			if(ownemptycamps.isEmpty()) {
				System.out.println("\tYou don't have any camps under you");
				break;
			}
			InformationViewer.campNamesViewer(ownemptycamps);
			System.out.println("\tPlease enter the name of the camp whose committee you wish to see");
			System.out.print("\t");
			campname = s.nextLine();
			sv.viewCom(new Camp(campname));
			break;

		case 10:
			System.out.println("\n\tThese are all the camps under you: ");
			ownemptycamps = DatabaseSearchManager.SearchStaffDatabase(UserHandler.getCurrentUserInstance()).getCampsStarted();
			if(ownemptycamps.isEmpty()) {
				System.out.println("You don't have any camps under you");
				break;
			}
			InformationViewer.campNamesViewer(ownemptycamps);
			System.out.println("\tPlease enter the name of the camp for which you wish to see the suggestions");
			System.out.print("\t");
			campname = s.nextLine();
			sv.viewSuggForCamp(new Camp(campname));
			break;
		case 11:
			System.out.println("\n\tThese are all the camps under you: ");
			ownemptycamps = DatabaseSearchManager.SearchStaffDatabase(UserHandler.getCurrentUserInstance()).getCampsStarted();
			if(ownemptycamps.isEmpty()) {
				System.out.println("\tYou don't have any camps under you");
				break;
			}
			InformationViewer.campNamesViewer(ownemptycamps);

			System.out.println("\tPlease enter the name of the camp for which you wish to accept suggestions");
			System.out.print("\t");
			campname = s.nextLine();

			if (!sv.viewSuggForCamp(new Camp(campname), false)) {
				System.out.println("\tThis camp has no pending suggestions waiting to be accepted!");

			} else {
				if(((Staff) UserHandler.getCurrentUserInstance()).acceptSuggestions()) {
					System.out.println("\tYou have successfully accepted the suggestion");
				}

			}
			break;
		case 12:
			System.out.println("\n\tThese are all the camps under you: ");
			ownemptycamps = DatabaseSearchManager.SearchStaffDatabase(UserHandler.getCurrentUserInstance()).getCampsStarted();
			if(ownemptycamps.isEmpty()) {
				System.out.println("You don't have any camps under you");
				break;
			}
			InformationViewer.campNamesViewer(ownemptycamps);
			System.out.println("\tPlease enter the name of the camp for which you wish to see the enquiries");
			System.out.print("\t");
			campname = s.nextLine();
			sv.viewEnquiriesForCamp(new Camp(campname));
			break;
		case 13:
			System.out.println("\n\tThese are all the camps under you: ");
			ownemptycamps = DatabaseSearchManager.SearchStaffDatabase(UserHandler.getCurrentUserInstance()).getCampsStarted();
			if(ownemptycamps.isEmpty()) {
				System.out.println("You don't have any camps under you");
				break;
			}
			InformationViewer.campNamesViewer(ownemptycamps);

			System.out.println("\tPlease enter the name of the camp for which you wish to answer enquiries");
			System.out.print("\t");
			campname = s.nextLine();

			if (!sv.viewEnquiriesForCamp(new Camp(campname), false)) {
				System.out.println("\tThis camp has no pending enquiries waiting to be answered!");

			} else {
				EnquiryManager.replyToEnquiries();
			}
			break;
		case 14:
			System.out.println("\n\tThese are all the camps under you: ");
			ownemptycamps = DatabaseSearchManager.SearchStaffDatabase(UserHandler.getCurrentUserInstance()).getCampsStarted();
			if(ownemptycamps.isEmpty()) {
				System.out.println("You don't have any camps under you");
				break;
			}
			InformationViewer.campNamesViewer(ownemptycamps);
			System.out.println("\tPlease enter the name of the camp for which you wish to generate a report");
			System.out.print("\t");
			campname = s.nextLine();
			System.out.println("\tYou can generate a report in five different formats: ");
			System.out.println("\t1. Generate full camp details and registrants report");
			System.out.println("\t2. Generate camp attendees report");
			System.out.println("\t3. Generate camp committee report");
			System.out.println("\t4. Generate camp committee performance report");
			System.out.println("\t5. Generate students' enquiries report");
			System.out.println("\n\tPlease choose a format (1/2/3/4/5): ");
			System.out.print("\t");
			innerchoice = s.nextInt();
			s.nextLine();
			switch (innerchoice) {

			case 1:
				ReportGenerator.generateCampReport(new Camp(campname), false, false);
				break;
			case 2:
				ReportGenerator.generateCampReport(new Camp(campname), false, true);
				break;
			case 3:
				ReportGenerator.generateCampReport(new Camp(campname), true, false);
				break;
			case 4:
				ReportGenerator.generateComPerformanceReport(new Camp(campname));
				break;
			case 5:
				try {
					ReportGenerator.generateStudentEnquiryReport(new Camp(campname));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
			break;
		case 15:
			if(DataManager.getMailboxDatabase().contains(UserHandler.getCurrentUserInstance().getUserInbox())) {
				System.out.println(DatabaseSearchManager.SearchMailboxDatabase(UserHandler.getCurrentUserInstance().getUserInbox()));
				DataManager.getMailboxDatabase().remove(UserHandler.getCurrentUserInstance().getUserInbox());
				UserHandler.getCurrentUserInstance().getUserInbox().clearNotifications();
			} else {
				System.out.println(UserHandler.getCurrentUserInstance().getUserInbox());
			}
			break;
		case 16:
			UserHandler.logoutNormally();

			return;
		default:
			System.out.println("\u001B[31;1m" + "\n\tPlease choose an option from \u001B[32;1m1-16\u001B[31;1m only!\n" + "\u001B[0m");
			UserHandler.getMainpage().showMenuPage();

			return;

		}
		System.out.println("\u001B[34m" + "\n\tPlease enter \u001B[31m'X'\u001B[34m to go back to main page" + "\u001B[0m");
		System.out.print("\t");
		String exit = s.nextLine();
		if (exit.equalsIgnoreCase(exit)) {
			System.out.println("\u001B[34m" + "\n\tContinuing to main page...\n" + "\u001B[0m");
			UserHandler.hyperLinkToMainPage();
		}

	}
	/**
	 * Runs the menu for camp committee members. This includes viewing camps, managing suggestions,
	 * responding to enquiries, generating reports, and switching to the student interface.
	 */
	@SuppressWarnings("resource")
	public static void campComMenuRunner() {
		Scanner s = new Scanner(System.in);
		System.out.print("\n\tPlease enter a menu option: ");
		int choice=0;
		try{
			choice = s.nextInt();}
		catch(Exception e) {
			System.out.println("\u001B[31;1m" + "\n\tPlease enter a valid \u001B[32;1minteger\u001B[31;1m!\n" + "\u001B[0m");
			UserHandler.getMainpage().showMenuPage();

			return;
		}
		s.nextLine();
		CampCommitteeMemberView sv = new CampCommitteeMemberView();
		SuggestionsManager sqm = new SuggestionsManager();
		switch (choice) {
		case 1:
			UserHandler.changePswd();
			UserHandler.hyperLinkToMainPage();
			break;
		case 2:
			sv.viewYourCamps();
			break;
		case 3:

			sqm.submit(UserHandler.getCurrentUserInstance());
			break;
		case 4:
			sv.viewSuggSentByYou();
			break;
		case 5:
			System.out.println("\n\tPress 1 to edit a suggestion. Press 2 to delete a suggestion");
			System.out.print("\t");
			int option = s.nextInt();
			s.nextLine();
			System.out
			.println("\tThese are the suggestions that you have submitted and which haven't been processed yet:");
			System.out.println("");
			if(!sv.viewSuggSentByYou(false)) {
				if(option==1) {
					System.out.println("\tYou don't have any suggestions that can be edited!");
				} else if(option==2) {
					System.out.println("\tYou don't have any suggestions that can be deleted!");
				}
				break;
			}

			if (option == 1) {
				System.out.println(
						"\tPlease choose the index of the suggestion (type without #) that you wish to edit (You can only edit suggestions that have not been processed yet!): ");
			} else if (option == 2) {
				System.out.println(
						"\tPlease choose the index of the suggestion (type without #) that you wish to delete (You can only delete suggestions that have not been processed yet!): ");
			}
			System.out.print("\t");
			int sugindex = s.nextInt();
			s.nextLine();


			if (option == 1) {
				sqm.edit(new Suggestions(sugindex), UserHandler.getCurrentUserInstance());
			} else if (option == 2) {
				sqm.delete(new Suggestions(sugindex), UserHandler.getCurrentUserInstance());
			}
			break;
		case 6:
			sv.viewEnquiriesForCamp(((CampCommitteeMember) UserHandler.getCurrentUserInstance()).getCamp());
			break;
		case 7:
			if (!sv.viewEnquiriesForCamp(((CampCommitteeMember) UserHandler.getCurrentUserInstance()).getCamp(),
					false)) {
				System.out.println("\nThis camp has no pending enquiries waiting to be answered!");

			} else {
				EnquiryManager.replyToEnquiries();
			}
			break;
		case 8:
			System.out.println("\n\tYou can generate a report in four different formats: ");
			System.out.println("\t1. Generate full camp details and registrants report");
			System.out.println("\t2. Generate camp attendees report");
			System.out.println("\t3. Generate camp committee report");
			System.out.println("\t4. Generate students' enquiries report");
			System.out.println("\n\tPlease choose a format (1/2/3/4): ");
			System.out.print("\t");
			int innerchoice = s.nextInt();
			s.nextLine();
			switch (innerchoice) {

			case 1:
				ReportGenerator.generateCampReport(
						((CampCommitteeMember) UserHandler.getCurrentUserInstance()).getCamp(), false, false);
				break;
			case 2:
				ReportGenerator.generateCampReport(
						((CampCommitteeMember) UserHandler.getCurrentUserInstance()).getCamp(), false, true);
				break;
			case 3:
				ReportGenerator.generateCampReport(
						((CampCommitteeMember) UserHandler.getCurrentUserInstance()).getCamp(), true, false);
				break;
			case 4:
				try {
					ReportGenerator.generateStudentEnquiryReport(
							((CampCommitteeMember) UserHandler.getCurrentUserInstance()).getCamp());
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
			break;
		case 9:
			UserHandler.setMainpage(new StudentInterface());
			UserHandler.getMainpage().showWholePage();

			return;
		case 10:
			if(DataManager.getMailboxDatabase().contains(UserHandler.getCurrentUserInstance().getUserInbox())) {
				System.out.println(DatabaseSearchManager.SearchMailboxDatabase(UserHandler.getCurrentUserInstance().getUserInbox()));
				DataManager.getMailboxDatabase().remove(UserHandler.getCurrentUserInstance().getUserInbox());
				UserHandler.getCurrentUserInstance().getUserInbox().clearNotifications();
			} else {
				System.out.println(UserHandler.getCurrentUserInstance().getUserInbox());
			}
			break;

		case 11:
			UserHandler.logoutNormally();

			return;
		default:
			System.out.println("\u001B[31;1m" + "\n\tPlease choose an option from \u001B[32;1m1-11\u001B[31;1m only!\n" + "\u001B[0m");
			UserHandler.getMainpage().showMenuPage();

			return;

		}
		System.out.println("\u001B[34m" + "\n\tPlease enter \u001B[31m'X'\u001B[34m to go back to main page" + "\u001B[0m");
		System.out.print("\t");
		String exit = s.nextLine();
		if (exit.equalsIgnoreCase(exit)) {
			System.out.println("\u001B[34m" + "\n\tContinuing to main page...\n" + "\u001B[0m");
			UserHandler.hyperLinkToMainPage();
		}

	}

}
