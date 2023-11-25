package view;
/**
 * The CampsViewer interface defines methods for viewing and searching camps based on various criteria.
 * This interface is intended to be implemented by classes that require functionality to display camp
 * information in different contexts.
 */
public interface CampsViewer {
	/**
	 * Displays a default view of all camps.
	 *
	 * @return true if there are camps to display, false otherwise.
	 */
	public boolean defaultCampsView();
	/**
	 * Provides functionality to search camps by their name.
	 * This method typically prompts the user to enter the name of a camp and displays the matching results.
	 */
	public void searchByCampName();
	/**
	 * Allows searching of camps based on specific details like location, date, etc.
	 * This method typically prompts the user to enter search criteria and displays camps that match these criteria.
	 */
	public void searchByCampDetails();
	/**
	 * Enables the searching of camps based on their duration.
	 * This method typically prompts the user to select or enter a camp duration and displays camps that match this duration.
	 */
	public void searchByCampDuration();
}
