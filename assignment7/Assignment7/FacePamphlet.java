/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import java.util.Iterator;
import acm.io.*;

import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		dataBase = new FacePamphletDatabase();
		networkSaver = new FacePamphletSaveNetwork();
		addSocialNetwork();
		
		canvas = new FacePamphletCanvas(); 
		add(canvas);

		addInteractorsWest();
		addInteractorsNorth();
		addListeners();
    }
    
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		String statusText = statusToAdd.getText();
		String pictureText = pictureToAdd.getText();
		String friendText = friendToAdd.getText();
		String name = profileEntry.getText();
		
		
		if(!statusText.equals("") && (cmd.equals("Change Status") || e.getSource() == statusToAdd) ) {// this if statement is true if user wants to change status
			changeStatus(statusText);
		}
		
		if(!pictureText.equals("") && (cmd.equals("Change Picture") || e.getSource() == pictureToAdd) ) {// this if statement is true if user wants to change picture
			changePicture(pictureText);
		}
		
		if(!friendText.equals("") && (cmd.equals("Add Friend") || e.getSource() == friendToAdd) ) {// this if statement is true if user wants to add friend
			addFriend(friendText);
		}
		
		if(!profileEntry.equals("") && cmd.equals("Add")) {// this if statement is true if user wants to add profile
			addProfile(name);
		}
		
		if(!profileEntry.equals("") && cmd.equals("Delete")) {// this if statement is true if user wants to delete profile
			deleteProfile(name);
		}
		
		if(!profileEntry.equals("") && cmd.equals("Lookup")) {// this if statement is true if user wants to delete profile
			lookUpProfile(name);
		}
		
		if(currentProfile != null) {
			canvas.displayProfile(currentProfile);
			canvas.showMessage(msg);
			networkSaver.saveProfilesToAdd(currentProfile.getName());
			networkSaver.saveSocialNetwork();//this method will update social network file
			
		} else {
			canvas.removeAll();
			canvas.showMessage(msg);
		}
		
	    
	}
    
    private void addProfile(String name) {
    	
    	if(!dataBase.containsProfile(name)) {
    		currentProfile = new FacePamphletProfile(name);
			dataBase.addProfile(currentProfile);
			msg = "New profile created";
		} else {
			currentProfile = dataBase.getProfile(name);
			msg = "A profile with name " + currentProfile.getName() + " Already exists";
		}
    	
    }
    
    private void deleteProfile(String name) {
    	currentProfile = null;
    	
    	if(dataBase.containsProfile(name)) {
			dataBase.deleteProfile(name);
			msg = "A profile of " + name + " Deleted";
			
			networkSaver.delateImageLocation(name);// this method deletes saved location of the picture if profile was deleted
		} else {
			msg = "A profile with the name " + name + " doesn't exists";
		}
    }
    
    private void lookUpProfile(String name) {
    	
    	if(dataBase.containsProfile(name)) {
    		currentProfile = dataBase.getProfile(name);
    		msg = "Displaying " + name;
		} else {
			currentProfile = null;
			msg = "A profile with the name " + name + " doesn't exists";
		}
    }
    
    private void changeStatus(String statusText) {
    	
    	if(currentProfile != null) {
    		currentProfile.setStatus(statusText);
    		msg = "Status updated to " + statusText;
    	} else {
    		msg = "Please select a profile to change status";
    	}
    }
    
    private void changePicture(String pictureText) {
    	if(currentProfile != null) {
    		GImage image = null;
    		
    		try {
    			image = new GImage(pictureText);
    		} catch (ErrorException ex){
    			msg = "Unable to open image file: " + pictureText;
    		}
    		
    		if(image != null) {
    			currentProfile.setImage(image);
    			msg = "Picture updated";
    			
    			networkSaver.addImageLocationToName(currentProfile.getName(), pictureText);// this method saves location of picture if picture was added
    		}
    		
    	} else {
    		msg = "Please select a profile to change picture";
    	}
    }
    
    private void addFriend(String friendText) {
    	
    	if(currentProfile != null) {
    		if(dataBase.containsProfile(friendText)) {
    			connectWithCurrentProfile(friendText);
    			
    		} else {
    			msg = friendText + " does not exist";
    		}
    	} else {
    		msg = "Please select a profile to add friend";
    	}
    }
    
    private void connectWithCurrentProfile(String friendText) {
    	if(theyAreFriends(friendText)) {
    		msg = currentProfile.getName() + " already has " + friendText + " as a friend";
		} else {
			currentProfile.addFriend(friendText);
			
			FacePamphletProfile friendsProfile = dataBase.getProfile(friendText);
			friendsProfile.addFriend(currentProfile.getName());
			msg = friendText + " added as a friend";
		}
    }
    
    private boolean theyAreFriends(String friendText) {
    	Iterator<String> iterator = currentProfile.getFriends();
    	
    	while(iterator.hasNext()) {
    		String current = iterator.next();
    		if(current.equals(friendText)) return true;
    	}
    	
    	return false;
    }

    
    
    private void addInteractorsWest() {
    	
    	statusToAdd = new JTextField(TEXT_FIELD_SIZE);
    	add(statusToAdd, WEST);
		
    	JButton changeStatus = new JButton("Change Status");
    	add(changeStatus, WEST);
    	
    	JLabel blank1 = new JLabel(EMPTY_LABEL_TEXT);
    	add(blank1, WEST);
    	
		pictureToAdd = new JTextField(TEXT_FIELD_SIZE);
		add(pictureToAdd, WEST);
		
		JButton changePicture = new JButton("Change Picture");
		add(changePicture, WEST);
		
    	JLabel blank2 = new JLabel(EMPTY_LABEL_TEXT);
    	add(blank2, WEST);
		
		friendToAdd = new JTextField(TEXT_FIELD_SIZE);
		add(friendToAdd, WEST);
		
		JButton addFriend = new JButton("Add Friend");
		add(addFriend, WEST);
		
    }
    
    private void addInteractorsNorth() {
    	
    	JLabel nameLabel = new JLabel("Name");
    	add(nameLabel, NORTH);
    	
    	profileEntry = new JTextField(TEXT_FIELD_SIZE);
		add(profileEntry, NORTH);
		
		JButton addButton = new JButton("Add");
		add(addButton, NORTH);
		
		JButton deleteButton = new JButton("Delete");
		add(deleteButton, NORTH);
		
		JButton lookupButton = new JButton("Lookup");
		add(lookupButton, NORTH);
		
    }
    
    private void addSocialNetwork() {
    	networkSaver.openSocialNetwork();// this method will reopen old social network
    	Iterator<FacePamphletProfile> it = networkSaver.iteratorOverSavedProfiles();
		while(it.hasNext()) {
			FacePamphletProfile profileToAdd = it.next();
			dataBase.addProfile(profileToAdd);
		}
    }
    
    private void addListeners() {
    	addActionListeners();
    	statusToAdd.addActionListener(this);
    	pictureToAdd.addActionListener(this);
    	friendToAdd.addActionListener(this);
    }
    
    //instance variables
    private JTextField statusToAdd;
    private JTextField pictureToAdd;
    private JTextField friendToAdd;
    private JTextField profileEntry;
    
    private FacePamphletDatabase dataBase;
    private FacePamphletProfile currentProfile;
    private FacePamphletCanvas canvas;
    private FacePamphletSaveNetwork networkSaver;
    
    private String msg = "";

}
