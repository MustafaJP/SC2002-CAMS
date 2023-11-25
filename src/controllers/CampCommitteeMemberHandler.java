package controllers;

import models.CampCommitteeMember;
import models.DataManager;
import models.Suggestions;

/**
 * Handles operations for CampCommitteeMembers related to the management of suggestions.
 * This class provides methods to submit, delete, and edit suggestions, interfacing with the camp's database
 * and updating the committee members' records and points accordingly.
 */
public class CampCommitteeMemberHandler {

	/**
	 * Submits a suggestion to the camp's database and updates the committee member's suggestion list and points.
	 * 
	 * @param s The suggestion to be submitted.
	 * @param cm The CampCommitteeMember who is submitting the suggestion.
	 * @return true if the operation is successful.
	 */
	public static boolean SuccessfullySubmitSuggestions(Suggestions s, CampCommitteeMember cm) {
		DatabaseSearchManager.SearchCampsDatabase(cm.getCamp()).addSuggestion(new Suggestions(s.getMessageIndex()));
		DataManager.getSuggestionsDatabase().add(s);
		cm.setSuggList(new Suggestions(s.getMessageIndex()));
		cm.getPointsHolder().incPt();
		return true;
	}
	/**
	 * Deletes a suggestion from the camp's database and updates the committee member's suggestion list and points.
	 * 
	 * @param s The suggestion to be deleted.
	 * @param cm The CampCommitteeMember who is deleting the suggestion.
	 * @return true if the operation is successful.
	 */
	public static boolean SuccessfullyDeleteSuggestions(Suggestions s, CampCommitteeMember cm) {
		DatabaseSearchManager.SearchCampsDatabase(cm.getCamp()).remSuggestion(s);
		DataManager.getSuggestionsDatabase().remove(s);
		cm.remSugg(s);
		cm.getPointsHolder().decPt();
		return true;
	}
	/**
	 * Edits an existing suggestion in the camp's database by replacing it with a new suggestion, and updates 
	 * the committee member's suggestion list and points accordingly.
	 * 
	 * @param olds The old suggestion to be replaced.
	 * @param news The new suggestion to replace the old one.
	 * @param cm The CampCommitteeMember who is editing the suggestion.
	 * @return true if the operation is successful.
	 */
	public static boolean SuccessfullyEditSuggestions(Suggestions olds, Suggestions news, CampCommitteeMember cm) {
		DatabaseSearchManager.SearchCampsDatabase(cm.getCamp()).remSuggestion(olds);
		DatabaseSearchManager.SearchCampsDatabase(cm.getCamp()).addSuggestion(new Suggestions(news.getMessageIndex()));
		cm.remSugg(olds);
		cm.setSuggList(news);
		DataManager.getSuggestionsDatabase().remove(olds);
		DataManager.getSuggestionsDatabase().add(news);
		return true;
	}

}
