package controllers;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import models.Camp;
import models.CampCommitteeMember;
import models.DataManager;
import models.Enquiries;
import models.Mailbox;
import models.Messages;
import models.Student;
import models.User;
import view.StudentView;
/**
 * This class manages the functionalities related to enquiries in the system. 
 * It allows for submitting, editing, deleting, and replying to enquiries. It implements
 * the MessageManager interface, providing specific implementations for handling enquiries.
 */
public class EnquiryManager implements MessageManager{
	/**
	 * Allows a user to submit an enquiry. This method guides the user through selecting a camp
	 * and submitting an enquiry to it. It handles input validation and updates the enquiries database.
	 *
	 * @param usr The user who is submitting the enquiry.
	 */
	@SuppressWarnings("resource")
	@Override
	public void submit(User usr) {
		Scanner s=new Scanner(System.in);
		System.out.println("\tThese are the camps that are currently open to your faculty: ");
		StudentView sv=new StudentView();
		if(!sv.defaultCampsView()) {
			System.out.println("\tThere are currently no camps open to your faculty! You cannot send enquiries to any camp yet");

			return;
		}

		Student stu=(Student)usr;
		String cmpp;
		boolean campExists;

		do {
			System.out.println("\tPlease enter name of camp to which you wish to submit your enquiry: ");
			System.out.print("\t");
			cmpp = s.nextLine();
			campExists = DataManager.getCampsDatabase().contains(new Camp(cmpp));
			if (!campExists) {
				System.out.println("\tInvalid input. No such camp found. Please enter a valid camp name.");
			}
		} while (!campExists);
		if(UserHandler.getCurrentUserInstance() instanceof CampCommitteeMember && cmpp.equals(((CampCommitteeMember)UserHandler.getCurrentUserInstance()).getCamp().getcampName())){
			System.out.println("\tYou cannot send enquiries to this camp as you are a part of its committee");

			return;
		}
		System.out.println("\tPlease enter your enquiry: ");
		String eqs;
		do {
			System.out.print("\t");
			eqs = s.nextLine();
			if (eqs.trim().isEmpty()) {
				System.out.println("\tEnquiry cannot be empty. Please enter your enquiry.");
			}

		} while (eqs.trim().isEmpty());

		Enquiries newe=new Enquiries(eqs,stu,DatabaseSearchManager.SearchCampsDatabase(cmpp));
		DataManager.getEnquiriesDatabase().add(newe);
		if(StudentHandler.SuccessfullySubmitEnq(newe, stu,newe.getC())) {
			System.out.println("\tYour enquiry has been successfully submitted! Your unique enquiry ID is #"+newe.getMessageIndex()+ ". It is currently being processed- you will be notified when the staff/committee replies to it!");
		}

	}
	/**
	 * Allows a user to edit an existing enquiry. The method checks if the enquiry exists and
	 * if it is editable (not yet processed). It then allows the user to input a new enquiry text.
	 *
	 * @param oldmsg The original enquiry message to be edited.
	 * @param usr The user who is editing the enquiry.
	 */
	@SuppressWarnings("resource")
	@Override
	public void edit(Messages oldmsg, User usr) {
		Enquiries oldenq=(Enquiries) oldmsg;
		Student stu=(Student)usr;
		if(!stu.getEnqList().containsKey(oldenq)) {
			System.out.println("\tYou have not submitted any such enquiry yet!");
			return;
		}
		if(stu.getEnqList().get(oldenq)) {
			System.out.println("\tThis enquiry has already been processed! You can no longer edit it! ");
			return;
		}
		Scanner s=new Scanner(System.in);

		String neweq;
		System.out.println("\tPlease enter your new enquiry: ");
		do {
			System.out.print("\t");
			neweq = s.nextLine();
			if (neweq.trim().isEmpty()) {
				System.out.println("\tEnquiry cannot be empty. Please enter your enquiry.");
			}

		} while (neweq.trim().isEmpty());
		Enquiries newen=new Enquiries(neweq,oldenq.getMessageIndex(),stu,oldenq.getC());
		if(StudentHandler.SuccessfullyEditEnq(oldenq, newen, stu)) {
			System.out.println("\tYour enquiry with ID #"+newen.getMessageIndex()+" has been successfully edited! ");
			System.out.println("\tYour new enquiry is as follows: ");
			System.out.println("\t"+newen.getS());}


	}

