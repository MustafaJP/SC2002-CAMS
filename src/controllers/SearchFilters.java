package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import models.Camp;
import models.Enquiries;
import models.FACULTIES;
import models.Messages;
import models.Student;
import models.User;

/**
 * Provides a comprehensive set of filters for different entities like Camps, Enquiries, etc based on various
 * criteria.
 */
public class SearchFilters {
	/**
	 * Filters camps by their name.
	 *
	 * @param camps The list of Camp objects to filter.
	 * @param name  The name to filter the Camps by.
	 * @return A list of Camps that match the specified name.
	 */
	public static ArrayList<Camp> filterCampsByCampName(ArrayList<Camp> camps, String name) {
		return camps.stream().filter(camp -> camp.getcampName().equalsIgnoreCase(name))
				.collect(Collectors.toCollection(ArrayList::new));
	}
	/**
	 * Filters messages by the name of the associated camp.
	 *
	 * @param msgs The list of Messages to filter.
	 * @param name The name of the camp to filter the Messages by.
	 * @return A list of Messages associated with the specified camp name.
	 */
	public static ArrayList<? extends Messages> filterMessagesByCampName(ArrayList<? extends Messages> msgs,
			String name) {
		return msgs.stream().filter(msg -> msg.getC().getcampName().equalsIgnoreCase(name))
				.collect(Collectors.toCollection(ArrayList::new));
	}
	/**
	 * Filters camps by a specified closing date.
	 *
	 * @param camps The list of Camp objects to filter.
	 * @param date  The closing date to filter the Camps by.
	 * @return A list of Camps that close after the specified date.
	 */
	public static ArrayList<Camp> filterBeforeClosingDate(ArrayList<Camp> camps, Date date) {
		return camps.stream().filter(camp -> camp.getregistrationClosingDate().after(date))
				.collect(Collectors.toCollection(ArrayList::new));
	}
	/**
	 * Filters camps by the faculty allowed.
	 *
	 * @param camps     The list of Camp objects to filter.
	 * @param userGroup The user group (faculty) to filter the Camps by.
	 * @return A list of Camps that are open for the specified user group.
	 */
	public static ArrayList<Camp> filterByUserGroup(ArrayList<Camp> camps, String userGroup) {
		return camps.stream()
				.filter(camp -> camp.getfacultyAllowed().equals(FACULTIES.valueOf(userGroup))
						|| camp.getfacultyAllowed().equals(FACULTIES.NTU))
				.collect(Collectors.toCollection(ArrayList::new));
	}
	/**
	 * Filters a list of Camp objects based on their availability in terms of total slots. This method selects camps
	 * that have at least one available slot.
	 *
	 * @param camps The list of Camp objects to filter.
	 * @return An ArrayList of Camps with available slots.
	 */
	public static ArrayList<Camp> filterByAvailability_TotalSlots(ArrayList<Camp> camps) {
		return camps.stream().filter(camp -> camp.getEmptySlots() != 0)
				.collect(Collectors.toCollection(ArrayList::new));
	}
	/**
	 * Filters a list of Camp objects to find camps that are completely empty. This method selects camps
	 * where the number of empty slots is equal to the total number of slots.
	 *
	 * @param camps The list of Camp objects to filter.
	 * @return An ArrayList of completely empty Camps.
	 */
	public static ArrayList<Camp> filterByEmptyCampsOnly(ArrayList<Camp> camps) {
		return camps.stream().filter(camp -> camp.getEmptySlots() == camp.getTotalSlots())
				.collect(Collectors.toCollection(ArrayList::new));
	}
	/**
	 * Filters a list of Camp objects based on the availability of committee slots. This method selects camps
	 * that have at least one available committee slot.
	 *
	 * @param camps The list of Camp objects to filter.
	 * @return An ArrayList of Camps with available committee slots.
	 */

