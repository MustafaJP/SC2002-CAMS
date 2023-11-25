package view;

import controllers.MenuRunner;
import controllers.UserHandler;
import models.CampCommitteeMember;
import models.Student;
/**
 * This class extends the StudentInterface class to provide a specific user interface
 * for camp committee members. It includes functionalities and displays tailored to committee members,
 * such as submitting and managing suggestions, viewing and replying to enquiries, generating reports,
 * and switching between committee and student modes.
 */
public class CampCommitteeMemberInterface extends StudentInterface {
	/**
	 * Displays the byline information for the camp committee member interface.
	 * This includes the camp name and the current points of the committee member.
	 */
	@Override
	public void showByLine() {

		int lineLength = 100; 


		String title = "Camp Committee Main Page";
		String campName = ((Student)UserHandler.getCurrentUserInstance()).getCoordinator();
		String points = String.valueOf(((CampCommitteeMember)UserHandler.getCurrentUserInstance()).getPointsHolder().getPt());


		System.out.println("=".repeat(lineLength));
		System.out.println(String.format("%" + ((lineLength - title.length()) / 2 + title.length()) + "s", title));
		System.out.println("Camp Name: " + campName);
		System.out.println("You currently have : " + points + " points");
		System.out.println("=".repeat(lineLength));
	}
	/**
	 * Shows the menu page for the camp committee member interface.
	 * This menu includes options specific to camp committee member functionalities.
	 */
	@Override
	public void showMenuPage() {

		String[] menuItems = {
				"1. Change your password",
				"2. View Camp Details",
				"3. Submit a new suggestion",
				"4. View suggestions submitted by you",
				"5. Edit/Delete suggestions submitted by you",
				"6. View enquiries",
				"7. Reply to enquiries",
				"8. Generate report for camp",
				"9. Go back to Student mode"
		};


		System.out.println("Hello, " + UserHandler.getCurrentUserInstance().getUserName() + "\n");


		for (String item : menuItems) {
			System.out.println("\t" + item);
		}


		System.out.println("\t10. Check mailbox - You have ("
				+ UserHandler.getCurrentUserInstance().getUserInbox().getBuffer() + ") notifications");


		System.out.println("\t11. Logout");

		System.out.println("\n" + "=".repeat(100) + "\n");


		MenuRunner.campComMenuRunner();
	}

}
