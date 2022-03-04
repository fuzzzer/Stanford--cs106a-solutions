
import java.io.*;
import java.util.*;
import acm.graphics.*;
import acm.util.ErrorException;




public class FacePamphletSaveNetwork implements FacePamphletConstants {

	public FacePamphletSaveNetwork() {
		
	}
	
	
	public void saveProfilesToAdd(String name) {
		nameBase.add(name);
	}
	
	
	public void saveSocialNetwork() {
		try {
			PrintWriter wr = new PrintWriter(new FileWriter("SocialNetwork.txt", false));
			if(nameBase.size() > 0) {
				for(String name : nameBase) {
					profile = new FacePamphletProfile(name);
					GImage currentProfileImage = profile.getImage();
					if(currentProfileImage != null) { 
						wr.println(imageNames.get(name) + "|" + profile.toString());
					} else {
						wr.println("NO" + "|" + profile.toString());
					}
				}
			}
				
			wr.close();
		} catch (IOException ex) {
			throw new ErrorException("Unknown Error");
		}
	}
	
	public void openSocialNetwork() {
		try {
			BufferedReader rd = new BufferedReader(new FileReader("SocialNetwork.txt"));
			String line = rd.readLine();
			while(line != null) {
				String readPictureLocation = line.substring(0, line.indexOf("|"));
				String readName = line.substring(line.indexOf("|") + 1, line.indexOf("(") - 1);
				String readStatus = "";
				if(line.indexOf("(") + 1 != line.indexOf(")")) {
					readStatus = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
				}
				String friendsList = line.substring(line.indexOf(")") + 2);
				
				addProfile(readName);
				addPictureToProfile(readPictureLocation);
				addStatus(readStatus);
				addFriends(friendsList);
				profilesToAdd.put(readName, creatingProfile);
				
				System.out.println("added: " + line);
				line = rd.readLine();
				
			}
			rd.close();
		} catch (IOException ex) {
			//throw new ErrorException("Unknown Error");
			System.out.println("something wrong buddy");
		}
	}
	
	public Iterator<FacePamphletProfile> iteratorOverSavedProfiles() {
		return profilesToAdd.values().iterator();
	}
	
	public void addImageLocationToName(String name, String imageLocation) {
		imageNames.put(name, imageLocation);
	}
	
	public void delateImageLocation(String name) {
		imageNames.remove(name);
	}
	
	private void addProfile(String name) {
		creatingProfile = new FacePamphletProfile(name);
	}
	
	private void addPictureToProfile(String pictureName) {
		GImage image = null;
		if(!pictureName.equals("NO")) {
			try {
				image = new GImage(pictureName);
				imageNames.put(creatingProfile.getName(), pictureName);
			} catch (ErrorException ex){
				//picture not found
			}
		
		}
		
		if(image != null) {
			creatingProfile.setImage(image);
		}
	}
	
	private void addStatus(String status) {
		creatingProfile.setStatus(status);
	}
	
	private void addFriends(String friendsList) {
		StringTokenizer tokenizer = new StringTokenizer(friendsList, ",");
		while(tokenizer.hasMoreTokens()) {
			String friendsName = tokenizer.nextToken();
			friendsName = friendsName.trim();
			creatingProfile.addFriend(friendsName);
		}
	}
	
	private HashMap<String, String> imageNames = new HashMap<String, String>();
	private FacePamphletProfile creatingProfile;
	private HashMap<String, FacePamphletProfile> profilesToAdd = new HashMap<String, FacePamphletProfile>();
	Set<String> nameBase = new LinkedHashSet<>();
	FacePamphletDatabase dataBase;
	FacePamphletProfile profile;
	
}
