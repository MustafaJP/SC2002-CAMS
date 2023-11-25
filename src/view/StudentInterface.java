package view;

import controllers.MenuRunner;
import controllers.UserHandler;
import models.CampCommitteeMember;
import models.FACULTIES;
import models.Student;
/**
 * This class extends UserInterface class to provide a specialized user interface for students.
 * It includes methods to display student-specific profile headers, main page information, and a student-specific menu.
 */
public class StudentInterface extends UserInterface{
	/**
	 * Displays the profile header information for a student.
	 * This includes the student's name, account ID, faculty, and coordinator status if applicable.
	 */
	@Override
	public void showProfileHeader() {

		String userName = UserHandler.getCurrentUserInstance().getUserName();
		String accountId = UserHandler.getCurrentUserInstance().getUserAccountId();
		FACULTIES faculty = UserHandler.getCurrentUserInstance().getFaculty();
		String coordinator = ((Student)UserHandler.getCurrentUserInstance()).getCoordinator();


		String nameLine = String.format("%-30s %-37s %-30s", "Student Name: " + userName, "Student Account ID: " + accountId, "Login Status: Currently Active");
		String detailLine = String.format("%-30s %-70s", "Faculty: " + faculty, "Camp Coordinator: " + coordinator);
		System.out.println(nameLine);
		System.out.println(detailLine);
		System.out.println("-".repeat(100)+"\n");
	}
	/**
	 * Displays the byline information for the student main page.
	 * This includes a title indicating that it is the student main page.
	 */
	@Override
	public void showByLine() {
		String title = "Student Main Page";
		System.out.println("=".repeat(100));
		System.out.println(String.format("%" + ((100 - title.length()) / 2 + title.length()) + "s", title));
		System.out.println("=".repeat(100));
	}
	/**
	 * Shows the menu page specific to students.
	 * This menu includes options tailored to student functionalities like viewing and registering for camps,
	 * submitting and managing enquiries, and checking the mailbox.
	 */
	@Override
	public void showMenuPage() {

		String[] menuItems = {
				"1. Change your password",
				"2. View list of camps open to your faculty",
				"3. Search for a camp open to your faculty",
				"4. Register for a camp",
				"5. View list of camps you have registered for",
				"6. Withdraw from a camp",
				"7. Submit enquiries for a camp",
				"8. View replies to your enquiries",
				"9. Edit/Delete your enquiries"
		};

		System.out.println("Hello, " + UserHandler.getCurrentUserInstance().getUserName() +"\n");


		for (String item : menuItems) {
			System.out.println("\t"+ item);
		}


		if (UserHandler.getCurrentUserInstance() instanceof CampCommitteeMember) {
			System.out.println("\t10. Go into Camp Committee Member mode for Camp '"
					+ ((Student)UserHandler.getCurrentUserInstance()).getCoordinator() + "'");
			System.out.println("\t11. Check mailbox - You have ("
					+ UserHandler.getCurrentUserInstance().getUserInbox().getBuffer() + ") notifications");
			System.out.println("\t12. Logout");
		} else {
			System.out.println("\t10. Check mailbox - You have ("
					+ UserHandler.getCurrentUserInstance().getUserInbox().getBuffer() + ") notifications");
			System.out.println("\t11. Logout");
		}

		System.out.println("\n"+"=".repeat(100) +"\n");


		MenuRunner.studentMenuRunner();
	}


}