	public static ArrayList<Camp> filterByAvailability_ComSlots(ArrayList<Camp> camps) {
		return camps.stream().filter(camp -> camp.getEmptyComSlots() != 0)
				.collect(Collectors.toCollection(ArrayList::new));
	}
	/**
	 * Filters camps based on their location.
	 *
	 * @param camps     The list of Camp objects to filter.
	 * @param location  The location to filter the Camps by.
	 * @return A list of Camps that match the specified location.
	 */
	public static ArrayList<Camp> filterByLocation(ArrayList<Camp> camps, String location) {
		return camps.stream().filter(camp -> camp.getlocation().equalsIgnoreCase(location))
				.collect(Collectors.toCollection(ArrayList::new));
	}
	/**
	 * Filters a list of Camp objects based on a specified start date. This method selects camps that start
	 * on the given date.
	 *
	 * @param camps     The list of Camp objects to filter.
	 * @param startDate The start date to filter the Camps by.
	 * @return An ArrayList of Camps starting on the specified date.
	 */
	public static ArrayList<Camp> filterStartingOn(ArrayList<Camp> camps, Date startDate) {
		return camps.stream().filter(camp -> camp.getdates()[0].equals(startDate))
				.collect(Collectors.toCollection(ArrayList::new));
	}
	/**
	 * Filters a list of Camp objects based on a specified end date. This method selects camps that end
	 * on the given date.
	 *
	 * @param camps   The list of Camp objects to filter.
	 * @param endDate The end date to filter the Camps by.
	 * @return An ArrayList of Camps ending on the specified date.
	 */
	public static ArrayList<Camp> filterEndingOn(ArrayList<Camp> camps, Date endDate) {
		return camps.stream().filter(camp -> camp.getdates()[1].equals(endDate))
				.collect(Collectors.toCollection(ArrayList::new));
	}
	/**
	 * Filters a list of Camp objects based on a specified duration category (SHORT, NORMAL, LONG). This method
	 * selects camps that match the duration criteria.
	 *
	 * @param camps    The list of Camp objects to filter.
	 * @param duration The duration category to filter the Camps by.
	 * @return An ArrayList of Camps that match the specified duration category.
	 */

