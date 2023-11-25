package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import models.Camp;
import models.DataManager;
import models.FACULTIES;
import models.Staff;
/**
 * This class provides functionalities for staff members to manage camps. 
 * This includes creating camps, changing camp visibility, deleting camps, and editing various 
 * aspects of a camp.
 */
public class StaffHandler {
	/**
	 * Creates a new Camp object associated with a staff member.
	 *
	 * @param s The staff member who is creating the camp.
	 * @return The newly created Camp object, or null if an exception occurs.
	 */
	public static Camp createCamps(Staff s) {
		try {
			Camp c = new Camp(s);

			return c;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Toggles the visibility of a given camp.
	 *
	 * @param c The camp whose visibility is to be toggled.
	 */
	public static void changeVis(Camp c) {
		c.toggleVisibility();
		return;

	}
	/**
	 * Deletes a specified camp from the database and the staff member's list of started camps.
	 *
	 * @param c The camp to be deleted.
	 * @param s The staff member who started the camp.
	 */
	public static void delCamp(Camp c, Staff s) {
		DataManager.getCampsDatabase().remove(c);
		s.getCampsStarted().remove(c);

	}
	/**
	 * Edits the details of a specified camp based on user choices.
	 *
	 * @param choice The choice indicating which detail of the camp to edit.
	 * @param c      The camp to be edited.
	 * @param s      The staff member editing the camp.
	 * @return true if the editing is successful, false otherwise.
	 */
	@SuppressWarnings("resource")
	public static boolean campEditor(int choice, Camp c, Staff s) {
		Scanner sc = new Scanner(System.in);
		switch (choice) {
		case 1:
			System.out.println("\tPlease enter a new name for the camp: ");
			System.out.print("\t");
			String newname = sc.nextLine();
			s.getCampsStarted().get(s.getCampsStarted().indexOf(c)).setCampName(newname);
			DatabaseSearchManager.SearchCampsDatabase(c).setCampName(newname);
			System.out.println("\tThe name of the camp has been changed successfully!");
			break;
		case 2:
			System.out.println("\tPlease enter a new description for the camp: ");
			System.out.print("\t");
			String newdesc = sc.nextLine();
			s.getCampsStarted().get(s.getCampsStarted().indexOf(c)).setDesc(newdesc);
			DatabaseSearchManager.SearchCampsDatabase(c).setDesc(newdesc);
			System.out.println("\tThe description of the camp has been changed successfully!");
			break;
		case 3:
			System.out.println("\tPlease enter a new location for the camp: ");
			System.out.print("\t");
			String newl = sc.nextLine();
			s.getCampsStarted().get(s.getCampsStarted().indexOf(c)).setPlace(newl);
			DatabaseSearchManager.SearchCampsDatabase(c).setPlace(newl);
			System.out.println("\tThe location of the camp has been changed successfully!");
			break;
		case 4:
			System.out.println("\tPlease enter a new number of total slots for the camp: ");
			System.out.print("\t");
			int newslots = sc.nextInt();
			sc.nextLine();
			s.getCampsStarted().get(s.getCampsStarted().indexOf(c)).setTotalSlots(newslots);
			DatabaseSearchManager.SearchCampsDatabase(c).setTotalSlots(newslots);
			System.out.println("\tThe total number of slots in the camp has been changed successfully!");
			break;
		case 5:
			System.out.println("\tPlease enter a new number of committee slots for the camp: ");
			System.out.print("\t");
			int newcomslots = sc.nextInt();
			sc.nextLine();
			s.getCampsStarted().get(s.getCampsStarted().indexOf(c)).setComSlots(newcomslots);
			DatabaseSearchManager.SearchCampsDatabase(c).setComSlots(newcomslots);
			System.out.println("\tThe number of committee slots in the camp has been changed successfully!");
			break;
		case 6:
			System.out.println("\tPlease enter a new start date for the camp(DD/MM/YYYY): ");
			System.out.print("\t");
			String sd = sc.nextLine();
			try {
				Date sdformat = new SimpleDateFormat("dd/MM/yyyy").parse(sd);
				s.getCampsStarted().get(s.getCampsStarted().indexOf(c)).setStartDate(sdformat);
				DatabaseSearchManager.SearchCampsDatabase(c).setStartDate(sdformat);
				System.out.println("\tThe start date of the camp has been changed successfully!");
				break;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		case 7:
			System.out.println("\tPlease enter a new end date for the camp(DD/MM/YYYY): ");
			System.out.print("\t");
			String ed = sc.nextLine();
			try {
				Date edformat = new SimpleDateFormat("dd/MM/yyyy").parse(ed);
				s.getCampsStarted().get(s.getCampsStarted().indexOf(c)).setEndDate(edformat);
				DatabaseSearchManager.SearchCampsDatabase(c).setEndDate(edformat);
				System.out.println("\tThe end date of the camp has been changed successfully!");
				break;
			} catch (ParseException e) {

				e.printStackTrace();
			}
		case 8:
			System.out.println("\tPlease enter a new registration deadline for the camp(DD/MM/YYYY): ");
			System.out.print("\t");
			String rd = sc.nextLine();
			try {
				Date rdformat = new SimpleDateFormat("dd/MM/yyyy").parse(rd);
				s.getCampsStarted().get(s.getCampsStarted().indexOf(c)).setRegDeadline(rdformat);
				DatabaseSearchManager.SearchCampsDatabase(c).setRegDeadline(rdformat);
				System.out.println("\tThe registration deadline of the camp has been changed successfully!");
				break;
			} catch (ParseException e) {

				e.printStackTrace();
			}
		case 9:
			System.out.println("\tPlease enter the name of the new faculty for which the camp will be open: ");
			System.out.print("\t");
			String f = sc.nextLine();
			s.getCampsStarted().get(s.getCampsStarted().indexOf(c)).setFaculty(FACULTIES.valueOf(f));
			DatabaseSearchManager.SearchCampsDatabase(c).setFaculty(FACULTIES.valueOf(f));
			System.out.println("\tThe faculty for which the camp is open has been changed successfully!");
			break;
		default:
			System.out.println("\tPlease enter a option from 1 to 9 only!");

			return false;

		}

		return true;

	}

}
