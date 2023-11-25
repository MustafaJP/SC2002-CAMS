package view;
/**
 * This class is a custom exception used in the context of viewing lists
 * within the application. It is thrown when an attempt is made to view a list 
 * that doesn't contain any items
 */
@SuppressWarnings("serial")
public class ListDoesntExistYetException extends Exception {
	/**
	 * Constructs a new ListDoesntExistYetException with the specified detail message.
	 *
	 * @param message The detail message (which is saved for later retrieval by the Throwable.getMessage() method).
	 */
	public ListDoesntExistYetException( String message ) {
		super( message );
	}

}
