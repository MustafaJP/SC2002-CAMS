package models;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import controllers.DatabaseSearchManager;
import controllers.ExcelFileManager;
/**
 * This class handles the loading, updating, and management of various databases
 * such as Camps, Staff, Students, Camp Committee Members, Enquiries, Suggestions, and Mailboxes.
 */
public class DataManager {
	/**
	 * Database for storing Camp objects.
	 */
	private static ArrayList<Camp> CampsDatabase = new ArrayList<Camp>();
	/**
	 * Database for storing Staff objects.
	 */
	private static ArrayList<Staff> StaffDatabase = new ArrayList<Staff>();
	/**
	 * Database for storing Student objects.
	 */
	private static ArrayList<Student> StudentDatabase = new ArrayList<Student>();
	/**
	 * Database for storing CampCommitteeMember objects.
	 */
	private static ArrayList<CampCommitteeMember> CampComMemDatabase = new ArrayList<CampCommitteeMember>();
	/**
	 * Database for storing Enquiries objects.
	 */
	private static ArrayList<Enquiries> EnquiriesDatabase = new ArrayList<Enquiries>();
	/**
	 * Database for storing Suggestions objects.
	 */
	private static ArrayList<Suggestions> SuggestionsDatabase = new ArrayList<Suggestions>();
	/**
	 * Database for storing Mailbox objects.
	 */
	private static ArrayList<Mailbox> MailboxDatabase = new ArrayList<Mailbox>();
	// File paths for Excel databases
	private static final String staffFilePath = "/Users/mohorbanerjee/eclipse-workspace/OOPCAMSFinalcodes/OOPDatabases/Staff_List.xlsx";
	private static final String studentFilePath = "/Users/mohorbanerjee/eclipse-workspace/OOPCAMSFinalcodes/OOPDatabases/Students_List.xlsx";
	private static final String coordinatorFilePath = "/Users/mohorbanerjee/eclipse-workspace/OOPCAMSFinalcodes/OOPDatabases/CampCoordinators_List.xlsx";
	private static final String mailboxesFilePath = "/Users/mohorbanerjee/eclipse-workspace/OOPCAMSFinalcodes/OOPDatabases/Mailboxes.xlsx";
	private static final String suggestionsFilePath = "/Users/mohorbanerjee/eclipse-workspace/OOPCAMSFinalcodes/OOPDatabases/Suggestions.xlsx";
	private static final String enquiriesFilePath = "/Users/mohorbanerjee/eclipse-workspace/OOPCAMSFinalcodes/OOPDatabases/Enquiries.xlsx";
	private static final String campsFilePath = "/Users/mohorbanerjee/eclipse-workspace/OOPCAMSFinalcodes/OOPDatabases/Camps_List.xlsx";
	// Getters
	/**
	 * Returns the database of camps.
	 *
	 * @return ArrayList of Camp objects.
	 */
	public static ArrayList<Camp> getCampsDatabase() {
		return CampsDatabase;
	}
	/**
	 * Returns the database of staff members.
	 *
	 * @return ArrayList of Staff objects.
	 */
	public static ArrayList<Staff> getStaffDatabase() {
		return StaffDatabase;
	}
	/**
	 * Returns the database of students.
	 *
	 * @return ArrayList of Student objects.
	 */
	public static ArrayList<Student> getStudentDatabase() {
		return StudentDatabase;
	}
	/**
	 * Returns the database of camp committee members.
	 *
	 * @return ArrayList of CampCommitteeMember objects.
	 */
	public static ArrayList<CampCommitteeMember> getCampComMemDatabase() {
		return CampComMemDatabase;
	}
	/**
	 * Returns the database of enquiries.
	 *
	 * @return ArrayList of Enquiries objects.
	 */
	public static ArrayList<Enquiries> getEnquiriesDatabase() {
		return EnquiriesDatabase;
	}
	/**
	 * Returns the database of suggestions.
	 *
	 * @return ArrayList of Suggestions objects.
	 */
	public static ArrayList<Suggestions> getSuggestionsDatabase() {
		return SuggestionsDatabase;
	}
	/**
	 * Returns the database of mailboxes.
	 *
	 * @return ArrayList of Mailbox objects.
	 */
	public static ArrayList<Mailbox> getMailboxDatabase() {
		return MailboxDatabase;
	}
	// Setters
	/**
	 * Sets the camps database.
	 * @param campsDatabase ArrayList of Camp objects to set as the current database.
	 */
	public static void setCampsDatabase(ArrayList<Camp> campsDatabase) {
		CampsDatabase = campsDatabase;
	}
	/**
	 * Sets the staff database.
	 * @param staffDatabase ArrayList of Staff objects to set as the current database.
	 */
	public static void setStaffDatabase(ArrayList<Staff> staffDatabase) {
		StaffDatabase = staffDatabase;
	}
	/**
	 * Sets the student database.
	 * @param studentDatabase ArrayList of Student objects to set as the current database.
	 */
	public static void setStudentDatabase(ArrayList<Student> studentDatabase) {
		StudentDatabase = studentDatabase;
	}
	/**
	 * Sets the camp committee member database.
	 * @param campComMemDatabase ArrayList of CampCommitteeMember objects to set as the current database.
	 */
	public static void setCampComMemDatabase(ArrayList<CampCommitteeMember> campComMemDatabase) {
		CampComMemDatabase = campComMemDatabase;
	}
	/**
	 * Sets the enquiries database.
	 * @param enquiriesDatabse ArrayList of Enquiries objects to set as the current database.
	 */
	public static void setEnquiriesDatabase(ArrayList<Enquiries> enquiriesDatabse) {
		EnquiriesDatabase = enquiriesDatabse;
	}

