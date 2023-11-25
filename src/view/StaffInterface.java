package view;

import controllers.MenuRunner;
import controllers.UserHandler;
import models.FACULTIES;
/**
 * This class extends UserInterface class to provide a specialized user interface for staff members.
 * It includes methods to display staff-specific profile headers, main page information, and a staff-specific menu.
 */
public class StaffInterface extends UserInterface {
	/**
	 * Displays the profile header information for a staff member.
	 * This includes the staff's name, account ID, faculty, and login status.
	 */
	@Override
	public void showProfileHeader() {

		String userName = UserHandler.getCurrentUserInstance().getUserName();
		String accountId = UserHandler.getCurrentUserInstance().getUserAccountId();
		FACULTIES faculty = UserHandler.getCurrentUserInstance().getFaculty();


		String nameLine = String.format("%-30s %-37s %-33s", 
				"Staff Name: " + userName, 
				"Staff Account ID: " + accountId, 
				"Login Status: Currently Active");

		String detailLine = String.format("%-30s %-70s", 
				"Faculty: " + faculty, 
				"");


		System.out.println(nameLine);
		System.out.println(detailLine);
		System.out.println("=".repeat(100));
	}
	/**
	 * Displays the byline information for the staff main page.
	 * This includes a title indicating that it is the staff main page.
	 */
	@Override
	public void showByLine() {
		String title = "Staff Main Page";
		System.out.println("=".repeat(100));
		System.out.println(String.format("%" + ((100 - title.length()) / 2 + title.length()) + "s", title));
		System.out.println("=".repeat(100));
	}
	/**
	 * Shows the menu page specific to staff members.
	 * This menu includes options tailored to staff functionalities like managing camps, 
	 * viewing enquiries, generating reports, and more.
	 */
	@Override
	public void showMenuPage() {

		String[] menuItems = {
				"1. Change your password",
				"2. Create a new camp",
				"3. View list of all camps",
				"4. Search for a camp",
				"5. View list of camps started by you",
				"6. Edit/Delete a camp under you",
				"7. Toggle the visibility of a camp under you",
				"8. View list of students registered for a camp under you",
				"9. View the Camp Committee for a camp under you",
				"10. View suggestions for a camp under you",
				"11. Accept suggestions for a camp under you",
				"12. View enquiries to a camp under you",
				"13. Reply to enquiries to a camp under you",
				"14. Generate report for a camp under you",
				"15. Check mailbox - You have (%d) notifications",
				"16. Logout"
		};

		System.out.println("Hello, " + UserHandler.getCurrentUserInstance().getUserName() +"\n");

		for (String item : menuItems) {
			if (item.contains("%d")) { 
				System.out.printf("\t"+ item + "\n", UserHandler.getCurrentUserInstance().getUserInbox().getBuffer());
			} else {
				System.out.println("\t"+ item);
			}
		}

		System.out.println("\n"+"=".repeat(100) +"\n");

		MenuRunner.staffMenuRunner();
	}




}
