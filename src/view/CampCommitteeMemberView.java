package view;

import java.util.ArrayList;
import java.util.Collections;

import controllers.DatabaseSearchManager;
import controllers.SearchFilters;
import controllers.UserHandler;
import models.Camp;
import models.CampCommitteeMember;
import models.DataManager;
import models.Suggestions;
/**
 * This class extends StaffComCommonPrivelegesViewer class and implements 
 * OwnCampsViewer interface to provide view functionalities specific to camp committee members. It includes 
 * methods for viewing committee member details, their own camps, and the suggestions they have submitted.
 */
public class CampCommitteeMemberView extends StaffComCommonPrivelegesViewer implements OwnCampsViewer{
	/**
	 * Displays the committee members of a specific camp.
	 *
	 * @param c The camp whose committee members are to be displayed.
	 */
	@Override
	public void viewCom(Camp c) {
		ArrayList<CampCommitteeMember> temp2=DatabaseSearchManager.SearchCampsDatabase(((CampCommitteeMember)UserHandler.getCurrentUserInstance()).getCamp()).getcampCommittee();

		Collections.sort(temp2);
		InformationViewer.comsHeader(false);
		for(CampCommitteeMember y:temp2) {

			System.out.println(y.getUserAccountId()+"          "+y.getUserName()+"          "+y.getFaculty().toString()+"          "+y.getUserAccountId()+"@e.ntu.edu.sg");
		}
	}
	/**
	 * Displays the camps associated with the current camp committee member.
	 *
	 * @return true if the camps are successfully displayed.
	 */
	@Override
	public boolean viewYourCamps() {
		String[] headers = InformationViewer.campInfoHeader(false, false);
		InformationViewer.printCampofCor(DatabaseSearchManager.SearchCampsDatabase(((CampCommitteeMember)UserHandler.getCurrentUserInstance()).getCamp()), headers,true);
		return true;
	}
	/**
	 * Displays the suggestions submitted by the current camp committee member.
	 */
	@SuppressWarnings("unchecked")
	public void viewSuggSentByYou() {
		try {
			ArrayList<Suggestions> suglist=DataManager.getSuggestionsDatabase();
			suglist=(ArrayList<Suggestions>) SearchFilters.filterMessagesBySender(suglist, UserHandler.getCurrentUserInstance());
			if(suglist.isEmpty()) {
				throw new ListDoesntExistYetException("\tNo suggestions submitted yet!");
			}
			Collections.sort(suglist);
			String[] headers = InformationViewer.suggestionsInfoHeader(true);
			InformationViewer.printSuggestion(suglist,headers,true);}catch(ListDoesntExistYetException e) {
				System.out.println(e.getMessage());
			}

	}
	/**
	 * Displays the suggestions submitted by the current camp committee member,
	 * filtered by their processing state.
	 *
	 * @param state The processing state of the suggestions to be displayed (processed or unprocessed).
	 * @return true if there are suggestions to display, false otherwise.
	 */
	@SuppressWarnings("unchecked")
	public boolean viewSuggSentByYou(boolean state) {
		ArrayList<Suggestions> suglist=DataManager.getSuggestionsDatabase();
		suglist = (ArrayList<Suggestions>) SearchFilters.filterMessagesBySender(suglist,
				UserHandler.getCurrentUserInstance());
		suglist=(ArrayList<Suggestions>) SearchFilters.filterMessagesByStatus(suglist, state);
		if(suglist.isEmpty()) {
			return false;
		}
		Collections.sort(suglist);
		InformationViewer.suggestionsInfoHeader(true) ;
		String[] headers = InformationViewer.suggestionsInfoHeader(true);
		InformationViewer.printSuggestion(suglist,headers,true);
		return true;
	}
}
