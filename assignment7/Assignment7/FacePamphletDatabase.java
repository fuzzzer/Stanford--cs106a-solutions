/*
 * File: FacePamphletDatabase.java
 * -------------------------------
 * This class keeps track of the profiles of all users in the
 * FacePamphlet application.  Note that profile names are case
 * sensitive, so that "ALICE" and "alice" are NOT the same name.
 */


import java.io.*;
import java.util.*;
import acm.graphics.*;
import acm.util.ErrorException;


public class FacePamphletDatabase implements FacePamphletConstants {

	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the database.
	 */
	public FacePamphletDatabase() {
	
	}
	
	
	/** 
	 * This method adds the given profile to the database.  If the 
	 * name associated with the profile is the same as an existing 
	 * name in the database, the existing profile is replaced by 
	 * the new profile passed in.
	 */
	
	public void addProfile(FacePamphletProfile profile) { 
		String name = profile.getName();
		profiles.put(name, profile);
	}

	
	/** 
	 * This method returns the profile associated with the given name 
	 * in the database.  If there is no profile in the database with 
	 * the given name, the method returns null.
	 */
	public FacePamphletProfile getProfile(String name) {
		FacePamphletProfile result = null;
		if(profiles.containsKey(name)) {
			result = profiles.get(name);
		}
		return result;
	}
	
	
	/** 
	 * This method removes the profile associated with the given name
	 * from the database.  It also updates the list of friends of all
	 * other profiles in the database to make sure that this name is
	 * removed from the list of friends of any other profile.
	 * 
	 * If there is no profile in the database with the given name, then
	 * the database is unchanged after calling this method.
	 */
	public void deleteProfile(String name) {
		if(profiles.containsKey(name)) {
			profiles.remove(name);
			removeProfileFromFriendsLists(name);
		}
	}

	
	/** 
	 * This method returns true if there is a profile in the database 
	 * that has the given name.  It returns false otherwise.
	 */
	public boolean containsProfile(String name) {
		if(profiles.containsKey(name)) {
			return true;
		} else {
			return false;
		}
	}
	
	public Iterator<String> iteratorOverProfiles(){
		if(profiles.keySet() != null) {
			return profiles.keySet().iterator();
		} else {
			return null;
		}
		
	}
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------------
	private void removeProfileFromFriendsLists(String name) {
		for(String F : profiles.keySet()) {
			if(checkIfPersonHasFriend(F, name)) {
				FacePamphletProfile profileOfF = profiles.get(F);
				profileOfF.removeFriend(name);
			}
		}
	}
	
	private boolean checkIfPersonHasFriend(String person, String friend) {
		FacePamphletProfile personsProfile = profiles.get(person);
		Iterator<String> friendsIterator =  personsProfile.getFriends();
		while(friendsIterator.hasNext()) {
			String currentFriend = friendsIterator.next();
			if(currentFriend.equals(friend)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * these methods will make sure
	 * that the program will save save social network
	 * and will  make it usable it when the user opens program again
	 */
	
	
	
	
	private HashMap<String, FacePamphletProfile> profiles = new HashMap<String, FacePamphletProfile>();

}
