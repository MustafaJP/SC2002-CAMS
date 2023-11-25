package models;

import java.util.ArrayList;
/**
 * This class represents a mailbox for storing notifications.
 * It allows for managing and accessing a list of notifications associated with an owner.
 */
public class Mailbox {
	/**
	 * The list of notifications in the mailbox.
	 */
	private ArrayList<String> notifications = new ArrayList<String>();
	/**
	 * The owner of the mailbox.
	 */
	private String owner;
	/**
	 * The count of unread or new notifications in the mailbox.
	 */
	private int buffer = 0;
	/**
	 * Constructs a new Mailbox with a list of initial notifications and an owner.
	 * 
	 * @param msglist the list of initial notifications
	 * @param owner the owner of the mailbox
	 */
	public Mailbox(ArrayList<String> msglist, String owner) {
		this.notifications = msglist;
		this.buffer = this.notifications.size();
		this.owner = owner;
	}
	/**
	 * Constructs a new Mailbox with an owner and an empty list of notifications.
	 * 
	 * @param owner the owner of the mailbox
	 */
	public Mailbox(String owner) {
		this.owner=owner;
	}
	// Getters
	/**
	 * Gets the owner of the mailbox.
	 * 
	 * @return the owner of the mailbox
	 */
	public String getOwner() {

		return this.owner;
	}
	/**
	 * Gets the number of unread notifications in the mailbox.
	 * 
	 * @return the number of unread notifications
	 */
	public int getBuffer() {
		return this.buffer;
	}
	/**
	 * Gets the list of notifications in the mailbox.
	 * 
	 * @return the list of notifications
	 */
	public ArrayList<String> getNotifs() {
		return this.notifications;
	}
	// Setters

	/**
	 * Sets the number of unread notifications in the mailbox.
	 * 
	 * @param buffer the number of unread notifications
	 */
	public void setBuffer(int buffer) {
		this.buffer = buffer;
	}
	// Mailbox management methods
	/**
	 * Clears all notifications from the mailbox and resets the unread count.
	 */
	public void clearNotifications() {
		this.buffer = 0;
		this.notifications.clear();
	}

	/**
	 * Adds a new notification to the mailbox and increments the unread count.
	 * 
	 * @param newnotif the new notification to be added
	 */
	public void recieveNotifications(String newnotif) {
		this.notifications.add(newnotif);
		this.buffer++;
	}
	/**
	 * Returns a string representation of the mailbox, listing all notifications.
	 * 
	 * @return a string representation of the mailbox
	 */
	@Override
	public String toString() {
		String result = "\tUnseen Notifications: \n\n";
		for (String it : this.notifications) {
			result = result.concat(it + "\n" + "\n");
		}
		return result;
	}
	/**
	 * Compares this mailbox to another object for equality.
	 * 
	 * @param o the object to compare with
	 * @return true if the other object is a Mailbox with the same owner, false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		Mailbox otherbox = (Mailbox) o;
		if ((otherbox.getOwner()).equals(this.owner)) {
			return true;
		} else {
			return false;
		}
	}

}
