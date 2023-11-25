package controllers;

import models.Messages;
import models.User;
/**
 * The MessageManager interface defines methods for managing messages. 
 * This includes submitting, editing, and deleting messages, applicable to different types of messages 
 * handled in the system (enquiries and suggestions). Classes implementing this interface 
 * will provide concrete implementations for these methods.
 */
public interface MessageManager {
	/**
	 * Submits a new message to the system.
	 *
	 * @param usr The user who is submitting the message.
	 */
	public void submit(User usr);
	/**
	 * Edits an existing message in the system.
	 *
	 * @param oldmsg The original message to be edited.
	 * @param usr    The user who is editing the message.
	 */
	public void edit(Messages oldmsg, User usr);
	/**
	 * Deletes an existing message from the system.
	 *
	 * @param oldmsg The message to be deleted.
	 * @param usr    The user who is deleting the message.
	 */
	public void delete(Messages oldmsg, User usr);
}
