/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		this.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		if(messageLabel != null) remove(messageLabel);
		
		messageLabel = new GLabel (msg);
		messageLabel.setFont(MESSAGE_FONT);
		add(messageLabel, (getWidth() - messageLabel.getWidth()) / 2, getHeight() - BOTTOM_MESSAGE_MARGIN);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		addNameOnCanvas(profile);
		addPhotoOnCanvas(profile);
		addStatusOnCanvas(profile);
		addFriendsListOnCanvas(profile);
	}
	
	private void addNameOnCanvas(FacePamphletProfile profile) {
		String name = profile.getName();
		
		nameLabel = new GLabel(name);
		nameLabel.setColor(Color.BLUE);
		nameLabel.setFont(PROFILE_NAME_FONT);
		add(nameLabel, LEFT_MARGIN, TOP_MARGIN + nameLabel.getAscent());
	}
	
	private void addPhotoOnCanvas(FacePamphletProfile profile) {
		GImage img = profile.getImage();
		Double pictureLeftX = LEFT_MARGIN;
		Double pictureBottomY = TOP_MARGIN + nameLabel.getAscent() + IMAGE_MARGIN + IMAGE_HEIGHT;
		Double pictureRightX = LEFT_MARGIN + IMAGE_WIDTH;
		Double pictureUpperY = TOP_MARGIN + nameLabel.getAscent() + IMAGE_MARGIN;
		
		GRect pictureFrame = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
		add(pictureFrame, pictureLeftX, pictureUpperY);
		
		if(img == null) {
			GLabel noPictureLabel = new GLabel("No Image");
			noPictureLabel.setFont(PROFILE_IMAGE_FONT);
			add(noPictureLabel, pictureLeftX + (pictureRightX - pictureLeftX - noPictureLabel.getWidth()) / 2, pictureUpperY + (pictureBottomY - pictureUpperY + noPictureLabel.getAscent()) / 2);
		} else {
			Double xScale = IMAGE_WIDTH / img.getWidth();
			Double yScale = IMAGE_HEIGHT / img.getHeight();
			img.scale(xScale, yScale);
			add(img, pictureLeftX, pictureUpperY);
		}
	}
	
	private void addStatusOnCanvas(FacePamphletProfile profile) {
		String status = profile.getStatus();
		
		if(status.equals("")) {
			status = "No Current Status";
		} else {
			status = profile.getName() + " is "  + status;
		}
		
		GLabel statusLabel = new GLabel(status);
		statusLabel.setFont(PROFILE_STATUS_FONT);
		add(statusLabel, LEFT_MARGIN, TOP_MARGIN + nameLabel.getAscent() + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN + statusLabel.getAscent());
			
	}
	
	private void addFriendsListOnCanvas(FacePamphletProfile profile) {
		Iterator<String> it = profile.getFriends();
		Double firstLabelY = TOP_MARGIN + nameLabel.getAscent() + IMAGE_MARGIN;
		int friendsCounter = 0;
		
		GLabel label = new GLabel("Friends:");
		label.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(label, getWidth() / 2, firstLabelY);
		
		while(it.hasNext()) {
			String friendsName = it.next();
			GLabel friendsNameLabel = new GLabel(friendsName);
			friendsNameLabel.setFont(PROFILE_FRIEND_FONT );
			add(friendsNameLabel, getWidth() / 2, firstLabelY + (friendsCounter + 1) * friendsNameLabel.getHeight());
			friendsCounter++;
		}
		
	}

	//instance variables
	GLabel messageLabel;
	GLabel nameLabel;
}
