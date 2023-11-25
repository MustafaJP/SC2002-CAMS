package models;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import controllers.DatabaseSearchManager;
import controllers.SearchFilters;
import controllers.StaffHandler;
import controllers.UserHandler;
import view.InformationViewer;
/**
 * This class extends the User class and represents staff members with 
 * specific functionalities like managing camps. It includes operations such as 
 * creating, editing, deleting, and toggling visibility of camps, as well as 
 * accepting suggestions.
 */
public class Staff extends User{
	/**
	 * List of camps initiated by the staff member.
	 */
	private ArrayList<Camp> campsStarted=new ArrayList<Camp>();
	/**
	 * Constructs a Staff object with only an ID, inheriting from the User class.
	 * 
	 * @param id the unique ID of the staff member
	 */	
	public Staff(String id) {
		super(id);
	}

	/**
	 * Constructs a Staff object with an ID, name, password, faculty, and a list of camps.
	 * 
	 * @param id the unique ID of the staff member
	 * @param nm the name of the staff member
	 * @param pwd the password of the staff member
	 * @param f the faculty of the staff member
	 * @param camps the list of camps initiated by the staff member
	 */
	public Staff(String id, String nm, String pwd, FACULTIES f,ArrayList<Camp> camps) {
		super(id, nm, pwd, f);

		this.campsStarted=camps;
	}
	/**
	 * Constructs a Staff object with an ID, name, password, faculty (as String), and a list of camps.
	 * 
	 * @param id the unique ID of the staff member
	 * @param nm the name of the staff member
	 * @param pwd the password of the staff member
	 * @param f the faculty (as a String) of the staff member
	 * @param camps the list of camps initiated by the staff member
	 */
	public Staff(String id, String nm, String pwd, String f,ArrayList<Camp> camps) {
		super(id, nm, pwd, f);


		this.campsStarted=camps;
	}
	// Getters
	/**
	 * Retrieves the list of camps started by this staff member.
	 * 
	 * @return ArrayList of Camp objects that were initiated by this staff member.
	 */
	public ArrayList<Camp> getCampsStarted() {
		return this.campsStarted;
	}
	// Setters
	/**
	 * Sets or updates the list of camps started by this staff member.
	 * 
	 * @param campsStarted an ArrayList of Camp objects to be associated with this staff member.
	 */	
	public void setCampsStarted(ArrayList<Camp> campsStarted) {
		this.campsStarted = campsStarted;
	}
	// Staff management methods
	/**
	 * Creates a new camp and adds it to the database and the list of camps started by the staff member.
	 */
	public void creationOfCamps() {
		Camp cmp=StaffHandler.createCamps(this);

		DataManager.getCampsDatabase().add(cmp);
		this.campsStarted.add(cmp);
		System.out.println("\tYou have successfully created the camp '"+cmp.getcampName()+"' !");
	}
	/**
	 * Allows editing of a camp's details if the camp is eligible for editing.
	 */
	@SuppressWarnings("resource")
	public void editCamp() {
		System.out.println();
		System.out.println("\tThese are the camps currently under you (that don't have any registrants yet):");
		ArrayList<Camp> ownemptycamps=DatabaseSearchManager.SearchStaffDatabase(UserHandler.getCurrentUserInstance()).getCampsStarted();
		ownemptycamps.replaceAll(ele->DatabaseSearchManager.SearchCampsDatabase(ele));
		ownemptycamps=SearchFilters.filterByEmptyCampsOnly(ownemptycamps);
		if(ownemptycamps.isEmpty()) {
			System.out.println("\tYou don't have any camps that you can edit");
			return;
		}
		InformationViewer.campNamesViewer(ownemptycamps);
		System.out.println("\tPlease enter name of camp that you wish to edit: ");
		Scanner s=new Scanner(System.in);
		System.out.print("\t");
		String cname=s.nextLine();
		Camp cchoice=DatabaseSearchManager.SearchCampsDatabase(cname);
		if(cchoice==null || !cchoice.getstaffInCharge().equals(new Staff(this.getUserAccountId()))) {
			System.out.println("\tYou have not created any such camp!");
		}
		else if (cchoice.getEmptySlots()!=cchoice.getTotalSlots()) {
			System.out.println("\tCamp '"+cchoice.getcampName()+"' already has some registrants. It can no longer be edited!");

		}
		else{
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd yyyy");
			System.out.println("");
			System.out.println("\tThe current camp details are as follows: ");
			System.out.println("\t1. Name: "+cchoice.getcampName());
			System.out.println("\t2. Description: "+cchoice.getdescription());
			System.out.println("\t3. Location: "+cchoice.getlocation());
			System.out.println("\t4. Total Slots: "+cchoice.getTotalSlots());
			System.out.println("\t5. Committee Slots: "+cchoice.getcampCommitteeSlots());
			System.out.println("\t6. Start Date: "+dateFormat.format(cchoice.getdates()[0]));
			System.out.println("\t7. End Date: "+dateFormat.format(cchoice.getdates()[1]));
			System.out.println("\t8. Registration Deadline: "+dateFormat.format(cchoice.getregistrationClosingDate()));
			System.out.println("\t9. Faculties Allowed: "+cchoice.getfacultyAllowed());
			System.out.println();
			System.out.println("\tPlease choose option number of the detail that you wish to edit: ");
			System.out.print("\t");
			int choice=s.nextInt();
			s.nextLine();
			while(!StaffHandler.campEditor(choice,cchoice,this)) {
				System.out.println("\tPlease choose option number of the detail that you wish to edit: ");
				System.out.print("\t");
				choice=s.nextInt();
				s.nextLine();
			}



		}


	}
	/**
	 * Deletes a camp from the database and the list of camps started by the staff member.
	 */
	@SuppressWarnings("resource")
	public void deleteCamp() {
		System.out.println("\tThese are the camps currently under you (that don't have any registrants yet):");
		ArrayList<Camp> ownemptycamps = DatabaseSearchManager.SearchStaffDatabase(UserHandler.getCurrentUserInstance()).getCampsStarted();
		ownemptycamps.replaceAll(ele->DatabaseSearchManager.SearchCampsDatabase(ele));
		ownemptycamps=SearchFilters.filterByEmptyCampsOnly(ownemptycamps);
		if(ownemptycamps.isEmpty()) {
			System.out.println("\tYou don't have any camps that you can delete");
			return;
		}
		InformationViewer.campNamesViewer(ownemptycamps);


		System.out.println("\tPlease enter name of camp that you wish to delete: ");

		Scanner s=new Scanner(System.in);
		System.out.print("\t");
		String cname=s.nextLine();
		Camp cchoice=DatabaseSearchManager.SearchCampsDatabase(cname);
		if(cchoice==null || !cchoice.getstaffInCharge().equals(new Staff(this.getUserAccountId()))) {
			System.out.println("\tYou have not created any such camp!");
		}
		else if (cchoice.getEmptySlots()!=cchoice.getTotalSlots()) {
			System.out.println("\tCamp '"+cchoice.getcampName()+"' already has some registrants. It can no longer be deleted!");

			return;
		}
		else{
			StaffHandler.delCamp(cchoice, this);

			System.out.println("\tCamp '"+cchoice.getcampName()+"' has been successfully deleted!");
		}


	}
	/**
	 * Toggles the visibility of a camp in the database.
	 */
	@SuppressWarnings("resource")
	public void visibilityToggler() {
		Scanner s=new Scanner(System.in);
		ArrayList<Camp> ownemptycamps=DatabaseSearchManager.SearchStaffDatabase(UserHandler.getCurrentUserInstance()).getCampsStarted();
		ownemptycamps.replaceAll(ele->DatabaseSearchManager.SearchCampsDatabase(ele));
		ownemptycamps=SearchFilters.filterByEmptyCampsOnly(ownemptycamps);
		System.out.println("\tThese are the current visibility states of the camps started by you:");
		if(ownemptycamps.isEmpty()) {
			System.out.println("\tYou don't have any camps whose status you can toggle");

			return;
		}
		for(Camp c:ownemptycamps) {
			Camp cmp=DatabaseSearchManager.SearchCampsDatabase(c);
			String temp=(cmp.getVisibility()?"ON":"OFF");

			System.out.println("\t"+c.getcampName()+" - "+temp);
		}
		System.out.println("\tPlease enter name of camp whose state you wish to toggle: ");
		System.out.print("\t");
		String cname=s.nextLine();
		if(!ownemptycamps.contains(new Camp(cname))) {
			System.out.println("\tYou cannot toggle status of this camp anymore as it already has some registrants");
			return;
		}
		Camp cchoice=DatabaseSearchManager.SearchCampsDatabase(cname);
		StaffHandler.changeVis(cchoice);
		System.out.println("\tCamp '"+cname+"' has been successfully set to "+(cchoice.getVisibility()?"ON":"OFF"));

	}
	/**
	 * Accepts a suggestion and updates relevant databases accordingly.
	 * 
	 * @return true if the suggestion is successfully accepted, false otherwise
	 */
	@SuppressWarnings("resource")
	public boolean acceptSuggestions() {
		Scanner s=new Scanner(System.in);
		System.out.println("\tPlease enter index number of suggestion that you wish to accept: ");
		System.out.print("\t");
		int ind=s.nextInt();
		s.nextLine();
		if(DatabaseSearchManager.SearchSuggestionsDatabase(ind)==null || !DatabaseSearchManager.SearchCampsDatabase(DatabaseSearchManager.SearchSuggestionsDatabase(ind).getC()).getstaffInCharge().equals(this))
		{System.out.println("\tNo such suggestion could be found!");

		return false;}
		if(DatabaseSearchManager.SearchSuggestionsDatabase(ind).isStatus()) {
			System.out.println("\tThis suggestion has already been accepted!");

			return false;
		}
		DatabaseSearchManager.SearchSuggestionsDatabase(ind).setStatus(true);
		DatabaseSearchManager.SearchCampComMemDatabase(DatabaseSearchManager.SearchSuggestionsDatabase(ind).getSender()).getPointsHolder().incPt();
		DatabaseSearchManager.SearchCampComMemDatabase(DatabaseSearchManager.SearchSuggestionsDatabase(ind).getSender()).setAcceptedWhenAccepted(DatabaseSearchManager.SearchSuggestionsDatabase(ind));
		if(DataManager.getMailboxDatabase().contains(new Mailbox(DatabaseSearchManager.SearchSuggestionsDatabase(ind).getSender().getUserAccountId()))) {
			DatabaseSearchManager.SearchMailboxDatabase(DatabaseSearchManager.SearchSuggestionsDatabase(ind).getSender().getUserAccountId()).recieveNotifications("\tYour suggestion with index #"+ind+" has been accepted");
		} else {
			ArrayList<String> msgs = new ArrayList<String>();
			msgs.add("\tYour suggestion with index #"+ind+" has been accepted");
			DataManager.getMailboxDatabase().add(new Mailbox(msgs,DatabaseSearchManager.SearchSuggestionsDatabase(ind).getSender().getUserAccountId()));}

		return true;
	}




}
