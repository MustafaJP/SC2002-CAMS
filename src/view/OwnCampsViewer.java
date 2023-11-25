package view;
/**
 * The OwnCampsViewer interface defines a method for viewing camps associated with a specific user.
 * This interface is intended to be implemented by classes that require functionality to display
 * camps owned or managed by a user.
 */
public interface OwnCampsViewer {
	/**
	 * Displays the camps associated with the current user.
	 *
	 * @return true if there are camps to display, false otherwise.
	 */
	public boolean viewYourCamps();
}
