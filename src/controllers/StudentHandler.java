package controllers;

import java.util.Date;

import models.Camp;
import models.CampCommitteeMember;
import models.DataManager;
import models.Enquiries;
import models.Student;
/**
 * This class provides functionalities related to student interactions with camps.
 * It includes handling student registrations, withdrawals, and managing enquiries for camps.
 */
public class StudentHandler {
	/**
	 * Handles successful registration of a student for a camp.
	 *
	 * @param s The student who is registering.
	 * @param c The camp for which the student is registering.
	 * @return true if the registration is successful.
	 */
	public static boolean SuccessfulRegistrationHandler(Student s, Camp c) {
		s.addRegCamps(c);
		s.addBlockeddates(c.getdates());
		if (s instanceof CampCommitteeMember && ((Student) UserHandler.getCurrentUserInstance()).getCoordinator().equals("NA")) {
			CampCommitteeMember ccm = (CampCommitteeMember) (s);
			UserHandler.setCurrentUserInstance(ccm);
			ccm.setCoordinator(c.getcampName());
			DatabaseSearchManager.SearchStudentDatabase(ccm).setCoordinator(c.getcampName());
			DataManager.getCampComMemDatabase().add(ccm);
			c.addComMemb(ccm);
			return true;
		}

		c.addAttendee(s);
		return true;

	}

	/**
	 * Checks if a student has already registered for a specific camp.
	 *
	 * @param s The student to check.
	 * @param c The camp to check against.
	 * @return true if the student has already registered for the camp.
	 */
	public static boolean hasStudentRegisteredAlready(Student s, Camp c) {
		return s.getRegCamps().contains(c);
	}
	/**
	 * Checks for any date clashes with the student's existing registrations and a new camp.
	 *
	 * @param s The student whose schedule is to be checked.
	 * @param c The new camp to check against the student's schedule.
	 * @return true if there is a date clash.
	 */
	public static boolean checkDateClash(Student s, Camp c) {
		for (Date[] dates : s.getBlockeddates()) {
			if ((c.getdates()[0].getTime() < dates[0].getTime() && c.getdates()[1].getTime() < dates[0].getTime())
					|| (c.getdates()[0].getTime() > dates[1].getTime()
							&& c.getdates()[1].getTime() > dates[1].getTime())) {
				continue;
			} else {
				return true;
			}
		}
		return false;
	}
	/**
	 * Checks if a student has withdrawn from a camp previously.
	 *
	 * @param s The student to check.
	 * @param c The camp to check against.
	 * @return true if the student has previously withdrawn from the camp.
	 */
	public static boolean hasStudentWithdrawnBefore(Student s, Camp c) {
		return c.getWithdrawn().contains(s);

	}
	/**
	 * Handles successful withdrawal of a student from a camp.
	 *
	 * @param s The student who is withdrawing.
	 * @param c The camp from which the student is withdrawing.
	 * @return true if the withdrawal is successful.
	 */
	public static boolean SuccessfulWithdrawal(Student s, Camp c) {
		s.remRegCamps(c);
		c.remAttendee(s);
		return true;
	}
	/**
	 * Handles successful submission of an enquiry by a student to a camp.
	 *
	 * @param s   The enquiry being submitted.
	 * @param cm  The student submitting the enquiry.
	 * @param cmp The camp to which the enquiry is submitted.
	 * @return true if the submission is successful.
	 */
	public static boolean SuccessfullySubmitEnq(Enquiries s, Student cm, Camp cmp) {
		cmp.addEnq(s);
		cm.setEnqList(s);
		return true;
	}
	/**
	 * Handles successful deletion of an enquiry submitted by a student.
	 *
	 * @param s  The enquiry to be deleted.
	 * @param cm The student who submitted the enquiry.
	 * @return true if the deletion is successful.
	 */
	public static boolean SuccessfullyDeleteEnq(Enquiries s, Student cm) {
		DatabaseSearchManager.SearchCampsDatabase(s.getC()).remEnq(s);
		cm.remEnq(s);
		DataManager.getEnquiriesDatabase().remove(s);
		return true;
	}
	/**
	 * Handles successful editing of an enquiry by a student.
	 *
	 * @param olds The original enquiry to be edited.
	 * @param news The new enquiry details.
	 * @param cm   The student editing the enquiry.
	 * @return true if the editing is successful.
	 */
	public static boolean SuccessfullyEditEnq(Enquiries olds, Enquiries news, Student cm) {
		DatabaseSearchManager.SearchEnquiriesDatabase(olds).setS(news.getS());
		return true;
	}

}
