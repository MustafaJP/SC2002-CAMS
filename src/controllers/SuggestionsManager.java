package controllers;

import java.util.Scanner;

import models.CampCommitteeMember;
import models.Messages;
import models.Suggestions;
import models.User;
/**
 * This class implements the MessageManager interface to manage suggestions.
 * It provides functionalities for submitting, editing, and deleting suggestions made by 
 * camp committee members.
 */
public class SuggestionsManager implements MessageManager {
	/**
	 * Submits a new suggestion made by a user. The suggestion details are entered by the user.
	 * @param usr The user (CampCommitteeMember) who is submitting the suggestion.
	 */
	@SuppressWarnings("resource")
	@Override
	public void submit(User usr) {

		System.out.println("\tPlease enter your suggestion: ");
		Scanner s = new Scanner(System.in);
		String sgs;
		do {System.out.print("\t");
		sgs = s.nextLine();
		if (sgs.trim().isEmpty()) {
			System.out.println("\tSuggestion cannot be empty. Please enter your suggestion.");
		}

		} while (sgs.trim().isEmpty());
		CampCommitteeMember cmem = (CampCommitteeMember) usr;
		Suggestions newsug = new Suggestions(sgs, cmem);
		if (CampCommitteeMemberHandler.SuccessfullySubmitSuggestions(newsug, cmem)) {

			System.out.println("\tYour suggestion has been successfully submitted! Your unique suggestion ID is #"
					+ newsug.getMessageIndex()
					+ ". It is currently being processed- you will be notified when the staff-in-charge processes it!");

		}

	}
	/**
	 * Edits an existing suggestion. The new suggestion details are entered by the user. This method checks if the suggestion is eligible for editing before proceeding.
	 *
	 * @param oldmsg The original suggestion to be edited.
	 * @param usr    The user (CampCommitteeMember) who is editing the suggestion.
	 */
	@SuppressWarnings("resource")
	@Override
	public void edit(Messages oldmsg, User usr) {

		Suggestions oldsug = (Suggestions) oldmsg;
		CampCommitteeMember cmem = (CampCommitteeMember) usr;
		if (!cmem.getSuggList().containsKey(oldsug)) {

			System.out.println("\tYou have not submitted any such suggestion yet!");
			return;
		}
		if (cmem.getSuggList().get(oldsug)) {
			System.out.println("\tThis suggestion has already been processed! You can no longer edit it! ");
			return;
		}
		Scanner s = new Scanner(System.in);
		System.out.println("\tPlease enter your new suggestion: ");
		String newsgs;
		do {System.out.print("\t");
		newsgs = s.nextLine();
		if (newsgs.trim().isEmpty()) {
			System.out.println("\tSuggestion cannot be empty. Please enter your new suggestion");
		}
		} while (newsgs.trim().isEmpty());
		Suggestions newsug = new Suggestions(newsgs, oldsug.getMessageIndex(), cmem);

		if (CampCommitteeMemberHandler.SuccessfullyEditSuggestions(oldsug, newsug, cmem)) {
			System.out.println(
					"\tYour suggestion with ID #" + newsug.getMessageIndex() + " has been successfully edited! ");
			System.out.println("\tYour new suggestion is as follows: ");
			System.out.println("\t"+newsug.getS());
		}

	}
	/**
	 * Deletes an existing suggestion. This method checks if the suggestion is eligible for deletion
	 * before proceeding.
	 *
	 * @param oldmsg The suggestion to be deleted.
	 * @param usr    The user (CampCommitteeMember) who is deleting the suggestion.
	 */
	@Override
	public void delete(Messages oldmsg, User usr) {

		Suggestions oldsug = (Suggestions) oldmsg;
		CampCommitteeMember cmem = (CampCommitteeMember) usr;
		if (!cmem.getSuggList().containsKey(oldsug)) {
			System.out.println("\tYou have not submitted any such suggestion yet!");
			return;
		}
		if (cmem.getSuggList().get(oldsug)) {
			System.out.println("\tThis suggestion has already been processed! You can no longer delete it! ");
			return;
		}
		if (CampCommitteeMemberHandler.SuccessfullyDeleteSuggestions(oldsug, cmem)) {
			System.out.println(
					"\tYour suggestion with ID #" + oldsug.getMessageIndex() + " has been successfully deleted! ");
		}
	}

}
