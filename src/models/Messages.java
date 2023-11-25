package models;
/**
 * This class represents a message with various attributes such as text,
 * associated camp, index, sender, and status. It also provides functionalities 
 * for message comparison and equality checks.
 */
public class Messages implements Comparable<Messages> {
	/**
	 * The text content of the message.
	 */
	private String s;
	/**
	 * The index of the message, used for identification and sorting.
	 */
	private int messageIndex;
	/**
	 * The camp associated with the message.
	 */
	private Camp c;
	/**
	 * The status of the message, typically representing read or unread state.
	 */
	private boolean status = false;
	/**
	 * The sender of the message.
	 */
	private User sender;
	/**
	 * Constructs a Messages object with a specified message index.
	 * 
	 * @param mi the message index
	 */
	public Messages(int mi) {
		this.messageIndex=mi;
	}
	/**
	 * Constructs a Messages object with specified text, camp, message index, and sender.
	 * 
	 * @param txt the text of the message
	 * @param cmp the camp associated with the message
	 * @param mi the message index
	 * @param from the sender of the message
	 */
	public Messages(String txt, Camp cmp, int mi, User from) {
		this.s = txt;

		this.c = cmp;

		this.messageIndex = mi;
		this.sender = from;
	}
	// Getters
	/**
	 * Gets the sender of the message.
	 * 
	 * @return the sender of the message
	 */
	public User getSender() {
		return this.sender;
	}
	/**
	 * Gets the text content of the message.
	 * 
	 * @return the text content of the message
	 */
	public String getS() {
		return this.s;
	}
	/**
	 * Gets the index of the message.
	 * 
	 * @return the index of the message
	 */
	public int getMessageIndex() {
		return this.messageIndex;
	}
	/**
	 * Gets the camp associated with the message.
	 * 
	 * @return the camp associated with the message
	 */
	public Camp getC() {
		return this.c;
	}
	/**
	 * Checks the status of the message.
	 * 
	 * @return true if the status is set, false otherwise
	 */
	public boolean isStatus() {
		return this.status;
	}
	// Setters
	/**
	 * Sets the sender of the message.
	 * 
	 * @param sender the new sender of the message
	 */
	public void setSender(User sender) {
		this.sender = sender;
	}
	/**
	 * Sets the text content of the message.
	 * 
	 * @param s the new text content of the message
	 */
	public void setS(String s) {
		this.s = s;
	}
	/**
	 * Sets the index of the message.
	 * 
	 * @param index the new index of the message
	 */
	public void setMessageIndex(int index) {
		this.messageIndex = index;
	}
	/**
	 * Sets the camp associated with the message.
	 * 
	 * @param c the new camp associated with the message
	 */
	public void setC(Camp c) {
		this.c = c;
	}

	/**
	 * Sets the status of the message.
	 * 
	 * @param status the new status of the message
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
	// Message management methods
	/**
	 * Compares this message with another message for equality.
	 * Two messages are considered equal if they have the same message index.
	 * 
	 * @param o the object to be compared for equality
	 * @return true if the specified object is a message with the same index; false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		Messages othersug = (Messages) o;
		if (othersug.getMessageIndex() == this.getMessageIndex()) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Compares this message with another message for order.
	 * Returns a negative integer, zero, or a positive integer as this message
	 * is less than, equal to, or greater than the specified message.
	 * 
	 * @param o the message to be compared
	 * @return a negative integer, zero, or a positive integer as this message
	 *         is less than, equal to, or greater than the specified message
	 */
	@Override
	public int compareTo(Messages o) {
		int compareindex = (o).getMessageIndex();
		int a;
		a = this.getMessageIndex() > compareindex ? 1 : -1;
		return a;
	}

	/**
	 * Returns a hash code value for the message.
	 * 
	 * @return a hash code value for this message
	 */
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + this.messageIndex;

		return result;
	}
}
