package view;

import java.util.ArrayList;
import java.util.Collections;

import controllers.DatabaseSearchManager;
import controllers.SearchFilters;
import models.Camp;
import models.CampCommitteeMember;
import models.DataManager;
import models.Enquiries;
import models.Student;
/**
 * This abstract class provides abstract and concrete methods for staff and committee members
 * to view information related to camps. This includes viewing camp committee members, camp registrants, and enquiries
 * for a camp. It serves as a base class for more specific viewer implementations.
 */
public abstract class StaffComCommonPrivelegesViewer {
	/**
	 * Abstract method to view committee members of a specific camp.
	 *
	 * @param c The camp whose committee members are to be viewed.
	 */
	public abstract void viewCom(Camp c);

	/**
	 * Views the registrants (both attendees and committee members) of a specific camp.
	 *
	 * @param c The camp whose registrants are to be viewed.
	 */
	public void viewRegistrants(Camp c) {
		try {
			ArrayList<Student> temp=DatabaseSearchManager.SearchCampsDatabase(c).getStudent();
			ArrayList<CampCommitteeMember> temp2=DatabaseSearchManager.SearchCampsDatabase(c).getcampCommittee();
			ArrayList<Student> all=new ArrayList<Student>();
			all.addAll(temp);
			all.addAll(temp2);
			if(all.isEmpty()) {
				throw new ListDoesntExistYetException("\tNo registrants yet!");
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
	 * Views the enquiries for a specific camp.
	 *
	 * @param c The camp whose enquiries are to be viewed.
	 */
	@SuppressWarnings("unchecked")
	public void viewEnquiriesForCamp(Camp c) {
		try {
			ArrayList<Enquiries> enqlist=DataManager.getEnquiriesDatabase();
			enqlist=(ArrayList<Enquiries>) SearchFilters.filterMessagesByCampName(enqlist, c.getcampName());
			if(enqlist.isEmpty()) {
				throw new ListDoesntExistYetException("\tNo enquiries yet!");
			}
			Collections.sort(enqlist);
			String[] headers = InformationViewer.enquiriesInfoHeader(false, false);
			InformationViewer.printEnquiry(enqlist, headers,false,false);}catch(ListDoesntExistYetException e) {
				System.out.println("\t"+e.getMessage());

			}
	}
	/**
	 * Views the enquiries for a specific camp, filtered by their processing state.
	 *
	 * @param c     The camp whose enquiries are to be viewed.
	 * @param state The processing state of the enquiries to be displayed (processed or unprocessed).
	 * @return true if there are enquiries to display, false otherwise.
	 */
	@SuppressWarnings("unchecked")
	public boolean viewEnquiriesForCamp(Camp c,boolean state) {
		ArrayList<Enquiries> enqlist=DataManager.getEnquiriesDatabase();
		enqlist=(ArrayList<Enquiries>) SearchFilters.filterMessagesByCampName(enqlist, c.getcampName());
		enqlist=(ArrayList<Enquiries>) SearchFilters.filterMessagesByStatus(enqlist, state);
		if(enqlist.isEmpty()) {
			return false;
		}
		Collections.sort(enqlist);
		String[] headers = InformationViewer.enquiriesInfoHeader(false, false);
		InformationViewer.printEnquiry(enqlist, headers,false,false);
		return true;
	}
}
