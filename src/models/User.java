package models;

import controllers.DatabaseSearchManager;
import controllers.PasswordManager;
import controllers.UserHandler;
/**
 * This class represents a generic user in the system.
 * It includes basic user information and functionalities like handling user accounts and passwords.
 */
public class User {
	/**
	 * The name of the user.
	 */
	private String userName;
	/**
	 * The unique account ID of the user.
	 */
	private String userAccountId;
	/**
	 * The password of the user, stored in hashed format.
	 */	
	private String password=PasswordManager.defaultpassword;
	/**
	 * The faculty to which the user belongs.
	 */
	private FACULTIES faculty;
	/**
	 * The mailbox associated with the user for receiving notifications.
	 */
	private Mailbox userInbox=new Mailbox(this.userAccountId);
	/**
	 * Constructs a User object with a specified user account ID.
	 * 
	 * @param i the user account ID
	 */
	public User(String i) {
		this.userAccountId=i;
	}
	/**
	 * Constructs a User object with a user account ID, name, hashed password, and faculty as a string.
	 * 
	 * @param id the user account ID
	 * @param nm the name of the user
	 * @param hashedpwd the hashed password of the user
	 * @param f the faculty of the user as a string
	 */
	public User(String id, String nm,String hashedpwd,String f){
		this.userName = nm;
		this.userAccountId = id;
		this.password = hashedpwd;
		this.faculty=FACULTIES.valueOf(f);
	}
	/**
	 * Constructs a User object with a user account ID, name, hashed password, and faculty as an enum.
	 * 
	 * @param id the user account ID
	 * @param nm the name of the user
	 * @param hashedpwd the hashed password of the user
	 * @param f the faculty of the user as an enum
	 */
	public User(String id, String nm,String hashedpwd,FACULTIES f){
		this.userName = nm;
		this.userAccountId = id;
		this.password = hashedpwd;
		this.faculty=f;
	}
	// Getters
	/**
	 * Retrieves the username of this user.
	 * 
	 * @return The current username.
	 */	
	public String getUserName() {
		return this.userName;
	}
	/**
	 * Retrieves the user account ID of this user.
	 * 
	 * @return The current user account ID.
	 */
	public String getUserAccountId(){
		return this.userAccountId;
	}

	/**
	 * Retrieves the password of this user.
	 * 
	 * @return The current password in hashed format.
	 */	
	public String getPassword() {
		return this.password;
	}
	/**
	 * Retrieves the faculty to which this user belongs.
	 * 
	 * @return The faculty enumeration of this user.
	 */
	public FACULTIES getFaculty() {
		return this.faculty;
	}
	/**
	 * Retrieves the mailbox of this user.
	 * 
	 * @return The mailbox associated with this user.
	 */
	public Mailbox getUserInbox() {
		return this.userInbox;
	}
	// Setters
	/**
	 * Sets or updates the username of this user.
	 * 
	 * @param name The new username to be set.
	 */	
	public void setUserName(String name) {
		this.userName=name;
	}
	/**
	 * Sets or updates the user account ID of this user.
	 * 
	 * @param uid The new user account ID to be set.
	 */
	public void setUserAccountId(String uid){
		this.userAccountId=uid;
	}
	/**
	 * Sets the user's password using a pre-hashed value.
	 * 
	 * @param newp The new hashed password to be set.
	 */	
	public void setPasswordFromHashed(String newp) {
		this.password=newp;
	}
	/**
	 * Sets the user's password from an unhashed value. The value is hashed before being set.
	 * 
	 * @param pw The unhashed password to be set after hashing.
	 */
	public void setPasswordFromUnhashed(String pw) {
		String pd=PasswordManager.hash(pw);
		this.password = pd;
		if(UserHandler.getCurrentUserInstance() instanceof Student) {
			DatabaseSearchManager.SearchStudentDatabase(this).setPasswordFromHashed(pd);
		}
		if(UserHandler.getCurrentUserInstance() instanceof Staff) {
			DatabaseSearchManager.SearchStaffDatabase(this).setPasswordFromHashed(pd);
		}
	}
	/**
	 * Sets or updates the faculty of this user.
	 * 
	 * @param faculty The new faculty enumeration to be set.
	 */
	public void setFaculty(FACULTIES faculty) {
		this.faculty = faculty;
	}
	/**
	 * Sets or updates the mailbox of this user.
	 * 
	 * @param userInbox The new mailbox to be associated with this user.
	 */
	public void setUserInbox(Mailbox userInbox) {
		this.userInbox = userInbox;
	}
	// User management methods
	/**
	 * Compares this User object with another object for equality.
	 * Equality is based on the user account ID.
	 * 
	 * @param o the object to be compared with
	 * @return true if the specified object is a User with the same account ID; false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		User newuser=(User)o;
		if(newuser.userAccountId.equalsIgnoreCase(this.userAccountId)) {
			return true;
		} else {
			return false;
		}
	}
}

