package controllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import models.Camp;
import models.CampCommitteeMember;
import models.DataManager;
import models.Enquiries;
import models.Student;
/**
 * This class is responsible for creating and writing various reports related to camps,
 * such as camp details, attendees, committee performance, and student enquiries. It outputs the reports
 * as text files.
 */
public class ReportGenerator {
	/**
	 * Generates a report for a specific camp. The report can include details about the camp,
	 * its attendees, and/or its committee members, based on the provided flags.
	 *
	 * @param c The camp for which the report is to be generated.
	 * @param skipattendee If true, attendee details are omitted from the report.
	 * @param skipcommittee If true, committee member details are omitted from the report.
	 */
	public static void generateCampReport(Camp c, boolean skipattendee, boolean skipcommittee) {
		try {
			String filePath = "/Users/mohorbanerjee/eclipse-workspace/OOPCAMSFinalcodes/Camp_" + c.getcampName();
			filePath += skipattendee ? "_Committee_Report.txt" : skipcommittee ? "_Attendee_Report.txt" : "_Full_Report.txt";

			FileWriter fileWriter = new FileWriter(filePath);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write("Camp Details");
			bufferedWriter.newLine();
			bufferedWriter.newLine();


			String formatHeader = "%-25s %-35s %-30s %-25s %-30s %-40s %-40s %-60s %-60s %-60s %-35s %-35s %-25s";
			String separator = String.join("", Collections.nCopies(formatHeader.length()*1000, "-")); 
			bufferedWriter.write(String.format(formatHeader, "Camp Name", "Staff-In-Charge", "Description", "Location", "Faculty", "Total Slots", "Total Committee Slots", "Start Date", "End Date", "Registration Deadline", "Empty Slots", "Empty Committee Slots", "Visibility"));
			bufferedWriter.newLine();
			bufferedWriter.write(separator);
			bufferedWriter.newLine();
			bufferedWriter.write(DatabaseSearchManager.SearchCampsDatabase(c).toString());
			bufferedWriter.newLine();
			bufferedWriter.newLine();

			String formatData = "%-15s %-25s %-20s %-25s";

			if (skipattendee) {
				bufferedWriter.write("Camp Committee List");
				bufferedWriter.newLine();
				bufferedWriter.newLine();
				bufferedWriter.write(String.format(formatData, "UserID", "Name", "Faculty", "NTU Email"));
				bufferedWriter.newLine();
				bufferedWriter.write(separator);
				bufferedWriter.newLine();

				ArrayList<CampCommitteeMember> temp = DatabaseSearchManager.SearchCampsDatabase(c).getcampCommittee();
				Collections.sort(temp);
				for (CampCommitteeMember yi : temp) {
					CampCommitteeMember y=DatabaseSearchManager.SearchCampComMemDatabase(yi);
					bufferedWriter.write(String.format(formatData, y.getUserAccountId(), y.getUserName(), y.getFaculty().toString(), y.getUserAccountId() + "@e.ntu.edu.sg"));
					bufferedWriter.newLine();
					bufferedWriter.write(separator);
					bufferedWriter.newLine();
				}
				System.out.println("\tYour report Camp_" + c.getcampName() + "_Committee_Report.txt has been successfully generated!");
			} else if (skipcommittee) {
				bufferedWriter.write("Camp Attendees List");
				bufferedWriter.newLine();
				bufferedWriter.newLine();
				bufferedWriter.write(String.format(formatData, "UserID", "Name", "Faculty", "NTU Email"));
				bufferedWriter.newLine();
				bufferedWriter.write(separator);
				bufferedWriter.newLine();

				ArrayList<Student> temp = DatabaseSearchManager.SearchCampsDatabase(c).getStudent();
				Collections.sort(temp);
				for (Student yi : temp) {
					Student y=DatabaseSearchManager.SearchStudentDatabase(yi);
					bufferedWriter.write(String.format(formatData, y.getUserAccountId(), y.getUserName(), y.getFaculty().toString(), y.getUserAccountId() + "@e.ntu.edu.sg"));
					bufferedWriter.newLine();
					bufferedWriter.write(separator);
					bufferedWriter.newLine();
				}
				System.out.println("\tYour report Camp_" + c.getcampName() + "_Attendee_Report.txt has been successfully generated!");
			} else {
				bufferedWriter.write("Camp Registrants List");
				bufferedWriter.newLine();
				bufferedWriter.newLine();
				bufferedWriter.write(String.format(formatData, "UserID", "Name", "Faculty", "Role", "NTU Email"));
				bufferedWriter.newLine();
				bufferedWriter.write(separator);
				bufferedWriter.newLine();

				ArrayList<Student> temp = DatabaseSearchManager.SearchCampsDatabase(c).getStudent();
				ArrayList<CampCommitteeMember> temp2 = DatabaseSearchManager.SearchCampsDatabase(c).getcampCommittee();
				ArrayList<Student> all = new ArrayList<>(temp);
				all.addAll(temp2);
				Collections.sort(all);
				for (Student yi : all) {
					Student y=DatabaseSearchManager.SearchStudentDatabase(yi);
					String role = temp.contains(y) ? "Attendee" : "Committee Member";
					bufferedWriter.write(String.format(formatData, y.getUserAccountId(), y.getUserName(), y.getFaculty().toString(), role, y.getUserAccountId() + "@e.ntu.edu.sg"));
					bufferedWriter.newLine();
					bufferedWriter.write(separator);
					bufferedWriter.newLine();
				}
				System.out.println("\tYour report Camp_" + c.getcampName() + "_Full_Report.txt has been successfully generated!");
			}
			bufferedWriter.close();
		} catch (IOException e) {
			System.err.println("\tError generating the report!" + e.getMessage());
		}
	}
	/**
	 * Generates a performance report for the committee members of a specific camp. 
	 * The report includes details such as enquiries answered, suggestions sent, and total points.
	 *
	 * @param c The camp for which the committee performance report is to be generated.
	 */
	public static void generateComPerformanceReport(Camp c) {
		try {
			String filePath = "/Users/mohorbanerjee/eclipse-workspace/OOPCAMSFinalcodes/Camp_" + c.getcampName()
			+ "_Committee_Performance_Report.txt";

			FileWriter fileWriter = new FileWriter(filePath);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write("Camp Committee Performance Report");
			bufferedWriter.newLine();
			bufferedWriter.newLine();
			String formatHeader = "%-10s %-20s %-15s %-30s %-30s %-30s %-20s %-30s";
			String separator = String.join("", Collections.nCopies(formatHeader.length()*1000, "-")); 
			bufferedWriter.write(String.format(formatHeader, "UserID", "Name", "Faculty", "Enquiries Answered", "Suggestions Sent", "Accepted Suggestions", "Total Points", "NTU Email"));
			bufferedWriter.newLine();
			bufferedWriter.write(separator);
			bufferedWriter.newLine();

			String formatData = "%-10s %-20s %-15s %-30d %-30d %-30d %-20d %-30s";

			ArrayList<CampCommitteeMember> temp = DatabaseSearchManager.SearchCampsDatabase(c)
					.getcampCommittee();
			Collections.sort(temp);
			for (CampCommitteeMember yi : temp) {
				CampCommitteeMember y=DatabaseSearchManager.SearchCampComMemDatabase(yi);
				int suggsent = y.getSuggList().size();
				int accsugg = y.getSuggList().values().stream().filter(status -> status)
						.collect(Collectors.toCollection(ArrayList::new)).size();
				ArrayList<Enquiries> enqlist = DataManager.getEnquiriesDatabase();
				enqlist = SearchFilters.filterEnquiriesByHelper(enqlist, y);
				int enqanswd = enqlist.size();

				bufferedWriter.write(String.format(formatData, y.getUserAccountId(), y.getUserName(), y.getFaculty().toString(), enqanswd, suggsent, accsugg, y.getPointsHolder().getPt(), y.getUserAccountId() + "@e.ntu.edu.sg"));
				bufferedWriter.newLine();
				bufferedWriter.write(separator);
				bufferedWriter.newLine();
			}
			System.out.println("\tYour report Camp_" + c.getcampName() + "_Committee_Performance_Report.txt has been successfully generated!");
			bufferedWriter.close();

		} catch (IOException e) {
			System.err.println("\tError generating the report!" + e.getMessage());
		}
	}
	/**
	 * Generates a report on student enquiries for a specific camp. The report includes
	 * details of each enquiry such as sender, replier, status, and the enquiry text itself.
	 *
	 * @param c The camp for which the student enquiry report is to be generated.
	 * @throws IOException If there is an issue in writing the report to a file.
	 */
	@SuppressWarnings("unchecked")
	public static void generateStudentEnquiryReport(Camp c) throws IOException {

		String filePath = "/Users/mohorbanerjee/eclipse-workspace/OOPCAMSFinalcodes/Camp_" + c.getcampName() + "_Student_Enquiry_Report.txt";

		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write("Student Enquiry Report");
		bufferedWriter.newLine();
		bufferedWriter.newLine();
		String formatHeader = "%-15s %-40s %-30s %-30s %-40s %-20s";
		String separator = String.join("", Collections.nCopies(formatHeader.length()*1000, "-")); 
		bufferedWriter.write(String.format(formatHeader, "Enquiry Index", "Enquiry", "Sender", "Replier", "Reply", "Status"));
		bufferedWriter.newLine();
		bufferedWriter.write(separator);
		bufferedWriter.newLine();

		String formatData = "%-15d %-40s %-30s %-30s %-40s %-20s";

		ArrayList<Enquiries> enqlist = DataManager.getEnquiriesDatabase();
		enqlist = (ArrayList<Enquiries>) SearchFilters.filterMessagesByCampName(enqlist, c.getcampName());
		Collections.sort(enqlist);

		for (Enquiries e : enqlist) {
			int enquiryIndex = e.getMessageIndex(); 
			String enquiry = e.getS(); 
			String sender = e.getSender().getUserAccountId(); 
			String replier;
			if(e.getHelper()==null) {
				replier="";
			} else {
				replier = e.getHelper().getUserAccountId();
			} 
			String reply = e.getReply(); 
			String status = e.isStatus()?"Processed":"Being Processed"; 

			try {
				bufferedWriter.write(String.format(formatData, enquiryIndex, enquiry, sender, replier, reply, status));
				bufferedWriter.newLine();
				bufferedWriter.write(separator);
				bufferedWriter.newLine();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		System.out.println("\tYour report Camp_" + c.getcampName() + "_Student_Enquiry_Report.txt has been successfully generated!");
		bufferedWriter.close();
	}


}