	/**
	 * Allows a user to delete an existing enquiry. The method checks if the enquiry exists and
	 * if it is deletable (not yet processed). It then removes the enquiry from the database.
	 *
	 * @param oldmsg The enquiry message to be deleted.
	 * @param usr The user who is deleting the enquiry.
	 */
	@Override
	public void delete(Messages oldmsg, User usr) {
		Enquiries oldenq=(Enquiries) oldmsg;
		Student stu=(Student)usr;
		if(!stu.getEnqList().containsKey(oldenq)) {
			System.out.println("\tYou have not submitted any such enquiry yet!");
			return;
		}
		if(stu.getEnqList().get(oldenq)) {
			System.out.println("\tThis enquiry has already been replied to! You can no longer delete it! ");
			return;
		}
		if(StudentHandler.SuccessfullyDeleteEnq(oldenq, stu)) {
			System.out.println("\tYour enquiry with ID #"+oldenq.getMessageIndex()+" has been successfully deleted! ");
		}
	}
	/**
	 * Sends a reply to a specific enquiry. This method updates the enquiry with the reply,
	 * marks it as answered, and updates the related user's mailbox.
	 *
	 * @param e The enquiry to which the reply is being sent.
	 * @param replier The user who is replying to the enquiry.
	 * @return true if the reply is sent successfully.
	 */
	@SuppressWarnings("resource")
	public static  boolean sendReplySuccessfully(Enquiries e,User replier) {
		Scanner s=new Scanner(System.in);
		String r;
		System.out.println("\tPlease enter your reply: ");
		do {
			System.out.print("\t");
			r = s.nextLine();
			if (r.isEmpty()) {
				System.out.println("\tReply cannot be empty. Please enter your reply.");
			}

		} while (r.isEmpty());

		DatabaseSearchManager.SearchEnquiriesDatabase(e).setReply(r);
		DatabaseSearchManager.SearchStudentDatabase(DatabaseSearchManager.SearchEnquiriesDatabase(e).getSender()).setAnsweredwhenAnswered(DatabaseSearchManager.SearchEnquiriesDatabase(e));
		DatabaseSearchManager.SearchEnquiriesDatabase(e).setHelper(replier);
		DatabaseSearchManager.SearchEnquiriesDatabase(e).setStatus(true);
		if(replier instanceof CampCommitteeMember) {
			DatabaseSearchManager.SearchCampComMemDatabase(DatabaseSearchManager.SearchEnquiriesDatabase(e).getHelper()).getPointsHolder().incPt();
		}

		return true;
	}
	/**
	 * Facilitates the process of replying to enquiries. This method guides the user to choose an enquiry
	 * and then call the method to send a reply to it.
	 */
	@SuppressWarnings("resource")
	public static void replyToEnquiries() {
		Scanner s=new Scanner(System.in);
		int ind = -1;
		boolean validIndex = false;

		do {
			try {
				System.out.println("\tPlease enter index number of enquiry that you wish to reply to: ");
				System.out.print("\t");
				ind = s.nextInt();
				s.nextLine(); 
				validIndex = ind >= 0;
				if (!validIndex) {
					System.out.println("\tInvalid index. Please enter a valid index number.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\tInvalid input. Please enter a valid integer.");
				System.out.print("\t");
				s.nextLine(); 
			}
		} while (!validIndex);
		if(DatabaseSearchManager.SearchEnquiriesDatabase(ind)==null)
		{System.out.println("\tNo such enquiry could be found!");

		return;}
		if(EnquiryManager.sendReplySuccessfully(new Enquiries(ind), UserHandler.getCurrentUserInstance())) {
			System.out.println("\tYour reply has been sent successfully");
			if(DataManager.getMailboxDatabase().contains(new Mailbox(DatabaseSearchManager.SearchEnquiriesDatabase(ind).getSender().getUserAccountId()))) {
				DatabaseSearchManager.SearchMailboxDatabase(DatabaseSearchManager.SearchEnquiriesDatabase(ind).getSender().getUserAccountId()).recieveNotifications("Your enquiry with index #"+ind+" has been answered");
			} else {
				ArrayList<String> msgs = new ArrayList<String>();
				msgs.add("Your enquiry with index #"+ind+" has been answered");
				DataManager.getMailboxDatabase().add(new Mailbox(msgs,DatabaseSearchManager.SearchEnquiriesDatabase(ind).getSender().getUserAccountId()));}
		}

	}
}