	/**
	 * Sets the suggestions database.
	 * @param suggestionsDatabase ArrayList of Suggestions objects to set as the current database.
	 */
	public static void setSuggestionsDatabase(ArrayList<Suggestions> suggestionsDatabase) {
		SuggestionsDatabase = suggestionsDatabase;
	}
	/**
	 * Sets the mailbox database.
	 * @param mailboxDatabase ArrayList of Mailbox objects to set as the current database.
	 */
	public static void setMailboxDatabase(ArrayList<Mailbox> mailboxDatabase) {
		MailboxDatabase = mailboxDatabase;
	}
	// Data Management Methods
	/**
	 * Initializes the staff database from an Excel file.
	 * @throws IOException if there is an issue reading the Excel file.
	 */
	public static void initializeStaffDatabase() {
		try {
			ArrayList<ArrayList<Object>> details = ExcelFileManager.readExcel(staffFilePath);
			for (ArrayList<Object> a : details) {
				ArrayList<Camp> camplist = new ArrayList<Camp>();
				if (!((String) a.get(4)).equalsIgnoreCase("null")) {
					String[] temp = ((String) a.get(4)).split(",");
					for (String str : temp) {
						camplist.add(new Camp(str));

					}
				}

				Staff s = new Staff(((String) (a.get(1))).substring(0, ((String) (a.get(1))).length() - 11),
						(String) a.get(0), (String) a.get(3), (String) a.get(2), camplist);
				StaffDatabase.add(s);

			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	/**
	 * Initializes the camp database from an Excel file.
	 * @throws IOException if there is an issue reading the Excel file.
	 */
	public static void initializeCampsDatabase() {
		try {
			ArrayList<ArrayList<Object>> details = ExcelFileManager.readExcel(campsFilePath);
			for (ArrayList<Object> a : details) {

				Camp c = new Camp((String) a.get(0));
				c.setStaff(new Staff((String) a.get(1)));
				c.setDesc((String) a.get(2));
				c.setPlace((String) a.get(3));
				c.setFaculty(FACULTIES.valueOf((String) a.get(4)));

				c.setTotalSlots((int) Math.round((double) a.get(5)));
				c.setComSlots((int) Math.round((double) a.get(6)));
				Date fd,ed,rd;
				if(a.get(7) instanceof String) {
					fd = new SimpleDateFormat("dd/MM/yyyy").parse((String) a.get(7));
				} else {
					fd=(Date)a.get(7);
				}
				if(a.get(8) instanceof String) {
					ed = new SimpleDateFormat("dd/MM/yyyy").parse((String) a.get(8));
				} else {
					ed=(Date)a.get(8);
				}
				if(a.get(9) instanceof String) {
					rd = new SimpleDateFormat("dd/MM/yyyy").parse((String) a.get(9));
				} else {
					rd=(Date)a.get(9);
				}

				c.setStartDate(fd);
				c.setEndDate(ed);
				c.setRegDeadline(rd);
				if (!((String) a.get(10)).equalsIgnoreCase("null")) {
					String[] temp = ((String) a.get(10)).split(",");
					for (String str : temp) {
						c.addAttendeeNormal(new Student(str));
					}
				}
				if (!((String) a.get(11)).equalsIgnoreCase("null")) {
					String[] temp2 = ((String) a.get(11)).split(",");
					for (String str : temp2) {
						c.addWithdrawn(new Student(str));
					}
				}
				if (!((String) a.get(12)).equalsIgnoreCase("null")) {
					String[] temp3 = ((String) a.get(12)).split(",");
					for (String str : temp3) {
						c.addComMembNormal(new CampCommitteeMember(str));
					}
				}
				c.setEmptySlots((int) Math.round((double) a.get(13)));
				c.setEmptyCommitteeSlots((int) Math.round((double) a.get(14)));
				boolean setb = ((String) a.get(15)).equalsIgnoreCase("ON") ? true : false;
				c.setVis(setb);
				if (!((String) a.get(16)).equalsIgnoreCase("null")) {
					String[] temp4 = ((String) a.get(16)).split(",");
					for (String str : temp4) {
						c.addSuggestion(new Suggestions(Integer.parseInt(str)));
					}
				}
				if (!((String) a.get(17)).equalsIgnoreCase("null")) {
					String[] temp5 = ((String) a.get(17)).split(",");
					for (String str : temp5) {
						c.addEnq(new Enquiries(Integer.parseInt(str)));
					}
				}
				CampsDatabase.add(c);
			}
		} catch (IOException | ParseException e) {

			e.printStackTrace();
		}
	}
	/**
	 * Initializes the student database from an Excel file.
	 * @throws IOException if there is an issue reading the Excel file.
	 */
	public static void initializeStudentDatabase() {
		try {
			ArrayList<ArrayList<Object>> details = ExcelFileManager.readExcel(studentFilePath);
			for (ArrayList<Object> a : details) {
				Student s = new Student(((String) (a.get(1))).substring(0, ((String) (a.get(1))).length() - 14));
				s.setUserName((String) a.get(0));
				s.setPasswordFromHashed((String) a.get(3));
				s.setFaculty(FACULTIES.valueOf((String) a.get(2)));
				s.setCoordinator((String) a.get(5));
				if (!((String) a.get(4)).equalsIgnoreCase("null")) {
					String[] temp = ((String) a.get(4)).split(",");
					for (String cmp : temp) {
						s.addRegCamps(new Camp(cmp));
					}
				}

				if (!((String) a.get(6)).equalsIgnoreCase("null")) {

					String[] temp = ((String) a.get(6)).split(",");
					for (String cmp : temp) {
						String[] temp2 = cmp.split("-");
						Date fd;
						try {
							fd = new SimpleDateFormat("dd/MM/yyyy").parse(temp2[0]);
						} catch (ParseException e) {

							e.printStackTrace();
							fd = null;
						}
						Date ed;
						try {
							ed = new SimpleDateFormat("dd/MM/yyyy").parse(temp2[1]);
						} catch (ParseException e) {

							e.printStackTrace();
							ed = null;
						}
						Date[] datearray = { fd, ed };
						s.getBlockeddates().add(datearray);
					}
				}
				if (!((String) a.get(7)).equalsIgnoreCase("null")) {
					String[] temp3 = ((String) a.get(7)).split(",");
					for (String cmp : temp3) {
						String[] temp4 = cmp.split("-");
						int index = Integer.parseInt(temp4[0]);
						Enquiries e = new Enquiries(index);
						boolean acc = temp4[1].equalsIgnoreCase("Y") ? true : false;
						s.getEnqList().put(e, acc);
					}
				}
				StudentDatabase.add(s);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	/**
	 * Initializes the camp committee members database from an Excel file.
	 * @throws IOException if there is an issue reading the Excel file.
	 */
	public static void initializeCoordinatorDatabase() {
		try {
			ArrayList<ArrayList<Object>> details = ExcelFileManager.readExcel(coordinatorFilePath);
			for (ArrayList<Object> a : details) {
				CampCommitteeMember s = new CampCommitteeMember((String) a.get(0));
				s.setUserName(DatabaseSearchManager.SearchStudentDatabase(s).getUserName());
				s.setCamp(new Camp((String) a.get(1)));
				s.setFaculty(DatabaseSearchManager.SearchStudentDatabase(s).getFaculty());
				s.setCoordinator(DatabaseSearchManager.SearchStudentDatabase(s).getCoordinator());
				s.setBlockeddates(DatabaseSearchManager.SearchStudentDatabase(s).getBlockeddates());
				s.getPointsHolder().setPt((int) Math.round((double) a.get(2)));
				s.setEnqList(DatabaseSearchManager.SearchStudentDatabase(s).getEnqList());
				s.setRegList(DatabaseSearchManager.SearchStudentDatabase(s).getRegCamps());
				if (!((String) a.get(3)).equalsIgnoreCase("null")) {
					String[] temp3 = ((String) a.get(3)).split(",");
					for (String cmp : temp3) {
						String[] temp4 = cmp.split("-");
						int index = Integer.parseInt(temp4[0]);

						Suggestions sg = new Suggestions(index);
						boolean acc = temp4[1].equalsIgnoreCase("Y") ? true : false;
						s.getSuggList().put(sg, acc);

					}
				}
				CampComMemDatabase.add(s);
			}

		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	/**
	 * Initializes the suggestions database from an Excel file.
	 * @throws IOException if there is an issue reading the Excel file.
	 */
	public static void initializeSuggDatabase() {
		try {
			ArrayList<ArrayList<Object>> details = ExcelFileManager.readExcel(suggestionsFilePath);
			if(details.isEmpty()) {
				Suggestions.setTotalSuggestions(0);
			} else {
				Suggestions.setTotalSuggestions((int) Math.round((double) (details.get(details.size()-1)).get(0)));
			};
			for (ArrayList<Object> a : details) {
				Suggestions s = new Suggestions((int) Math.round((double) a.get(0)));
				s.setS((String) a.get(1));
				s.setSender(new User((String) a.get(2)));
				boolean r = (((String) a.get(4)).equalsIgnoreCase("Yes")) ? true : false;
				s.setStatus(r);
				s.setC(new Camp((String) a.get(3)));
				SuggestionsDatabase.add(s);
			}

		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	/**
	 * Initializes the enquiries database from an Excel file.
	 * @throws IOException if there is an issue reading the Excel file.
	 */
	public static void initializeEnqDatabase() {
		try {
			ArrayList<ArrayList<Object>> details = ExcelFileManager.readExcel(enquiriesFilePath);
			if(details.isEmpty()) {
				Enquiries.setTotalEnquiries(0);
			} else {
				Enquiries.setTotalEnquiries((int) Math.round((double) (details.get(details.size()-1)).get(0)));
			}
			for (ArrayList<Object> a : details) {
				Enquiries s = new Enquiries((int) Math.round((double) a.get(0)));
				s.setS((String) a.get(1));
				s.setSender(new User((String) a.get(3)));
				boolean r = (((String) a.get(6)).equalsIgnoreCase("Yes")) ? true : false;
				s.setStatus(r);
				if (!((String) a.get(5)).equalsIgnoreCase("null")) {
					s.setReply((String) a.get(5));
				}
				if (!((String) a.get(4)).equalsIgnoreCase("null")) {
					s.setHelper(new User((String) a.get(4)));
				}
				s.setC(new Camp((String) a.get(2)));
				EnquiriesDatabase.add(s);
			}

		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	/**
	 * Initializes the mailboxes database from an Excel file.
	 * @throws IOException if there is an issue reading the Excel file.
	 */
	public static void initializeMailboxes() {
		try {
			ArrayList<ArrayList<Object>> details = ExcelFileManager.readExcel(mailboxesFilePath);
			for (ArrayList<Object> a : details) {
				ArrayList<String> msgs = new ArrayList<String>();

				String[] temp3 = ((String) a.get(1)).split(",");
				for (String h : temp3) {
					msgs.add(h);
				}


				Mailbox m = new Mailbox(msgs, (String) a.get(0));
				MailboxDatabase.add(m);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	/**
	 * Loads all necessary data for the application from various Excel files.
	 */
	public static void dataLoader() {

		initializeStaffDatabase();
		initializeCampsDatabase();
		initializeStudentDatabase();
		initializeCoordinatorDatabase();
		initializeSuggDatabase();
		initializeEnqDatabase();
		initializeMailboxes();
	}
	/**
	 * Updates the camps database with the latest information.
	 */
	public static void updateCampsDatabase() {
		ArrayList<ArrayList<Object>> details = new ArrayList<ArrayList<Object>>();
		for (Camp c : CampsDatabase) {
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(c.getcampName());
			temp.add(c.getstaffInCharge().getUserAccountId());
			temp.add(c.getdescription());
			temp.add(c.getlocation());
			temp.add(c.getfacultyAllowed().toString());
			temp.add(c.getTotalSlots());
			temp.add(c.getcampCommitteeSlots());
			temp.add(c.getdates()[0]);
			temp.add(c.getdates()[1]);
			temp.add(c.getregistrationClosingDate());
			if (c.getStudent().size() != 0) {
				String stemp = "";
				for (Student s : c.getStudent()) {
					stemp = stemp.concat(s.getUserAccountId() + ",");
				}
				temp.add(stemp);
			} else {
				temp.add("null");
			}
			if (c.getWithdrawn().size() != 0) {
				String stemp = "";
				for (Student s : c.getWithdrawn()) {
					stemp = stemp.concat(s.getUserAccountId() + ",");
				}
				temp.add(stemp);
			} else {
				temp.add("null");
			}
			if (c.getcampCommittee().size() != 0) {
				String stemp = "";
				for (CampCommitteeMember s : c.getcampCommittee()) {
					stemp = stemp.concat(s.getUserAccountId() + ",");
				}
				temp.add(stemp);
			} else {
				temp.add("null");
			}
			temp.add(c.getEmptySlots());
			temp.add(c.getEmptyComSlots());
			String j = (c.getVisibility()) ? "ON" : "OFF";
			temp.add(j);
			if (c.getSuggList().size() != 0) {
				String stemp = "";
				for (Suggestions s : c.getSuggList()) {
					stemp = stemp.concat(Integer.toString(s.getMessageIndex()) + ",");
				}
				temp.add(stemp);
			} else {
				temp.add("null");
			}
			if (c.getEnqList().size() != 0) {
				String stemp = "";
				for (Enquiries s : c.getEnqList()) {
					stemp = stemp.concat(Integer.toString(s.getMessageIndex()) + ",");
				}
				temp.add(stemp);
			} else {
				temp.add("null");
			}
			details.add(temp);
		}
		ExcelFileManager.writeExcel(details, campsFilePath);

	}
	/**
	 * Updates the staff database with the latest information.
	 */
	public static void updateStaffDatabase() {
		ArrayList<ArrayList<Object>> details = new ArrayList<ArrayList<Object>>();
		for (Staff st : StaffDatabase) {
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(st.getPassword());

			if (st.getCampsStarted().size() != 0) {
				String stemp = "";
				for (Camp s : st.getCampsStarted()) {
					stemp = stemp.concat(s.getcampName() + ",");
				}
				temp.add(stemp);
			} else {
				temp.add("null");
			}
			details.add(temp);
		}
		ArrayList<Integer> cols = new ArrayList<Integer>();
		cols.add(3);
		cols.add(4);
		ExcelFileManager.writeExcel(details, staffFilePath, cols);
	}
	/**
	 * Updates the students database with the latest information.
	 */
	public static void updateStudentDatabase() {
		ArrayList<ArrayList<Object>> details = new ArrayList<ArrayList<Object>>();
		for (Student st : StudentDatabase) {
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(st.getPassword());

			if (st.getRegCamps().size() != 0) {
				String stemp = "";
				for (Camp s : st.getRegCamps()) {
					stemp = stemp.concat(s.getcampName() + ",");
				}
				temp.add(stemp);
			} else {
				temp.add("null");
			}
			temp.add(st.getCoordinator());
			String dates = "";
			if (st.getBlockeddates().isEmpty()) {
				dates = "null";
			} else {
				for (Date[] d : st.getBlockeddates()) {
					dates = dates + ExcelFileManager.dateToString(d[0]) + "-" + ExcelFileManager.dateToString(d[1])
					+ ",";
				}
			}
			temp.add(dates);
			if (!st.getEnqList().isEmpty()) {
				String stemp = "";
				for (Enquiries e : st.getEnqList().keySet()) {
					String holder = st.getEnqList().get(e) ? "Y" : "N";
					stemp = stemp + Integer.toString(e.getMessageIndex()) + "-" + holder + ",";

				}
				temp.add(stemp);
			} else {
				temp.add("null");
			}
			details.add(temp);
		}
		ArrayList<Integer> cols = new ArrayList<Integer>();
		cols.add(3);
		cols.add(4);
		cols.add(5);
		cols.add(6);
		cols.add(7);

		ExcelFileManager.writeExcel(details, studentFilePath, cols);
	}
	/**
	 * Updates the camp committee members database with the latest information.
	 */
	public static void updateCoordinatorDatabase() {
		ArrayList<ArrayList<Object>> details = new ArrayList<ArrayList<Object>>();
		for (CampCommitteeMember st : CampComMemDatabase) {
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(st.getUserAccountId());
			temp.add(st.getCamp().getcampName());
			temp.add(st.getPointsHolder().getPt());
			if (!st.getSuggList().isEmpty()) {
				String stemp = "";
				for (Suggestions e : st.getSuggList().keySet()) {
					String holder = st.getSuggList().get(e) ? "Y" : "N";
					stemp = stemp + Integer.toString(e.getMessageIndex()) + "-" + holder + ",";

				}
				temp.add(stemp);
			} else {
				temp.add("null");
			}
			details.add(temp);

		}
		ExcelFileManager.writeExcel(details, coordinatorFilePath);
	}
	/**
	 * Updates the enquiries database with the latest information.
	 */
	public static void updateEnquiriesDatabase() {
		ArrayList<ArrayList<Object>> details = new ArrayList<ArrayList<Object>>();
		for (Enquiries st : EnquiriesDatabase) {
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(st.getMessageIndex());
			temp.add(st.getS());
			temp.add(st.getC().getcampName());
			temp.add(st.getSender().getUserAccountId());
			if (st.getHelper() != null) {
				temp.add(st.getHelper().getUserAccountId());
			} else {
				temp.add("null");
			}
			temp.add(st.getReply());
			String holder = st.isStatus() ? "Yes" : "No";
			temp.add(holder);
			details.add(temp);

		}
		ExcelFileManager.writeExcel(details, enquiriesFilePath);
	}
	/**
	 * Updates the suggestions database with the latest information.
	 */
	public static void updateSuggDatabase() {
		ArrayList<ArrayList<Object>> details = new ArrayList<ArrayList<Object>>();
		for (Suggestions st : SuggestionsDatabase) {
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(st.getMessageIndex());
			temp.add(st.getS());
			temp.add(st.getSender().getUserAccountId());
			temp.add(st.getC().getcampName());

			String holder = st.isStatus() ? "Yes" : "No";
			temp.add(holder);
			details.add(temp);

		}
		ExcelFileManager.writeExcel(details, suggestionsFilePath);
	}
	/**
	 * Updates the mailboxes database with the latest information.
	 */
	public static void updateMailDatabase() {
		ArrayList<ArrayList<Object>> details = new ArrayList<ArrayList<Object>>();
		for (Mailbox st : MailboxDatabase) {
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(st.getOwner());
			String stemp = "";
			for (String s : st.getNotifs()) {
				stemp = stemp + s + ",";
			}
			temp.add(stemp);
			details.add(temp);
		}

		ExcelFileManager.writeExcel(details, mailboxesFilePath);

	}
	/**
	 * Updates all databases with the latest information.
	 */
	public static void dataUpdation() {
		updateCampsDatabase();
		updateCoordinatorDatabase();
		updateStudentDatabase();
		updateStaffDatabase();
		updateEnquiriesDatabase();
		updateSuggDatabase();
		updateMailDatabase();

	}
}