	public static ArrayList<Camp> filterByDuration(ArrayList<Camp> camps, DURATION duration) {
		return camps.stream().filter(camp -> matchDuration(camp, duration))
				.collect(Collectors.toCollection(ArrayList::new));
	}
	/**
	 * Filters a list of Camp objects based on a specified number of days. This method selects camps
	 * whose duration matches the specified number of days.
	 *
	 * @param camps    The list of Camp objects to filter.
	 * @param noofdays The number of days to filter the Camps by.
	 * @return An ArrayList of Camps that last for the specified number of days.
	 */
	public static ArrayList<Camp> filterByDuration(ArrayList<Camp> camps, int noofdays) {
		return camps.stream().filter(camp -> {
			long days = calculateCampDays(camp);
			return days == noofdays;
		}).collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * Determines whether the duration of a given Camp object matches a specified duration category (SHORT, NORMAL, LONG).
	 * The duration is calculated based on the difference in days between the start and end dates of the camp.
	 *
	 * @param camp     The Camp object whose duration is to be checked.
	 * @param duration The specified duration category (SHORT, NORMAL, LONG).
	 * @return true if the camp's duration matches the specified category, false otherwise.
	 */

	private static boolean matchDuration(Camp camp, DURATION duration) {
		long days = calculateCampDays(camp);
		switch (duration) {
		case SHORT:
			return days < 5;
		case NORMAL:
			return days >= 5 && days <= 10;
		case LONG:
			return days > 10;
		default:
			return false;
		}
	}

	/**
	 * Calculates the number of days for a given Camp object. The calculation is based on the difference
	 * between the start and end dates of the camp.
	 *
	 * @param camp The Camp object for which the duration in days is to be calculated.
	 * @return The number of days from the start to the end of the camp.
	 */

	private static long calculateCampDays(Camp camp) {
		long differenceMillis = camp.getdates()[1].getTime() - camp.getdates()[0].getTime();
		return TimeUnit.MILLISECONDS.toDays(differenceMillis);
	}
	/**
	 * Filters a list of Camp objects based on the name of the staff in charge. This method selects camps
	 * where the staff in charge matches the specified name, based on the 'matches' parameter.
	 *
	 * @param camps          The list of Camp objects to filter.
	 * @param instructorName The name of the instructor to filter the Camps by.
	 * @param matches        A boolean indicating whether to match or not match the instructor name.
	 * @return An ArrayList of Camps that meet the staff in charge criteria.
	 */

	public static ArrayList<Camp> filterByStaff(ArrayList<Camp> camps, String instructorName, boolean matches) {

		return camps.stream()
				.filter(camp -> matches && camp.getstaffInCharge().getUserAccountId().equals(instructorName))
				.collect(Collectors.toCollection(ArrayList::new));
	}
	/**
	 * Filters a list of Camp objects based on their visibility status. This method selects camps that are
	 * marked as visible.
	 *
	 * @param camps The list of Camp objects to filter.
	 * @return An ArrayList of visible Camps.
	 */

	public static ArrayList<Camp> filterByVisibility(ArrayList<Camp> camps) {
		return camps.stream().filter(camp -> camp.getVisibility()).collect(Collectors.toCollection(ArrayList::new));
	}
	/**
	 * Filters a list of Camp objects to find camps where a specific student has not withdrawn. This method
	 * selects camps where the specified student is not in the withdrawn list.
	 *
	 * @param camps The list of Camp objects to filter.
	 * @param stu   The Student object to check for withdrawal.
	 * @return An ArrayList of Camps where the student has not withdrawn.
	 */

	public static ArrayList<Camp> filterByIfWithdrawn(ArrayList<Camp> camps, Student stu) {
		return camps.stream().filter(camp -> !(camp.getWithdrawn().contains(stu)))
				.collect(Collectors.toCollection(ArrayList::new));
	}
	/**
	 * Filters a list of Camp objects based on full occupancy. This method selects camps where there are
	 * no empty slots available.
	 *
	 * @param camps The list of Camp objects to filter.
	 * @return An ArrayList of Camps that are fully occupied.
	 */
	public static ArrayList<Camp> filterByNonAvailability_TotalSlots(ArrayList<Camp> camps) {
		return camps.stream().filter(camp -> camp.getEmptySlots() == 0)
				.collect(Collectors.toCollection(ArrayList::new));
	}
	/**
	 * Filters a list of Messages based on their status. This method selects messages that match the
	 * specified status (true or false).
	 *
	 * @param msgs   The list of Messages to filter.
	 * @param status The status to filter the Messages by.
	 * @return An ArrayList of Messages that match the specified status.
	 */
	public static ArrayList<? extends Messages> filterMessagesByStatus(ArrayList<? extends Messages> msgs,
			boolean status) {
		return msgs.stream().filter(msg -> msg.isStatus() == status).collect(Collectors.toCollection(ArrayList::new));
	}
	/**
	 * Filters a list of Enquiries based on a helper (User object). This method selects enquiries where the
	 * helper matches the specified User object.
	 *
	 * @param msgs The list of Enquiries to filter.
	 * @param t    The User object to filter the Enquiries by.
	 * @return An ArrayList of Enquiries assisted by the specified helper.
	 */
	public static ArrayList<Enquiries> filterEnquiriesByHelper(ArrayList<Enquiries> msgs, User t) {
		return msgs.stream().filter(msg -> msg.getHelper() != null && msg.getHelper() == t)
				.collect(Collectors.toCollection(ArrayList::new));
	}
	/**
	 * Filters a list of Messages based on the sender. This method selects messages sent by the specified User object.
	 *
	 * @param msgs The list of Messages to filter.
	 * @param u    The User object representing the sender to filter the Messages by.
	 * @return An ArrayList of Messages sent by the specified User.
	 */
	public static ArrayList<? extends Messages> filterMessagesBySender(ArrayList<? extends Messages> msgs, User u) {
		return msgs.stream().filter(msg -> msg.getSender().equals(u)).collect(Collectors.toCollection(ArrayList::new));
	}

}
